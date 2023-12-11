package creeperbabytea.phlib.common;

import net.minecraft.util.ResourceLocation;

public interface IModidHelper {
    String modid();

    default ResourceLocation modLoc(String path) {
        return new ResourceLocation(modid(), path);
    }

    default ResourceLocation forgeLoc(String path) {
        return new ResourceLocation("forge", path);
    }

    default ResourceLocation mcLoc(String path) {
        return new ResourceLocation("minecraft", path);
    }
}
