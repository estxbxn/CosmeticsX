package fr.zebulon.cosmeticsx.models;

import java.util.HashMap;
import java.util.Map;

public class CosmeticProfile {

    private Map<String, Map<String, CosmeticData>> purchased;

    public CosmeticProfile() {
        this.purchased = new HashMap<>();
    }

    public CosmeticProfile(Map<String, Map<String, CosmeticData>> purchased) {
        this.purchased = purchased;
    }

    public Map<String, Map<String, CosmeticData>> getPurchased() {
        return purchased;
    }

    public void setPurchased(Map<String, Map<String, CosmeticData>> purchased) {
        this.purchased = purchased;
    }
}
