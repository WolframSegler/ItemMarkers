package wfg.item_markers.ui.script;

import static wfg.native_ui.util.UIConstants.pad;

import java.util.HashMap;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;
import com.fs.starfarer.campaign.ui.trade.CargoDataGridView;
import com.fs.starfarer.campaign.ui.trade.CargoStackView;

import rolflectionlib.util.RolfLectionUtil;
import wfg.item_markers.item.ItemMarker;
import wfg.item_markers.serializable.ItemMarkersMap;
import wfg.item_markers.ui.Sprites;
import wfg.native_ui.ui.Attachments;
import wfg.native_ui.ui.panel.CustomPanel;
import wfg.native_ui.ui.visual.SpritePanel.Base;

public class CargoTabUIBuilder implements CoreTabUIBuilder {

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
        for (Object child : (List<Object>) RolfLectionUtil.invokeMethodDirectly(CustomPanel.getChildrenNonCopyMethod, grid)) {
            if (child instanceof CargoStackView view) {
                if (IdentityMarker.isPresent(view)) continue;
                IdentityMarker.attach(view);

                final Object itemId;
                if (!view.getStack().isSpecialStack()) {
                    itemId = view.getStack().getData();
                } else {
                    final SpecialItemData special = (SpecialItemData) view.getStack().getData();
                    itemId = special.getData() == null ? special.getId() : special.getData();
                }

                if (!activeMarkers.containsKey(itemId)) continue;
                final Base icon = new Base(view, 20, 20, Sprites.MARKER, null, null);
                view.addComponent(icon.getPanel()).inTR(pad, pad);
            }
        }
    }
}