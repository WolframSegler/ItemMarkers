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

import wfg.item_markers.config.VisualConfig;
import wfg.item_markers.item.ItemMarker;
import wfg.native_ui.internal.util.BorderRenderer;
import wfg.native_ui.ui.component.AudioFeedbackComp;
import wfg.native_ui.ui.component.HoverGlowComp;
import wfg.native_ui.ui.component.HoverGlowComp.GlowType;
import wfg.native_ui.ui.component.InteractionComp;
import wfg.native_ui.ui.component.NativeComponents;
import wfg.native_ui.ui.core.UIElementAPI;
import wfg.native_ui.ui.core.UIElementFlags.HasAudioFeedback;
import wfg.native_ui.ui.core.UIElementFlags.HasHoverGlow;
import wfg.native_ui.ui.interaction.UIClickable;
import wfg.native_ui.ui.table.WidgetAPI;
import wfg.native_ui.ui.visual.InteractiveSprite;
import wfg.native_ui.ui.visual.AbstractSpriteElement.SpriteElement;
import wfg.native_ui.util.NativeUiUtils;
import wfg.native_ui.util.RenderUtils;

public class MarkerWidget extends UIClickable<MarkerWidget> implements WidgetAPI<MarkerWidget>,
    HasAudioFeedback, HasHoverGlow
{
    private static final Color WIDGET_BG = new Color(28, 35, 48, 240);
    private static final Color WIDGET_BG_SELECTED = new Color(46, 125, 50);
    public static final BorderRenderer border = new BorderRenderer(UI_BORDER_3, true, VisualConfig.getWidgetW(), VisualConfig.getWidgetH());

    private final AudioFeedbackComp audio = comp().get(NativeComponents.AUDIO_FEEDBACK);
    private final HoverGlowComp glow = comp().get(NativeComponents.HOVER_GLOW);

    public final ItemMarker data;

    public MarkerWidget(ItemMarker marker) {
        super(VisualConfig.getWidgetW(), VisualConfig.getWidgetH(), null);

        data = marker;

        audio.hoverOnly = true;

        glow.type = GlowType.UNDERLAY;
        glow.glowBrightness = 1.2f;
        glow.flashBrightness = 1.8f;
        glow.color = base;

        buildUI();
    }

    @Override
    public void buildUI() {
        clearChildren();

        final SpriteAPI sprite = settings.getSprite(data.iconID);

        final int maxSize = VisualConfig.getWidgetW() - opad * 2;
        final float spriteW = sprite.getWidth();
        final float spriteH = sprite.getHeight();
        final float scale = Math.min(maxSize / spriteW, maxSize / spriteH);
        final int scaledW = (int) (spriteW * scale);
        final int scaledH = (int) (spriteH * scale);

        final UIElementAPI icon;
        if (VisualConfig.SIMPLE_WIDGET_ICON) {
            icon = new SpriteElement(scaledW, scaledH, sprite, null, null);

        } else {
            final InteractiveSprite interactiveIcon = new InteractiveSprite(scaledW, scaledH, sprite, null, null);
            interactiveIcon.tooltip.enabled = false;
            interactiveIcon.audio.enabled = false;
            interactiveIcon.glow.isFaderOwner = false;
            interactiveIcon.glow.fader = glow.fader;
            interactiveIcon.glow.type = GlowType.ADDITIVE;
            interactiveIcon.glow.glowBrightness = 0.8f;
            interactiveIcon.glow.flashBrightness = 1.2f;
            icon = interactiveIcon;
        }

        add(icon).inTMid(opad + (maxSize - scaledH) / 2);

        final LabelAPI nameLbl = settings.createLabel(data.name, Fonts.DEFAULT_SMALL);
        nameLbl.setColor(base);
        nameLbl.setAlignment(Alignment.MID);
        nameLbl.autoSizeToWidth(VisualConfig.getWidgetW() - pad*2);
        add(nameLbl).inBMid(hpad);

        while (NativeUiUtils.intersects(icon.pos(), nameLbl.getPosition())) {
            final String text = nameLbl.getText();
            if (text.length() <= 4) {
                remove(nameLbl);
            } else {
                final String newTxt = text.substring(0, text.length() - 4) + "..";
                nameLbl.setText(newTxt);
                nameLbl.autoSizeToWidth(VisualConfig.getWidgetW() - pad*2);
            }
        }
    }

    @Override
    public void renderBelowImpl(float alpha) {
        final Color bgColor = data.isActive ? WIDGET_BG_SELECTED : WIDGET_BG;
        if (VisualConfig.SIMPLE_WIDGET_BORDER) {
            RenderUtils.drawQuad(getX(), getY(), getWidth(), getHeight(), bgColor, alpha, false);
        } else {
            border.centerColor = bgColor;
            border.render(getX(), getY(), alpha);
        }
    }

    public InteractionComp<MarkerWidget> getInteraction() {
        return interaction;
    }

    @Override
    public void processInputImpl(List<InputEventAPI> events) {
        super.processInputImpl(events);
        if (!NativeUiUtils.containsMouse(mPos)) return;
        
        for (InputEventAPI e : events) {
            if (!e.isConsumed() && e.isKeyboardEvent() && e.getEventValue() == Keyboard.KEY_F2) {
                settings.showCodex(data.getCodexId());
            }
        }
    }
}