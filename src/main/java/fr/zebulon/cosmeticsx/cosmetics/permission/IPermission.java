package fr.zebulon.cosmeticsx.cosmetics.permission;

import fr.zebulon.cosmeticsx.CosmeticsX;
import fr.zebulon.cosmeticsx.cosmetics.AbstractCosmetic;
import fr.zebulon.cosmeticsx.cosmetics.CosmeticCategory;
import fr.zebulon.cosmeticsx.cosmetics.CosmeticCollection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IPermission {

    /**
     * Checks whether the given player has access to this permission
     *
     * @param player to check for
     * @return a future containing whether the player has access or not.
     */
    CompletableFuture<Boolean> hasAccess(Player player);

    /**
     * An empty permission that always contains the result "true"
     *
     * @return a permission that is always true.
     */
    static IPermission none() {
        return player -> CompletableFuture.completedFuture(true);
    }

    /**
     * Create a permission that is always false
     *
     * @return permission that's always false
     */
    static IPermission deny() {
        return player -> CompletableFuture.completedFuture(false);
    }

    static IPermission uuid(UUID uuid) {
        return player -> CompletableFuture.completedFuture(uuid.equals(player.getUniqueId()));
    }

    static IPermission collectionIsActive(CosmeticCollection collection) {
        return player -> CompletableFuture.completedFuture(CosmeticCollection.isActive(collection));
    }

    /**
     * A permission that checks if a given player has a specified rank
     *
     * @param name of the rank to check the player for
     * @return a permission that checks for this rank
     */
    static IPermission hasRank(String name) {
        return player -> CosmeticsX.get()
                .getCosmeticsManager()
                .getProfile(player.getUniqueId())
                .thenApplyAsync(maybeProfile -> maybeProfile
                        .flatMap((res) -> PlayerRank.getBackingRank(res.getRank())) // get rank for this player
                        .map((res) -> res.hasAccess(name)) // check if rank has access
                        .orElse(false) // default to false if a call fails
                );
    }

    /**
     * Check if the given player is staff
     *
     * @return a permission that validates whether player are staff
     */
    static IPermission staff() {
        return player -> hasRank("MOD").hasAccess(player);
    }

    /**
     * Check if a given player has purchased the given item.
     *
     * @param category of the item to check purchases for
     * @param id       of the purchased item.
     * @return a permission for whether a player has purchased the given item.
     */
    static IPermission hasPurchased(final String category, final String id) {
        return player -> CosmeticsX.get()
                .getCosmeticsManager()
                .getProfile(player.getUniqueId())
                .thenApplyAsync((maybeResponse) ->
                        maybeResponse.map(CosmeticProfileResponse::getProfile)
                                .map(CosmeticProfile::getPurchased)
                                .map(purchased -> purchased.get(category))
                                .map(cosmeticCategory -> cosmeticCategory.get(id))
                                .map(CosmeticData::getMeta)
                                .map(meta -> meta.getQuantity() > 0)
                                .orElse(false)
                );
    }

    /**
     * Check if a given player has purchased the given item.
     *
     * @param category of the item to check purchases for
     * @param id       of the purchased item.
     * @return a permission for whether a player has purhcased the given item.
     */
    static IPermission hasPurchased(final CosmeticCategory category, final String id) {
        return hasPurchased(category.name(), id);
    }

    static IPermission hasPurchased(final AbstractCosmetic cosmetic) {
        return hasPurchased(cosmetic.getCategory(), cosmetic.getId());
    }

    /**
     * Compound permission for checking if *all* passed {@link IPermission} are true.
     *
     * @param permissions to check
     * @return whether all passed {@link IPermission} return true.
     */
    static IPermission all(List<IPermission> permissions) {
        return player -> CompletableFuture.supplyAsync(() -> {
            // convert to a list of futures
            final List<CompletableFuture<Boolean>> futures = new ArrayList<>();
            for (final IPermission requirement : permissions) {
                futures.add(requirement.hasAccess(player));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();

            // if any of the futures returns false then we want to return false.
            for (final CompletableFuture<Boolean> resultFuture : futures) {
                final boolean result = resultFuture.join();
                if (!result) return false;
            }
            return true;
        });
    }

    static IPermission all(IPermission... permissions) {
        return all(List.of(permissions));
    }

    /**
     * Compound permission where if any {@link IPermission} result returns true, we return true.
     *
     * @param permissions to check
     * @return whether any {@link  IPermission} passed in was true
     */
    static IPermission any(List<IPermission> permissions) {
        return player -> CompletableFuture.supplyAsync(() -> {

            final List<CompletableFuture<Boolean>> futures = getResultFutures(player, permissions);
            CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();

            // if any of the futures return true, we want to return true
            for (final CompletableFuture<Boolean> resultFuture : futures) {
                if (resultFuture.join()) return true;
            }
            return false;
        });
    }

    static IPermission any(IPermission... permissions) {
        return any(List.of(permissions));
    }

    /**
     * Convert a list of permissions to a list of future permission results for a given player.
     *
     * @param player      to create permission futures for
     * @param permissions to generate futures for
     * @return a list of the futures relevant to this player
     */
    private static List<CompletableFuture<Boolean>> getResultFutures(Player player, List<IPermission> permissions) {
        // convert to a list of futures
        final List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        for (final IPermission requirement : permissions) {
            futures.add(requirement.hasAccess(player));
        }
        return futures;
    }
}
