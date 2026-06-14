package wfg.item_markers.ui;

import static wfg.native_ui.util.Globals.settings;

import com.fs.starfarer.api.graphics.SpriteAPI;

public class Sprites {
    public static final SpriteAPI MARKER = settings.getSprite("intel", "important_accepted_mission");
    public static final SpriteAPI HUE_FRAME = settings.getSprite("ui", "hue_frame");
}