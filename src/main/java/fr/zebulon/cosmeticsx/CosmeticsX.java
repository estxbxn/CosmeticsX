package fr.zebulon.cosmeticsx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import fr.zebulon.cosmeticsx.api.CosmeticsXAPI;
import fr.zebulon.cosmeticsx.api.rest.RestAPI;
import fr.zebulon.cosmeticsx.api.rest.impl.RestExternalAPI;
import fr.zebulon.cosmeticsx.api.rest.impl.RestInternalAPI;
import fr.zebulon.cosmeticsx.configuration.Config;
import fr.zebulon.cosmeticsx.cosmetics.CosmeticsManager;
import fr.zebulon.cosmeticsx.data.Key;
import fr.zebulon.cosmeticsx.storage.StorageLocalManager;
import fr.zebulon.cosmeticsx.storage.polls.impl.PollingRankContainer;
import fr.zebulon.cosmeticsx.utils.EntityUtil;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public class CosmeticsX extends JavaPlugin {

    private static CosmeticsX instance;

    // Storage
    private Config config;
    private Gson gson;

    // Managers
    private CosmeticsManager manager;

    // Local storage only for use on player servers
    private StorageLocalManager localStorage;

    // API for accessing remote services
    private CosmeticsXAPI api;
    private RestAPI restApi;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize Keys
        Key.init(this);

        // Storage
        this.gson = new GsonBuilder().registerTypeAdapter(TextColor.class, (JsonDeserializer<TextColor>) (element, type, context) -> {
            try {
                return NamedTextColor.NAMES.value(element.getAsJsonPrimitive().getAsString().toLowerCase(Locale.ROOT));
            } catch (IllegalArgumentException ignored) {
                return NamedTextColor.WHITE;
            }
        }).create();
        this.config = new Config(this);

        // Managers
        this.manager = new CosmeticsManager(this);

        // API
        this.api = new CosmeticsXAPI(this);

        // Process different actions depending on the operation mode the server is in
        switch (config.getMode()) {
            // Define the RestAPI as the internal API
            case LOBBY -> this.restApi = new RestInternalAPI(config, gson);
            case PLAYER_SERVER -> {
                this.localStorage = new StorageLocalManager(this);

                // Define the RestAPI as the external API
                this.restApi = new RestExternalAPI(config, gson);

                // Register the skins listeners because we are on a player server
            }
        }

        // Set up the polling containers
        new PollingRankContainer(this).init();

        getLogger().info("Using operation mode '%s' ! ".formatted(config.getMode()));

        // TODO: Register cosmetics listeners

        // TODO: Manage visibility listeners

        // TODO: Register commands
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * Remove any entities that ar marked w/ the cosmetic key
     */
    private void removeCosmeticEntities() {
        Bukkit.getWorlds().forEach(world -> world.getEntities().stream().filter(EntityUtil::isCosmeticEntity).forEach(Entity::remove));
    }

    public static CosmeticsX get() {
        return instance;
    }

    public Config getConfiguration() {
        return config;
    }

    public Gson getGson() {
        return gson;
    }

    public CosmeticsManager getManager() {
        return manager;
    }

    public StorageLocalManager getLocalStorage() {
        return localStorage;
    }

    public CosmeticsXAPI getApi() {
        return api;
    }

    public RestAPI getRestApi() {
        return restApi;
    }
}
