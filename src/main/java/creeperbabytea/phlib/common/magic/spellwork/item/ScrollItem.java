package creeperbabytea.phlib.common.magic.spellwork.item;

import creeperbabytea.phlib.common.init.MagicObjects;
import creeperbabytea.phlib.common.magic.general.wizard.Capabilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ScrollItem extends Item {
    public ScrollItem() {
        super(new Properties().group(MagicObjects.MAGIC_GROUP).maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.getCapability(Capabilities.MAGIC_CAPABILITY).ifPresent(cap -> System.out.println(cap.innerThoughts()));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
