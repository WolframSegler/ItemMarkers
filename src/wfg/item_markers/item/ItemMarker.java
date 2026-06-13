package wfg.item_markers.item;

import static wfg.native_ui.util.Globals.settings;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.SpecialItemSpecAPI;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipHullSpecAPI.ShipTypeHints;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.codex.CodexDataV2;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;

public class ItemMarker {
    private static final List<ItemMarker> allMarkers = new ArrayList<>(256);
    static {
        for (CommoditySpecAPI spec : settings.getAllCommoditySpecs()) {
            if (spec.hasTag(Tags.NO_SELL) || spec.isMeta() || spec.hasTag(Tags.HIDE_IN_CODEX)) continue;
            allMarkers.add(new ItemMarker(MarkerType.COMMODITY, spec.getId(), spec.getIconName(), spec.getName()));
        }
        for (SpecialItemSpecAPI spec : settings.getAllSpecialItemSpecs()) {
            if (spec.hasTag(Tags.NO_SELL) || spec.hasTag(Tags.HIDE_IN_CODEX)) continue;
            allMarkers.add(new ItemMarker(MarkerType.SPECIAL_ITEM, spec.getId(), spec.getIconName(), spec.getName()));
        }
        for (ShipHullSpecAPI spec : settings.getAllShipHullSpecs()) {
            if (spec.hasTag(Tags.NO_SELL) || spec.getHullSize() == HullSize.FIGHTER) continue;
            if (spec.isDHull()) continue;
            if (spec.getHints().contains(ShipTypeHints.STATION) || spec.getHints().contains(ShipTypeHints.MODULE)) continue;
            if (spec.getHints().contains(ShipTypeHints.HIDE_IN_CODEX) || spec.getHints().contains(ShipTypeHints.DO_NOT_SHOW_MODULES_IN_FLEET_LIST)) continue;

            allMarkers.add(new ItemMarker(MarkerType.SHIP, spec.getHullId(), spec.getSpriteName(), spec.getHullName()));
        }
        for (FighterWingSpecAPI spec : settings.getAllFighterWingSpecs()) {
            if (spec.hasTag(Tags.NO_SELL) || spec.hasTag(Tags.WING_NO_SELL) || spec.hasTag(Tags.HIDE_IN_CODEX)) continue;
            allMarkers.add(new ItemMarker(MarkerType.FIGHTER, spec.getId(), spec.getVariant().getHullSpec().getSpriteName(), spec.getWingName()));
        }
        for (HullModSpecAPI spec : settings.getAllHullModSpecs()) {
            if (spec.hasTag(Tags.NO_SELL) || spec.hasTag(Tags.HULLMOD_DMOD)) continue;
            if (spec.isHiddenEverywhere() || spec.isHidden() || spec.hasTag(Tags.HIDE_IN_CODEX)) continue;
            allMarkers.add(new ItemMarker(MarkerType.MOD_SPEC, spec.getId(), spec.getSpriteName(), spec.getDisplayName()));
        }
        for (WeaponSpecAPI spec : settings.getAllWeaponSpecs()) {
            if (spec.hasTag(Tags.NO_SELL) || spec.hasTag(Tags.WEAPON_NO_SELL) || spec.hasTag(Tags.HIDE_IN_CODEX)) continue;
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

    public final String getCodexId() {
        return switch (type) {
            case COMMODITY -> CodexDataV2.getCommodityEntryId(itemID);
            case SPECIAL_ITEM -> CodexDataV2.getItemEntryId(itemID);
            case SHIP -> CodexDataV2.getShipEntryId(itemID);
            case FIGHTER -> CodexDataV2.getFighterEntryId(itemID);
            case MOD_SPEC -> CodexDataV2.getHullmodEntryId(itemID);
            case WEAPON -> CodexDataV2.getWeaponEntryId(itemID);
            default -> null;
        };
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