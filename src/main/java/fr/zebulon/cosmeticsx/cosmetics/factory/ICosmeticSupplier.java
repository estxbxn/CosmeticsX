package fr.zebulon.cosmeticsx.cosmetics.factory;

import fr.zebulon.cosmeticsx.cosmetics.AbstractCosmetic;

public interface ICosmeticSupplier<T extends AbstractCosmetic> {

    /**
     * Get the Cosmetic using supplier
     *
     * @return the t {@link AbstractCosmetic}
     */
    T get();
}
