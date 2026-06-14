package wfg.item_markers.plugin;

import static wfg.item_markers.constant.Mods.LUNA_LIB;
import static wfg.native_ui.util.Globals.settings;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

import lunalib.lunaSettings.LunaSettings;
import wfg.item_markers.config.loader.ConfigLunaSettingsListener;
import wfg.item_markers.serializable.ItemMarkersMap;
import wfg.item_markers.ui.script.UIInjectorListener;

public class ItemMarkersModPlugin extends BaseModPlugin {

    @Override
    public void onApplicationLoad() throws Exception {
        if (settings.getModManager().isModEnabled(LUNA_LIB)) {
            LunaSettings.addSettingsListener(new ConfigLunaSettingsListener());
        }
    }

    @Override
    public void onGameLoad(boolean newGame) {
        ItemMarkersMap.loadInstance();

        Global.getSector().getListenerManager().addListener(new UIInjectorListener(), true);
    }

    @Override
    public void beforeGameSave() {
        ItemMarkersMap.saveInstance();
    }

    @Override
    public void afterGameSave() {
        ItemMarkersMap.loadInstance();
    }
}