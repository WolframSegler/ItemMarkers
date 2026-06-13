package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.Globals.settings;
import static wfg.native_ui.util.UIConstants.*;

import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;

import wfg.item_markers.item.ItemMarker;
import wfg.item_markers.item.MarkerFilters;
import wfg.item_markers.item.MarkerFilters.ActiveFilters;
import wfg.item_markers.item.MarkerFilters.CommodityFilters;
import wfg.native_ui.ui.table.GridTable;

public class MarkerGrid extends GridTable<ItemMarker, MarkerWidget> {

    public MarkerGrid(UIPanelAPI parent) {
        super(parent, MarkerManagmentPanel.MAIN_PANEL_W, (int) (MarkerManagmentPanel.MAIN_PANEL_H * 0.67f), MarkerWidget.WIDTH, MarkerWidget.HEIGHT, opad);

        uniformOuterGap = true;
        justifyGrid = true;
        isSelectionEnabled = true;
        
        buildUI();
    }

    protected final List<ItemMarker> getDataList() {
        final List<ItemMarker> list = ItemMarker.getAllMarkersCopy();

        list.removeIf(this::filterMarker);
        list.sort(MarkerGrid::compareMarkers);

        return list;
    }

    protected MarkerWidget createWidget(ItemMarker item, int index) {
        final MarkerWidget widget = new MarkerWidget(container, item);

        return widget;
    }

    protected void onWidgetClicked(MarkerWidget source) {
        source.data.isActive = !source.data.isActive;
        source.buildUI();
        Global.getSoundPlayer().playUISound("ui_button_pressed", 1f, 1f);
    }

    protected String getEmptyMessage() {
        return "No matches";
    }

    
    private final boolean filterMarker(ItemMarker marker) {
        if (MarkerFilters.activeFilter == ActiveFilters.ACTIVE_ONLY && !marker.isActive) return true;
        if (MarkerFilters.activeFilter == ActiveFilters.INACTIVE_ONLY && marker.isActive) return true;
        if (!MarkerFilters.typeFilter.contains(marker.type)) return true;

        switch (marker.type) {
        case COMMODITY:
            final CommoditySpecAPI spec = settings.getCommoditySpec(marker.itemID);
            if (spec.isNonEcon() && MarkerFilters.comFilters == CommodityFilters.ECONOMY) return true;
            if (!spec.isNonEcon() && MarkerFilters.comFilters == CommodityFilters.NON_ECONOMY) return true;
            break;
    
            default: break;
        }

        final String query = MarkerFilters.searchQuery.trim().toLowerCase();
        if (!query.isEmpty()) {
            final String name = marker.name.toLowerCase();
            final String id = marker.itemID.toLowerCase();
            if (!name.contains(query) && !id.contains(query)) return true;
        }
        if (marker.getCodexId() == null || marker.getCodexId().isEmpty()) return true;

        return false;
    }

    private static final int compareMarkers(ItemMarker a, ItemMarker b) {
        if (a.isActive != b.isActive) return a.isActive ? -1 : 1;
        
        final int typeCmp = Integer.compare(a.type.ordinal(), b.type.ordinal());
        if (typeCmp != 0) return typeCmp;
        
        return a.name.compareTo(b.name);
    }
}