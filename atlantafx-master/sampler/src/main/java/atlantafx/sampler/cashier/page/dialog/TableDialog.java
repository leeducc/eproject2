package atlantafx.sampler.cashier.page.dialog;

import atlantafx.base.controls.CustomTextField;
import atlantafx.sampler.base.entity.common.Category;
import atlantafx.sampler.base.entity.common.Discount;
import atlantafx.sampler.base.entity.common.Product;
import atlantafx.sampler.base.service.cashier.CashierService;
import atlantafx.sampler.cashier.layout.ModalDialog;
import atlantafx.sampler.cashier.page.components.TableListPage;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TableDialog extends ModalDialog {

    private final TableView<Product> productTableView; // TableView for displaying selected products
    private final CashierService cashierService;
    private final int tableNumber; // Store the table number
    private final CustomTextField quantityInput; // Input field for quantity

    public TableDialog(String tableTitle, CashierService cashierService, int tableNumber) {
        super();
        this.cashierService = cashierService;
        this.tableNumber = tableNumber; // Initialize table number

        this.productTableView = createProductTableView(); // Initialize productTableView
        this.quantityInput = new CustomTextField();
        setId("table-dialog");
        header.setTitle("Table: " + tableTitle);

        content.setBody(createContent());
        content.setPrefSize(1200, 800);
    }



    private VBox createContent() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER); // Center align all content

        Label quantityLabel = new Label("Enter Quantity:");
        quantityInput.setPromptText("Quantity");
        quantityInput.setPrefWidth(200);

        VBox leftPane = new VBox(10);
        leftPane.setAlignment(Pos.TOP_LEFT);
        leftPane.getChildren().addAll(quantityLabel, quantityInput, productTableView);

        VBox rightPane = createCategoryButtons();
        HBox mainContent = new HBox(20, leftPane, rightPane);
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        leftPane.setPrefWidth(800);
        rightPane.setPrefWidth(200);
        mainLayout.getChildren().add(mainContent);

        HBox footer = createFooter();
        mainLayout.getChildren().add(footer);

        return mainLayout;
    }

    private HBox createFooter() {
        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20, 0, 0, 0));

        Label totalQuantityLabel = new Label("Total Quantity: " + getTotalQuantity());
        Label totalDiscountLabel = new Label("Total Discount: " + getTotalDiscount());
        Label totalPriceLabel = new Label("Total Price: " + getTotalPrice());

        footer.getChildren().addAll(totalQuantityLabel, totalDiscountLabel, totalPriceLabel);

        Button backButton = new Button("Back");
        Button cancelLastButton = new Button("Cancel Last Line");
        Button quantityButton = new Button("Quantity");
        Button calculateButton = new Button("Calculate");

        backButton.setOnAction(e -> close());
        cancelLastButton.setOnAction(e -> cancelLastProduct());
        quantityButton.setOnAction(e -> updateQuantity());
        calculateButton.setOnAction(e -> openCalculateDialog());

        footer.getChildren().addAll(backButton, cancelLastButton, quantityButton, calculateButton);
        return footer;
    }

    private void cancelLastProduct() {
        if (!productTableView.getItems().isEmpty()) {
            productTableView.getItems().remove(productTableView.getItems().size() - 1);
        }
    }

    private void updateQuantity() {
        if (!productTableView.getItems().isEmpty() && !quantityInput.getText().isEmpty()) {
            int quantity = Integer.parseInt(quantityInput.getText());
            Product lastProduct = productTableView.getItems().get(productTableView.getItems().size() - 1);
            lastProduct.setQuantity(quantity);
            productTableView.refresh();
        }
    }

    private void calculateTotal() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Calculation");
        alert.setHeaderText(null);
        alert.setContentText("Total calculation logic goes here.");
        alert.showAndWait();
    }

    private TableView<Product> createProductTableView() {
        TableView<Product> tableView = new TableView<>();
        tableView.setPrefHeight(400);
        tableView.setPrefWidth(800);
        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (product != null) {
                    // Check for valid discount for the current product
                    Discount validDiscount = CashierService.getValidDiscountForProduct(product.getId());

                    // If there is a valid discount, calculate the discount amount and final price
                    if (validDiscount != null) {
                        BigDecimal discountAmount = BigDecimal.valueOf(product.getPrice())
                                .multiply(validDiscount.getDiscountPercentage().divide(BigDecimal.valueOf(100)))
                                .setScale(2, RoundingMode.HALF_UP);
                        BigDecimal finalPrice = BigDecimal.valueOf(product.getPrice()).subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);

                        // Create the discount label with formatted values
                        Label discountLabel = new Label(String.format("Discount: %s - %s%% (Final Price: %s)",
                                validDiscount.getDiscountName(),
                                validDiscount.getDiscountPercentage().toString(),
                                finalPrice.toString()));

                        setGraphic(discountLabel);
                    } else {
                        // If no valid discount, clear the graphic
                        setGraphic(null);
                    }

                    // Set product name or other properties as text
                    setText(product.getName());
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }



        });

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameColumn.setMinWidth(300);

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        quantityColumn.setMinWidth(100);

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        priceColumn.setMinWidth(100);

        TableColumn<Product, Double> totalColumn = new TableColumn<>("Total Amount");
        totalColumn.setCellValueFactory(cellData -> {
            BigDecimal price = BigDecimal.valueOf(cellData.getValue().getPrice());
            int quantity = cellData.getValue().getQuantity();
            BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
            return new SimpleDoubleProperty(total.doubleValue()).asObject();
        });
        totalColumn.setMinWidth(200);

        tableView.getColumns().addAll(nameColumn, quantityColumn, priceColumn, totalColumn);
        return tableView;
    }

    private int getTotalQuantity() {
        return productTableView.getItems().stream().mapToInt(Product::getQuantity).sum();
    }

    private double getTotalDiscount() {
        return productTableView.getItems().stream()
                .filter(p -> p.getDiscount() != null)
                .mapToDouble(p -> p.getDiscount().getDiscountPercentage()
                        .multiply(BigDecimal.valueOf(p.getPrice()))
                        .divide(BigDecimal.valueOf(100))
                        .doubleValue()).sum();
    }

    private double getTotalPrice() {
        return productTableView.getItems().stream()
                .mapToDouble(p -> {
                    if (p.getDiscount() != null) {
                        BigDecimal discountAmount = p.getDiscount().getDiscountPercentage()
                                .multiply(BigDecimal.valueOf(p.getPrice()))
                                .divide(BigDecimal.valueOf(100));
                        return BigDecimal.valueOf(p.getPrice()).subtract(discountAmount).doubleValue() * p.getQuantity();
                    }
                    return p.getPrice() * p.getQuantity();
                }).sum();
    }

    public void addProductToTable(Product product) {
        for (Product existingProduct : productTableView.getItems()) {
            if (existingProduct.getId() == product.getId()) {
                existingProduct.setQuantity(existingProduct.getQuantity() + 1);
                productTableView.refresh();
                return;
            }
        }
        product.setQuantity(1);
        productTableView.getItems().add(product);
    }

    private VBox createCategoryButtons() {
        VBox categoryBox = new VBox(10);
        categoryBox.setAlignment(Pos.TOP_LEFT);

        Label categoryLabel = new Label("Select a Category:");
        categoryBox.getChildren().add(categoryLabel);

        for (Category category : cashierService.getAllCategories()) {
            Button categoryButton = new Button(category.getName());
            categoryButton.setMinSize(150, 50);
            categoryButton.setOnAction(e -> openCategorySelectionDialog(category, tableNumber));
            categoryBox.getChildren().add(categoryButton);
        }

        return categoryBox;
    }


    private void openCategorySelectionDialog(Category category, int tableNumber) {
        CategorySelectionDialog dialog = new CategorySelectionDialog(productTableView, category.getId(), new ArrayList<>(), this, tableNumber);
        dialog.showModalAndWait(getScene());
    }

    private void openCalculateDialog() {
    double totalAmount = getTotalPrice();
    CalculateDialog calculateDialog = new CalculateDialog(totalAmount, this);
    calculateDialog.showModalAndWait(getScene());
}


}
