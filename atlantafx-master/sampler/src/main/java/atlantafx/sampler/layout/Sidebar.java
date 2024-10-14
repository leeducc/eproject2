/* SPDX-License-Identifier: MIT */

package atlantafx.sampler.layout;

import static atlantafx.base.theme.Styles.TEXT_BOLD;
import static atlantafx.base.theme.Styles.TEXT_MUTED;
import static atlantafx.base.theme.Styles.TEXT_SMALL;
import static atlantafx.base.theme.Styles.TEXT_SUBTLE;
import static atlantafx.base.theme.Styles.TITLE_3;
import static atlantafx.sampler.Launcher.IS_DEV_MODE;

import atlantafx.base.controls.Spacer;
import atlantafx.base.theme.Styles;
import atlantafx.sampler.Resources;
import atlantafx.sampler.event.BrowseEvent;
import atlantafx.sampler.event.DefaultEventBus;
import atlantafx.sampler.event.HotkeyEvent;
import atlantafx.sampler.util.Lazy;
import java.net.URI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2MZ;
import org.kordamp.ikonli.material2.Material2OutlinedAL;

final class Sidebar extends VBox {

    private final NavTree navTree;
    private final Lazy<SearchDialog> searchDialog;


    public Sidebar(MainModel model) {
        super();

        this.navTree = new NavTree(model);

        createView();

        searchDialog = new Lazy<>(() -> {
            var dialog = new SearchDialog(model);
            dialog.setClearOnClose(true);
            return dialog;
        });



        model.selectedPageProperty().addListener((obs, old, val) -> {
            if (val != null) {
                navTree.getSelectionModel().select(model.getTreeItemForPage(val));
            }
        });

        DefaultEventBus.getInstance().subscribe(HotkeyEvent.class, e -> {
            if (e.getKeys().getCode() == KeyCode.SLASH) {
                openSearchDialog();
            }
        });


    }

    private void createView() {
        var header = new Header();

        VBox.setVgrow(navTree, Priority.ALWAYS);

        setId("sidebar");
        getChildren().addAll(header, navTree, createFooter());
    }

    void begForFocus() {
        navTree.requestFocus();
    }

    private HBox createFooter() {
        var versionLbl = new Label("v" + System.getProperty("app.version"));
        versionLbl.getStyleClass().addAll(
            "version", TEXT_SMALL, TEXT_BOLD, TEXT_SUBTLE
        );
        versionLbl.setCursor(Cursor.HAND);
        versionLbl.setOnMouseClicked(e -> {
            var homepage = System.getProperty("app.homepage");
            if (homepage != null) {
                DefaultEventBus.getInstance().publish(new BrowseEvent(URI.create(homepage)));
            }
        });
        versionLbl.setTooltip(new Tooltip("Visit homepage"));

        var footer = new HBox(versionLbl);
        footer.getStyleClass().add("footer");

        return footer;
    }

    private void openSearchDialog() {
        var dialog = searchDialog.get();
        dialog.show(getScene());
        Platform.runLater(dialog::begForFocus);
    }


    ///////////////////////////////////////////////////////////////////////////

    private class Header extends VBox {

        public Header() {
            super();

            getStyleClass().add("header");
            getChildren().setAll(
                createLogo(), createSearchButton()
            );
        }

        private HBox createLogo() {
            var image = new ImageView(
                new Image(Resources.getResource("assets/app-icon.png").toString())
            );
            image.setFitWidth(32);
            image.setFitHeight(32);

            var imageBorder = new Insets(1);
            var imageBox = new StackPane(image);
            imageBox.getStyleClass().add("image");
            imageBox.setPadding(imageBorder);
            imageBox.setPrefSize(
                image.getFitWidth() + imageBorder.getRight() * 2,
                image.getFitWidth() + imageBorder.getTop() * 2
            );
            imageBox.setMaxSize(
                image.getFitHeight() + imageBorder.getTop() * 2,
                image.getFitHeight() + imageBorder.getRight() * 2
            );

            var titleLbl = new Label("BlueOPS");
            titleLbl.getStyleClass().addAll(TITLE_3);
            if (IS_DEV_MODE) {
                var devLabel = new Label();
                devLabel.setGraphic(new FontIcon(Material2OutlinedAL.INFO));
                devLabel.getStyleClass().addAll("dev-indicator", Styles.WARNING);
                devLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                devLabel.setTooltip(new Tooltip("App is running in development mode"));

                titleLbl.setContentDisplay(ContentDisplay.RIGHT);
                titleLbl.setGraphic(devLabel);
            }


            var root = new HBox(10, imageBox, titleLbl, new Spacer());
            root.getStyleClass().add("logo");
            root.setAlignment(Pos.CENTER_LEFT);

            return root;
        }

        private Button createSearchButton() {
            var titleLbl = new Label("Search", new FontIcon(Material2MZ.SEARCH));

            var hintLbl = new Label("Press /");
            hintLbl.getStyleClass().addAll("hint", TEXT_MUTED, TEXT_SMALL);

            var searchBox = new HBox(titleLbl, new Spacer(), hintLbl);
            searchBox.getStyleClass().add("content");
            searchBox.setAlignment(Pos.CENTER_LEFT);

            var root = new Button();
            root.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            root.getStyleClass().addAll("search-button");
            root.setGraphic(searchBox);
            root.setOnAction(e -> openSearchDialog());
            root.setMaxWidth(Double.MAX_VALUE);

            return root;
        }
    }
}
