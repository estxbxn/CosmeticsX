package fr.zebulon.cosmeticsx.configuration;

/**
 * Describes operating modes for the plugin
 * can be easily grabbed using {@link Config#getMode()}
 */
public enum PluginMode {

    /**
     * UNKNOWN plugin mode is to get information of the Default API.
     */
    UNKNOWN,

    /**
     * PLAYER_SERVER plugin mode is to get information of players from the API.
     */
    PLAYER_SERVER
}
