package wfg.item_markers.item;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponSize;
import com.fs.starfarer.api.loading.WingRole;

public class MarkerFilters {
    private MarkerFilters() {};
    public static final String OTHER_STRING = "Other";
    public static final Set<String> EXPLICIT_MANUFACTURERS = new HashSet<>(Arrays.asList("Low Tech", "Midline", "High Tech", "Pirate", "Luddic Path"));
    public static final List<String> manufacturers = Arrays.asList("Low Tech", "Midline", "High Tech", "Pirate", "Luddic Path", OTHER_STRING);

    public static final EnumSet<MarkerType> typeFilter = EnumSet.of(MarkerType.COMMODITY, MarkerType.SHIP, MarkerType.WEAPON, MarkerType.FIGHTER);
    public static final EnumSet<HullSize> hullSizeFilters = EnumSet.allOf(HullSize.class);
    public static final EnumSet<WingRole> wingRoleFilters = EnumSet.allOf(WingRole.class);
    public static final EnumSet<WeaponSize> weaponSizeFilters = EnumSet.allOf(WeaponSize.class);
    public static final EnumSet<DamageType> weaponDamageFilters = EnumSet.allOf(DamageType.class);
    public static final HashSet<String> manufacturerFilters = new HashSet<>(manufacturers);
    
    public static ActiveFilters activeFilter = ActiveFilters.ALL;

    public static String searchQuery = "";
    
    public static enum ActiveFilters {
        ALL,
        ACTIVE_ONLY,
        INACTIVE_ONLY
    }

    public static enum CommodityFilters {
        ALL,
        ECONOMY,
        NON_ECONOMY
    }
}