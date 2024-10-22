/* SPDX-License-Identifier: MIT */

package atlantafx.sampler.cashier.layout;

import atlantafx.sampler.cashier.page.Page;
import atlantafx.sampler.cashier.event.DefaultEventBus;
import atlantafx.sampler.cashier.event.HotkeyEvent;
import atlantafx.sampler.cashier.event.PageEvent;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

import static atlantafx.sampler.cashier.event.PageEvent.Action;
import static javafx.scene.layout.Priority.ALWAYS;

class MainLayer extends BorderPane {

    static final int PAGE_TRANSITION_DURATION = 500; // ms

    private final MainModel model = new MainModel();
    private final Sidebar sidebar = new Sidebar(model);
    private final StackPane subLayerPane = new StackPane();



    public MainLayer() {
        super();

        createView();
        initListeners();

        model.navigate(MainModel.DEFAULT_PAGE);

        // keyboard navigation won't work without focus
        Platform.runLater(sidebar::begForFocus);

        var snapshotKeys = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN);
        DefaultEventBus.getInstance().subscribe(HotkeyEvent.class, e -> {
            if (Objects.equals(e.getKeys(), snapshotKeys)) {
                final Page page = (Page) subLayerPane.getChildren().stream()
                    .filter(c -> c instanceof Page)
                    .findFirst()
                    .orElse(null);
                if (page == null || page.getSnapshotTarget() == null) {
                    return;
                }

                var fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(Paths.get(System.getProperty("user.home")).toFile());
                fileChooser.setInitialFileName("snapshot.png");
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png")
                );

                var file = fileChooser.showSaveDialog(getScene().getWindow());
                if (file == null) {
                    return;
                }

                try {
                    SnapshotParameters sp = new SnapshotParameters();
                    WritableImage img = page.getSnapshotTarget().snapshot(sp, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void createView() {
        sidebar.setMinWidth(CashierApp.SIDEBAR_WIDTH);
        sidebar.setMaxWidth(CashierApp.SIDEBAR_WIDTH);


        HBox.setHgrow(subLayerPane, ALWAYS);

        // ~

        setId("main");
        //setTop(headerBar);
        setLeft(sidebar);
        setCenter(subLayerPane);
    }

    private void initListeners() {
        model.selectedPageProperty().addListener((obs, old, val) -> {
            if (val != null) {
                loadPage(val);
            }
        });


        // switch to the source code and back
        DefaultEventBus.getInstance().subscribe(PageEvent.class, e -> {
            if (e.getAction() == Action.SOURCE_CODE_ON) {
                model.showSourceCode();
            }
            if (e.getAction() == Action.SOURCE_CODE_OFF) {
                model.hideSourceCode();
            }
        });
    }

    private void loadPage(Class<? extends Page> pageClass) {
        try {
            final Page prevPage = (Page) subLayerPane.getChildren().stream()
                .filter(c -> c instanceof Page)
                .findFirst()
                .orElse(null);
            final Page nextPage = pageClass.getDeclaredConstructor().newInstance();

            // startup, no prev page, no animation
            if (getScene() == null) {
                subLayerPane.getChildren().add(nextPage.getView());
                return;
            }

            Objects.requireNonNull(prevPage);

            // reset previous page, e.g. to free resources
            prevPage.reset();

            // animate switching between pages
            subLayerPane.getChildren().add(nextPage.getView());
            subLayerPane.getChildren().remove(prevPage.getView());
            var transition = new FadeTransition(Duration.millis(PAGE_TRANSITION_DURATION), nextPage.getView());
            transition.setFromValue(0.0);
            transition.setToValue(1.0);
            transition.setOnFinished(t -> {
                if (nextPage instanceof Pane nextPane) {
                    nextPane.toFront();
                }
            });
            transition.play();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
