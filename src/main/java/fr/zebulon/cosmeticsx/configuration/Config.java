package fr.zebulon.cosmeticsx.configuration;

import fr.zebulon.cosmeticsx.utils.EnumUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

    private String apiUrl;
    private String apiSecret;
    private PluginMode mode;
    private boolean hideEntitiesWithoutPack = true;
    private boolean resourcePackSender = true;

    private final Plugin plugin;

    public Config(Plugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public void load() {
        final FileConfiguration config = plugin.getConfig();

        // Set the mode variable from the config.yml file
        this.mode = EnumUtil.valueOfSafe(PluginMode.class, config.getString("global-settings.mode")).orElse(PluginMode.PLAYER_SERVER);
        this.hideEntitiesWithoutPack = config.getBoolean("global-settings.hide-entities-without-pack", true);
        this.resourcePackSender = config.getBoolean("global-settings.resource-pack-sender", true);

        // Switch the mode variable
        switch (mode) {
            // If the mode is UNKNOWN
            case LOBBY -> {
                // Set the apiUrl and apiSecret variables as default values
                this.apiUrl = "http://localhost:3001";
                this.apiSecret = "secret";
            }
            // If the mode is PLAYER_SERVER
            case PLAYER_SERVER -> {
                // Set the apiUrl variable from the config.yml file
                this.apiUrl = config.getString("api-settings.url", "http://localhost:3001");
                this.apiSecret = config.getString("api-settings.secret", "secret");
            }
        }
    }

    /**
     * Reload the plugin configuration
     */
    public void reload() {
        plugin.reloadConfig();
        load();
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public PluginMode getMode() {
        return mode;
    }

    public boolean hideEntitiesWithoutPack() {
        return hideEntitiesWithoutPack;
    }

    public boolean resourcePackSender() {
        return resourcePackSender;
    }
}
