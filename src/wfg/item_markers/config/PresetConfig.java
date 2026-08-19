package wfg.item_markers.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import wfg.item_markers.config.loader.PresetConfigLoader;

public class PresetConfig {
    private PresetConfig() {}
    public static final Map<String, ItemMarkerPreset> map = new LinkedHashMap<>(16);

    static {
        PresetConfigLoader.loadConfig();
    }
    
    public static final List<ItemMarkerPreset> getPlansCopy() {
        return new ArrayList<>(map.values());
    }

    public static class ItemMarkerPreset {
        public HashSet<String> activeMarkerIds = new HashSet<>(32);
        public String id;
    }
}