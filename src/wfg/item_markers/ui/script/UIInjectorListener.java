package wfg.item_markers.ui.script;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CoreUITabId;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.listeners.CoreUITabListener;

public class UIInjectorListener implements CoreUITabListener {
    
    @Override
    public void reportAboutToOpenCoreTab(CoreUITabId tabID, Object param) {
        final SectorAPI sector = Global.getSector();

        sector.removeTransientScriptsOfClass(CoreTabUIBuilder.class);

        switch (tabID) {
        case INTEL:
            sector.addTransientScript(new IntelTabUIBuilder());
            break;

        case FLEET:
            sector.addTransientScript(new FleetTabUIBuilder());
            break;

        case CARGO:
            sector.addTransientScript(new CargoTabUIBuilder());
            break;
    
        default: break;
        }
    }
}