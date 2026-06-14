package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.UIConstants.*;
import static wfg.native_ui.util.Globals.settings;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.UIComponentAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;

import wfg.item_markers.item.ItemMarker;
import wfg.native_ui.internal.util.BorderRenderer;
import wfg.native_ui.ui.component.AudioFeedbackComp;
import wfg.native_ui.ui.component.HoverGlowComp;
import wfg.native_ui.ui.component.HoverGlowComp.GlowType;
import wfg.native_ui.ui.component.InteractionComp;
import wfg.native_ui.ui.component.NativeComponents;
import wfg.native_ui.ui.core.UIElementFlags.HasAudioFeedback;
import wfg.native_ui.ui.core.UIElementFlags.HasHoverGlow;
import wfg.native_ui.ui.functional.UIClickable;
import wfg.native_ui.ui.table.WidgetAPI;
import wfg.native_ui.ui.visual.SpritePanelWithTp;
import wfg.native_ui.util.NativeUiUtils;

public class MarkerWidget extends UIClickable<MarkerWidget> implements WidgetAPI<MarkerWidget>,
    HasAudioFeedback, HasHoverGlow
{
    private static final Color WIDGET_BG = new Color(28, 35, 48, 240);
    private static final Color WIDGET_BG_SELECTED = new Color(46, 125, 50);

    public static final int WIDTH = 100;
    public static final int HEIGHT = 140;

    private final AudioFeedbackComp audio = comp().get(NativeComponents.AUDIO_FEEDBACK);
    private final HoverGlowComp glow = comp().get(NativeComponents.HOVER_GLOW);
    private final BorderRenderer border = new BorderRenderer(UI_BORDER_3, true, WIDTH, HEIGHT);

    public final ItemMarker data;

    public MarkerWidget(UIPanelAPI parent, ItemMarker marker) {
        super(parent, WIDTH, HEIGHT, null);

        data = marker;

        audio.hoverOnly = true;

        glow.type = GlowType.UNDERLAY;
        glow.overlayBrightness = 0.6f;
        glow.color = base;

        border.centerColor = WIDGET_BG;

        buildUI();
    }

    @Override
    public void buildUI() {
        clearChildren();

        final SpriteAPI sprite = settings.getSprite(data.iconID);

        final int maxSize = WIDTH - opad * 2;
        final float spriteW = sprite.getWidth();
        final float spriteH = sprite.getHeight();
        final float scale = Math.min(maxSize / spriteW, maxSize / spriteH);
        final int scaledW = (int) (spriteW * scale);
        final int scaledH = (int) (spriteH * scale);

        final SpritePanelWithTp icon = new SpritePanelWithTp(m_panel, scaledW, scaledH, sprite, null, null);
        icon.tooltip.enabled = false;
        icon.audio.enabled = false;
        icon.glow.isFaderOwner = false;
        icon.glow.fader = glow.fader;
        icon.glow.type = GlowType.ADDITIVE;
        icon.glow.additiveBrightness = 0.8f;
        add(icon).inTMid(opad + (maxSize - scaledH) / 2);

        final LabelAPI nameLbl = settings.createLabel(data.name, Fonts.DEFAULT_SMALL);
        nameLbl.setColor(base);
        nameLbl.setAlignment(Alignment.MID);
        nameLbl.autoSizeToWidth(WIDTH - pad*2);
        add(nameLbl).inBMid(hpad);

        border.centerColor = data.isActive ? WIDGET_BG_SELECTED : WIDGET_BG;
    }

    @Override
    public void renderBelow(float alpha) {
        super.renderBelow(alpha);

        border.render(pos.getX(), pos.getY(), alpha);
    }

    public UIComponentAPI getElement() {
        return m_panel;
    }

    public InteractionComp<MarkerWidget> getInteraction() {
        return interaction;
    }

    @Override
    public void processInput(List<InputEventAPI> events) {
        super.processInput(events);
        if (!NativeUiUtils.containsMouse(pos)) return;
        
        for (InputEventAPI e : events) {
            if (!e.isConsumed() && e.isKeyboardEvent() && e.getEventValue() == Keyboard.KEY_F2) {
                settings.showCodex(data.getCodexId());
            }
        }
    }
}