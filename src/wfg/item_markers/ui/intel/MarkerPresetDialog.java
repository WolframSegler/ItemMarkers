package wfg.item_markers.ui.intel;

import static wfg.native_ui.util.Globals.settings;
import static wfg.native_ui.util.UIConstants.*;

import java.util.HashSet;

import java.awt.Color;

import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TextFieldAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.ui.UIComponentAPI;
import com.fs.starfarer.api.util.Misc;

import wfg.item_markers.config.PresetConfig;
import wfg.item_markers.config.PresetConfig.ItemMarkerPreset;
import wfg.item_markers.config.loader.PresetConfigLoader;
import wfg.item_markers.item.ItemMarker;
import wfg.item_markers.serializable.ItemMarkersMap;
import wfg.native_ui.internal.ui.core.UIContainer;
import wfg.native_ui.internal.util.BorderRenderer;
import wfg.native_ui.ui.ComponentFactory;
import wfg.native_ui.ui.component.HoverGlowComp.GlowType;
import wfg.native_ui.ui.core.UIBuildableAPI;
import wfg.native_ui.ui.dialog.DialogPanel;
import wfg.native_ui.ui.visual.AbstractSpriteElement.SpriteElement;
import wfg.native_ui.ui.widget.Button;
import wfg.native_ui.util.NativeUiUtils;
import wfg.native_ui.util.NativeUiUtils.AnchorType;

// TODO test this
public final class MarkerPresetDialog extends DialogPanel {
    private static final int PANEL_W = 900;
    private static final int PANEL_H = 640;
    private static final int BTN_H = 32;
    private static final int WIDGET_H = 220;

    private final MarkerGrid mGrid;

    public MarkerPresetDialog(MarkerGrid grid) {
        super(PANEL_W, PANEL_H, null, null, "Dismiss");

        mGrid = grid;

        backgroundDimAmount = 0.2f;
        holo.borderAlpha = 0.8f;

        setConfirmShortcut();

        buildUI();
    }

    @Override
    public void buildUI() {
        clearChildren();

        final Button createBtn = new CreatePresetButton();
        add(createBtn).inTL(hpad, hpad);
        
        final TooltipMakerAPI container = ComponentFactory.createTooltip(PANEL_W - 60, true);

        float yCoord = 0f;
        for (ItemMarkerPreset preset : PresetConfig.getPlansCopy()) {
            final PresetWidget widget = new PresetWidget(preset);
            container.addCustom(widget, 0f).getPosition().inTL(opad, yCoord);
            yCoord += WIDGET_H + hpad;
        }

        container.setHeightSoFar(yCoord);
        ComponentFactory.addTooltip(container, yCoord, LMBOutsideCancels, this).inBL(0f, 0f);
    }

    public final class CreatePresetButton extends Button {

        public CreatePresetButton() {
            super(160, 36, "Create...", Fonts.INSIGNIA_LARGE, (btn) -> {
                final var preset = new ItemMarkerPreset();
                preset.id = "Preset " + Long.toString(Misc.genRandomSeed());
                preset.activeMarkerIds.addAll(ItemMarkersMap.instance().activeMarkerIds);
                PresetConfig.map.put(preset.id, preset);
                PresetConfigLoader.serializeAndWriteToCommon();
                MarkerPresetDialog.this.buildUI();
            });
        
            tooltip.builder = (tp, isExpanded) -> {
                tp.addPara("Create a new preset using the currently marked items.", 0f);
            };
            tooltip.positioner = (tp, isExpanded) -> {
                NativeUiUtils.anchorPanel(tp, this, AnchorType.RightMid, opad*2);
            };
        }
    }

    public final class PresetWidget extends UIContainer implements UIBuildableAPI {
        private static final Color WARNING_COLOR = new Color(255, 190, 60);
        private static final Color WIDGET_BG = new Color(28, 35, 48, 240);

        private static final int PANEL_W = MarkerPresetDialog.PANEL_W - opad * 2;
        private static final HashSet<String> allMarkerIds = new HashSet<>(ItemMarker.getAllMarkers().size());

        public static final BorderRenderer border = new BorderRenderer(UI_BORDER_4, true, PANEL_W, WIDGET_H);

        static {
            for (ItemMarker marker : ItemMarker.getAllMarkers()) {
                allMarkerIds.add(marker.itemID);
            }
        }

        protected ItemMarkerPreset mPreset;

        public PresetWidget(ItemMarkerPreset preset) {
            super(PANEL_W, WIDGET_H);

            mPreset = preset;

            buildUI();
        }

        @Override
        public void buildUI() {
            clearChildren();

            final LabelAPI title = settings.createLabel(mPreset.id, Fonts.INSIGNIA_VERY_LARGE);
            add(title).inTL(hpad, hpad);


            final String countStr = Integer.toString(mPreset.activeMarkerIds.size());
            final LabelAPI count = settings.createLabel("Marked: " + countStr, Fonts.DEFAULT_SMALL);
            count.setHighlightColor(highlight);
            count.setHighlight(countStr);
            add(count).belowLeft((UIComponentAPI) title, hpad);

            final int missingCount = (int) mPreset.activeMarkerIds.stream()
                .filter(id -> !allMarkerIds.contains(id)).count();

            if (missingCount > 0) {
                final String missingStr = Integer.toString(missingCount);
                final LabelAPI missing = settings.createLabel("Missing: ", missingStr);
                missing.setColor(WARNING_COLOR);
                missing.setHighlightColor(negative);
                missing.setHighlight(missingStr);
                add(missing).belowLeft((UIComponentAPI) count, pad);
            }

            final Button deleteBtn = new DeleteButton(this);
            final Button renameBtn = new RenameButton(this);
            final Button applyBtn = new ApplyButton(this);

            add(deleteBtn).inTR(hpad, hpad);
            add(renameBtn).rightOfMid(deleteBtn, hpad);
            add(applyBtn).inBR(hpad, hpad);
        }

        @Override
        public void renderBelowImpl(float alpha) {
            border.centerColor = WIDGET_BG;
            border.render(getX(), getY(), alpha);
        }
    }

    public final class DeleteDialog extends DialogPanel {
        private final PresetWidget mWidget;

        public DeleteDialog(PresetWidget widget) {
            super(200, 80, null, "Are you sure you want to delete " + widget.mPreset.id + "? This cannot be undone.", "Confirm", "Cancel");

            mWidget = widget;
            
            backgroundDimAmount = 0.1f;
            holo.borderAlpha = 0.66f;

            setConfirmShortcut();
        }

        @Override
        public void dismiss(int option) {
            super.dismiss(option);

            if (option == 0) {
                PresetConfig.map.remove(mWidget.mPreset.id);
                PresetConfigLoader.serializeAndWriteToCommon();
                MarkerPresetDialog.this.buildUI();
            }
        }
    }

    public static final class RenameDialog extends DialogPanel {
        private static final int PANEL_W = 400;
        private static final int PANEL_H = 90;
        private static final int FIELD_H = 30;

        private final PresetWidget mWidget;
        private TextFieldAPI nameField;
        private String lastText = "";

        public RenameDialog(PresetWidget widget) {
            super(PANEL_W, PANEL_H, null, "Rename...", "Confirm", "Cancel");

            mWidget = widget;
            
            backgroundDimAmount = 0.1f;
            holo.borderAlpha = 0.66f;

            setConfirmShortcut();

            nameField = settings.createTextField(mWidget.mPreset.id, Fonts.INSIGNIA_LARGE);
            nameField.getPosition().setSize(300, FIELD_H);
            add(nameField).inMid();
        }

        @Override
        public void advanceImpl(float delta) {
            super.advanceImpl(delta);

            final String titleText = nameField.getText().trim();
            if (titleText.equals(lastText)) return;
            lastText = titleText;

            final boolean isBlank = titleText.isEmpty();
            boolean duplicate = false;
            if (!isBlank) {
                for (ItemMarkerPreset preset : PresetConfig.map.values()) {
                    if (!preset.equals(mWidget.mPreset) && preset.id.equals(titleText)) {
                        duplicate = true;
                        break;
                    }
                }
            }

            final boolean invalidId = isBlank || duplicate;

            final Button confirmBtn = getButton(0);
            
            if (confirmBtn.tooltip.positioner == null) {
                confirmBtn.tooltip.positioner = (tp, isExpanded) -> {
                    NativeUiUtils.anchorPanel(tp, confirmBtn, AnchorType.LeftMid, opad);
                };
            }

            if (isBlank) {
                confirmBtn.tooltip.builder = (tp, isExpanded) -> tp.addPara("Name cannot be empty.", 0f);
            } else if (duplicate) {
                confirmBtn.tooltip.builder = (tp, isExpanded) -> tp.addPara("Identical name already in use.", 0f);
            } else {
                confirmBtn.tooltip.builder = null;
            }
            confirmBtn.setEnabled(!invalidId);
        }

        @Override
        public void dismiss(int option) {
            super.dismiss(option);

            if (option == 0) {
                final String newName = nameField.getText().trim();
                PresetConfig.map.remove(mWidget.mPreset.id);
                mWidget.mPreset.id = newName;
                PresetConfig.map.put(newName, mWidget.mPreset);

                PresetConfigLoader.serializeAndWriteToCommon();
                mWidget.buildUI();
            }
        }
    }

    public final class DeleteButton extends Button {
        private static final SpriteAPI ICON = settings.getSprite("delete", "ui");
        private static final Color ICON_COLOR = new Color(160, 45, 45);

        public DeleteButton(PresetWidget widget) {
            super(BTN_H, BTN_H, "", null, (btn) -> new DeleteDialog(widget).show(0.3f, 0.3f));

            bgAlpha = 0f;
            bgDisabledAlpha = 0f;

            tooltip.builder = (tp, expanded) -> tp.addPara("Delete preset", 0f);
            tooltip.positioner = (tp, expanded) -> {
                NativeUiUtils.anchorPanel(tp, this, AnchorType.LeftMid, opad);
            };

            final SpriteElement icon = new SpriteElement(BTN_H, BTN_H, ICON, ICON_COLOR, null);
            add(icon).inBL(0f, 0f);
            glow.type = GlowType.ADDITIVE;
            glow.additiveSprite = icon.getSprite();
        }
    }

    public static final class RenameButton extends Button {
        private static final SpriteAPI ICON = settings.getSprite("rename", "ui");
        private static final Color ICON_COLOR = base;

        public RenameButton(PresetWidget widget) {
            super(BTN_H, BTN_H, "", null, (btn) -> new RenameDialog(widget).show(0.3f, 0.3f));

            bgAlpha = 0f;
            bgDisabledAlpha = 0f;

            tooltip.builder = (tp, expanded) -> tp.addPara("Rename preset", 0f);
            tooltip.positioner = (tp, expanded) -> {
                NativeUiUtils.anchorPanel(tp, this, AnchorType.LeftMid, opad);
            };

            final SpriteElement icon = new SpriteElement(BTN_H, BTN_H, ICON, ICON_COLOR, null);
            add(icon).inBL(0f, 0f);
            glow.type = GlowType.ADDITIVE;
            glow.additiveSprite = icon.getSprite();
        }
    }

    public final class ApplyButton extends Button {
        private static final SpriteAPI ICON = settings.getSprite("apply", "ui");
        private static final Color ICON_COLOR = Color.WHITE;

        public ApplyButton(PresetWidget widget) {
            super(80, BTN_H, "Apply", Fonts.DEFAULT_SMALL, (btn) -> {
                ItemMarkersMap.instance().activeMarkerIds.clear();
                ItemMarkersMap.instance().activeMarkerIds.addAll(widget.mPreset.activeMarkerIds);
                ItemMarkersMap.instance().rebuildMarkers();
                mGrid.buildUI();
            });

            bgAlpha = 0f;
            bgDisabledAlpha = 0f;

            tooltip.builder = (tp, expanded) -> tp.addPara("Apply preset. %s the active one.", 0f, highlight, "Overrides");
            tooltip.positioner = (tp, expanded) -> {
                NativeUiUtils.anchorPanel(tp, this, AnchorType.LeftMid, opad);
            };
            setAlignment(Alignment.LMID);

            final SpriteElement icon = new SpriteElement(BTN_H + 120, BTN_H, ICON, ICON_COLOR, null);
            add(icon).inBR(0f, 0f);
            glow.type = GlowType.ADDITIVE;
            glow.additiveSprite = icon.getSprite();
        }
    }
}