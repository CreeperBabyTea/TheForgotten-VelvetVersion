package creeperbabytea.fgt.common.init.magic;

import creeperbabytea.fgt.TheForgotten;
import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.magic.spellwork.entity.ConflictSpellEntity;
import creeperbabytea.fgt.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.fgt.common.magic.spellwork.item.WandItem;
import creeperbabytea.fgt.common.magic.warecraft.block.TableOfEnchantmentsBlock;
import creeperbabytea.fgt.common.magic.warecraft.tileentity.TableOfEnchantmentsTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

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
    public static final EntityType<SpellEntity> SPELL_ENTITY = ForgottenObjects.ENTITIES.add("spell",
            EntityType.Builder.<SpellEntity>create(SpellEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F)
                    .disableSummoning()
                    .func_225435_d()
                    .trackingRange(50)
                    .updateInterval(1)
                    .setUpdateInterval(1)
                    .build(TheForgotten.MODID + ':' + "spell"));
    public static final EntityType<ConflictSpellEntity> CONFLICT_SPELL = ForgottenObjects.ENTITIES.add("conflict_spell",
            EntityType.Builder.<ConflictSpellEntity>create(ConflictSpellEntity::new, EntityClassification.MISC)
                    .size(0.7F, 0.7F)
                    .disableSummoning()
                    .func_225435_d()
                    .build(TheForgotten.MODID + ':' + "conflict_spell"));


    public static final Item SCROLL = ForgottenObjects.ITEMS.add("scroll", new ScrollItem(ScrollItem.ScrollType.BAMBOO));
    public static final Item WAND = ForgottenObjects.ITEMS.add("wand", new WandItem());

    /*----------------------------------------Ware Craft----------------------------------------*/
    public static final Block TABLE_OF_ENCHANTMENTS = ForgottenObjects.BLOCKS.add("table_of_enchantments",new TableOfEnchantmentsBlock());

    public static final TileEntityType<?> TABLE_OF_ENCHANTMENTS_TE = ForgottenObjects.TILE_ENTITIES.add("table_of_enchantments",
            TileEntityType.Builder.create(TableOfEnchantmentsTileEntity::new, TABLE_OF_ENCHANTMENTS).build(null));

    public static void init() {
    }
}
