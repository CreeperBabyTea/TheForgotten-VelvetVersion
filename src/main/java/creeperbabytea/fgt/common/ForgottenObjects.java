package creeperbabytea.fgt.common;

import creeperbabytea.fgt.TheForgotten;
import creeperbabytea.fgt.common.init.*;
import creeperbabytea.fgt.common.init.magic.MagicObjects;
import creeperbabytea.fgt.common.init.magic.Spells;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;
import creeperbabytea.fgt.common.registry.SpellRegistry;
import creeperbabytea.tealib.common.registry.TeaGeneralRegister;
import creeperbabytea.tealib.common.registry.TeaRegister;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgottenObjects {
    public static final TeaGeneralRegister GENERAL = TeaGeneralRegister.create(TheForgotten.MODID);

    public static final TeaRegister<Item> ITEMS = GENERAL.getRegister(Item.class);
    public static final TeaRegister<Block> BLOCKS = GENERAL.getRegister(Block.class);
    public static final TeaRegister<Effect> EFFECTS = GENERAL.getRegister(Effect.class);
    public static final TeaRegister<EntityType<?>> ENTITIES = GENERAL.getRegister(ForgeRegistries.ENTITIES);
    public static final TeaRegister<ParticleType<?>> PARTICLES = GENERAL.getRegister(ForgeRegistries.PARTICLE_TYPES);
    public static final TeaRegister<TileEntityType<?>> TILE_ENTITIES = GENERAL.getRegister(ForgeRegistries.TILE_ENTITIES);
    public static final TeaRegister<Attribute> ATTRIBUTES = GENERAL.getRegister(Attribute.class);

    public static final TeaRegister<Spell> SPELLS = GENERAL.getRegister(Spell.class);

    public static void init(IEventBus mod) {
        SpellRegistry.init();

        MagicObjects.init();

        Items.init();
        Blocks.init();
        Effects.init();

        Entities.init();
        ParticleTypes.init();
        Attributes.init();

        Spells.init();

        GENERAL.register(mod);
    }
}
