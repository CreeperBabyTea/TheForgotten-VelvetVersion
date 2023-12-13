package creeperbabytea.phlib.client.screen.spellwork;

import com.mojang.blaze3d.matrix.MatrixStack;
import creeperbabytea.phlib.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.tealib.client.screen.ITeaScreen;
import creeperbabytea.tealib.client.screen.ITeaScreenProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScrollGUI extends Screen implements ITeaScreen {
    protected final ResourceLocation bg;
    protected final int xSize;
    protected final int ySize;

    private Button edit;
    private Button save;
    private boolean editing;
    private TextFieldWidget editSpell;

    public ScrollGUI(ScrollItem.IScrollType type) {
        super(type.getTitle());
        this.bg = type.getBackgroundTexture();
        this.xSize = type.xSize();
        this.ySize = type.ySize();
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        minecraft.keyboardListener.enableRepeatEvents(true);
        //this.editSpell = new TextFieldWidget()

        super.init(minecraft, width, height);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
    }
}
