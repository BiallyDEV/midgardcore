package me.bially.midgardcore;

import io.th0rgal.oraxen.mechanics.MechanicsManager;
import me.bially.midgardcore.archeology.ArcheologyMechanicFactory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        if (null == this.getConfig().getConfigurationSection("blocks")) {
            this.getLogger().warning("`blocks` section does not exist! Creating...");
            this.getConfig().createSection("blocks");
        }
        MechanicsManager.registerMechanicFactory("archeology", ArcheologyMechanicFactory::new);

        this.getLogger().info("MidgardCore enabled.");
    }

    @Override
    public void onDisable() {this.getLogger().info("MidgardCore disabled.");
    }
}

