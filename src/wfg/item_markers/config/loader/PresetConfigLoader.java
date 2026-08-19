package wfg.item_markers.config.loader;

import static wfg.native_ui.util.Globals.settings;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fs.starfarer.api.Global;

import wfg.item_markers.config.PresetConfig;
import wfg.item_markers.config.PresetConfig.ItemMarkerPreset;

public class PresetConfigLoader {
    private static final Logger log = Global.getLogger(PresetConfigLoader.class);

    private static final String CONFIG_NAME = "item_marker_preset.json";

    public static final void loadConfig() {
        if (!PresetConfig.map.isEmpty()) PresetConfig.map.clear();

        final JSONArray presetArray = getConfig().optJSONArray("item_marker_presets");
        if (presetArray == null) return;

        for (int i = 0; i < presetArray.length(); i++) {
            try {
                final JSONObject presetJson = presetArray.getJSONObject(i);
                final ItemMarkerPreset preset = new ItemMarkerPreset();

                final String planId = presetJson.getString("id");
                if (PresetConfig.map.containsKey(planId)) {
                    throw new RuntimeException("Duplicate item marker preset ID: " + planId);
                }
                preset.id = planId;

                final JSONArray markerArray = presetJson.optJSONArray("activeMarkerIds");
                if (markerArray != null) {
                    for (int j = 0; j < markerArray.length(); j++) {
                        preset.activeMarkerIds.add(markerArray.getString(j));
                    }
                }

                PresetConfig.map.put(planId, preset);
            } catch (Exception e) {
                log.error("Exception loading item marker preset at index " + i, e);
            }
        }
    }

    public static final void serializeAndWriteToCommon() {
        final JSONObject json = serializeConfig();

        try {
            settings.writeJSONToCommon(
                CONFIG_NAME, json, false
            );
        } catch (Exception e) {
            log.error(
                "Failed to write item marker preset configuration to common JSON file "
                + CONFIG_NAME, e
            );
        }
    }

    private static final JSONObject serializeConfig() {
        final JSONObject root = new JSONObject();

        try {
        final List<JSONObject> presets = new ArrayList<>();
        for (ItemMarkerPreset preset : PresetConfig.map.values()) {

            final JSONObject presetJson = new JSONObject();
            presetJson.put("id", preset.id);

            final JSONArray markerIds = new JSONArray();
            for (String markerId : preset.activeMarkerIds) {
                markerIds.put(markerId);
            }
            presetJson.put("activeMarkerIds", markerIds);

            presets.add(presetJson);
        }
        root.put("item_marker_presets", presets);
        } catch (JSONException e) {
            log.error("Failed to serialize item marker presets to JSON.", e);
        }

        return root;
    }

    private static final JSONObject getConfig() {
        JSONObject config;
        try {
            config = settings.readJSONFromCommon(CONFIG_NAME, false);

            if (config == null || config.length() < 1) {
                log.info("Item marker config missing or empty. Creating new JSONObject.");
                config = new JSONObject();
            }
        } catch (Exception ex) {
            log.warn("Failed to read item marker config, creating new JSONObject.");
            config = new JSONObject();
        }

        return config;
    }
}