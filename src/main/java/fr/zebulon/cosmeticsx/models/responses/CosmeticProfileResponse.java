package fr.zebulon.cosmeticsx.models.responses;

import fr.zebulon.cosmeticsx.cosmetics.AbstractCosmetic;
import fr.zebulon.cosmeticsx.models.CosmeticData;
import fr.zebulon.cosmeticsx.models.CosmeticMeta;
import fr.zebulon.cosmeticsx.models.CosmeticProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CosmeticProfileResponse {

    private Map<String, String> equipped;
    private CosmeticProfile profile;
    private String rank;
    private final String hashedPack;

    public CosmeticProfileResponse() {
        this.equipped = new HashMap<>();
        this.profile = new CosmeticProfile();
        this.rank = "DEFAULT";
        this.hashedPack = "";
    }

    public CosmeticProfileResponse(Map<String, String> equipped, CosmeticProfile profile, String rank, String hashedPack) {
        this.equipped = equipped;
        this.profile = profile;
        this.rank = rank;
        this.hashedPack = hashedPack;
    }

    /**
     * Gets quantity of cosmetic in a category by id
     *
     * @param category the category
     * @param id       the id
     * @return the quantity
     */
    public int getQuantity(String category, String id) {
        return Optional.ofNullable(profile.getPurchased().get(category))
                .map(_id -> _id.get(id))
                .map(CosmeticData::getMeta)
                .map(CosmeticMeta::getQuantity)
                .orElse(0);
    }

    /**
     * Gets quantity of cosmetic in a category by id.
     *
     * @param cosmetic the cosmetic
     * @return the quantity
     */
    public int getQuantity(AbstractCosmetic cosmetic) {
        return getQuantity(cosmetic.getCategory().name(), cosmetic.getId());
    }

    public Map<String, String> getEquipped() {
        return equipped;
    }

    public void setEquipped(Map<String, String> equipped) {
        this.equipped = equipped;
    }

    public CosmeticProfile getProfile() {
        return profile;
    }

    public void setProfile(CosmeticProfile profile) {
        this.profile = profile;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getHashedPack() {
        return hashedPack;
    }
}
