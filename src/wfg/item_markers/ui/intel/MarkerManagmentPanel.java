package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.UIConstants.*;

import com.fs.starfarer.api.ui.Fonts;

import wfg.native_ui.internal.ui.core.UIContainer;
import wfg.native_ui.ui.core.UIBuildableAPI;
import wfg.native_ui.ui.widget.Button;

public class MarkerManagmentPanel extends UIContainer implements UIBuildableAPI {
    protected static final int MAIN_PANEL_W = 1250;
    protected static final int MAIN_PANEL_H = screenH - 140;

    public MarkerManagmentPanel() {
        super(MAIN_PANEL_W, MAIN_PANEL_H);
        buildUI();
    }

    @Override
    public void buildUI() {
        clearChildren();

        final MarkerGrid grid = new MarkerGrid();
        grid.buildUI();
        add(grid).inBL(0f, 0f);

        final MarkerFiltersPanel filter = new MarkerFiltersPanel(grid);
        add(filter).inTL(0f, 0f);

        final Button presetBtn = new Button(150, 28, "Manage Presets", Fonts.INSIGNIA_LARGE, (btn) -> new MarkerPresetDialog(grid));
        add(presetBtn).aboveRight(grid, pad*2);
    }
}