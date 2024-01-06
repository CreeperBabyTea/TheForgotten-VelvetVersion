package creeperbabytea.fgt.common.world;

import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.world.gen.feature.tree.*;
import creeperbabytea.tealib.common.registry.TeaGeneralRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Features extends net.minecraft.world.gen.feature.Features {
    public static final SkywardGazingTree SKYWARD_GAZING;

    public static void init() {
    }

    static {
        TeaGeneralRegister reg = ForgottenObjects.GENERAL;
        reg.bindSuperClass(ForgeRegistries.FEATURES.getRegistrySuperType());
        SKYWARD_GAZING = reg.add("skyward_gazing", new SkywardGazingTree());
    }
}
