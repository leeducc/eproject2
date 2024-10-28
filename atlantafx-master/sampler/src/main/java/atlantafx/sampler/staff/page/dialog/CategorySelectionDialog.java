package atlantafx.sampler.staff.page.dialog;

import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.base.entity.common.Product;
import atlantafx.sampler.cashier.layout.ModalDialog;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CategorySelectionDialog extends ModalDialog {

    private TableView<Product> productTableView;
    private final List<Product> selectedProducts;
    private final int categoryId;
    private final TableDialog parentDialog;
    private final int tableNumber;


    public CategorySelectionDialog(TableView<Product> productTableView, int categoryId, List<Product> selectedProducts, TableDialog parentDialog, int tableNumber) {
        super();
        this.productTableView = productTableView;
        this.categoryId = categoryId;
        this.selectedProducts = selectedProducts;
        this.parentDialog = parentDialog;
        this.tableNumber = tableNumber; // Initialize table number

        setId("category-selection-dialog");
        header.setTitle("Select Products for Table " + tableNumber);
        content.setBody(createContent());
        content.setPrefSize(600, 400);
    }

    private VBox createContent() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        productTableView = createProductTableView();
        Button addButton = new Button("Add Selected Product");
        addButton.setOnAction(e -> addSelectedProduct());

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> redirectToTableDialog()); // Redirect instead of close

        HBox buttonLayout = new HBox(10, addButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(productTableView, buttonLayout);
        return mainLayout;
    }

    private TableView<Product> createProductTableView() {
        TableView<Product> tableView = new TableView<>();

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty().asObject());

        tableView.getColumns().addAll(nameColumn, priceColumn);
        loadProductsByCategory(categoryId, tableView);

        return tableView;
    }

    private void loadProductsByCategory(int categoryId, TableView<Product> tableView) {
        String query = "SELECT id, name, price FROM products WHERE category_id = ?"; // Adjust the SQL query as per your database structure

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                Product product = new Product(id, name, price, 1); // Set default quantity to 1
                tableView.getItems().add(product);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions properly in production code
        }
    }

    private void addSelectedProduct() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Product productToAdd = new Product(selectedProduct.getId(), selectedProduct.getName(),
                    selectedProduct.getPrice(), 1); // Default quantity is 1
            parentDialog.addProductToTable(productToAdd); // Add to product table in TableDialog



            redirectToTableDialog(); // Close dialog after adding
        } else {
            showAlert("No Product Selected", "Please select a product to add.");
        }
    }

    private void redirectToTableDialog() {
        // Close this dialog and open the parent TableDialog
        parentDialog.show(getScene()); // Show the parent TableDialog again
        close(); // Close this dialog
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
