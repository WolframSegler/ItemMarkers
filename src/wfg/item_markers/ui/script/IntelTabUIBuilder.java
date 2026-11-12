package wfg.item_markers.ui.script;

import com.fs.starfarer.api.campaign.CoreUITabId;
import com.fs.starfarer.api.ui.UIPanelAPI;
import com.fs.starfarer.campaign.CampaignEngine;
import com.fs.starfarer.campaign.comms.IntelTabData;

import wfg.item_markers.ui.intel.MarkerManagmentPanel;
import wfg.native_ui.ui.core.UIElementAPI;
import wfg.utils.ui.AbstractTabButtonInjector;

public final class IntelTabUIBuilder extends AbstractTabButtonInjector {
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

    protected UIElementAPI createCustomComponent(UIPanelAPI parent) {
        return new MarkerManagmentPanel();
    }
}