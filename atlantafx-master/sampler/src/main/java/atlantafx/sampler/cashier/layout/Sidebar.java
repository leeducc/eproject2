/* SPDX-License-Identifier: MIT */

package atlantafx.sampler.cashier.layout;


import atlantafx.base.controls.Spacer;
import atlantafx.sampler.Resources;
import atlantafx.sampler.cashier.event.BrowseEvent;
import atlantafx.sampler.cashier.event.DefaultEventBus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URI;

final class Sidebar extends VBox {

    private final NavTree navTree;


    public Sidebar(MainModel model) {
        super();

        this.navTree = new NavTree(model);

        createView();




        model.selectedPageProperty().addListener((obs, old, val) -> {
            if (val != null) {
                navTree.getSelectionModel().select(model.getTreeItemForPage(val));
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




    ///////////////////////////////////////////////////////////////////////////

    private class Header extends VBox {

        public Header() {
            super();

            getStyleClass().add("header");
            getChildren().setAll(
                createLogo()
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




            var root = new HBox(10, imageBox, titleLbl, new Spacer());
            root.getStyleClass().add("logo");
            root.setAlignment(Pos.CENTER_LEFT);

            return root;
        }


    }
}
