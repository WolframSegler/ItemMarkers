package wfg.item_markers.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

import wfg.item_markers.serializable.ItemMarkersMap;
import wfg.item_markers.ui.script.UIInjectorListener;

public class ItemMarkersModPlugin extends BaseModPlugin {

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