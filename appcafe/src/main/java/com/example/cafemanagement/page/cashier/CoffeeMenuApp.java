package com.example.cafemanagement.page.cashier;

import com.example.cafemanagement.entities.Products;
import com.example.cafemanagement.service.cashier.CashierService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CoffeeMenuApp {
  List<Products> filteredProducts = new ArrayList<>();

  public VBox viewProduct(Button comeBack) {
    // Main layout
    VBox mainLayout = new VBox(10);
    mainLayout.setPadding(new Insets(10));
    mainLayout.getStylesheets().add(getClass().getResource("/css/coffeeMenuApp.css").toExternalForm());
    mainLayout.getStyleClass().add("root"); // Apply background style from CSS

    // Search Bar
    TextField searchBar = new TextField();
    searchBar.setPromptText("Nhập mã/Tên món cần tìm");
    searchBar.setPrefWidth(300);
    searchBar.getStyleClass().add("text-field");

    // Search Button
    Button searchButton = new Button("🔍");
    searchButton.getStyleClass().add("button");

    // ComboBox for filtering
    ComboBox<String> comboBox = CashierService.createPayCategoriesSelectionBox();
    comboBox.getItems().add("All");
    comboBox.setValue("All");
    comboBox.getStyleClass().add("combo-box");

    // Top HBox layout for search bar, search button, and ComboBox
    HBox topLayout = new HBox(10, searchBar, searchButton, comboBox);
    topLayout.setPadding(new Insets(10));
    topLayout.setAlignment(Pos.CENTER);

    // Product Grid Layout
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(15);
    gridPane.setVgap(15);
    gridPane.setAlignment(Pos.CENTER);
    // Add a listener to the ComboBox for filtering
    // Coffee menu items
    filteredProducts = CashierService.getAllProducts();
    comboBox.setOnAction(event -> {
      String selectFilter =  comboBox.getValue();
      if ("All".equals(selectFilter) || selectFilter.isEmpty() || selectFilter == null) {
        filteredProducts = CashierService.getAllProducts();
      } else {
        filteredProducts = CashierService.getProductsByCategory(selectFilter);
      }
      updateProductGrid(gridPane, filteredProducts);
      });

    // Create coffee menu grid
    int column = 0;
    int row = 0;

    for (int i = 0; i < filteredProducts.size(); i++) {
      Products product = filteredProducts.get(i);
      String imagePath = product.getImageLink();

      // Ensure the image path is valid
      if (imagePath != null && getClass().getResource(imagePath) != null) {
        Image productImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180, 175, false, false);
        ImageView imageView = new ImageView(productImage);

        // Coffee name and price labels
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(new Font("Arial", 18));
        nameLabel.getStyleClass().add("label-bold");

        Label priceLabel = new Label("$" + String.valueOf(product.getPrice()));
        priceLabel.setFont(new Font("Arial", 14));
        priceLabel.getStyleClass().add("label-price");

        // VBox for each product item
        VBox productBox = new VBox(10, imageView, nameLabel, priceLabel);
        productBox.setPadding(new Insets(10));
        productBox.setAlignment(Pos.CENTER);
        productBox.getStyleClass().add("product-box");
        productBox.setOnMouseEntered(e -> productBox.setStyle("-fx-background-color: #f0f0f0;"));
        productBox.setOnMouseExited(e -> productBox.setStyle("-fx-background-color: #f9f9f9;"));

        // Add to grid
        gridPane.add(productBox, column, row);

        // Adjust column/row for next product
        column++;
        if (column == 3) {  // 3 products per row
          column = 0;
          row++;
        }
      } else {
        System.out.println("Invalid image path for product: " + product.getName());
      }
    }

    // ScrollPane for Product Grid Layout
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(gridPane);
    scrollPane.setFitToWidth(true); // Makes sure the content adjusts to the width of the ScrollPane
    scrollPane.setPannable(true); // Allows panning
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar when needed
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar

    // Add components to the main layout, including the ScrollPane
    mainLayout.getChildren().addAll(topLayout, scrollPane, comeBack);

    return mainLayout;
  }
  private void updateProductGrid(GridPane gridPane, List<Products> productList) {
    gridPane.getChildren().clear(); // Clear existing items in the grid

    int column = 0;
    int row = 0;
    for (int i = 0; i < productList.size(); i++) {
      Products product = productList.get(i);
      String imagePath = product.getImageLink();

      // Ensure the image path is valid
      if (imagePath != null && getClass().getResource(imagePath) != null) {
        Image productImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180, 175, false, false);
        ImageView imageView = new ImageView(productImage);

        // Coffee name and price labels
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(new Font("Arial", 18));
        nameLabel.getStyleClass().add("label-bold");

        Label priceLabel = new Label("$" + String.valueOf(product.getPrice()));
        priceLabel.setFont(new Font("Arial", 14));
        priceLabel.getStyleClass().add("label-price");

        // VBox for each product item
        VBox productBox = new VBox(10, imageView, nameLabel, priceLabel);
        productBox.setPadding(new Insets(10));
        productBox.setAlignment(Pos.CENTER);
        productBox.getStyleClass().add("product-box");

        productBox.setOnMouseEntered(e -> productBox.setStyle("-fx-background-color: #f0f0f0;"));
        productBox.setOnMouseExited(e -> productBox.setStyle("-fx-background-color: #f9f9f9;"));

        // Add to grid
        gridPane.add(productBox, column, row);

        // Adjust column/row for next product
        column++;
        if (column == 3) {  // 3 products per row
          column = 0;
          row++;
        }
      } else {
        System.out.println("Invalid image path for product: " + product.getName());
      }
    }
  }

}
