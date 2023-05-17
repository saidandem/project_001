/**
 * Copyright (c) Thales Air Systems SA 2022-2022 all rights reserved.
 * This software is the property of Thales Air Systems SA
 * and may not be used in any manner
 * except under a license agreement signed with Thales Air Systems SA.
 */
package com.hussain.project_001.utils;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SuppressWarnings({ "rawtypes", "javadoc", "unchecked", "unused" })
public final class MyScenicView {

    public static final String PROP_COLOR = "#0082DD";

    public static void scanScene(final Scene scene) {
        new MyScenicView().scan(scene.getRoot());
    }

    public static void show(final Node node) {
        if (node.getScene() == null) {
            node.sceneProperty().addListener((obs, old, scene) -> {
                if (scene != null) {
                    show(scene);
                }
            });
        } else {
            show(node.getScene());
        }
    }

    public static void show(final Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isControlDown() && e.isAltDown() && e.isShiftDown() && e.getCode() == KeyCode.T) {
                new MyScenicView().scan(scene.getRoot());
            }
        });
    }

    private final Map<String, ObservableValue> properties = new TreeMap();

    private int rowIndex = 0;

    private final List<String> highlights = Arrays.asList("id", "width", "height", "layoutBounds");

    private CheckBox showBounds;

    private CheckBox showPreview;

    private CheckBox darkPreview;

    private TitledPane nodePreview;

    private final Popup overLayPopup = new Popup();

    private GridPane buildFullPropertiesDetails(final Node node) {
        final GridPane detailsPane = buildGridPane();
        rowIndex = 0;
        properties.forEach((key, val) -> {
            final Node value = buildValueNode(val);
            if (highlights.contains(key)) {
                value.setStyle("-fx-font-weight:bold;-fx-fill:red;");
            }
            detailsPane.addRow(rowIndex++, buildPropText(key), value);
        });
        return detailsPane;
    }

    private GridPane buildGridPane() {
        final GridPane detailsPane = new GridPane();
        detailsPane.setPadding(new Insets(8));
        detailsPane.setVgap(5);
        detailsPane.setHgap(5);
        final ColumnConstraints c = new ColumnConstraints();
        c.setMinWidth(200);
        c.setMaxWidth(200);
        c.setHalignment(HPos.RIGHT);
        detailsPane.getColumnConstraints().add(c);
        final RowConstraints r = new RowConstraints();
        r.setValignment(VPos.TOP);
        detailsPane.getRowConstraints().addAll(r);
        return detailsPane;
    }

    private GridPane buildInsetsGrid(final Insets insets, final String lbl) {
        final Text lblText = new Text(lbl);
        lblText.setStyle("-fx-font-size:9px;-fx-fill:" + PROP_COLOR);
        final GridPane insetGrid = new GridPane();
        insetGrid.setVgap(2);
        insetGrid.setHgap(2);
        insetGrid.addRow(0, new Text(), new Text(insets.getTop() + ""), new Text());
        insetGrid.addRow(1, new Text(insets.getLeft() + ""), lblText, new Text(insets.getRight() + ""));
        insetGrid.addRow(2, new Text(), new Text(insets.getBottom() + ""), new Text());
        final ColumnConstraints cc = new ColumnConstraints();
        cc.setHalignment(HPos.CENTER);
        insetGrid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        return insetGrid;
    }

    private GridPane buildNodeDetails(final Node node) {
        final GridPane detailsPane = buildGridPane();
        rowIndex = 0;
        highlights.forEach(key -> {
            final ObservableValue val = properties.get(key);
            detailsPane.addRow(rowIndex++, buildPropText(key), buildValueNode(val));
        });

        final Bounds screenBounds = node.localToScreen(node.getLayoutBounds());
        detailsPane.addRow(rowIndex++, buildPropText("screenBounds"),
                buildValueNode(new SimpleObjectProperty(screenBounds)));

        final String styleClass = node.getStyleClass().stream().collect(Collectors.joining(", "));
        detailsPane.addRow(rowIndex++, buildPropText("styleClass"),
                buildValueNode(new SimpleObjectProperty(styleClass)));

        final String pseudoClass =
                node.getPseudoClassStates().stream().map(state -> state.getPseudoClassName()).collect(
                        Collectors.joining(", "));
        detailsPane.addRow(rowIndex++, buildPropText("pseudoClass"),
                buildValueNode(new SimpleObjectProperty(pseudoClass)));

        if (properties.get("font") != null) {
            detailsPane.addRow(rowIndex++, buildPropText("font"), buildValueNode(properties.get("font")));
        }
        if (properties.get("vgap") != null) {
            detailsPane.addRow(rowIndex++, buildPropText("vgap"), buildValueNode(properties.get("vgap")));
        }
        if (properties.get("hgap") != null) {
            detailsPane.addRow(rowIndex++, buildPropText("hgap"), buildValueNode(properties.get("hgap")));
        }
        if (properties.get("spacing") != null) {
            detailsPane.addRow(rowIndex++, buildPropText("spacing"), buildValueNode(properties.get("spacing")));
        }
        detailsPane.addRow(rowIndex++, buildPropText("style"), buildValueNode(node.styleProperty()));

        if (node instanceof Region) {
            Insets padding = ((Region) node).getPadding();
            if (padding == null) {
                padding = Insets.EMPTY;
            }
            detailsPane.addRow(rowIndex++, buildPropText("padding"),
                    buildValueNode(new SimpleObjectProperty(padding)));
        }
        final List<String> styleSheets = new ArrayList<>();
        getAppliedStyleSheets(node, styleSheets);
        if (!styleSheets.isEmpty()) {
            detailsPane.addRow(rowIndex++, buildPropText("styleSheets"), buildValueNode(
                    new SimpleObjectProperty(styleSheets.stream().collect(Collectors.joining("\n")))));
        }

        return detailsPane;
    }

    private TitledPane buildPreview(final Node node) {
        final ImageView image = new ImageView(node.snapshot(null, null));
        final StackPane previewPane = new StackPane(image);
        previewPane.setStyle(darkPreview.isSelected() ? "-fx-background-color:#555555;" : "");
        previewPane.setAlignment(Pos.TOP_LEFT);
        previewPane.setPadding(new Insets(10));
        nodePreview = new TitledPane("Preview", previewPane);
        nodePreview.setId("preview");
        return nodePreview;
    }

    private Node buildPropText(final String text) {
        final Text prop = new Text(text + ":");
        prop.setStyle("-fx-font-weight:bold;-fx-fill:" + PROP_COLOR);
        if (text.equals("background")
            || text.equals("padding")
            || text.equals("insets")
            || text.equals("border")) {
            final StackPane sp = new StackPane(prop);
            sp.setAlignment(Pos.TOP_RIGHT);
            return sp;
        }
        return prop;
    }

    private GridPane buildRadiiGrid(final CornerRadii radii) {
        final Text lblText = new Text("rad");
        lblText.setStyle("-fx-font-size:9px;-fx-fill:" + PROP_COLOR);
        final GridPane insetGrid = new GridPane();
        insetGrid.setVgap(2);
        insetGrid.setHgap(2);
        insetGrid.addRow(0, new Text(radii.getTopLeftHorizontalRadius() + ""), new Text(),
                new Text(radii.getTopRightHorizontalRadius() + ""));
        insetGrid.addRow(1, new Text(), lblText, new Text());
        insetGrid.addRow(2, new Text(radii.getBottomLeftHorizontalRadius() + ""), new Text(),
                new Text(radii.getBottomRightHorizontalRadius() + ""));
        final ColumnConstraints cc = new ColumnConstraints();
        cc.setHalignment(HPos.CENTER);
        insetGrid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        return insetGrid;
    }

    private Node buildValueNode(final ObservableValue value) {
        if (value != null && value.getValue() != null) {
            if (value.getValue() instanceof Insets) {
                final Insets insets = (Insets) value.getValue();
                return buildInsetsGrid(insets, null);
            } else if (value.getValue() instanceof Background) {
                final Background background = (Background) value.getValue();
                final Text bgToString = new Text(background.toString());
                final GridPane pane = new GridPane();
                pane.setHgap(10);
                pane.setVgap(2);
                pane.add(bgToString, 0, 0, 4, 1);
                if (background.getFills().isEmpty()) {
                    bgToString.setText(bgToString.getText() + "  (no fills)");
                } else {
                    final AtomicInteger cnt = new AtomicInteger();
                    background.getFills().forEach(fill -> {
                        final int index = cnt.getAndIncrement();
                        final Text fillText = new Text("fill#" + index + ":");
                        fillText.setStyle("-fx-fill:" + PROP_COLOR);
                        final String hexColor = getHexColor(fill.getFill());
                        final String rgbaColor = getRGBAColor(fill.getFill());
                        final StackPane box = new StackPane(new Text(rgbaColor + ", " + hexColor));
                        box.setPrefSize(200, 25);
                        box.setMaxSize(200, 25);
                        box.setStyle("-fx-background-color:" + hexColor);
                        pane.addRow(index + 1, fillText, box, buildInsetsGrid(fill.getInsets(), "insets"),
                                buildRadiiGrid(fill.getRadii()));
                    });
                }
                return pane;
            } else if (!value.getValue().toString().trim().isEmpty()) {
                return new Text(value.getValue().toString().trim());
            }
        }
        return new Text("-");
    }

    /**
     * Changes the given color value to hex value.
     *
     * @param channelValue color channel value
     * @return Hex value of color channel
     */
    private String colorChannelToHex(final double channelValue) {
        String hex = Integer.toHexString((int) Math.min(Math.round(channelValue * 255), 255));
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        return hex;
    }

    private void getAppliedStyleSheets(final Node node, final List<String> styleSheets) {
        if (node instanceof Parent) {
            final String styleSheet = ((Parent) node).getStylesheets().stream().collect(Collectors.joining("\n"));
            if (styleSheet != null && !styleSheet.isEmpty()) {
                styleSheets.add(styleSheet);
            }
        }
        if (node.getParent() != null) {
            getAppliedStyleSheets(node.getParent(), styleSheets);
        }
    }

    private List<TreeItem<Node>> getChildItems(final Node node) {
        final List<TreeItem<Node>> list = new ArrayList<>();
        if (node instanceof Parent) {
            final Parent parent = (Parent) node;
            parent.getChildrenUnmodifiable().forEach(childNode -> {
                final TreeItem<Node> childNodeItem = new TreeItem<>(childNode);
                childNodeItem.getChildren().addAll(getChildItems(childNode));
                childNodeItem.setExpanded(true);
                list.add(childNodeItem);
            });
        }
        return list;
    }

    /**
     * Returns the hex color value for the provided {@link Color}.
     *
     * @param paint Color reference object
     * @return Hex Color value in {@link Color}
     */
    private String getHexColor(final Paint paint) {
        if (paint instanceof Color) {
            final Color color = (Color) paint;
            return "#"
                + colorChannelToHex(color.getRed())
                + colorChannelToHex(color.getGreen())
                + colorChannelToHex(color.getBlue())
                + colorChannelToHex(color.getOpacity());
        }
        return "-";
    }

    /**
     * Returns the RGBA color values for the provided {@link Color}.
     *
     * @param paint Color reference object
     * @return RGBA color values in {@link Color}
     */
    private String getRGBAColor(final Paint paint) {
        if (paint instanceof Color) {
            final Color color = (Color) paint;
            final int r = (int) Math.round(color.getRed() * 255.0);
            final int g = (int) Math.round(color.getGreen() * 255.0);
            final int b = (int) Math.round(color.getBlue() * 255.0);
            final int a = (int) Math.round(color.getOpacity() * 255.0);
            return String.format("(%s,%s,%s,%s)", r, g, b, a);
        }
        return "-";
    }

    private void loadProperties(final Node node) {
        properties.clear();
        final Method[] var2 = node.getClass().getMethods();
        final int var3 = var2.length;
        for (int var4 = 0; var4 < var3; ++var4) {
            final Method method = var2[var4];
            if (method.getName().endsWith("Property")) {
                try {
                    final Class returnType = method.getReturnType();
                    if (ObservableValue.class.isAssignableFrom(returnType)) {
                        final String propertyName =
                                method.getName().substring(0, method.getName().lastIndexOf("Property"));
                        method.setAccessible(true);
                        final ObservableValue property = (ObservableValue) method.invoke(node);
                        properties.put(propertyName, property);
                    }
                } catch (final Exception var9) {
                    var9.printStackTrace();
                }
            }
        }
    }

    private void scan(final Node node) {
        final VBox root = new VBox();
        final Stage stage = new Stage();
        final Scene sc = new Scene(root, 1300, 800);
        stage.setScene(sc);
        stage.setTitle("FX17-ScenicView");
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isControlDown() && e.isAltDown() && e.getCode() == KeyCode.X) {
                overLayPopup.hide();
                stage.close();
            }
        });
        stage.setOnHiding(e -> overLayPopup.hide());

        final ScrollPane detailsContent = new ScrollPane();
        detailsContent.setFitToHeight(true);
        detailsContent.setFitToWidth(true);

        final TreeView<Node> treeView = new TreeView();
        treeView.setCellFactory(param -> new TreeCell<>() {

            @Override
            protected final void updateItem(final Node item, final boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    final Text id = new Text(item.getId() != null ? " [#" + item.getId() + "]" : "");
                    id.setStyle("-fx-font-weight:bold;");
                    String className = item.getClass().getSimpleName();
                    if (className == null || className.isEmpty()) {
                        className = item.getClass().toString();
                        className = className.substring(className.lastIndexOf(".") + 1);
                    }
                    setGraphic(new HBox(new Text(className), id));
                } else {
                    setGraphic(null);
                }
            }
        });
        treeView.getSelectionModel().selectedItemProperty().addListener((obs, old, item) -> {
            if (item != null) {
                final Node selectedNode = item.getValue();
                showDetails(selectedNode, detailsContent);
            }
        });
        final TreeItem<Node> rootItem = new TreeItem<>(node);
        rootItem.getChildren().addAll(getChildItems(node));
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);

        final Tab detailsTab = new Tab("Details");
        detailsTab.setClosable(false);
        detailsTab.setContent(detailsContent);
        final Tab cssTab = new Tab("Scene CSS");
        cssTab.setClosable(false);
        final VBox cssList = new VBox();
        cssList.setPadding(new Insets(10));
        cssList.setSpacing(10);
        final ScrollPane cssScrollContent = new ScrollPane();
        cssScrollContent.setContent(cssList);
        cssScrollContent.setFitToHeight(true);
        cssScrollContent.setFitToWidth(true);
        cssTab.setContent(cssScrollContent);
        node.getScene().getStylesheets().forEach(sheet -> cssList.getChildren().add(new Text(sheet)));

        final TabPane tabPane = new TabPane(detailsTab, cssTab);
        final SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.getItems().addAll(treeView, tabPane);
        splitPane.setDividerPosition(0, .30);

        showBounds = new CheckBox("Show Layout Bounds");
        showBounds.setSelected(true);
        showBounds.selectedProperty().addListener((obs, old, show) -> {
            if (!show) {
                overLayPopup.hide();
            } else if (treeView.getSelectionModel().getSelectedItem() != null) {
                showBounds(treeView.getSelectionModel().getSelectedItem().getValue());
            }
        });
        showPreview = new CheckBox("Show Node Preview");
        showPreview.setSelected(true);

        darkPreview = new CheckBox("Dark Preview");
        darkPreview.disableProperty().bind(showPreview.selectedProperty().not());
        darkPreview.selectedProperty().addListener((obs, old, dark) -> {
            if (nodePreview != null) {
                nodePreview.getContent().setStyle(dark ? "-fx-background-color:#555555;" : "");
            }
        });

        final ToolBar toolBar = new ToolBar(showBounds, showPreview, darkPreview);
        root.getChildren().addAll(toolBar, splitPane);

        /* Always call the show at the end. */
        stage.show();
    }

    private void showBounds(final Node node) {
        final Bounds bounds = node.getBoundsInLocal();
        final Bounds screenBounds = node.localToScreen(bounds);
        final StackPane overLay = new StackPane();
        overLay.setStyle(
                "-fx-background-color:#FFFF0066;-fx-border-color:green;-fx-border-width:1px;-fx-border-style: segments(5, 5, 5, 5)  line-cap round ;");
        overLay.setPrefSize(bounds.getWidth(), bounds.getHeight());
        overLayPopup.getContent().clear();
        overLayPopup.getContent().addAll(overLay);
        overLayPopup.setX(screenBounds.getMinX());
        overLayPopup.setY(screenBounds.getMinY());
        overLayPopup.show(node.getScene().getWindow());
    }

    private void showDetails(final Node node, final ScrollPane detailsContent) {
        loadProperties(node);
        final TitledPane nodeDetails = new TitledPane("Node Details", buildNodeDetails(node));
        final TitledPane fullPropDetails =
                new TitledPane("Full Properties Details", buildFullPropertiesDetails(node));
        final VBox vPane = new VBox(nodeDetails, fullPropDetails);
        if (showPreview.isSelected()) {
            vPane.getChildren().add(0, buildPreview(node));
        }
        showPreview.selectedProperty().addListener((obs, old, show) -> {
            if (show) {
                vPane.getChildren().add(0, buildPreview(node));
            } else if ("preview".equals(vPane.getChildren().get(0).getId())) {
                vPane.getChildren().remove(0);
            }
        });
        detailsContent.setContent(vPane);
        if (showBounds.isSelected()) {
            showBounds(node);
        }
    }
}