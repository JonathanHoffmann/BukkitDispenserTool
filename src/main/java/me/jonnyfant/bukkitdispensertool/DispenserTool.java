package me.jonnyfant.bukkitdispensertool;

import org.bukkit.plugin.java.JavaPlugin;

public final class DispenserTool extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DispenserListener(), this);
    }
}
