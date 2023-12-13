package creeperbabytea.phlib.common.init.magic;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.entity.ConflictSpellEntity;
import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.phlib.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.phlib.common.magic.spellwork.item.WandItem;
import creeperbabytea.phlib.common.magic.warecraft.block.TableOfEnchantmentsBlock;
import creeperbabytea.phlib.common.magic.warecraft.tileentity.TableOfEnchantmentsTileEntity;
import creeperbabytea.tealib.common.objects.SingleItemEntry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

/**
 * <p>This class is where I put the functional objects as the implementation of the mod itself.</p>
 *
 * <p>
 * The objects here are the 'basic elements' of the mod, without which the mod is not complete.
 * As for the 'extended elements, please refer to the other classes under this package'
 * </p>
 */
public class MagicObjects {
    public static final ItemGroup MAGIC_GROUP = new ItemGroup("magic") {
        @Override
        public ItemStack createIcon() {
            return WAND.getDefaultInstance();
        }
    };

    /*----------------------------------------Spell Work----------------------------------------*/
    public static final EntityType<SpellEntity> SPELL_ENTITY = PhilosophersObjects.ENTITIES.add("spell",
            EntityType.Builder.<SpellEntity>create(SpellEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F)
                    .disableSummoning()
                    .func_225435_d()
                    .trackingRange(50)
                    .updateInterval(1)
                    .setUpdateInterval(1)
                    .build(TheForgotten.MODID + ':' + "spell"));
    public static final EntityType<ConflictSpellEntity> CONFLICT_SPELL = PhilosophersObjects.ENTITIES.add("conflict_spell",
            EntityType.Builder.<ConflictSpellEntity>create(ConflictSpellEntity::new, EntityClassification.MISC)
                    .size(0.7F, 0.7F)
                    .disableSummoning()
                    .func_225435_d()
                    .build(TheForgotten.MODID + ':' + "conflict_spell"));


    //public static final Item SCROLL = PhilosophersObjects.ITEMS.add("scroll", new ScrollItem(ScrollItem.EnumScrollTypes.BAMBOO));
    public static final Item WAND = PhilosophersObjects.ITEMS.add("wand", new WandItem());

    /*----------------------------------------Ware Craft----------------------------------------*/
    public static final Block TABLE_OF_ENCHANTMENTS = PhilosophersObjects.BLOCKS.add("table_of_enchantments",new TableOfEnchantmentsBlock());

    public static final TileEntityType<?> TABLE_OF_ENCHANTMENTS_TE = PhilosophersObjects.TILE_ENTITIES.add("table_of_enchantments",
            TileEntityType.Builder.create(TableOfEnchantmentsTileEntity::new, TABLE_OF_ENCHANTMENTS).build(null));

    public static void init() {
    }
}