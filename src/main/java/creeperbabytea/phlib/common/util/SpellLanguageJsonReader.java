package creeperbabytea.phlib.common.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.client.spell.ClientSpellTranslation;
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
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SpellLanguageJsonReader implements ISelectiveResourceReloadListener {
    @Override
    public void onResourceManagerReload(IResourceManager whatTheHellAmIDoing, Predicate<IResourceType> resourcePredicate) {
        System.out.println(Minecraft.getInstance().gameSettings.language);
        final IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        Collection<ResourceLocation> locations = resourceManager.getAllResourceLocations("lang/", (a) -> a.endsWith(".json"));
        locations = locations.stream().filter(loc -> loc.getNamespace().equals(TheForgotten.MODID)).collect(Collectors.toSet());
        locations.forEach(loc -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceManager.getResource(loc).getInputStream()));
                JsonObject json = new JsonParser().parse(reader).getAsJsonObject();

                Map<String, String> map = ClientSpellTranslation.fromLanguage(loc.getPath().substring(loc.getPath().length() - 10, loc.getPath().length() - 5));

                json.entrySet().forEach(e -> {
                    if (e.getKey().startsWith("incantation."))
                        map.put(e.getValue().getAsString().toLowerCase(), e.getKey().replaceAll("incantation.", ""));
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Nullable
    @Override
    public IResourceType getResourceType() {
        return VanillaResourceType.LANGUAGES;
    }
}
