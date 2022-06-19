package me.bially.midgardcore.customsoundsystem;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import io.th0rgal.oraxen.mechanics.MechanicsManager;
import org.bukkit.configuration.ConfigurationSection;

public class CustomSoundSystemMechanicFactory extends MechanicFactory {

    public CustomSoundSystemMechanicFactory(ConfigurationSection section) {
        super(section);
        MechanicsManager.registerListeners(OraxenPlugin.get(), new CustomSoundSystemListener(this));
    }

    @Override
    public Mechanic parse(ConfigurationSection section) {
        Mechanic mechanic = new CustomSoundSystemMechanic(this, section);
        addToImplemented(mechanic);
        return mechanic;
    }
}
