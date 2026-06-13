package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.UIConstants.*;

import java.util.LinkedHashSet;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.codex.CodexIntelAdder;
import com.fs.starfarer.api.ui.UIPanelAPI;

import rolflectionlib.util.RolfLectionUtil;
import wfg.item_markers.item.ItemMarker;
import wfg.native_ui.ui.table.GridTable;

public class MarkerGrid extends GridTable<ItemMarker, MarkerWidget> {
    @SuppressWarnings("unchecked")
    private final LinkedHashSet<String> entries = (LinkedHashSet<String>) RolfLectionUtil.getPrivateVariable("unlockedEntries", CodexIntelAdder.get());

    public MarkerGrid(UIPanelAPI parent, int w, int h) {
        super(parent, w, h, MarkerWidget.WIDTH, MarkerWidget.HEIGHT, opad);

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

        // final int col = index % calculateColumns();
        // widget.tooltip.positioner = (tp, exp) -> {
        //     NativeUiUtils.anchorPanel(tp, widget.getPanel(), (col > 2 ?
        //         AnchorType.LeftTop : AnchorType.RightTop), opad
        //     );
        // };

        return widget;
    }

    protected void onWidgetClicked(MarkerWidget source) {
        source.data.isActive = !source.data.isActive;
        source.buildUI();
        Global.getSoundPlayer().playUISound("ui_button_pressed", 1f, 1f);
    }

    protected String getEmptyMessage() {
        return "No markers";
    }

    
    private final boolean filterMarker(ItemMarker marker) {
        if (entries.contains(marker.itemID)) return true;

        return false;
    }

    private static final int compareMarkers(ItemMarker a, ItemMarker b) {
        if (a.isActive != b.isActive) return a.isActive ? -1 : 1;
        
        final int typeCmp = Integer.compare(a.type.ordinal(), b.type.ordinal());
        if (typeCmp != 0) {
            return typeCmp;
        }
        
        return a.itemID.compareTo(b.itemID);
    }
}