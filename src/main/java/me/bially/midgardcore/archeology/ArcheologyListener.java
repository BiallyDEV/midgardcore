package me.bially.midgardcore.archeology;

import io.th0rgal.oraxen.items.OraxenItems;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanicFactory;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Tripwire;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.Objects;

import static io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicListener.getNoteBlockMechanic;

public class ArcheologyListener implements Listener {

    private final ArcheologyMechanicFactory factory;

    public ArcheologyListener(ArcheologyMechanicFactory factory) {
        super();
        this.factory = factory;
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
            if (Objects.equals(mechanic.getNextBlock(), "AIR")) block.setType(Material.AIR, false);
            else NoteBlockMechanicFactory.setBlockModel(block, mechanic.getNextBlock());

            if (mechanic.hasDrops())
                for (String d : mechanic.getDrops()) {
                    ItemStack drop;
                    if (StringUtils.isAllUpperCase(d)) drop = new ItemStack(Material.valueOf(d));
                    else drop = OraxenItems.getItemById(d).build();
                    player.getWorld().dropItemNaturally(block.getLocation(), drop);
                }
        }

        else if (block.getType() == Material.TRIPWIRE) {
            StringBlockMechanic stringMechanic = StringBlockMechanicFactory.getBlockMechanic(StringBlockMechanicFactory.getCode((Tripwire) block.getBlockData()));
            if (stringMechanic == null) return;

            mechanic = (ArcheologyMechanic) factory.getMechanic(stringMechanic.getItemID());
            if (mechanic == null || !mechanic.hasNextBlock() || event.getAction() != mechanic.getOnAction()) return;
            if (mechanic.hasItemRequirement() && !Objects.equals(OraxenItems.getIdByItem(item), mechanic.getRequiredItem())) return;

            if (Objects.equals(mechanic.getNextBlock(), "AIR")) block.setType(Material.AIR, false);
            else StringBlockMechanicFactory.setBlockModel(block, mechanic.getNextBlock());

            if (mechanic.hasDrops())
                for (String d : mechanic.getDrops()) {
                    ItemStack drop;
                    if (StringUtils.isAllUpperCase(d)) drop = new ItemStack(Material.valueOf(d));
                    else drop = OraxenItems.getItemById(d).build();
                    player.getWorld().dropItemNaturally(block.getLocation(), drop);
                }
        }
        player.swingMainHand();
    }
}
