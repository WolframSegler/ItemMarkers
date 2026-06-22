package wfg.item_markers.config;

import com.fs.starfarer.api.ui.Alignment;

import wfg.item_markers.config.loader.VisualConfigLoader;

public class VisualConfig {
    private VisualConfig() {};
    static { VisualConfigLoader.loadConfig(); }

    private static final int WIDTH = 100;
    private static final int HEIGHT = 140;

    public static Alignment CARGO_MARKER_POSITION;
    public static float HIGHLIGHT_FRAME_ALPHA;
    public static float ITEM_FILTER_WIDGET_SCALAR;
    public static boolean SIMPLE_WIDGET_BORDER;
    public static boolean SIMPLE_WIDGET_ICON;

    public static int getWidgetW() { return (int) (WIDTH * ITEM_FILTER_WIDGET_SCALAR); }
    public static int getWidgetH() { return (int) (HEIGHT * ITEM_FILTER_WIDGET_SCALAR); }
}