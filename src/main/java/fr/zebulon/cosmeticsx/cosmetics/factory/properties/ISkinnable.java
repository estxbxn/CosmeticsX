package fr.zebulon.cosmeticsx.cosmetics.factory.properties;

import fr.zebulon.cosmeticsx.data.Key;
import org.bukkit.inventory.ItemStack;

public interface ISkinnable {

    /**
     * Apply skin to a cosmetic item
     *
     * @param item the item
     */
    void applySkin(ItemStack item);

    /**
     * Remove skin to a cosmetic item
     *
     * @param item the item
     */
    default void removeSkin(ItemStack item) {
        // Remove the custom model data and the skin key
        item.editMeta(meta -> {
            meta.setCustomModelData(0);
            Key.SKINNED.remove(meta);
        });
    }
}
