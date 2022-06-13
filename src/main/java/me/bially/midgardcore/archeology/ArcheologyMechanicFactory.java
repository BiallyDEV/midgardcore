package me.bially.midgardcore.archeology;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import io.th0rgal.oraxen.mechanics.MechanicsManager;
import org.bukkit.configuration.ConfigurationSection;

public class ArcheologyMechanicFactory extends MechanicFactory {

    public ArcheologyMechanicFactory(ConfigurationSection section) {
        super(section);
        MechanicsManager.registerListeners(OraxenPlugin.get(), new ArcheologyListener(this));
    }

    @Override
    public Mechanic parse(ConfigurationSection section) {
        Mechanic mechanic = new ArcheologyMechanic(this, section);
        addToImplemented(mechanic);
        return mechanic;
    }
}
