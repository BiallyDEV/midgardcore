package me.bially.midgardcore;

import io.th0rgal.oraxen.mechanics.MechanicsManager;
import me.bially.midgardcore.archeology.ArcheologyMechanicFactory;
import me.bially.midgardcore.customsoundsystem.CustomSoundSystemMechanicFactory;
import org.bukkit.plugin.java.JavaPlugin;
import me.bially.midgardcore.listener.BlockPlaceListener;
import org.bukkit.Bukkit;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        if (null == this.getConfig().getConfigurationSection("blocks")) {
            this.getLogger().warning("`blocks` section does not exist! Creating...");
            this.getConfig().createSection("blocks");
        }
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new StripLogMechanic(), this);
        MechanicsManager.registerMechanicFactory("customsoundsystem", CustomSoundSystemMechanicFactory::new);
        MechanicsManager.registerMechanicFactory("archeology", ArcheologyMechanicFactory::new);

        this.getLogger().info("MidgardCore enabled.");
    }

    @Override
    public void onDisable() {this.getLogger().info("MidgardCore disabled.");
    }
}

