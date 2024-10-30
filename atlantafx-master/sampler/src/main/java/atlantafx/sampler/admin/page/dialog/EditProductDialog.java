package atlantafx.sampler.admin.page.dialog;

import atlantafx.sampler.admin.layout.ModalDialog;
import atlantafx.sampler.base.entity.common.Product;


import atlantafx.sampler.base.service.cashier.CashierService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditProductDialog extends ModalDialog {

    private final TextField imageLinkField = new TextField();
    private final TextField nameField = new TextField();
    private final TextField priceField = new TextField();
    private final ComboBox<Integer> categoryComboBox = new ComboBox<>();
    private final Product productToEdit;
    private final CashierService cashierService = new CashierService();

    public EditProductDialog(Product product) {
        super();
        this.productToEdit = product;
        header.setTitle("Edit Product");
        content.setBody(createContent());
        content.setFooter(createFooter());
        content.setPrefSize(400, 300);

        // Pre-fill fields with existing product data
        imageLinkField.setText(product.getImageLink());
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        categoryComboBox.setValue(product.getCategoryId()); // Assuming this is an Integer
    }

    private VBox createContent() {
        Label imageLinkLabel = new Label("Image Link:");
        Label nameLabel = new Label("Product Name:");
        Label priceLabel = new Label("Price:");
        Label categoryLabel = new Label("Category:");

        // Populate categoryComboBox with category IDs
        categoryComboBox.getItems().addAll(1, 2, 3); // Example IDs; replace with actual category IDs

        VBox content = new VBox(10, imageLinkLabel, imageLinkField,
                nameLabel, nameField,
                priceLabel, priceField,
                categoryLabel, categoryComboBox);
        content.setPadding(new Insets(20));
        return content;
    }

    private HBox createFooter() {
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            // Collect updated product data
            productToEdit.setImageLink(imageLinkField.getText());
            productToEdit.setName(nameField.getText());
            productToEdit.setPrice(Double.parseDouble(priceField.getText()));
            productToEdit.setCategoryId(categoryComboBox.getValue());

            if (cashierService.updateProduct(productToEdit)) {
                close(); // Close dialog on success
            } else {
                showAlert("Error", "Failed to update product.");
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> close());

        return new HBox(10, saveButton, cancelButton);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
