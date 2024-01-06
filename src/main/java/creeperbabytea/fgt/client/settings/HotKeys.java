package creeperbabytea.fgt.client.settings;

import creeperbabytea.fgt.TheForgotten;
import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.tealib.client.operation.HotKey;
import creeperbabytea.tealib.client.operation.HotKeyListener;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import org.lwjgl.glfw.GLFW;

public class HotKeys {
    public static final HotKeyListener LISTENER = HotKeyListener.create(TheForgotten.MODID);

    public static final HotKey SCROLL_SEL_X = make(new HotKey("key.scroll.sel.x", GLFW.GLFW_KEY_Z, "key.categories.magic").setPriority(EventPriority.HIGHEST)
            .serverAction((player, world, info) -> {
                ItemStack stack = player.getHeldItem(Hand.OFF_HAND);
                if (stack.getItem() instanceof ScrollItem) {
                    int index = info.getKey() - GLFW.GLFW_KEY_0;
                    if (index > 0 && index <= ((ScrollItem) stack.getItem()).getType().xSize()) {
                        ScrollItem.setCurrentSlot(stack, index - 1, ScrollItem.getCurrentSlot(stack)[1]);
                        player.sendContainerToPlayer(player.container);
                        player.connection.sendPacket(new STitlePacket(STitlePacket.Type.ACTIONBAR, new TranslationTextComponent("msg.scroll.sel.x", index)));
                    }
                }
            }));
    public static final HotKey SCROLL_SEL_Y = make(new HotKey("key.scroll.sel.y", GLFW.GLFW_KEY_X, "key.categories.magic").setPriority(EventPriority.HIGHEST)
            .serverAction((player, world, info) -> {
                ItemStack stack = player.getHeldItem(Hand.OFF_HAND);
                if (stack.getItem() instanceof ScrollItem) {
                    int index = info.getKey() - GLFW.GLFW_KEY_0;
                    if (index > 0 && index <= ((ScrollItem) stack.getItem()).getType().ySize()) {
                        ScrollItem.setCurrentSlot(stack, ScrollItem.getCurrentSlot(stack)[0], index - 1);
                        player.sendContainerToPlayer(player.container);
                        player.connection.sendPacket(new STitlePacket(STitlePacket.Type.ACTIONBAR, new TranslationTextComponent("msg.scroll.sel.y", index)));
                    }
                }
            }));

    private static HotKey make(HotKey key) {
        LISTENER.add(key);
        return key;
    }

    public static void addListener(IEventBus forge) {
        LISTENER.addListener(forge);
    }
}
