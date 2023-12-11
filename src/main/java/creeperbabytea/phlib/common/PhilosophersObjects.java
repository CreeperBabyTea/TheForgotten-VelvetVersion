package creeperbabytea.phlib.common;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.init.*;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.registry.SpellRegistry;
import creeperbabytea.tealib.registry.GeneralDeferredRegister;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PhilosophersObjects {
    public static final GeneralDeferredRegister GENERAL = GeneralDeferredRegister.create(TheForgotten.MODID);

    public static final DeferredRegister<Item> ITEMS = GENERAL.getRegister(Item.class);
    public static final DeferredRegister<Block> BLOCKS = GENERAL.getRegister(Block.class);
    public static final DeferredRegister<Spell> SPELLS = GENERAL.getRegister(Spell.class);
    public static final DeferredRegister<EntityType<?>> ENTITIES = GENERAL.getRegister(ForgeRegistries.ENTITIES);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = GENERAL.getRegister(ForgeRegistries.PARTICLE_TYPES);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = GENERAL.getRegister(ForgeRegistries.TILE_ENTITIES);
    public static final DeferredRegister<Attribute> ATTRIBUTES = GENERAL.getRegister(Attribute.class);

    public static void init(IEventBus bus) {
        SpellRegistry.init();

        MagicObjects.init();

        Blocks.init();
        Spells.init();
        Entities.init();
        ParticleTypes.init();
        Attributes.init();

        ITEMS.register(bus);
        BLOCKS.register(bus);
        SPELLS.register(bus);
        ENTITIES.register(bus);
        PARTICLES.register(bus);
        TILE_ENTITIES.register(bus);
    }
}
