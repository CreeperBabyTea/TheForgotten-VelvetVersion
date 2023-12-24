package creeperbabytea.fgt.client.data;

import creeperbabytea.fgt.TheForgotten;
import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.magic.spellwork.item.WandItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TheForgotten.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Item item : ForgottenObjects.ITEMS.getEntries()) {
            String name = Objects.requireNonNull(item.getRegistryName()).getPath();

            if (item instanceof WandItem)
                getBuilder(name).parent(getExistingFile(new ResourceLocation("item/handheld")))
                        .texture("layer0", modLoc("item/" + name));
        }
    }
}
