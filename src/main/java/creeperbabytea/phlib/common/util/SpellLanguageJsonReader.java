package creeperbabytea.phlib.common.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import creeperbabytea.phlib.TheForgotten;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.function.Predicate;

public class SpellLanguageJsonReader implements ISelectiveResourceReloadListener {
    @Override
    //TODO: 我谢谢你麻将，你是一点doc也不写啊
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        Collection<ResourceLocation> locations = resourceManager.getAllResourceLocations("the_forgotten/lang", (a) -> true);
        boolean a = resourceManager == Minecraft.getInstance().getResourceManager();
        locations.forEach(loc -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceManager.getResource(loc).getInputStream()));
                JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                System.out.println(json.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
