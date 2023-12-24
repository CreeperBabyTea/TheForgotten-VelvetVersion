package creeperbabytea.fgt.client.spell;

import creeperbabytea.fgt.common.registry.SpellRegistry;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class ClientSpellTranslation {
    private static final Map<String, Map<String, String>> LOCALES = new HashMap<>();

    public static Map<String, String> fromLanguage(String lang) {
        if (LOCALES.containsKey(lang)) return LOCALES.get(lang);
        else {
            Map<String, String> map = new HashMap<>();
            LOCALES.put(lang, map);
            return map;
        }
    }

    @Nullable
    public static String translateInLanguage(String value, String language) {
        if (LOCALES.containsKey(language)) {
            Map<String, String> map = LOCALES.get(language);
            if (map.containsKey(value)) return map.get(value);
        }
        return null;
    }

    @Nullable
    public static String forcedTranslate(String value) {
        Map<String, String> map = new HashMap<>();
        String tryTrans = translate(value);
        if (tryTrans != null) return tryTrans;

        for (Map<String, String> map1 : LOCALES.values()) {
            if (map1.containsKey(value)) {
                map = map1;
                break;
            }
        }
        return map.get(value);
    }

    @Nullable
    public static String translate(String value) {
        String tryTransZh = translateInLanguage(value, "zh_cn");
        if (tryTransZh != null) return tryTransZh;
        String tryTransEn = translateInLanguage(value, "en_us");
        if (tryTransEn != null) return tryTransEn;

        return translateInLanguage(value, Minecraft.getInstance().gameSettings.language);
    }

    public static List<String> translateAll(List<String> values) {
        return values.stream().map(ClientSpellTranslation::forcedTranslate).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<String> formatClientMsg(String msg) {
        String[] incantations = Arrays.stream(msg.split("\\|+")).map(SpellRegistry::format).toArray(String[]::new);

        return Arrays.asList(incantations);
    }
}
