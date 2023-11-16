package fr.zebulon.cosmeticsx;

import org.bukkit.plugin.java.JavaPlugin;

public class CosmeticsX extends JavaPlugin {

    private static CosmeticsX instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static CosmeticsX get() {
        return instance;
    }
}
