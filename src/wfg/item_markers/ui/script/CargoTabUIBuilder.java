package wfg.item_markers.ui.script;

import static wfg.native_ui.util.UIConstants.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.UIComponentAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;
import com.fs.starfarer.campaign.ui.trade.CargoDataGridView;
import com.fs.starfarer.campaign.ui.trade.CargoStackView;

import rolflectionlib.util.RolfLectionUtil;
import wfg.item_markers.config.VisualConfig;
import wfg.item_markers.item.ItemMarker;
import wfg.item_markers.serializable.ItemMarkersMap;
import wfg.item_markers.ui.Sprites;
import wfg.native_ui.ui.Attachments;
import wfg.native_ui.ui.MethodFields;
import wfg.native_ui.ui.util.IdentityMarker;
import wfg.native_ui.ui.visual.AbstractSpriteElement.SpriteElement;
import wfg.utils.ui.CoreTabUIBuilder;

public final class CargoTabUIBuilder implements CoreTabUIBuilder {

    @Override
    @SuppressWarnings("unchecked")
    public void advance(float delta) {
        final MarketAPI market = Global.getSector().getCurrentlyOpenMarket();
        if (market == null) return;

        final UIPanelAPI masterTab = Attachments.getCurrentTab();
        if (masterTab == null) return;

        final UIPanelAPI tradePanel = (UIPanelAPI) RolfLectionUtil.getMethodAndInvokeDirectly(
            "getTradePanel", masterTab);
        if (tradePanel == null) return;

        final UIPanelAPI entityCargoDisplay = (UIPanelAPI) RolfLectionUtil.getMethodAndInvokeDirectly(
            "getEntityCargoDisplay", tradePanel);
        if (entityCargoDisplay == null) return;
        
        final CargoDataGridView grid = (CargoDataGridView) RolfLectionUtil.getMethodAndInvokeDirectly(
            "getCargoDataView", entityCargoDisplay);

        final HashMap<String, ItemMarker> activeMarkers = ItemMarkersMap.instance().activeMarkers;
        for (UIComponentAPI child : MethodFields.getChildrenNonCopy(grid)) {
            if (child instanceof CargoStackView view) {
                if (IdentityMarker.isPresent(view)) continue;
                IdentityMarker.attach(view);

                final ItemMarker marker = activeMarkers.get(getStackId(view.getStack()));
                if (marker == null || !marker.shouldHighlight()) continue;
                final SpriteElement icon = new SpriteElement(20, 20, Sprites.MARKER, null, null);
                
                icon.attachTo(view);
                switch (VisualConfig.CARGO_MARKER_POSITION) {
                    default -> icon.pos().inTL(pad, pad);
                    case TL -> icon.pos().inTL(pad, pad);
                    case TR -> icon.pos().inTR(pad, pad);
                    case BL -> icon.pos().inBL(pad, pad);
                    case BR -> icon.pos().inBR(pad, pad);
                }

                if (VisualConfig.HIGHLIGHT_FRAME_ALPHA > 0f) {
                    final SpriteElement hue = new SpriteElement(view.getWidth(), view.getHeight(), Sprites.HUE_FRAME, null, null);
                    hue.attachTo(view).inBL(0f, 0f);
                    hue.texColor = new Color(1f, 1f, 1f, VisualConfig.HIGHLIGHT_FRAME_ALPHA);
                }
            }
        }

        final UIPanelAPI marketPicker = (UIPanelAPI) MethodFields
            .getChildrenNonCopy(tradePanel).stream()
            .filter(c -> RolfLectionUtil.hasMethodOfName("freezeOpenTab", c))
            .findFirst().orElse(null);
        if (marketPicker == null || IdentityMarker.isPresent(marketPicker)) return;
        IdentityMarker.attach(marketPicker);

        final List<ButtonAPI> submarketButtons = (List<ButtonAPI>) RolfLectionUtil.getAllVariables(marketPicker).stream()
            .filter(f -> f instanceof List).findFirst().orElse(null);

        for (ButtonAPI btn : submarketButtons) {
            
            final CargoAPI cargo = ((SubmarketAPI)btn.getCustomData()).getCargo();

            for (CargoStackAPI stack : cargo.getStacksCopy()) {
                final ItemMarker marker = activeMarkers.get(getStackId(stack));
                if (marker != null && marker.shouldHighlight()) {
                    final SpriteElement icon = new SpriteElement(20, 20, Sprites.MARKER, null, null);
                    icon.attachTo(marketPicker).rightOfTop(btn, -16);
                    break;
                }
            }
        }
    }

    private static final Object getStackId(CargoStackAPI stack) {
        if (!stack.isSpecialStack()) {
            return stack.getData();
        } else {
            final SpecialItemData special = (SpecialItemData) stack.getData();
            return special.getData() == null ? special.getId() : special.getData();
        }
    }
}