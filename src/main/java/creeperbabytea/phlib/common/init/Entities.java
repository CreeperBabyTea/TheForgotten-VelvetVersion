package creeperbabytea.phlib.common.init;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.spell.CatTransformingCharm;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class Entities {
    public static final EntityType<CatTransformingCharm.CatTransformationEntity> CAT_TRANSFORMATION = PhilosophersObjects.ENTITIES.add("cat_transformation",
            EntityType.Builder.<CatTransformingCharm.CatTransformationEntity>create(CatTransformingCharm.CatTransformationEntity::new, EntityClassification.AMBIENT)
                    .disableSummoning()
                    .size(0.5F, 0.5F)
                    .func_225435_d()
                    .trackingRange(50)
                    .updateInterval(1)
                    .setUpdateInterval(1)
                    .build(TheForgotten.MODID + ':' + "cat_transformation"));

    public static void init() {
    }
}
