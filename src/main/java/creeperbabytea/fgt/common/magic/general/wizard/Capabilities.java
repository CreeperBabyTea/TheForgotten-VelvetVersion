package creeperbabytea.fgt.common.magic.general.wizard;

import creeperbabytea.fgt.TheForgotten;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;

public class Capabilities {
    @CapabilityInject(IMagicCapability.class)
    public static Capability<IMagicCapability> MAGIC_CAPABILITY;

    public static void addListener(IEventBus mod, IEventBus forge) {
        mod.addListener(Capabilities::onCommonSetup);
        forge.addGenericListener(Entity.class, Capabilities::onCapability);
        forge.addListener(Capabilities::onPlayerCloned);
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(()-> CapabilityManager.INSTANCE.register(IMagicCapability.class, new Capability.IStorage<IMagicCapability>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<IMagicCapability> capability, IMagicCapability instance, Direction side) {
                return null;
            }
            @Override
            public void readNBT(Capability<IMagicCapability> capability, IMagicCapability instance, Direction side, INBT nbt) {

            }
        }, () -> null));
    }

    public static void onCapability(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity)
            event.addCapability(TheForgotten.modLocation("magic"), new MagicCapabilityProvider());
    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath())
            return;
        LazyOptional<IMagicCapability> oldCap = event.getOriginal().getCapability(MAGIC_CAPABILITY);
        LazyOptional<IMagicCapability> newCap = event.getPlayer().getCapability(MAGIC_CAPABILITY);

        if (oldCap.isPresent() && newCap.isPresent()) {
            newCap.ifPresent(cap1 -> {
                oldCap.ifPresent(cap0 -> {
                    cap1.deserializeNBT(cap0.serializeNBT());
                });
            });
        }
    }
}
