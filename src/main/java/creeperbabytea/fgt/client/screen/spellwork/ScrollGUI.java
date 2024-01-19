package creeperbabytea.fgt.client.screen.spellwork;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import creeperbabytea.fgt.client.spell.ClientSpellTranslation;
import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.fgt.common.magic.spellwork.item.scroll.ScrollSlot;
import creeperbabytea.fgt.common.magic.spellwork.item.scroll.ScrollState;
import creeperbabytea.fgt.common.magic.spellwork.network.SaveScrollPack;
import creeperbabytea.fgt.common.network.Networking;
import creeperbabytea.tealib.client.screen.ITeaScreen;
import creeperbabytea.tealib.client.screen.TScreen;
import creeperbabytea.tealib.util.math.vector.Vector2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class ScrollGUI extends TScreen implements ITeaScreen {
    protected final ResourceLocation bg;
    protected final int xSize;
    protected final int ySize;

    protected final int textureWidth;
    protected final int textureHeight;
    protected int xOffset = -1;
    protected int yOffset = -1;

    private boolean editing = false;
    private int xIndex = -1;
    private int yIndex = -1;

    private TextFieldWidget spellText;
    private Button save;
    private Button exit;

    private ItemStack stack;
    private final Hand handIn;
    private ScrollState scroll;

    public ScrollGUI(ScrollItem.IScrollType type, ItemStack stack, Hand handIn) {
        super(type.getTitle());
        this.bg = type.getBackgroundTexture();
        this.xSize = type.xSize();
        this.ySize = type.ySize();
        this.textureWidth = 24 + xSize * 48;
        this.textureHeight = 24 + ySize * 48;
        this.stack = stack;
        this.handIn = handIn;
    }

    @Override
    protected void init() {
        calcPosition();
        this.scroll = ScrollState.from(stack);

        assert minecraft != null;
        minecraft.keyboardListener.enableRepeatEvents(true);
        this.spellText = new TextFieldWidget(this.font, width / 2 - 150, yOffset + textureHeight / 2 - 30, 300, 20, new StringTextComponent(""));
        spellText.setMaxStringLength(128);
        this.save = new Button(width / 2 - 150, yOffset + textureHeight / 2, 140, 20, new TranslationTextComponent("gui.the_forgotten.item.scroll.save"), (button) -> {
            String incantation = spellText.getText();
            List<String> spells = ClientSpellTranslation.translateAll(ClientSpellTranslation.formatClientMsg(incantation));
            Networking.INSTANCE.sendToServer(new SaveScrollPack(handIn == Hand.MAIN_HAND, xIndex, yIndex, spells));
            this.reload();

            quitEdit();
        });
        this.exit = new Button(width / 2 + 10, yOffset + textureHeight / 2, 140, 20, new TranslationTextComponent("gui.the_forgotten.item.scroll.exit"), (button) -> {
            quitEdit();
        });

        this.children.add(spellText);
        this.addButton(save);
        this.addButton(exit);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().textureManager.bindTexture(this.bg);
        RenderSystem.blendColor(1, 1, 1, 0.1F);
        blit(matrixStack, xOffset, yOffset, 0, 0, textureWidth, textureHeight, 512, 512);

        if (editing) {
            if (spellText.getText().isEmpty()) {
                ScrollSlot slot = scroll.getSlot(xIndex, yIndex);
                if (!slot.getSpells().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    slot.getSpells().forEach(s -> {
                        sb.append(new TranslationTextComponent("incantation." + s).getString());
                        sb.append('|');
                    });
                    spellText.writeText(sb.toString());
                }
            }
            drawSlot(matrixStack, mouseX, mouseY, true);
            spellText.render(matrixStack, mouseX, mouseY, partialTicks);
            super.render(matrixStack, mouseX, mouseY, partialTicks);
        } else
            drawSlot(matrixStack, mouseX, mouseY, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!editing) {
            Vector2i pos = calcIndex(mouseX, mouseY);
            if (pos.x != -1 && pos.y != -1) {
                xIndex = pos.x;
                yIndex = pos.y;
                editing = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawSlot(MatrixStack matrixStack, double mouseX, double mouseY, boolean editing) {
        Vector2i pos = calcIndex(mouseX, mouseY);
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (x == pos.x && y == pos.y && !editing)
                    blit(matrixStack, xOffset + 16 + 48 * x, yOffset + 16 + 48 * y, textureWidth + 1, 40, 40, 40, 512, 512);
                else {
                    blit(matrixStack, xOffset + 16 + 48 * x, yOffset + 16 + 48 * y, textureWidth + 1, 0, 40, 40, 512, 512);
                }
            }
        }
    }

    private void calcPosition() {
        this.xOffset = (width - textureWidth) / 2;
        this.yOffset = (height - textureHeight) / 2;
    }

    private Vector2i calcIndex(double x, double y) {
        x -= xOffset + 16;
        y -= yOffset + 16;
        if (x < 0 || y < 0 || x % 48 > 40 || y % 48 > 40)
            return new Vector2i(-1, -1);
        int xIndex = Math.min((int) (x / 48), xSize - 1);
        int yIndex = Math.min((int) (y / 48), ySize - 1);
        return new Vector2i(xIndex, yIndex);
    }

    private void reload() {
        assert Minecraft.getInstance().player != null;
        this.stack = Minecraft.getInstance().player.getHeldItem(handIn);
        this.scroll = ScrollState.from(stack);
    }

    private void quitEdit() {
        this.xIndex = -1;
        this.yIndex = -1;
        this.editing = false;
        this.spellText.setText("");
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
