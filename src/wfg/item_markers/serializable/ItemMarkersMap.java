package wfg.item_markers.serializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;

import wfg.item_markers.item.ItemMarker;

public class ItemMarkersMap implements Serializable {
    private static final String ItemMarkersSerialID = "item_markers_save_data_id"; 
    private static ItemMarkersMap instance;

    public final HashSet<String> activeMarkerIds = new HashSet<>(32);
    public transient HashMap<String, ItemMarker> activeMarkers = new HashMap<>(activeMarkerIds.size());

    public final void toggleMarker(ItemMarker marker) {
        if (marker.isActive) deactivateMarker(marker);
        else activateMarker(marker); 
    }

    public final void activateMarker(ItemMarker marker) {
        marker.isActive = true;
        activeMarkerIds.add(marker.itemID);
        activeMarkers.put(marker.itemID, marker);
    }

    public final void deactivateMarker(ItemMarker marker) {
        marker.isActive = false;
        activeMarkerIds.remove(marker.itemID);
        activeMarkers.remove(marker.itemID);
    }

    private ItemMarkersMap() {
        instance = this;

        readResolve();
    }

    private final Object readResolve() {
        activeMarkers = new HashMap<>(activeMarkerIds.size());
        for (ItemMarker marker : ItemMarker.getAllMarkers()) {
            if (activeMarkerIds.contains(marker.itemID)) {
                marker.isActive = true;
                activeMarkers.put(marker.itemID, marker);
            } else {
                marker.isActive = false;
                activeMarkers.remove(marker.itemID);
            }
        }

        return this;
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