package creeperbabytea.fgt.client.particle;

import creeperbabytea.fgt.common.magic.general.particles.ColoredParticleData;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class ColoredParticle extends SpriteTexturedParticle {
    private final ColoredParticleData data;
    private final IAnimatedSprite spriteWithAge;

    protected ColoredParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, ColoredParticleData data, IAnimatedSprite sprite) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.data = data;
        this.spriteWithAge = sprite;

        this.particleRed = data.getRed() / 255F;
        this.particleGreen = data.getGreen() / 255F;
        this.particleBlue = data.getBlue() / 255F;
        this.particleAlpha = data.getAlpha() / 255F;
        this.particleScale = data.getScale() * 0.05F;
        this.maxAge = data.getMaxAge();
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.selectSpriteWithAge(sprite);
    }

    @Override
    public void tick() {
        if (this.age++ > data.getMaxAge()) {
            this.setExpired();
        } else {
            this.selectSpriteWithAge(spriteWithAge);

            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;

            this.posX += motionX;
            this.posY += motionY;
            this.posZ += motionZ;

            this.motionX *= 0.96F;
            this.motionY *= 0.96F;
            this.motionZ *= 0.96F;

            if (this.onGround) {
                this.motionX *= 0.7F;
                this.motionZ *= 0.7F;
            }
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<ColoredParticleData> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(ColoredParticleData data, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ColoredParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, data, this.sprite);
        }
    }
}
