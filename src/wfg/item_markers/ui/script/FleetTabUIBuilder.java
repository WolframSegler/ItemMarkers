package wfg.item_markers.ui.script;

import static wfg.native_ui.util.UIConstants.pad;

import java.util.HashMap;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.UIComponentAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;
import com.fs.starfarer.campaign.fleet.FleetMember;

import rolflectionlib.util.RolfLectionUtil;
import wfg.item_markers.item.ItemMarker;
import wfg.item_markers.serializable.ItemMarkersMap;
import wfg.item_markers.ui.Sprites;
import wfg.native_ui.ui.Attachments;
import wfg.native_ui.ui.panel.CustomPanel;
import wfg.native_ui.ui.visual.SpritePanel.Base;

public class FleetTabUIBuilder implements CoreTabUIBuilder {

    @SuppressWarnings("unchecked")
    public void advance(float delta) {
        final MarketAPI market = Global.getSector().getCurrentlyOpenMarket();
        if (market == null) return;

        final UIPanelAPI masterTab = Attachments.getCurrentTab();
        if (masterTab == null) return;
        final SubmarketAPI submarket = (SubmarketAPI) RolfLectionUtil.getAllVariables(masterTab).stream()
            .filter(f -> f instanceof SubmarketAPI).findFirst().orElse(null);
        if (submarket == null) return;
        final String buyVerb = submarket.getPlugin().getBuyVerb();

        boolean isInBuyMode = false;
        for (Object panel : (List<Object>) RolfLectionUtil.invokeMethodDirectly(CustomPanel.getChildrenNonCopyMethod, masterTab)) {
            if (panel instanceof ButtonAPI button && button.getText().startsWith(buyVerb) && button.isHighlighted()) {
                isInBuyMode = true;
                break;
            }
        }
        if (!isInBuyMode) return;

    
        final UIPanelAPI fleetPanel = (UIPanelAPI) RolfLectionUtil.getMethodAndInvokeDirectly("getFleetPanel", masterTab);
        if (fleetPanel == null) return;

        final UIPanelAPI fleetList = (UIPanelAPI) RolfLectionUtil.getMethodAndInvokeDirectly("getList", fleetPanel);
        final List<UIComponentAPI> widgets = (List<UIComponentAPI>) RolfLectionUtil.getMethodAndInvokeDirectly("getItems", fleetList);

        final HashMap<String, ItemMarker> activeMarkers = ItemMarkersMap.instance().activeMarkers;
        for (UIComponentAPI widgetObj : widgets) {
            final UIPanelAPI widget = (UIPanelAPI) widgetObj;

            if (IdentityMarker.isPresent(widget)) continue;
            IdentityMarker.attach(widget);

            final FleetMemberAPI member = (FleetMember) RolfLectionUtil.getMethodAndInvokeDirectly("getMember", widget);

            if (!activeMarkers.containsKey(member.getHullSpec().getBaseHullId())) continue;
            final Base icon = new Base(widget, 20, 20, Sprites.MARKER, null, null);
            widget.addComponent(icon.getPanel()).inTR(pad, pad);
        }
    }
}