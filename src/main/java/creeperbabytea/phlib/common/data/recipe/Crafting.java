package creeperbabytea.phlib.common.data.recipe;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.tealib.util.IModResourceHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

import java.util.function.Consumer;

public class Crafting extends RecipeProvider implements IModResourceHelper {
    public Crafting(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void registerRecipes(Consumer<IFinishedRecipe> consumer) {

    }

    @Override
    public String getName() {
        return TheForgotten.MODID + ":crafting";
    }

    @Override
    public String modId() {
        return TheForgotten.MODID;
    }
}
