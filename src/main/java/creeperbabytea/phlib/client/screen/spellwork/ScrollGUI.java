package creeperbabytea.phlib.client.screen.spellwork;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.magic.spellwork.item.ScrollItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ScrollGUI extends Screen {
    protected final ResourceLocation bg;
    protected final int xSize;
    protected final int ySize;

    private Button edit;
    private Button save;
    private boolean editing;
    private TextFieldWidget editSpell;

    protected ScrollGUI(ITextComponent title, ScrollItem.IScrollType type) {
        super(title);
        this.bg = type.getBackgroundTexture();
        this.xSize = type.xSize();
        this.ySize = type.ySize();
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        minecraft.keyboardListener.enableRepeatEvents(true);
        //this.editSpell = new TextFieldWidget(this.font);

        super.init(minecraft, width, height);

    }
}
