package me.bially.midgardcore;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.MechanicsManager;
import me.bially.midgardcore.archeology.ArcheologyMechanicFactory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        MechanicsManager.registerMechanicFactory("archeology", ArcheologyMechanicFactory::new);
        System.out.println("MidgardCore enabled.");
    }

    @Override
    public void onDisable() {this.getLogger().info("MidgardCore disabled.");
    }
}

