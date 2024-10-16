package com.example.cafemanagement.page.cashier;

import com.example.cafemanagement.entities.Products;
import com.example.cafemanagement.service.cashier.CashierService;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CoffeeMenuApp extends Application {

  private List<Products> productsList = new ArrayList<Products>();

  @Override
  public void start(Stage primaryStage) {
    // GridPane layout
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    // Search Bar
    TextField searchBar = new TextField();
    searchBar.setPromptText("Nhập mã/Tên món cần tìm");

    // Search Button (placeholder)
    Button searchButton = new Button("🔍");

    // ComboBox for filtering
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll("Coffe", "Tea", "Juice");
    comboBox.setValue("Coffe");

    // Top HBox layout for search bar, search button, and ComboBox
    HBox topLayout = new HBox(10, searchBar, searchButton, comboBox);
    topLayout.setPadding(new Insets(10));

    // Coffee menu items (images, names, and prices)
    productsList = CashierService.getAllProducts();

    // Create coffee menu grid

    for (int i = 0; i < productsList.size(); i++) {
      Products product = productsList.get(i);
      String imagePath = product.getImageLink();
      if (imagePath != null && getClass().getResource(imagePath) != null) {
        Image coffeeImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180, 175, false, false);
        ImageView imageView = new ImageView(coffeeImage);

        // Coffee name and price
        Label nameLabel = new Label(product.getName());
        Label priceLabel = new Label(String.valueOf(product.getPrice()));

        // VBox for each coffee item
        VBox coffeeItem = new VBox(imageView, nameLabel, priceLabel);
        gridPane.add(coffeeItem,  i/3, i%3);

//        col++;
//        if (col == 4) {  // Assume 3 items per row
//          col = 0;
//          row++;
//        }
      } else {
        System.out.println("Invalid image path for product: " + product.getName());
      }
    }

    // Main layout
    VBox mainLayout = new VBox(10, topLayout, gridPane);
    mainLayout.setPadding(new Insets(10));

    // Set scene and stage
    Scene scene = new Scene(mainLayout, 800, 600);
    scene.getStylesheets().add(getClass().getResource("/css/coffeeMenuApp.css").toExternalForm());
    primaryStage.setTitle("Coffee Menu");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

