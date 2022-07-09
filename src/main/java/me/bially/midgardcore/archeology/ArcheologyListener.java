package me.bially.midgardcore.archeology;

import com.jeff_media.customblockdata.CustomBlockData;
import io.th0rgal.oraxen.items.OraxenItems;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanicFactory;
import me.bially.midgardcore.Main;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Tripwire;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicListener.getNoteBlockMechanic;

public class ArcheologyListener implements Listener {

    public JavaPlugin plugin = Main.getPlugin(Main.class);
    private final ArcheologyMechanicFactory factory;
    private final List<Block> blockCooldownList = new ArrayList<>();
    public NamespacedKey usesKey = new NamespacedKey(plugin, "bottleUses");

    public ArcheologyListener(ArcheologyMechanicFactory factory) {
        super();
        this.factory = factory;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBottle(final PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (item == null || item.getType() != Material.GLASS_BOTTLE) return;
        if (block == null || block.getType() != Material.NOTE_BLOCK) return;
        if (!Objects.equals(getNoteBlockMechanic(block).getItemID(), "brzoza_kran") || blockCooldownList.contains(block)) return;

        item.subtract(1);
        player.getInventory().addItem(OraxenItems.getItemById("sok_klonowy").build());
        NoteBlockMechanicFactory.setBlockModel(block, "brzoza_kran_cooldown");
        PersistentDataContainer pdc = new CustomBlockData(block, plugin);
        blockCooldownList.add(block);
        if (pdc.has(usesKey, PersistentDataType.INTEGER)) {
            pdc.set(usesKey, PersistentDataType.INTEGER, pdc.get(usesKey, PersistentDataType.INTEGER) + 1);
        } else {
            pdc.set(usesKey, PersistentDataType.INTEGER, 1);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            NoteBlockMechanicFactory.setBlockModel(block, "brzoza_kran");
            blockCooldownList.remove(block);
            if (pdc.get(usesKey, PersistentDataType.INTEGER) >= 3) {
                block.setType(Material.STRIPPED_BIRCH_LOG, false);
                pdc.remove(usesKey);
            }
        }, 200L);
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        blockCooldownList.remove(event.getBlock());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSandBrush(final PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        ArcheologyMechanic mechanic;
        if (item == null || block == null || event.getHand() != EquipmentSlot.HAND) return;
        if (block.getType() == Material.NOTE_BLOCK) {
            NoteBlockMechanic noteMechanic = getNoteBlockMechanic(block);
            if (noteMechanic == null) return;

            mechanic = (ArcheologyMechanic) factory.getMechanic(noteMechanic.getItemID());
            if (mechanic == null || !mechanic.hasNextBlock() || event.getAction() != mechanic.getOnAction()) return;
            if (mechanic.hasItemRequirement() && !Objects.equals(OraxenItems.getIdByItem(item), mechanic.getRequiredItem()))
                return;
            setNextBlock(mechanic, block, player);
        } else if (block.getType() == Material.TRIPWIRE) {
            StringBlockMechanic stringMechanic = StringBlockMechanicFactory.getBlockMechanic(StringBlockMechanicFactory.getCode((Tripwire) block.getBlockData()));
            if (stringMechanic == null) return;

            mechanic = (ArcheologyMechanic) factory.getMechanic(stringMechanic.getItemID());
            if (mechanic == null || !mechanic.hasNextBlock() || event.getAction() != mechanic.getOnAction()) return;
            if (mechanic.hasItemRequirement() && !Objects.equals(OraxenItems.getIdByItem(item), mechanic.getRequiredItem()))
                return;
            event.setCancelled(true);
            setNextBlock(mechanic, block, player);
        }
        player.swingMainHand();
    }

    private void setNextBlock(ArcheologyMechanic mechanic, Block block, Player player) {
        if (Objects.equals(mechanic.getNextBlock(), "AIR")) block.setType(Material.AIR, false);
        else if (StringBlockMechanicFactory.getInstance().getMechanic(mechanic.getNextBlock()) != null) {
            if (block.getType() != Material.TRIPWIRE) block.setType(Material.AIR, false);
            Bukkit.getScheduler().runTaskLater(plugin, Runnable ->
                    StringBlockMechanicFactory.setBlockModel(block, mechanic.getNextBlock()), 2);
        }
        else if (NoteBlockMechanicFactory.getInstance().getMechanic(mechanic.getNextBlock()) != null) {
            NoteBlockMechanicFactory.setBlockModel(block, mechanic.getNextBlock());
        }

        if (mechanic.hasDrops()) {
            for (String d : mechanic.getDrops()) {
                ItemStack drop;
                if (StringUtils.isAllUpperCase(d)) drop = new ItemStack(Material.valueOf(d));
                else drop = OraxenItems.getItemById(d).build();
                player.getWorld().dropItemNaturally(block.getLocation(), drop);
            }
        }
    }
}
