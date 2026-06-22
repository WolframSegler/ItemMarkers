package wfg.item_markers.config.loader;

import lunalib.lunaSettings.LunaSettingsListener;
import wfg.item_markers.config.VisualConfig;
import wfg.item_markers.constant.Mods;
import wfg.item_markers.ui.intel.MarkerWidget;

public class ConfigLunaSettingsListener implements LunaSettingsListener {

    @Override
    public void settingsChanged(String modID) {
        if (!Mods.ITEM_MARKERS.equals(modID)) return;
        
        VisualConfigLoader.loadFromLunaSettings();

        MarkerWidget.border.setSize(VisualConfig.getWidgetW(), VisualConfig.getWidgetH());
    }
}