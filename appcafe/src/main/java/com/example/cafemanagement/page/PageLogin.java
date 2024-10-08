package com.example.cafemanagement.page;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PageLogin {


  public void pageLogin(Stage primaryStage) {
    VBox mainLayout = new VBox(20);
    mainLayout.setPadding(new Insets(20));
    mainLayout.setAlignment(Pos.CENTER);
    mainLayout.setStyle("-fx-background-color: #d1b7a1;");

    // Add logo
    ImageView logo = null;
    try {
      logo = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm()));
      logo.setFitWidth(150);
      logo.setPreserveRatio(true);
    } catch (Exception e) {
      System.out.println("Logo image not found!");
    }

    // Username and Password fields
    TextField usernameField = new TextField();
    usernameField.setPromptText("Tên đăng nhập");

    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Mật khẩu");

    ToggleGroup roleGroup = new ToggleGroup();
    RadioButton rbPhucVu = new RadioButton("Phục Vụ");
    RadioButton rbThuNgan = new RadioButton("Thu Ngân");
    RadioButton rbAdmin = new RadioButton("Quản Lý");

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
    mainLayout.getChildren().addAll(logo, usernameField, passwordField, roleSelectionBox, loginButton);

    // Create scene and stage
    Scene scene = new Scene(mainLayout, 400, 300);
    primaryStage.setTitle("Coffee Login");
    primaryStage.setScene(scene);
    primaryStage.show();

    try {
      scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/cssfilepagelogin.css")).toExternalForm());
    } catch (Exception e) {
      System.out.println("CSS file not found!");
    }

    // Set up login button action
    loginButton.setOnAction(e -> {
          String enteredUsername = usernameField.getText();
          String enteredPassword = passwordField.getText();

          if (enteredUsername.equals("admin") && enteredPassword.equals("123")) {
            // Successful login
            System.out.println("Login successful!");
          } else {
            // Invalid credentials
            showErrorLoginAlert();
          }
        }
    );
  }
  private void showErrorLoginAlert() {
    Stage alertStage = new Stage();
    alertStage.initModality(Modality.APPLICATION_MODAL);

    // Load the cartoon image (replace with your local image path or resource)
    ImageView imageView = new ImageView(new Image(
        Objects.requireNonNull(getClass().getResource("/images/ErrorLogin.png")).toExternalForm()));
    imageView.setFitHeight(100);
    imageView.setFitWidth(100);

    // Create the label for the error message
    Label messageLabel = new Label("Tên đăng nhập hoặc mật khẩu không chính xác.\nVui lòng nhập lại!");
    messageLabel.getStyleClass().add("error-message");

    // Create layout and add image and message
    VBox vbox = new VBox(10, imageView, messageLabel);
    vbox.setAlignment(Pos.CENTER);
    vbox.getStyleClass().add("custom-alert");

    Scene alertScene = new Scene(vbox, 400, 200);
    // Apply the external CSS file
    alertScene.getStylesheets().add(
        Objects.requireNonNull(getClass().getResource("/css/errorLoginAlert.css")).toExternalForm());

    alertStage.setScene(alertScene);
    alertStage.setTitle("Login Error");
    alertStage.showAndWait();
  }
}
