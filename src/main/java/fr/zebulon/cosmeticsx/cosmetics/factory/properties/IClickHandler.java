package fr.zebulon.cosmeticsx.cosmetics.factory.properties;

import fr.zebulon.cosmeticsx.cosmetics.CosmeticSlot;
import org.bukkit.event.inventory.ClickType;

@FunctionalInterface
public interface IClickHandler {

    /**
     * Apply cosmetic to a specific slot
     *
     * @param clickType the click type
     * @return the cosmetic slot
     */
    CosmeticSlot apply(ClickType clickType);

    /**
     * Slot click handler
     *
     * @param slot the slot
     * @return the click handler
     */
    static IClickHandler slot(CosmeticSlot slot) {
        return clickType -> slot;
    }

    /**
     * The constant HANDED.
     */
    IClickHandler HANDED = clickType -> {
        switch (clickType) {
            case RIGHT, SHIFT_RIGHT -> {
                return CosmeticSlot.OFF_HAND;
            }
            default -> {
                return CosmeticSlot.MAIN_HAND;
            }
        }
    };
}
