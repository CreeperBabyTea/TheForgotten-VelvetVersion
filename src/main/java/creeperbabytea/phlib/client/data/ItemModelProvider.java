package creeperbabytea.phlib.client.data;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.item.WandItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TheForgotten.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Item item : PhilosophersObjects.ITEMS.getEntries()) {
            String name = item.getRegistryName().getPath();

            if (item instanceof WandItem)
                getBuilder(name).parent(getExistingFile(new ResourceLocation("item/handheld")))
                        .texture("layer0", modLoc("item/" + name));
        }
    }
}
