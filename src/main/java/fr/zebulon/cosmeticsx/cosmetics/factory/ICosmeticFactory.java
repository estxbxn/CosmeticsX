package fr.zebulon.cosmeticsx.cosmetics.factory;

import fr.zebulon.cosmeticsx.cosmetics.AbstractCosmetic;

import java.util.Optional;

public interface ICosmeticFactory {

    /**
     * Get a cosmetic using a slot string and id string,
     * this is primarily useful for grabbing cosmetic values in game
     * from the "equipped" field of the cosmetic profile
     *
     * @param categoryId of the cosmetic
     * @param cosmeticId of the cosmetic
     * @return a possible cosmetic for the given slot and id
     */
    Optional<AbstractCosmetic> fromCategoryId(String categoryId, String cosmeticId);

    /**
     * Grab a cosmetic given a "qualified id",
     * qualified ids are in the format CATEGORY:COSMETIC
     *
     * @param query the query
     * @return the optional
     */
    Optional<AbstractCosmetic> fromQualifiedId(String query);
}
