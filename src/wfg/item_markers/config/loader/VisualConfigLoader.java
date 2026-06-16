package wfg.item_markers.config.loader;

import static wfg.native_ui.util.Globals.settings;
import static wfg.item_markers.constant.Mods.*;

import org.json.JSONObject;

import com.fs.starfarer.api.ui.Alignment;

import wfg.item_markers.config.VisualConfig;

import lunalib.lunaSettings.LunaSettings;

public class VisualConfigLoader {
    private static final String CONFIG_PATH = "./data/config/itemMarkers/visual_config.json";
    private static final String FAILURE_MESSAGE = "Failed to load config: " + CONFIG_PATH;

    private static JSONObject config;

    private VisualConfigLoader() {};
    private static final void load() {
        try {
            config = settings.getMergedJSON(CONFIG_PATH);
        } catch (Exception ex) {
            throw new RuntimeException(FAILURE_MESSAGE, ex);
        }
    }

    public static final JSONObject getConfig() {
        if (config == null) load();
        return config;
    }

    public static final void loadConfig() {
        final JSONObject root = getConfig();

        try {
            VisualConfig.CARGO_MARKER_POSITION = Alignment.valueOf(root.getString("CARGO_MARKER_POSITION"));
            VisualConfig.HIGHLIGHT_FRAME_ALPHA = (float) root.optDouble("HIGHLIGHT_FRAME_ALPHA", 1d);
            VisualConfig.ITEM_FILTER_WIDGET_SCALAR = getItemFilterWidgetScalar(root.getString("ITEM_FILTER_WIDGET_SCALAR"));

            if (settings.getModManager().isModEnabled(LUNA_LIB)) {
                loadFromLunaSettings();
            }

        } catch (Exception e) {
            throw new RuntimeException(FAILURE_MESSAGE, e);
        }
    }

    public static final void loadFromLunaSettings() {
        VisualConfig.CARGO_MARKER_POSITION = Alignment.valueOf(LunaSettings.getString(ITEM_MARKERS, "CARGO_MARKER_POSITION"));
        VisualConfig.HIGHLIGHT_FRAME_ALPHA = LunaSettings.getFloat(ITEM_MARKERS, "HIGHLIGHT_FRAME_ALPHA");
        VisualConfig.ITEM_FILTER_WIDGET_SCALAR = getItemFilterWidgetScalar(LunaSettings.getString(ITEM_MARKERS, "ITEM_FILTER_WIDGET_SCALAR"));
    }

    private static final float getItemFilterWidgetScalar(final String size) {
        return switch(size) {
            default -> 1f;
            case "TINY" -> 0.6f;
            case "SMALL" -> 0.8f;
            case "MEDIUM" -> 1f;
            case "LARGE" -> 1.2f;
            case "HUGE" -> 1.4f;
        };
    }
}