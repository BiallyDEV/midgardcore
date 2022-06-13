package me.bially.midgardcore;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Archaeology implements Listener {

    @EventHandler
    public void SandBrushing1(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || block.getType() != Material.SAND) return;
        if (player.getInventory().getItemInMainHand().getType() == Material.SHEARS) {
            block.setType(Material.OAK_SLAB);
        }
    }
    @EventHandler
    public void SandBrushing2(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() != Action.LEFT_CLICK_BLOCK || block.getType() != Material.OAK_SLAB) return;
        if (player.getInventory().getItemInMainHand().getType() == Material.SHEARS) {
            block.setType(Material.JUNGLE_SLAB);

        }
    }
    @EventHandler
    public void SandBrushing3(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || block.getType() != Material.JUNGLE_SLAB) return;
        if (player.getInventory().getItemInMainHand().getType() == Material.SHEARS) {
            block.setType(Material.ACACIA_SLAB);

        }
    }
    @EventHandler
    public void SandBrushing4(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() != Action.LEFT_CLICK_BLOCK || block.getType() != Material.ACACIA_SLAB) return;
        if (player.getInventory().getItemInMainHand().getType() == Material.SHEARS) {
            ItemStack kosc = new ItemStack(Material.BONE, 1);
            player.getWorld().dropItemNaturally(block.getRelative(player.getFacing().getOppositeFace()).getLocation(), kosc);
            block.setType(Material.AIR);

        }
    }
}