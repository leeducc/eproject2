package atlantafx.sampler.admin.page.components;

import atlantafx.sampler.admin.page.OutlinePage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox; // Import VBox for layout

public final class AddProductPage extends OutlinePage {
    public static final String NAME = "Add Product";

    @Override
    public String getName() {
        return NAME;
    }

    public AddProductPage() {
        super();

        // Create a label to display the message
        Label messageLabel = new Label("Hello, this is Add Product page");

        // Create a layout to hold the label
        VBox layout = new VBox();
        layout.getChildren().add(messageLabel);
        layout.setStyle("-fx-padding: 20;"); // Optional: add padding for aesthetics


    }
}
