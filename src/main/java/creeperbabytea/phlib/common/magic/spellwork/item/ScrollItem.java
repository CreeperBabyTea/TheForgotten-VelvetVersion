package creeperbabytea.phlib.common.magic.spellwork.item;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.init.MagicObjects;
import creeperbabytea.phlib.common.magic.general.wizard.Capabilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ScrollItem extends Item {
    private final IScrollType type;

    public ScrollItem(IScrollType type) {
        super(new Properties().group(MagicObjects.MAGIC_GROUP).maxStackSize(1));
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.getCapability(Capabilities.MAGIC_CAPABILITY).ifPresent(cap -> System.out.println(cap.innerThoughts()));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public interface IScrollType {
        ResourceLocation getBackgroundTexture();

        int xSize();

        int ySize();
    }

    public enum EnumScrollTypes implements IScrollType {
        //BAMBOO(TheForgotten.INSTANCE.modLoc("textures/gui/magic/spellwork/scroll"), 5, 5),
        ;

        final ResourceLocation bg;
        final int xSize, ySize;

        EnumScrollTypes(ResourceLocation bg, int xSize, int ySize) {
            this.bg = bg;
            this.xSize = Math.min(xSize, 5);
            this.ySize = Math.min(ySize, 5);
        }

        @Override
        public ResourceLocation getBackgroundTexture() {
            return bg;
        }

        @Override
        public int xSize() {
            return xSize;
        }

        @Override
        public int ySize() {
            return ySize;
        }
    }
}
