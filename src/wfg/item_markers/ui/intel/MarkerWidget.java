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

import wfg.item_markers.config.VisualConfig;
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
import wfg.native_ui.ui.panel.CustomPanel;
import wfg.native_ui.ui.table.WidgetAPI;
import wfg.native_ui.ui.visual.SpritePanelWithTp;
import wfg.native_ui.ui.visual.SpritePanel.Base;
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

    public MarkerWidget(UIPanelAPI parent, ItemMarker marker) {
        super(parent, VisualConfig.getWidgetW(), VisualConfig.getWidgetH(), null);

        data = marker;

        audio.hoverOnly = true;

        glow.type = GlowType.UNDERLAY;
        glow.overlayBrightness = 0.6f;
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

        final CustomPanel icon;
        if (VisualConfig.SIMPLE_WIDGET_ICON) {
            icon = new Base(m_panel, scaledW, scaledH, sprite, null, null);

        } else {
            final SpritePanelWithTp tpIcon = new SpritePanelWithTp(m_panel, scaledW, scaledH, sprite, null, null);
            tpIcon.tooltip.enabled = false;
            tpIcon.audio.enabled = false;
            tpIcon.glow.isFaderOwner = false;
            tpIcon.glow.fader = glow.fader;
            tpIcon.glow.type = GlowType.ADDITIVE;
            tpIcon.glow.additiveBrightness = 0.8f;
            icon = tpIcon;
        }

        add(icon).inTMid(opad + (maxSize - scaledH) / 2);

        final LabelAPI nameLbl = settings.createLabel(data.name, Fonts.DEFAULT_SMALL);
        nameLbl.setColor(base);
        nameLbl.setAlignment(Alignment.MID);
        nameLbl.autoSizeToWidth(VisualConfig.getWidgetW() - pad*2);
        add(nameLbl).inBMid(hpad);

        while (NativeUiUtils.intersects(icon.getPos(), nameLbl.getPosition())) {
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
    public void renderBelow(float alpha) {
        super.renderBelow(alpha);

        final Color bgColor = data.isActive ? WIDGET_BG_SELECTED : WIDGET_BG;
        if (VisualConfig.SIMPLE_WIDGET_BORDER) {
            RenderUtils.drawQuad(pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(), bgColor, alpha, false);
        } else {
            border.centerColor = bgColor;
            border.render(pos.getX(), pos.getY(), alpha);
        }
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