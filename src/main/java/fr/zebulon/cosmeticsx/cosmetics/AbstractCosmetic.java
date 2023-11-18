package fr.zebulon.cosmeticsx.cosmetics;

import fr.zebulon.cosmeticsx.cosmetics.factory.ICosmeticFactory;
import fr.zebulon.cosmeticsx.cosmetics.factory.permission.IPermission;
import fr.zebulon.cosmeticsx.utils.EnumUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractCosmetic implements ICosmeticFactory {

    private final String id;
    private final CosmeticCategory category;
    private UUID owner;

    protected AbstractCosmetic(String id, CosmeticCategory category) {
        this.id = id;
        this.category = category;
    }

    @Override
    public Optional<AbstractCosmetic> fromCategoryId(String categoryId, String cosmeticId) {
        // Define the variables as uppercase to avoid case sensitivity
        final String finalCategoryId = categoryId.toUpperCase();
        final String finalCosmeticId = cosmeticId.toUpperCase();

        // Return the cosmetic if the category is valid and the cosmetic exists
        return EnumUtil
                .valueOfSafe(CosmeticCategory.class, finalCategoryId)
                .flatMap(category -> category.getCosmetic(finalCosmeticId));
    }

    @Override
    public Optional<AbstractCosmetic> fromQualifiedId(String query) {
        // Split the query into chunks
        final String[] parts = query.split(":");

        // Return an empty optional if the query is invalid
        if (parts.length <= 2) return Optional.empty();

        // Define the variables with the chunks values
        final String categoryId = parts[0];
        final String cosmeticId = parts[1];

        // Return the cosmetic if the category is valid and the cosmetic exists
        return fromCategoryId(categoryId, cosmeticId);
    }

    public String getId() {
        return id;
    }

    public String getQualifiedId() {
        return getCategory().name() + ":" + getId();
    }

    public CosmeticCategory getCategory() {
        return category;
    }

    public Optional<UUID> getOwner() {
        return Optional.ofNullable(owner);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public IPermission getPermission() {
        return IPermission.hasPurchased(category, id);
    }

    public IPermission getVisibilityPermission() {
        return IPermission.collectionIsActive(getCollection());
    }

    public abstract Component getName();

    public abstract ItemStack getMenuIcon();

    public abstract CosmeticCollection getCollection();
}
