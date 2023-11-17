package fr.zebulon.cosmeticsx.models;

import fr.zebulon.cosmeticsx.cosmetics.AbstractCosmetic;
import fr.zebulon.cosmeticsx.cosmetics.CosmeticSlot;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("CosmeticProfile")
public class CosmeticLocalProfile implements ConfigurationSerializable {

    private Map<String, String> equipped;

    public CosmeticLocalProfile(Map<String, String> equipped) {
        this.equipped = equipped;
    }

    public CosmeticLocalProfile() {
        this(new HashMap<>());
    }

    public Map<String, String> getEquipped() {
        return equipped;
    }

    public void setEquipped(Map<String, String> equipped) {
        this.equipped = equipped;
    }

    public static CosmeticLocalProfile deserialize(Map<String, Object> map) {
        try {
            final Map<String, String> equipped = (Map<String, String>) map.getOrDefault("equipped", new HashMap<>());
            return new CosmeticLocalProfile(equipped);
        } catch (Exception ignored) {
            return new CosmeticLocalProfile();
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of("equipped", equipped);
    }

    public static CosmeticLocalProfile from(Map<CosmeticSlot, AbstractCosmetic> profile) {
        final CosmeticLocalProfile localProfile = new CosmeticLocalProfile();
        profile.forEach((slot, cosmetic) -> localProfile.equipped.put(slot.name(), cosmetic.getQualifiedId()));
        return localProfile;
    }
}
