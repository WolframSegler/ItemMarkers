package wfg.item_markers.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum MarkerType {
    COMMODITY("Commodity"),
    SPECIAL_ITEM("Special Item"),
    SHIP("Ship"),
    FIGHTER("Fighter Wing"),
    MOD_SPEC("Ship Mod"),
    WEAPON("Weapon");

    public final String label;

    MarkerType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    private static final List<String> LABELS =
        Collections.unmodifiableList(
            Arrays.stream(values())
                .map(MarkerType::getLabel)
                .collect(Collectors.toList())
        );

    public static List<String> getLabels() {
        return LABELS;
    }

    public static final MarkerType[] ALL_MARKERS = MarkerType.values();
}