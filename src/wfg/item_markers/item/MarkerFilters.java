package wfg.item_markers.item;

import java.util.EnumSet;

public class MarkerFilters {
    public static String searchQuery = "";
    public static EnumSet<MarkerType> typeFilter = EnumSet.allOf(MarkerType.class);
    
    public static ActiveFilters activeFilter = ActiveFilters.ALL;
    public static CommodityFilters comFilters = CommodityFilters.ALL;
    
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