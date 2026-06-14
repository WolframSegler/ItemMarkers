package wfg.item_markers.config;

import com.fs.starfarer.api.ui.Alignment;

import wfg.item_markers.config.loader.VisualConfigLoader;

public class VisualConfig {
    private VisualConfig() {};

    static {
        VisualConfigLoader.loadConfig();
    }

    public static Alignment CARGO_MARKER_POSITION;
}