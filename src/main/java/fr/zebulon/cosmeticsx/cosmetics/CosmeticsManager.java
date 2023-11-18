package fr.zebulon.cosmeticsx.cosmetics;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.zebulon.cosmeticsx.CosmeticsX;
import fr.zebulon.cosmeticsx.cosmetics.events.CosmeticEquipEvent;
import fr.zebulon.cosmeticsx.cosmetics.factory.properties.IEquippable;
import fr.zebulon.cosmeticsx.cosmetics.factory.properties.ISlotHandler;
import fr.zebulon.cosmeticsx.cosmetics.factory.properties.ITickable;
import fr.zebulon.cosmeticsx.models.requests.CosmeticEquipRequest;
import fr.zebulon.cosmeticsx.models.responses.CosmeticProfileResponse;
import fr.zebulon.cosmeticsx.models.responses.SimpleResponse;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CosmeticsManager {

    // Cache for retrieving player cosmetic profiles
    private final Cache<UUID, CosmeticProfileResponse> cache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();

    // Cache for active cosmetics
    private final Map<UUID, Map<CosmeticSlot, AbstractCosmetic>> cosmeticsCache = new HashMap<>();

    private final CosmeticsX plugin;

    public CosmeticsManager(CosmeticsX plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            cosmeticsCache.values().forEach(userCosmetics -> userCosmetics.values().forEach(cosmetic -> {
                if (!(cosmetic instanceof ITickable tickable)) return;
                tickable.tick();
            }));
        }, 0, 1);
    }

    public CompletableFuture<Optional<CosmeticProfileResponse>> getProfile(UUID uuid) {
        var cached = cache.getIfPresent(uuid);
        if (cached != null) return CompletableFuture.completedFuture(Optional.of(cached));

        return CompletableFuture.supplyAsync(() -> {
            final HttpResponse<CosmeticProfileResponse> res = plugin.getRestApi().getProfile(uuid).join();
            final CosmeticProfileResponse profile = res.getBody();
            if (profile != null) this.cache.put(uuid, profile);

            return Optional.ofNullable(profile);
        });
    }

    public void setCosmetic(UUID uuid, CosmeticSlot slot, AbstractCosmetic cosmetic, boolean updateMeta) {
        // Remove the old cosmetic from the cache
        removeCosmetic(uuid, slot, updateMeta);

        // Equip the item
        cosmetic.setOwner(uuid);

        // If we need to handle slots, set it immediately
        if (cosmetic instanceof ISlotHandler slotHandler) slotHandler.setSlot(slot);

        // If the item is equippable, equip it
        if (cosmetic instanceof IEquippable equippable) equippable.equip();

        // Put the item into the equipped map
        getEquippedMap(uuid).put(slot, cosmetic);

        // If we need to update the meta of cosmetic, set it immediately
        if (updateMeta) {
            sendEquipmentUpdate(uuid, slot, cosmetic.getQualifiedId());
            Bukkit.getServer().getPluginManager().callEvent(new CosmeticEquipEvent(uuid, slot, cosmetic));
        }
    }

    public void removeCosmetic(UUID uuid, CosmeticSlot slot, boolean updateMeta) {
        // Remove the cosmetic from the cache
        getEquippedCosmetic(uuid, slot).ifPresent(cosmetic -> {
            if (cosmetic instanceof IEquippable equippable) equippable.unequip();
        });

        // Remove the cosmetic from that players map
        getEquippedMap(uuid).remove(slot);

        // Update the meta if necessary
        if (updateMeta) {
            sendEquipmentUpdate(uuid, slot, "EMPTY");
            Bukkit.getServer().getPluginManager().callEvent(new CosmeticEquipEvent(uuid, slot, null));
        }
    }

    private void sendEquipmentUpdate(UUID uuid, CosmeticSlot slot, String id) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final HttpResponse<SimpleResponse> res = plugin.getRestApi().equipCosmetic(new CosmeticEquipRequest(uuid, slot.name(), id)).join();
            if (res == null) return;

            final Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;

            switch (res.getStatus()) {
                case 200 -> player.sendMessage(Component.text("Equipped cosmetic"));
                case 429 -> player.sendMessage(Component.text("Please wait a moment and try again.."));
                default ->
                        player.sendMessage(Component.text("An unknown error occurred while trying to equip your cosmetic.."));
            }
        });
    }

    public void handleConnect() {

    }

    public void handleDisconnect() {

    }

    public Map<CosmeticSlot, AbstractCosmetic> getEquippedMap(UUID uuid) {
        return cosmeticsCache.computeIfAbsent(uuid, (v) -> new HashMap<>());
    }

    public Optional<AbstractCosmetic> getEquippedCosmetic(UUID uuid, CosmeticSlot slot) {
        return Optional.ofNullable(getEquippedMap(uuid).get(slot));
    }
}
