package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.Globals.settings;
import static wfg.native_ui.util.UIConstants.*;

import java.awt.Color;

import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TextFieldAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.ui.UIComponentAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;

import wfg.item_markers.item.MarkerFilters;
import wfg.item_markers.item.MarkerType;
import wfg.item_markers.item.MarkerFilters.ActiveFilters;
import wfg.item_markers.item.MarkerFilters.CommodityFilters;
import wfg.native_ui.ui.ComponentFactory;
import wfg.native_ui.ui.core.UIBuildableAPI;
import wfg.native_ui.ui.functional.Button;
import wfg.native_ui.ui.panel.CustomPanel;
import wfg.native_ui.ui.widget.MultiSelect;
import wfg.native_ui.ui.widget.RadioPanel;
import wfg.native_ui.ui.widget.RadioPanel.LayoutMode;

public class MarkerFiltersPanel extends CustomPanel {
    private static final int nameFieldW = 192;
    private static final int btnH = 24;
    private static final int btnW = 100;
    private static final Color nearBlack = new Color(20, 20, 25);
    private static final String emptyNameFieldTxt = "Search...";

    private final UIBuildableAPI target;
    private final TextFieldAPI nameField;
    
    public MarkerFiltersPanel(UIPanelAPI parent, UIBuildableAPI target) {
        super(parent, MarkerManagmentPanel.MAIN_PANEL_W, (int) (MarkerManagmentPanel.MAIN_PANEL_H * 0.33f));

        this.target = target;

        final TooltipMakerAPI uiBuilder = ComponentFactory.createTooltip(nameFieldW, false);
        
        nameField = uiBuilder.addTextField(nameFieldW, btnH, Fonts.DEFAULT_SMALL, pad);
        nameField.setText(MarkerFilters.searchQuery);
        nameField.setMaxChars(30);
        nameField.setLimitByStringWidth(true);
        nameField.setUndoOnEscape(true);
        nameField.getPosition().inBL(0f, 0f);
        ComponentFactory.addTooltip(uiBuilder, btnH, false, m_panel).inTL(pad, pad);

        final RadioPanel activeFilterButtons = new RadioPanel(m_panel, btnW*3 + opad*2, btnH, LayoutMode.HORIZONTAL)
            .addOption("All", MarkerFilters.activeFilter == ActiveFilters.ALL)
            .addOption("Active", MarkerFilters.activeFilter == ActiveFilters.ACTIVE_ONLY)
            .addOption("Inactive", MarkerFilters.activeFilter == ActiveFilters.INACTIVE_ONLY);
        activeFilterButtons.optionSelected = (code) -> {
            MarkerFilters.activeFilter = ActiveFilters.values()[code];
            target.buildUI();
        };
        activeFilterButtons.buildUI();
        for (Button btn : activeFilterButtons.getButtons()) {
            btn.setHighlightBrightness(0f);
            btn.bgColor = nearBlack;
        }
        add(activeFilterButtons).rightOfMid(uiBuilder, opad);

        final LabelAPI typeLbl = settings.createLabel("Type", Fonts.ORBITRON_16);
        add(typeLbl).belowLeft(uiBuilder, opad);

        final MultiSelect typeFilterButtons = new MultiSelect(m_panel, btnW*6 + opad*4, btnH, MarkerType.getLabels(), LayoutMode.HORIZONTAL);
        typeFilterButtons.onSelected = (multi) -> {

            MarkerFilters.typeFilter.clear();
            for (int idx : multi.getSelectedIndexes()) {
                MarkerFilters.typeFilter.add(MarkerType.ALL_MARKERS[idx]);
            }
            target.buildUI();
        };
        typeFilterButtons.buildUI();

        for (Button btn : typeFilterButtons.getButtons()) {
            final int index = (Integer) btn.customData;
            final boolean contains = MarkerFilters.typeFilter.contains(MarkerType.ALL_MARKERS[index]);

            if (contains) typeFilterButtons.select(index);
            btn.setHighlightBrightness(0f);
            btn.bgColor = nearBlack;
        }
        add(typeFilterButtons).rightOfMid((UIComponentAPI) typeLbl, hpad);

        final LabelAPI commodityLbl = settings.createLabel("Commodity", Fonts.ORBITRON_16);
        add(commodityLbl).belowLeft((UIComponentAPI) typeLbl, opad);

        final RadioPanel comFilterButtons = new RadioPanel(m_panel, btnW*3 + opad*2, btnH, LayoutMode.HORIZONTAL)
            .addOption("All", MarkerFilters.comFilters == CommodityFilters.ALL)
            .addOption("Economic", MarkerFilters.comFilters == CommodityFilters.ECONOMY)
            .addOption("Non Economic", MarkerFilters.comFilters == CommodityFilters.NON_ECONOMY);
        comFilterButtons.optionSelected = (code) -> {
            MarkerFilters.comFilters = CommodityFilters.values()[code];
            target.buildUI();
        };
        comFilterButtons.buildUI();
        for (Button btn : comFilterButtons.getButtons()) {
            btn.setHighlightBrightness(0f);
            btn.bgColor = nearBlack;
        }
        add(comFilterButtons).rightOfMid((UIComponentAPI) commodityLbl, hpad);
    }

    @Override
    public void advance(float delta) {
        super.advance(delta);

        if (nameField.hasFocus()) {
            final String current = nameField.getText();
            final boolean equalsEmptyFieldTxt = current.equals(emptyNameFieldTxt);

            if (!current.equals(MarkerFilters.searchQuery) && !equalsEmptyFieldTxt) {
                MarkerFilters.searchQuery = current;
                target.buildUI();
            } else if (equalsEmptyFieldTxt) {
                nameField.setText("");
                nameField.setColor(base);
            }
        } else {
            if (nameField.getText().isEmpty()) {
                nameField.setText(emptyNameFieldTxt);
                nameField.setColor(gray);
            }
        }
    }
}