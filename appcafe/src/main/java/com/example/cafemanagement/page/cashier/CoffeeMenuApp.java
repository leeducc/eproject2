package com.example.cafemanagement.page.cashier;

import com.example.cafemanagement.entities.Products;
import com.example.cafemanagement.service.cashier.CashierService;
import javafx.application.Application;
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

  private List<Products> productsList = new ArrayList<>();


  public VBox viewProduct (Button comeBack) {
    // Main layout
    VBox mainLayout = new VBox(10);
    mainLayout.setPadding(new Insets(10));

    // Search Bar
    TextField searchBar = new TextField();
    searchBar.setPromptText("Nhập mã/Tên món cần tìm");
    searchBar.setPrefWidth(300);

    // Search Button
    Button searchButton = new Button("🔍");

    // ComboBox for filtering
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll("Coffe", "Tea", "Juice");
    comboBox.setValue("Coffe");

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

    // Coffee menu items
    productsList = CashierService.getAllProducts();

    // Create coffee menu grid
    int column = 0;
    int row = 0;

    for (int i = 0; i < productsList.size(); i++) {
      Products product = productsList.get(i);
      String imagePath = product.getImageLink();

      // Ensure the image path is valid
      if (imagePath != null && getClass().getResource(imagePath) != null) {
        Image productImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180, 175, false, false);
        ImageView imageView = new ImageView(productImage);

        // Coffee name and price labels
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(new Font("Arial", 18));
        nameLabel.setStyle("-fx-font-weight: bold;");
        Label priceLabel = new Label("$" + String.valueOf(product.getPrice()));
        priceLabel.setFont(new Font("Arial", 14));
        priceLabel.setStyle("-fx-text-fill: #777;");

        // VBox for each product item
        VBox productBox = new VBox(10, imageView, nameLabel, priceLabel);
        productBox.setPadding(new Insets(10));
        productBox.setAlignment(Pos.CENTER);
        productBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-color: #f9f9f9; -fx-background-radius: 8;");
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

    // Add components to the main layout
    mainLayout.getChildren().addAll(topLayout, gridPane,comeBack);

    return mainLayout;
  }
}
