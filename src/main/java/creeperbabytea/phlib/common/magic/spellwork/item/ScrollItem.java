package creeperbabytea.phlib.common.magic.spellwork.item;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.client.screen.spellwork.ScrollGUI;
import creeperbabytea.phlib.common.init.magic.MagicObjects;
import creeperbabytea.phlib.common.magic.spellwork.item.scroll.ScrollState;
import creeperbabytea.tealib.client.screen.ITeaScreenProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;

public class ScrollItem extends Item {
    private final IScrollType type;

    public ScrollItem(@Nullable IScrollType type) {
        super(new Properties().group(MagicObjects.MAGIC_GROUP).maxStackSize(1));
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack scroll = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote && scroll.getItem() instanceof ScrollItem)
            Minecraft.getInstance().displayGuiScreen(new ScrollGUI(type, scroll, handIn));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        //GLFW.GLFW_KEY_ESCAPE;
        KeyBinding keys = new KeyBinding("aa", )
    }

    public IScrollType getType() {
        return type;
    }

    public interface IScrollType extends ITeaScreenProperties {
        ResourceLocation getBackgroundTexture();

        int xSize();

        int ySize();
    }

    public static class ScrollType implements IScrollType {
        public static final ScrollType BAMBOO = new ScrollType(new TranslationTextComponent("gui.the_forgotten.item.scroll.bamboo"), TheForgotten.modLocation("textures/gui/magic/spellwork/scroll.png"), 5, 5);

        final ITextComponent title;
        final ResourceLocation bg;
        final int xSize, ySize;

        ScrollType(ITextComponent title, ResourceLocation bg, int xSize, int ySize) {
            this.title = title;
            this.bg = bg;
            this.xSize = Math.min(xSize, 4);
            this.ySize = Math.min(ySize, 4);
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

        @Override
        public ITextComponent getTitle() {
            return title;
        }
    }
}
