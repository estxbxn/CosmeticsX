package fr.zebulon.cosmeticsx.storage;

import fr.zebulon.cosmeticsx.models.CosmeticLocalProfile;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StorageLocalManager {

    private final ConcurrentHashMap<UUID, CosmeticLocalProfile> profiles;
    private final Plugin plugin;

    public StorageLocalManager(Plugin plugin) {
        this.plugin = plugin;
        this.profiles = new ConcurrentHashMap<>();
    }
}
