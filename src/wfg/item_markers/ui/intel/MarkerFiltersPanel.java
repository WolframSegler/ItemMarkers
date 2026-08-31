package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.Globals.settings;
import static wfg.native_ui.util.UIConstants.*;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponSize;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.loading.WingRole;
import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TextFieldAPI;
import com.fs.starfarer.api.ui.UIComponentAPI;

import wfg.item_markers.item.MarkerFilters;
import wfg.item_markers.item.MarkerType;
import wfg.item_markers.item.MarkerFilters.ActiveFilters;
import wfg.native_ui.internal.ui.core.UIContainer;
import wfg.native_ui.ui.core.UIBuildableAPI;
import wfg.native_ui.ui.widget.Button;
import wfg.native_ui.ui.widget.MultiSelect;
import wfg.native_ui.ui.widget.RadioPanel;
import wfg.native_ui.ui.widget.RadioPanel.LayoutMode;
import wfg.native_ui.util.NativeUiUtils;

public class MarkerFiltersPanel extends UIContainer {
    private static final int nameFieldW = 192;
    private static final int btnH = 24;
    private static final int btnW = 100;
    private static final Color nearBlack = new Color(20, 20, 25);
    private static final String emptyNameFieldTxt = "Ctrl-F to search";

    private final UIBuildableAPI target;
    private final TextFieldAPI nameField;
    
    public MarkerFiltersPanel(UIBuildableAPI target) {
        super(MarkerManagmentPanel.MAIN_PANEL_W, (int) (MarkerManagmentPanel.MAIN_PANEL_H * 0.33f));

        this.target = target;
        
        nameField = settings.createTextField(MarkerFilters.searchQuery, Fonts.DEFAULT_SMALL);
        nameField.getPosition().setSize(nameFieldW, btnH);
        nameField.setMaxChars(30);
        nameField.setLimitByStringWidth(true);
        nameField.setUndoOnEscape(true);
        nameField.getPosition().inBL(0f, 0f);
        add(nameField).inTL(pad, pad);

        final RadioPanel activeFilterButtons = new RadioPanel(btnW*3 + opad*2, btnH, LayoutMode.HORIZONTAL)
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
        add(activeFilterButtons).rightOfBottom(nameField, opad);

        final LabelAPI typeLbl = settings.createLabel("Type", Fonts.ORBITRON_16);
        add(typeLbl).belowLeft(nameField, opad);

        final MultiSelect typeFilterButtons = new MultiSelect(btnW*6 + opad*4, btnH, MarkerType.getLabels(), LayoutMode.HORIZONTAL);
        typeFilterButtons.onSelected = (button) -> {

            final MarkerType marker = MarkerType.ALL_MARKERS[(Integer) button.customData];
            if (button.isChecked()) {
                MarkerFilters.typeFilter.add(marker);
            } else {
                MarkerFilters.typeFilter.remove(marker);
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

        final LabelAPI shipLbl = settings.createLabel("Ship & Fighter", Fonts.ORBITRON_16);
        add(shipLbl).belowLeft((UIComponentAPI) typeLbl, opad);

        final List<String> hullSizeStrings = Arrays.asList("Frigate", "Destroyer", "Cruiser", "Capital");
        final MultiSelect hullSizeFilterButtons = new MultiSelect(btnW*4 + opad, btnH, hullSizeStrings, LayoutMode.HORIZONTAL);
        hullSizeFilterButtons.onSelected = (button) -> {

            final HullSize marker = HullSize.values()[((Integer) button.customData) + 2];
            if (button.isChecked()) {
                MarkerFilters.hullSizeFilters.add(marker);
            } else {
                MarkerFilters.hullSizeFilters.remove(marker);
            }

            target.buildUI();
        };
        hullSizeFilterButtons.buildUI();

        for (Button btn : hullSizeFilterButtons.getButtons()) {
            final int index = (Integer) btn.customData;
            final boolean contains = MarkerFilters.hullSizeFilters.contains(HullSize.values()[index + 2]);

            if (contains) hullSizeFilterButtons.select(index);
            btn.setHighlightBrightness(0f);
            btn.bgColor = nearBlack;
        }
        add(hullSizeFilterButtons).rightOfMid((UIComponentAPI) shipLbl, hpad);

        final List<String> wingRoles = Arrays.asList("Bomber", "Fighter", "Interceptor", "Assault", "Support");
        final MultiSelect wingRoleButtons = new MultiSelect(btnW*6 + opad, btnH, wingRoles, LayoutMode.HORIZONTAL);
        wingRoleButtons.onSelected = (button) -> {

            final WingRole marker = WingRole.values()[(Integer) button.customData];
            if (button.isChecked()) {
                MarkerFilters.wingRoleFilters.add(marker);
            } else {
                MarkerFilters.wingRoleFilters.remove(marker);
            }

            target.buildUI();
        };
        wingRoleButtons.buildUI();

        for (Button btn : wingRoleButtons.getButtons()) {
            final int index = (Integer) btn.customData;
            final boolean contains = MarkerFilters.wingRoleFilters.contains(WingRole.values()[index]);

            if (contains) wingRoleButtons.select(index);
            btn.setHighlightBrightness(0f);
            btn.bgColor = nearBlack;
        }
        add(wingRoleButtons).rightOfMid(hullSizeFilterButtons, opad);

        final MultiSelect manufacturerFilterButtons = new MultiSelect(btnW*5 + opad*2, btnH, MarkerFilters.manufacturers, LayoutMode.HORIZONTAL);
        manufacturerFilterButtons.onSelected = (button) -> {

            MarkerFilters.manufacturerFilters.clear();
            for (String key : manufacturerFilterButtons.getSelectedStrings()) {
                MarkerFilters.manufacturerFilters.add(key);
            }
            target.buildUI();
        };
        manufacturerFilterButtons.buildUI();

        for (Button btn : manufacturerFilterButtons.getButtons()) {
            final int index = (Integer) btn.customData;
            final boolean contains = MarkerFilters.manufacturerFilters.contains(MarkerFilters.manufacturers.get(index));

            if (contains) manufacturerFilterButtons.select(index);
            btn.setHighlightBrightness(0f);
            btn.bgColor = nearBlack;
        }
        add(manufacturerFilterButtons).belowLeft(hullSizeFilterButtons, hpad);

        final LabelAPI weaponLbl = settings.createLabel("Weapon", Fonts.ORBITRON_16);
        add(weaponLbl).belowLeft((UIComponentAPI) shipLbl, opad*2 + btnH);

        final List<String> weaponSizeStrings = Arrays.asList(WeaponSize.SMALL.getDisplayName(), WeaponSize.MEDIUM.getDisplayName(), WeaponSize.LARGE.getDisplayName());
        final MultiSelect weaponSizeButtons = new MultiSelect(btnW*3 + opad*2, btnH, weaponSizeStrings, LayoutMode.HORIZONTAL);
        weaponSizeButtons.onSelected = (button) -> {

            final WeaponSize marker = WeaponSize.values()[(Integer) button.customData];
            if (button.isChecked()) {
                MarkerFilters.weaponSizeFilters.add(marker);
            } else {
                MarkerFilters.weaponSizeFilters.remove(marker);
            }

            target.buildUI();
        };
        weaponSizeButtons.buildUI();

        for (Button btn : weaponSizeButtons.getButtons()) {
            final int index = (Integer) btn.customData;
            final boolean contains = MarkerFilters.weaponSizeFilters.contains(WeaponSize.values()[index]);

            if (contains) weaponSizeButtons.select(index);
            btn.setHighlightBrightness(0f);
            btn.bgColor = nearBlack;
        }
        add(weaponSizeButtons).rightOfMid((UIComponentAPI) weaponLbl, hpad);

        final List<String> damageTypeStrings = Arrays.asList(
            DamageType.KINETIC.getDisplayName(), DamageType.HIGH_EXPLOSIVE.getDisplayName(),
            DamageType.FRAGMENTATION.getDisplayName(), DamageType.ENERGY.getDisplayName(),
            DamageType.OTHER.getDisplayName()
        );
        final MultiSelect damageTypeButtons = new MultiSelect(btnW*5 + opad*4, btnH, damageTypeStrings, LayoutMode.HORIZONTAL);
        damageTypeButtons.onSelected = (button) -> {

            final DamageType marker = DamageType.values()[(Integer) button.customData];
            if (button.isChecked()) {
                MarkerFilters.weaponDamageFilters.add(marker);
            } else {
                MarkerFilters.weaponDamageFilters.remove(marker);
            }

            target.buildUI();
        };
        damageTypeButtons.buildUI();

        for (Button btn : damageTypeButtons.getButtons()) {
            final int index = (Integer) btn.customData;
            final boolean contains = MarkerFilters.weaponDamageFilters.contains(DamageType.values()[index]);

            if (contains) damageTypeButtons.select(index);
            btn.setHighlightBrightness(0f);
            btn.bgColor = nearBlack;
        }
        add(damageTypeButtons).belowLeft(weaponSizeButtons, hpad);
    }

    @Override
    public void advanceImpl(float delta) {
        super.advanceImpl(delta);

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
            nameField.setColor(gray);
            if (nameField.getText().isBlank()) {
                nameField.setText(emptyNameFieldTxt);
            }
        }
    }

    @Override
    public void processInputImpl(List<InputEventAPI> events) {
        super.processInputImpl(events);

        for (InputEventAPI event : events) {
            if (event.isConsumed()) continue;
            if (event.isKeyDownEvent() && event.getEventValue() == Keyboard.KEY_F) {
                if (NativeUiUtils.isCtrlDown()) nameField.grabFocus(true);
            }
        }
    }
}