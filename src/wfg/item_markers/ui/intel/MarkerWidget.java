package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.UIConstants.*;
import static wfg.native_ui.util.Globals.settings;

import java.awt.Color;

import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.impl.codex.CodexDataV2;
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
import wfg.native_ui.ui.component.TooltipComp;
import wfg.native_ui.ui.core.UIElementFlags.HasAudioFeedback;
import wfg.native_ui.ui.core.UIElementFlags.HasHoverGlow;
import wfg.native_ui.ui.core.UIElementFlags.HasTooltip;
import wfg.native_ui.ui.functional.UIClickable;
import wfg.native_ui.ui.table.WidgetAPI;
import wfg.native_ui.ui.visual.SpritePanelWithTp;

public class MarkerWidget extends UIClickable<MarkerWidget> implements WidgetAPI<MarkerWidget>,
    HasAudioFeedback, HasHoverGlow, HasTooltip
{
    private static final Color WIDGET_BG = new Color(28, 35, 48, 240);
    private static final Color WIDGET_BG_SELECTED = new Color(60, 80, 120, 255);

    public static final int WIDTH = 110;
    public static final int HEIGHT = 150;

    private final AudioFeedbackComp audio = comp().get(NativeComponents.AUDIO_FEEDBACK);
    private final HoverGlowComp glow = comp().get(NativeComponents.HOVER_GLOW);
    private final BorderRenderer border = new BorderRenderer(UI_BORDER_3, true, WIDTH, HEIGHT);
    public final TooltipComp tooltip = comp().get(NativeComponents.TOOLTIP);

    public final ItemMarker data;

    public MarkerWidget(UIPanelAPI parent, ItemMarker marker) {
        super(parent, WIDTH, HEIGHT, null);

        data = marker;

        audio.hoverOnly = true;

        glow.type = GlowType.UNDERLAY;
        glow.overlayBrightness = 0.6f;
        glow.color = base;

        border.centerColor = WIDGET_BG;

        tooltip.width = 300f;
        tooltip.codexID = switch (marker.type) {
            case COMMODITY -> CodexDataV2.getCommodityEntryId(data.itemID);
            case SPECIAL_ITEM -> CodexDataV2.getItemEntryId(data.itemID);
            case SHIP -> CodexDataV2.getShipEntryId(data.itemID);
            case FIGHTER -> CodexDataV2.getFighterEntryId(data.itemID);
            case MOD_SPEC -> CodexDataV2.getHullmodEntryId(data.itemID);
            case WEAPON -> CodexDataV2.getWeaponEntryId(data.itemID);
            default -> null;
        };

        tooltip.builder = (tp, expanded) -> {
            tp.addPara(data.itemID + " _ " + data.name + " _ " + data.iconID, BUTTON_H);
        };

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
        add(icon).inTMid(hpad + (maxSize - scaledH) / 2);

        final LabelAPI nameLbl = settings.createLabel(data.name, Fonts.DEFAULT_SMALL);
        nameLbl.setColor(base);
        nameLbl.setAlignment(Alignment.MID);
        nameLbl.autoSizeToWidth(WIDTH - opad);
        add(nameLbl).inBMid(opad);

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
}