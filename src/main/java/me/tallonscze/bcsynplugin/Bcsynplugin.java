package me.tallonscze.bcsynplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Bcsynplugin extends JavaPlugin {

    public static Bcsynplugin INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(new SyncClass(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
