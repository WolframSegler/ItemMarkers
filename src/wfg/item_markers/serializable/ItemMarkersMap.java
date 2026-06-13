package wfg.item_markers.serializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;

import wfg.item_markers.item.ItemMarker;

public class ItemMarkersMap implements Serializable {
    private static final String ItemMarkersSerialID = "item_markers_save_data_id"; 
    private static ItemMarkersMap instance;

    public final Map<String, ItemMarker> markers = new HashMap<>(32);

    private ItemMarkersMap() {
        instance = this;
    }

    public static final ItemMarkersMap loadInstance() {
        final SectorAPI sector = Global.getSector();

        ItemMarkersMap data = (ItemMarkersMap) sector.getPersistentData().get(ItemMarkersSerialID);

        if (data == null) data = new ItemMarkersMap();
        instance = data;

        return data;
    }

    public static final void saveInstance() {
        Global.getSector().getPersistentData().put(ItemMarkersSerialID, instance);

        instance = null;
    }

    public static final ItemMarkersMap instance() {
        if (instance == null) ItemMarkersMap.loadInstance();
        return instance;
    }

    public static boolean isInitialized() {
        return instance != null;
    }
}