package creeperbabytea.fgt.common.init;

import creeperbabytea.fgt.TheForgotten;
import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.spell.CatTransformingCharm;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class Entities {
    public static final EntityType<CatTransformingCharm.CatTransformationEntity> CAT_TRANSFORMATION = ForgottenObjects.ENTITIES.add("cat_transformation",
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
