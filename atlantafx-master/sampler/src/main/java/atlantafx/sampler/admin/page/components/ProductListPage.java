package atlantafx.sampler.admin.page.components;

import atlantafx.sampler.admin.page.OutlinePage;
import atlantafx.sampler.admin.page.dialog.AddProductDialog;
import atlantafx.sampler.admin.page.dialog.EditProductDialog;
import atlantafx.sampler.base.entity.common.Product;
import atlantafx.sampler.base.service.cashier.CashierService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProductListPage extends OutlinePage {

    public static final String NAME = "Product List Page";
    private List<Product> products;
    private final CashierService cashierService = new CashierService();
    private ListView<Product> productListView;

    @Override
    public String getName() {
        return NAME;
    }

    public ProductListPage() {
        super();
        loadProducts(); // Load products from the database
        createLayout();
    }

    private void createLayout() {
        productListView = new ListView<>();
        productListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productListView.getItems().addAll(products);

        Button addButton = new Button("Add Product");
        addButton.setOnAction(event -> openAddProductDialog());

        Button editButton = new Button("Edit Product");
        editButton.setOnAction(event -> openEditProductDialog());

        VBox layout = new VBox(10, productListView, addButton, editButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;"); // Add some padding
        getChildren().add(layout); // Add layout to the main page
    }

    private void loadProducts() {
        // Fetch products from the database
        products = cashierService.getAllProducts();
    }

    private void openAddProductDialog() {
        AddProductDialog addProductDialog = new AddProductDialog();
        addProductDialog.showModalAndWait(getScene());
        refreshProductList(); // Refresh the list after adding
    }

    private void openEditProductDialog() {
        Product selectedProduct = productListView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            EditProductDialog editProductDialog = new EditProductDialog(selectedProduct);
            editProductDialog.showModalAndWait(getScene());
            refreshProductList(); // Refresh the list after editing
        } else {
            showAlert("Error", "Please select a product to edit.");
        }
    }

    private void refreshProductList() {
        productListView.getItems().clear();
        loadProducts(); // Reload products from the database
        productListView.getItems().addAll(products); // Update the ListView
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
