package me.bially.midgardcore.customsoundsystem;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CustomSoundSystemListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSilverBlockBreak(final PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        location.add(0, -1, 0);
        Block block = location.getBlock();

        if (block.getType() == Material.OAK_LOG) {
            player.playSound(player.getLocation(), "minecraft:mud.step", 10, 0);
        }
    }
}