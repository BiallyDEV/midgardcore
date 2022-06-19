package me.bially.midgardcore.customsoundsystem;

import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicListener.getNoteBlockMechanic;

public class CustomSoundSystemListener implements Listener {

    private final CustomSoundSystemMechanicFactory;

    public CustomSoundSystemListener(CustomSoundSystemMechanicFactory factory) {
        super();
        this.factory = factory;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSilverBlockBreak(final PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        location.add(0, -1, 0);
        Block block = location.getBlock();
        CustomSoundSystemMechanic mechanic;

        if (block.getType() == Material.NOTE_BLOCK) {
            NoteBlockMechanic noteMechanic = getNoteBlockMechanic(block);
            if (noteMechanic == null) return;
            player.playSound(player.getLocation(), "minecraft:mud.step", 10, 0);
        }
    }
}