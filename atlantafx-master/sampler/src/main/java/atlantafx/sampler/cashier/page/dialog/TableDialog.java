package atlantafx.sampler.cashier.page.dialog;

import atlantafx.sampler.base.entity.common.Category;
import atlantafx.sampler.base.entity.common.Product;
import atlantafx.sampler.base.service.cashier.CashierService;
import atlantafx.sampler.cashier.layout.ModalDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableDialog extends ModalDialog {
    private TilePane productGrid; // Grid for displaying products
    private ObservableList<Product> addedProducts = FXCollections.observableArrayList(); // List of added products
    private ListView<String> orderList; // List view to show added products
    private Text totalQuantityText; // Text for total quantity
    private Text totalPriceText; // Text for total price
    private Map<Product, Integer> productQuantityMap = new HashMap<>(); // To track quantities


    public TableDialog(String tableName) {
        super();

        setId("table-dialog");
        header.setTitle("Table: " );

        content.setBody(createContent());
        content.setPrefSize(1200, 800);
    }


    private VBox createContent() {
        // Create main layout
        HBox mainLayout = new HBox(10);
        mainLayout.setPadding(new Insets(10));

        // Left side layout
        VBox leftSide = new VBox(10);

        // Search Field
        TextField searchField = new TextField();
        searchField.setPromptText("Search products...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadProductsBySearch(newValue);
        });

        // Category Row
        HBox categoryRow = new HBox(10);
        List<Category> categories = CashierService.loadCategories();
        Button allCategoryButton = new Button("All");
        allCategoryButton.setOnAction(e -> loadProducts(null));
        categoryRow.getChildren().add(allCategoryButton);

        // Create buttons for each category
        for (Category category : categories) {
            Button categoryButton = new Button(category.getName());
            categoryButton.setOnAction(e -> loadProducts(category.getId()));
            categoryRow.getChildren().add(categoryButton);
        }

        // Product Grid
        productGrid = createProductGrid();
        loadProducts(null); // Load all products initially

        // Add components to left side
        leftSide.getChildren().addAll(searchField, categoryRow, productGrid);

        // Right side layout
        VBox rightSide = createOrderSummary();

        // Add both sides to main layout
        mainLayout.getChildren().addAll(leftSide, rightSide);

        // Set the main layout into the dialog's content area
      return new VBox(mainLayout);
    }

    private VBox createOrderSummary() {
        VBox orderSummary = new VBox(10);
        orderSummary.setPadding(new Insets(10));

        Label orderTitle = new Label("Order Summary");
        orderList = new ListView<>();
        totalQuantityText = new Text("Total Quantity: 0");
        totalPriceText = new Text("Total Price: $0.00");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> close());

        Button payButton = new Button("Pay");
        payButton.setOnAction(e -> handlePayment()); // Handle payment process

        orderSummary.getChildren().addAll(orderTitle, orderList, totalQuantityText, totalPriceText, backButton, payButton);
        return orderSummary;
    }

    private VBox createProductBox(Product product) {
        VBox productBox = new VBox(5);
        productBox.setAlignment(Pos.CENTER);

        // Set image for product
        ImageView productImage = new ImageView(new Image(getClass().getResourceAsStream(product.getImageLink())));
        productImage.setFitWidth(100);
        productImage.setFitHeight(100);

        // Product details
        Label productName = new Label(product.getName());
        Label productPrice = new Label(String.format("Price: $%.2f", product.getDiscountedPrice()));

        // Quantity input
        TextField quantityField = new TextField("1");
        quantityField.setPrefWidth(50);

        // Add Product button
        Button addButton = new Button("Add Product");
        addButton.setOnAction(e -> {
            int quantity = Integer.parseInt(quantityField.getText());
            addProductToOrder(product, quantity);
        });

        // Delete button
        Button deleteButton = new Button("Remove");
        deleteButton.setOnAction(e -> {
            removeProductFromOrder(product);
        });

        productBox.getChildren().addAll(productImage, productName, productPrice, quantityField, addButton, deleteButton);
        return productBox;
    }

    private void addProductToOrder(Product product, int quantity) {
        addedProducts.add(product);
        productQuantityMap.put(product, productQuantityMap.getOrDefault(product, 0) + quantity);
        updateOrderList();
    }

    private void removeProductFromOrder(Product product) {
        if (addedProducts.remove(product)) {
            productQuantityMap.remove(product);
            updateOrderList();
        }
    }

    private void updateOrderList() {
        orderList.getItems().clear(); // Clear existing items
        int totalQuantity = 0;
        double totalPrice = 0.0;

        for (Product product : addedProducts) {
            int quantity = productQuantityMap.get(product);
            orderList.getItems().add(product.getName() + " - $" + product.getDiscountedPrice() + " x " + quantity);
            totalQuantity += quantity;
            totalPrice += product.getDiscountedPrice() * quantity;
        }

        totalQuantityText.setText("Total Quantity: " + totalQuantity);
        totalPriceText.setText(String.format("Total Price: $%.2f", totalPrice));
    }

    private TilePane createProductGrid() {
        TilePane productGrid = new TilePane();
        productGrid.setHgap(10);
        productGrid.setVgap(10);
        productGrid.setPadding(new Insets(10));
        return productGrid;
    }

    private void loadProducts(Integer categoryId) {
        productGrid.getChildren().clear();

        // Load products based on category from CashierService
        List<Product> products = CashierService.loadProducts();
        for (Product product : products) {
            if (categoryId == null || product.getCategoryId() == categoryId) {
                VBox productBox = createProductBox(product);
                productGrid.getChildren().add(productBox);
            }
        }
    }

    private void loadProductsBySearch(String query) {
        productGrid.getChildren().clear();

        List<Product> products = CashierService.loadProducts();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                VBox productBox = createProductBox(product);
                productGrid.getChildren().add(productBox);
            }
        }
    }

    private void handlePayment() {
        // Implement payment handling logic here
        Alert paymentAlert = new Alert(Alert.AlertType.INFORMATION);
        paymentAlert.setTitle("Payment");
        paymentAlert.setHeaderText("Payment Successful");
        paymentAlert.setContentText("Thank you for your order!");
        paymentAlert.showAndWait();
    }
}
