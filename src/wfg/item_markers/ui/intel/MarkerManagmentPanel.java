package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.UIConstants.*;

import com.fs.starfarer.api.ui.UIPanelAPI;

import wfg.native_ui.ui.core.UIBuildableAPI;
import wfg.native_ui.ui.panel.CustomPanel;

public class MarkerManagmentPanel extends CustomPanel implements UIBuildableAPI {
    protected static final int MAIN_PANEL_W = 1250;
    protected static final int MAIN_PANEL_H = screenH - 140;
    
    public MarkerManagmentPanel(UIPanelAPI parent) {
        super(parent, MAIN_PANEL_W, MAIN_PANEL_H);

        buildUI();
    }

    @Override
    public void buildUI() {
        clearChildren();

        final MarkerGrid grid = new MarkerGrid(m_panel, MAIN_PANEL_W, (int) (MAIN_PANEL_H * 0.67f));
        grid.buildUI();
        add(grid).inBL(0f, 0f);
    }
}