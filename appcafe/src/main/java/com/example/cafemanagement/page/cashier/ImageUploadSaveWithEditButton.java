package com.example.cafemanagement.page.cashier;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ImageUploadSaveWithEditButton extends Application {

  private File selectedFile;

  @Override
  public void start(Stage primaryStage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

    ImageView imageView = new ImageView();
    imageView.setFitWidth(200); // Set width
    imageView.setFitHeight(200); // Set height
    imageView.setPreserveRatio(true); // Preserve aspect ratio

    VBox vbox = new VBox();

    Button uploadButton = new Button("Upload Image");
    uploadButton.setOnAction(e -> {
      fileChooser.setTitle("Select Image");
      selectedFile = fileChooser.showOpenDialog(primaryStage);
      if (selectedFile != null) {
        // Load image
        Image image = new Image(selectedFile.toURI().toString());
        imageView.setImage(image);
      }
    });

    Button editButton = new Button("Edit Image");
    editButton.setOnAction(e -> {
      fileChooser.setTitle("Select New Image");
      File file = fileChooser.showOpenDialog(primaryStage);
      if (file != null) {
        // Load new image
        selectedFile = file;
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
      }
    });

    Button finishButton = new Button("Finish Upload");
    finishButton.setOnAction(e -> {
      if (selectedFile != null) {
        try {
          // Save image to project folder
          File destFile = new File("src/main/resources/images/products/" + selectedFile.getName());
          Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
          System.out.println("Image saved to: " + destFile.getPath());
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    vbox.getChildren().addAll(uploadButton, editButton, imageView, finishButton);

    Scene scene = new Scene(vbox, 400, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
