package fr.zebulon.cosmeticsx.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SerializableAs("CosmeticProfile")
public class CosmeticLocalProfile implements ConfigurationSerializable {

    @Override
    public @NotNull Map<String, Object> serialize() {
        return null;
    }
}
