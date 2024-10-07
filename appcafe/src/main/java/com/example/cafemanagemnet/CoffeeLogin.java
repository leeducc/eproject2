package com.example.cafemanagemnet;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CoffeeLogin extends Application {

  @Override
  public void start(Stage primaryStage) {
    // Main layout as a VBox
    VBox mainLayout = new VBox(20);
    mainLayout.setPadding(new Insets(20));
    mainLayout.setAlignment(Pos.CENTER);
    mainLayout.setStyle("-fx-background-color: #d1b7a1;");

    // Add logo
    ImageView logo = new ImageView(new Image(getClass().getResource("/logo.png").toExternalForm()));
    logo.setFitWidth(150);
    logo.setPreserveRatio(true);

    // Username and Password fields
    TextField usernameField = new TextField();
    usernameField.setPromptText("Tên đăng nhập");

    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Mật khẩu");

    ToggleGroup roleGroup = new ToggleGroup();
    RadioButton rbPhucVu = new RadioButton("Phục vụ");
    RadioButton rbThuNgan = new RadioButton("Thu ngân");
    RadioButton rbAdmin = new RadioButton("Admin");

    rbPhucVu.setToggleGroup(roleGroup);
    rbThuNgan.setToggleGroup(roleGroup);
    rbAdmin.setToggleGroup(roleGroup);

    rbPhucVu.setSelected(true);  // Default selection

    // HBox for radio buttons
    HBox roleSelectionBox = new HBox(10, rbPhucVu, rbThuNgan, rbAdmin);
    roleSelectionBox.setAlignment(Pos.CENTER);

    // Login button
    Button loginButton = new Button("ĐĂNG NHẬP");
    loginButton.setStyle(
        "-fx-background-color: #52342e; -fx-text-fill: white; -fx-padding: 10px 20px;");

    // Add elements to layout
    mainLayout.getChildren()
        .addAll(logo, usernameField, passwordField, roleSelectionBox, loginButton);

    // Create scene and stage
    Scene scene = new Scene(mainLayout, 400, 300);
    primaryStage.setTitle("Coffee Login");
    primaryStage.setScene(scene);
    primaryStage.show();
    scene.getStylesheets().add(getClass().getResource("/cssfile.css").toExternalForm());
  }

  public static void main(String[] args) {
    launch(args);
  }
}

