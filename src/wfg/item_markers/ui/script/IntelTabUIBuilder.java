package wfg.item_markers.ui.script;

import com.fs.starfarer.api.campaign.CoreUITabId;
import com.fs.starfarer.api.ui.UIComponentAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;
import com.fs.starfarer.campaign.CampaignEngine;
import com.fs.starfarer.campaign.comms.IntelTabData;

import wfg.item_markers.ui.intel.MarkerManagmentPanel;

public class IntelTabUIBuilder extends AbstractTabButtonInjector {
    private final IntelTabData tabData = CampaignEngine.getInstance().getUIData().getIntelData();

    protected int getCurrentTabIndex() {
        return tabData.getSelectedTabIndex();
    }

    protected void setCurrentTabIndex(int index) {
        tabData.setSelectedTabIndex(index);
    }

    protected CoreUITabId getTargetCoreTabId() {
        return CoreUITabId.INTEL;
    }

    protected String getButtonLabel() {
        return "Markers";
    }

    protected UIComponentAPI createCustomComponent(UIPanelAPI parent) {
        return new MarkerManagmentPanel(parent).getPanel();
    }
}