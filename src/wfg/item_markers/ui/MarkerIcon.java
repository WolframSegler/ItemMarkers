package wfg.item_markers.ui;

import wfg.item_markers.config.VisualConfig;
import wfg.native_ui.ui.visual.AbstractSpriteElement;

public final class MarkerIcon extends AbstractSpriteElement<MarkerIcon> {
    private static final float SIZE = 20f;

    public MarkerIcon() {
        super(SIZE, SIZE, Sprites.MARKER, null, null);
    }
    
    @Override
    public void advanceImpl(float delta) {
        super.advanceImpl(delta);

        if (VisualConfig.UPDATE_MARKER_POSITION_EVERY_FRAME) mPos.recompute();
    };
}