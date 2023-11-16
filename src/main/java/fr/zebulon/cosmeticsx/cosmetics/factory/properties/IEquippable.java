package fr.zebulon.cosmeticsx.cosmetics.factory.properties;

public interface IEquippable {

    /**
     * Equip the cosmetic
     * This is work only if the cosmetic is equippable
     */
    void equip();

    /**
     * Unequip the cosmetic
     * This is work only if the cosmetic is equippable
     */
    void unequip();
}
