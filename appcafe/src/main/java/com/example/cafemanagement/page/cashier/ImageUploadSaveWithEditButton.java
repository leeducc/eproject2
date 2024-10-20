//package com.example.cafemanagement.page.cashier;
//
//import javafx.application.Application;
//import javafx.stage.Stage;
//import javafx.stage.FileChooser;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.VBox;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.StandardCopyOption;
//
//public class ImageUploadSaveWithEditButton extends Application {
//
//  private File selectedFile;
//
//  @Override
//  public void start(Stage primaryStage) {
//    FileChooser fileChooser = new FileChooser();
//    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
//
//    ImageView imageView = new ImageView();
//    imageView.setFitWidth(200); // Set width
//    imageView.setFitHeight(200); // Set height
//    imageView.setPreserveRatio(true); // Preserve aspect ratio
//
//    VBox vbox = new VBox();
//
//    Button uploadButton = new Button("Upload Image");
//    uploadButton.setOnAction(e -> {
//      fileChooser.setTitle("Select Image");
//      selectedFile = fileChooser.showOpenDialog(primaryStage);
//      if (selectedFile != null) {
//        // Load image
//        Image image = new Image(selectedFile.toURI().toString());
//        imageView.setImage(image);
//      }
//    });
//
//    Button editButton = new Button("Edit Image");
//    editButton.setOnAction(e -> {
//      fileChooser.setTitle("Select New Image");
//      File file = fileChooser.showOpenDialog(primaryStage);
//      if (file != null) {
//        // Load new image
//        selectedFile = file;
//        Image image = new Image(file.toURI().toString());
//        imageView.setImage(image);
//      }
//    });
//
//    Button finishButton = new Button("Finish Upload");
//    finishButton.setOnAction(e -> {
//      if (selectedFile != null) {
//        try {
//          // Save image to project folder
//          File destFile = new File("src/main/resources/images/products/" + selectedFile.getName());
//          Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//          System.out.println("Image saved to: " + destFile.getPath());
//        } catch (IOException ex) {
//          ex.printStackTrace();
//        }
//      }
//    });
//
//    vbox.getChildren().addAll(uploadButton, editButton, imageView, finishButton);
//
//    Scene scene = new Scene(vbox, 400, 400);
//    primaryStage.setScene(scene);
//    primaryStage.show();
//  }
//
//  public static void main(String[] args) {
//    launch(args);
//  }
//}
////--------------------------------------------------------------------------------------------------------------
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//
//import java.util.List;
//
//public class CoffeeMenuApp extends Application {
//
//  public static void main(String[] args) {
//    launch(args);
//  }
//
//  @Override
//  public void start(Stage primaryStage) {
//    GridPane gridPane = new GridPane();
//    List<Products> productList = getSampleProductList(); // Replace with your actual list
//
//    createProductGrid(productList, gridPane);
//
//    Scene scene = new Scene(gridPane, 600, 400);
//    primaryStage.setScene(scene);
//    primaryStage.show();
//  }
//
//  public void createProductGrid(List<Products> productList, GridPane gridPane) {
//    int column = 0;
//    int row = 0;
//    for (int i = 0; i < productList.size(); i++) {
//      Products product = productList.get(i);
//      String imagePath = product.getImageLink();
//
//      // Ensure the image path is valid
//      if (imagePath != null && getClass().getResource(imagePath) != null) {
//        Image productImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180, 175, false, false);
//        ImageView imageView = new ImageView(productImage);
//
//        // Coffee name and price labels
//        Label nameLabel = new Label(product.getName());
//        nameLabel.setFont(new Font("Arial", 18));
//        nameLabel.getStyleClass().add("label-bold");
//
//        Label priceLabel = new Label("$" + product.getPrice());
//        priceLabel.setFont(new Font("Arial", 14));
//        priceLabel.getStyleClass().add("label-price");
//
//        // Edit Button
//        Button editButton = new Button("Edit");
//        editButton.setOnAction(e -> {
//          // Handle edit product
//          System.out.println("Edit button clicked for product: " + product.getName());
//        });
//
//        // VBox for each product item
//        VBox productBox = new VBox(10, imageView, nameLabel, priceLabel, editButton);
//        productBox.setPadding(new Insets(10));
//        productBox.setAlignment(Pos.CENTER);
//        productBox.getStyleClass().add("product-box");
//        productBox.setOnMouseEntered(e -> productBox.setStyle("-fx-background-color: #f0f0f0;"));
//        productBox.setOnMouseExited(e -> productBox.setStyle("-fx-background-color: #f9f9f9;"));
//
//        // Add to grid
//        gridPane.add(productBox, column, row);
//
//        // Adjust column/row for next product
//        column++;
//        if (column == 3) {  // 3 products per row
//          column = 0;
//          row++;
//        }
//      } else {
//        System.out.println("Invalid image path for product: " + product.getName());
//      }
//    }
//  }
//
//  private List<Products> getSampleProductList() {
//    // Sample product list for demonstration, replace with your actual product list
//    return List.of(
//        new Products("/images/products/product1.png", "Category1", "Coffee 1", 2.99),
//        new Products("/images/products/product2.png", "Category2", "Coffee 2", 3.99),
//        new Products("/images/products/product3.png", "Category3", "Coffee 3", 4.99)
//    );
//  }
//}
