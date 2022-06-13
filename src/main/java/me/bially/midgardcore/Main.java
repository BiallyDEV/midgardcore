package me.bially.midgardcore;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Archaeology(), this);

        System.out.println("MidgardCore enabled.");
    }

    @Override
    public void onDisable() {this.getLogger().info("MidgardCore disabled.");
    }
}

