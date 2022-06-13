package me.bially.midgardcore.archeology;

import io.th0rgal.oraxen.items.OraxenItems;
import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.MechanicFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanic;
import io.th0rgal.oraxen.utils.drops.Drop;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ArcheologyMechanic extends Mechanic {

    private final String nextBlock;
    private final String requiredItem;
    private final Action onAction;
    private final String drop;

    public ArcheologyMechanic(MechanicFactory factory, ConfigurationSection section) {
        super(factory, section);
        nextBlock = section.getString("nextBlock", null);
        requiredItem = section.getString("requiredItem", null);
        onAction = Action.valueOf(section.getString("onAction", "RIGHT_CLICK_BLOCK"));
        drop = section.getString("drop", null);
    }

    public boolean hasNextBlock() { return nextBlock != null; }
    public String getNextBlock() { return nextBlock; }

    public boolean hasItemRequirement() { return requiredItem != null; }
    public String getRequiredItem() { return requiredItem; }

    public Action getOnAction() { return onAction; }

    public boolean hasDrop() { return drop != null; }
    public ItemStack getDrop() { return OraxenItems.getItemById(drop).build(); }
}
