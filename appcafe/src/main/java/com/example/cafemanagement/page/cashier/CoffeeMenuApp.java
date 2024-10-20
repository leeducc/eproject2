package com.example.cafemanagement.page.cashier;

import com.example.cafemanagement.entities.Bill;
import com.example.cafemanagement.entities.Products;
import com.example.cafemanagement.service.cashier.CashierService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CoffeeMenuApp {

  List<Products> filteredProducts = new ArrayList<>();
  private static File selectedFile;
  FileChooser fileChooser = new FileChooser();

  public VBox viewProduct(Button comeBack, Stage primaryStage) {
    // Main layout
    VBox mainLayout = new VBox(10);
    mainLayout.setPadding(new Insets(10));
    mainLayout.getStylesheets()
        .add(getClass().getResource("/css/coffeeMenuApp.css").toExternalForm());
    mainLayout.getStyleClass().add("root"); // Apply background style from CSS

    // Search Bar
    TextField searchBar = new TextField();
    searchBar.setPromptText("Nhập mã/Tên món cần tìm");
    searchBar.setPrefWidth(300);
    searchBar.getStyleClass().add("text-field");

    // Search Button
    Button searchButton = new Button("🔍");
    searchButton.getStyleClass().add("button");

    Button addNewProductButton = new Button("Thêm mới đồ uống");
    Button refresh = new Button("Làm mới");

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
    searchButton.setOnAction(e -> {
      String searchText = searchBar.getText().trim();
      filteredProducts = CashierService.getProductsByKey(searchText);
      updateProductGrid(gridPane, filteredProducts);
    });
    comboBox.setOnAction(event -> {
      String selectFilter = comboBox.getValue();
      if ("All".equals(selectFilter) || selectFilter.isEmpty() || selectFilter == null) {
        filteredProducts = CashierService.getAllProducts();
      } else {
        filteredProducts = CashierService.getProductsByCategory(selectFilter);
      }
      updateProductGrid(gridPane, filteredProducts);
    });

    addNewProductButton.setOnAction(e -> {
      Products newProduct = showNewProductDialog(primaryStage);
      CashierService.addNewProduct(newProduct);

    });
    refresh.setOnAction(e -> {
      int column = 0;
      int row = 0;
      for (int i = 0; i < filteredProducts.size(); i++) {
        Products product = filteredProducts.get(i);
        String imagePath = product.getImageLink();

        // Ensure the image path is valid
        if (imagePath != null && getClass().getResource(imagePath) != null) {
          Image productImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180,
              175,
              false, false);
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
          productBox.setOnMouseEntered(
              event -> productBox.setStyle("-fx-background-color: #f0f0f0;"));
          productBox.setOnMouseExited(
              event -> productBox.setStyle("-fx-background-color: #f9f9f9;"));

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
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setFitToWidth(
            true); // Makes sure the content adjusts to the width of the ScrollPane
        scrollPane.setPannable(true); // Allows panning
        scrollPane.setVbarPolicy(
            ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar when needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar


      }
    });

    // Create coffee menu grid
    int column = 0;
    int row = 0;

    for (int i = 0; i < filteredProducts.size(); i++) {
      Products product = filteredProducts.get(i);
      String imagePath = product.getImageLink();

      // Ensure the image path is valid
      if (imagePath != null && getClass().getResource(imagePath) != null) {
        Image productImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180, 175,
            false, false);
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
    scrollPane.setVbarPolicy(
        ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar when needed
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar

    // Add components to the main layout, including the ScrollPane
    mainLayout.getChildren().addAll(topLayout, scrollPane, comeBack, addNewProductButton, refresh);

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
        Image productImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180, 175,
            false, false);
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

  private static Products showNewProductDialog(Stage primaryStage) {
    Dialog<Products> dialog = new Dialog<>();
    dialog.setHeaderText(null);
    TextField imageLink = new TextField();
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

    ImageView imageView = new ImageView();
    imageView.setFitWidth(200); // Set width
    imageView.setFitHeight(200); // Set height
    imageView.setPreserveRatio(true); // Preserve aspect ratio

    Button uploadButton = new Button("Upload Image");
    uploadButton.setOnAction(e -> {
      fileChooser.setTitle("Select Image");
      selectedFile = fileChooser.showOpenDialog(primaryStage);
      if (selectedFile != null) {
        // Load image
        Image image = new Image(selectedFile.toURI().toString());
        imageView.setImage(image);
        // Update the image link text field
        imageLink.setText(selectedFile.getName()); // Only the file name, adjust as needed
      }
    });
    Button editButton = new Button("Edit Image");
    editButton.setOnAction(e -> {
      fileChooser.setTitle("Select New Image");
      selectedFile = fileChooser.showOpenDialog(primaryStage);
      if (selectedFile != null) {
        // Load new image
        Image image = new Image(selectedFile.toURI().toString());
        imageView.setImage(image);
      }
    });

    TextField category = new TextField("");
    TextField name = new TextField("");
    TextField price = new TextField("");

    grid.add(new Label("Ảnh"), 0, 0);
    grid.add(imageView, 1, 0);
    grid.add(uploadButton, 2, 0);
    grid.add(editButton, 3, 0);
    grid.add(new Label("Loại Đồ Uống: "), 0, 1);
    grid.add(category, 1, 1);
    grid.add(new Label("Tên Đồ Uống"), 0, 2);
    grid.add(name, 1, 2);
    grid.add(new Label("Gía"), 0, 3);
    grid.add(price, 1, 3);

    dialog.getDialogPane().setContent(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    dialog.setResultConverter(button -> {
      if (button == ButtonType.OK) {
        // Save the image to the project folder
        try {
          File destinationFile = new File(
              "src/main/resources/images/products/" + selectedFile.getName());
          Files.copy(selectedFile.toPath(), destinationFile.toPath(),
              StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }

        return new Products(
            "/images/products/" + selectedFile.getName(), // Update with the correct path
            category.getText(),
            name.getText(),
            Double.parseDouble(price.getText())
        );
      }
      return null;
    });
    return dialog.showAndWait().orElse(null);
  }
}
