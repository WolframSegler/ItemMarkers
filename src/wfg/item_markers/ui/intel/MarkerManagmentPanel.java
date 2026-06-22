package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.UIConstants.*;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.UIPanelAPI;

import wfg.native_ui.ui.core.UIBuildableAPI;
import wfg.native_ui.ui.panel.CustomPanel;

public class MarkerManagmentPanel extends CustomPanel implements UIBuildableAPI {
    protected static final int MAIN_PANEL_W = 1250;
    protected static final int MAIN_PANEL_H = screenH - 140;

    private long time = 0L;                 // start time of current frame
    private long totalTime = 0L;            // sum of all frame times (nanoseconds)
    private int count = 0;                  // number of completed frames

    public MarkerManagmentPanel(UIPanelAPI parent) {
        super(parent, MAIN_PANEL_W, MAIN_PANEL_H);
        buildUI();
    }

    @Override
    public void buildUI() {
        clearChildren();

        final MarkerGrid grid = new MarkerGrid(m_panel);
        grid.buildUI();
        add(grid).inBL(0f, 0f);

        final MarkerFiltersPanel filter = new MarkerFiltersPanel(m_panel, grid);
        add(filter).inTL(0f, 0f);
    }

    @Override
    public void renderBelow(float alpha) {
        time = System.nanoTime();
        super.renderBelow(alpha);
    }

    @Override
    public void render(float alpha) {
        super.render(alpha);

        final long delta = System.nanoTime() - time;
        totalTime += delta;
        count++;

        final double avgMs = (totalTime / (double) count) / 1_000_000d;

        Global.getLogger(getClass()).info("Avg frame time: " + avgMs + " ms");
    }
}