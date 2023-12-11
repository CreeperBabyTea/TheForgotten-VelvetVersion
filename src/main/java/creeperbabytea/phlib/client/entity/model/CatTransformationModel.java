package creeperbabytea.phlib.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import creeperbabytea.phlib.common.spell.CatTransformingCharm.CatTransformationEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatTransformationModel extends EntityModel<CatTransformationEntity> {
    private final ModelRenderer ears;
    private final ModelRenderer tails;

    public CatTransformationModel() {
        textureWidth = 32;
        textureHeight = 32;

        ears = new ModelRenderer(this);
        ears.setRotationPoint(0.0F, -8.5F, 0.5F);
        setRotationAngle(ears, 0.2618F, 0.0F, 0.0F);
        ears.setTextureOffset(0, 12).addBox(-7.0F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, 0.0F, false);
        ears.setTextureOffset(12, 12).addBox(2.0F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, 0.0F, false);

        tails = new ModelRenderer(this);
        tails.setRotationPoint(0.0F, 14.75F, 3.25F);
        setRotationAngle(tails, 0.2618F, 0.0F, 0.0F);
        tails.setTextureOffset(0, 0).addBox(-1.0F, -6.6231F, -1.1124F, 2.0F, 10.0F, 2.0F, 0.0F, false);

        ModelRenderer bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 6.2214F, 0.2557F);
        tails.addChild(bone);
        setRotationAngle(bone, 0.3491F, 0.0F, 0.0F);
        bone.setTextureOffset(8, 0).addBox(-1.0F, -3.906F, 0.0326F, 2.0F, 10.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(CatTransformationEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        ears.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        tails.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
