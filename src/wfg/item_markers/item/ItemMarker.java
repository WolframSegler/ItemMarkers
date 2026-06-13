package wfg.item_markers.item;

import static wfg.native_ui.util.Globals.settings;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.SpecialItemSpecAPI;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;

public class ItemMarker {
    private static final List<ItemMarker> allMarkers = new ArrayList<>(256);
    static {
        for (CommoditySpecAPI spec : settings.getAllCommoditySpecs()) {
            if (spec.hasTag(Tags.NO_SELL)) continue;
            allMarkers.add(new ItemMarker(MarkerType.COMMODITY, spec.getId(), spec.getIconName(), spec.getName()));
        }
        for (SpecialItemSpecAPI spec : settings.getAllSpecialItemSpecs()) {
            if (spec.hasTag(Tags.NO_SELL)) continue;
            allMarkers.add(new ItemMarker(MarkerType.SPECIAL_ITEM, spec.getId(), spec.getIconName(), spec.getName()));
        }
        for (ShipHullSpecAPI spec : settings.getAllShipHullSpecs()) {
            if (spec.hasTag(Tags.NO_SELL)) continue;
            allMarkers.add(new ItemMarker(MarkerType.SHIP, spec.getHullId(), spec.getSpriteName(), spec.getHullName()));
        }
        for (FighterWingSpecAPI spec : settings.getAllFighterWingSpecs()) {
            if (spec.hasTag(Tags.NO_SELL) || spec.hasTag(Tags.WING_NO_SELL)) continue;
            allMarkers.add(new ItemMarker(MarkerType.FIGHTER, spec.getId(), spec.getVariantId(), spec.getWingName()));
        }
        for (HullModSpecAPI spec : settings.getAllHullModSpecs()) {
            if (spec.hasTag(Tags.NO_SELL)) continue;
            allMarkers.add(new ItemMarker(MarkerType.MOD_SPEC, spec.getId(), spec.getSpriteName(), spec.getDisplayName()));
        }
        for (WeaponSpecAPI spec : settings.getAllWeaponSpecs()) {
            if (spec.hasTag(Tags.NO_SELL) || spec.hasTag(Tags.WEAPON_NO_SELL)) continue;
            allMarkers.add(new ItemMarker(MarkerType.WEAPON, spec.getWeaponId(), spec.getTurretSpriteName(), spec.getWeaponName()));
        }
    }

    public final MarkerType type;
    public final String itemID;
    public final String iconID;
    public final String name;

    public boolean isActive = false;
    
    private ItemMarker(MarkerType type, String itemID, String iconID, String name) {
        this.type = type;
        this.itemID = itemID;
        this.iconID = iconID;
        this.name = name;
    }

    public static final List<ItemMarker> getAllMarkersCopy() {
        return new ArrayList<>(allMarkers);
    }

    public static final List<ItemMarker> getAllMarkers() {
        return allMarkers;
    }

    public static final List<ItemMarker> getActiveMarkers() {
        final List<ItemMarker> markers = getAllMarkersCopy();
        markers.removeIf(m -> !m.isActive);
        return markers;
    }
}