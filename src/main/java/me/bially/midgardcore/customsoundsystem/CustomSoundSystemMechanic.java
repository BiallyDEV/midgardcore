package me.bially.midgardcore.customsoundsystem;

import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import org.bukkit.configuration.ConfigurationSection;

public class CustomSoundSystemMechanic extends Mechanic {

    private final String stepSound;

    public CustomSoundSystemMechanic(MechanicFactory factory, ConfigurationSection section) {
        super(factory, section);
        stepSound = section.getString("stepSound", null);
    }


    public boolean hasItemRequirement() { return stepSound != null; }
    public String getRequiredItem() { return stepSound; }

}
