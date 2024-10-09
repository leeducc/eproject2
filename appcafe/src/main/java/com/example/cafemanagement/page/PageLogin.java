package com.example.cafemanagement.page;

import com.example.cafemanagement.page.staff.PageHome;
import com.example.cafemanagement.util.AlertUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class PageLogin {

  private Scene dashboardScene;


  public Scene pageLogin(Stage primaryStage) {
    VBox mainLayout = new VBox(20);
    mainLayout.setPadding(new Insets(20));
    mainLayout.setAlignment(Pos.CENTER);

    // Add logo
    ImageView logo = createLogo();

    // Username and Password fields
    TextField usernameField = new TextField();
    usernameField.setPromptText("Tên đăng nhập");

    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Mật khẩu");

    // Role selection
    ToggleGroup roleGroup = createRoleGroup();

    // Login button
    Button loginButton = new Button("ĐĂNG NHẬP");

    // Add elements to layout
    mainLayout.getChildren()
        .addAll(logo, usernameField, passwordField, createRoleSelectionBox(roleGroup), loginButton);

    // Create scene and stage
    Scene scene = new Scene(mainLayout, 400, 300);
    primaryStage.setTitle("Coffee Login");
    primaryStage.setScene(scene);
    primaryStage.show();

    // Load CSS
    try {
      scene.getStylesheets().add(
          Objects.requireNonNull(getClass().getResource("/css/cssfilepagelogin.css"))
              .toExternalForm());
    } catch (Exception e) {
      System.out.println("CSS file not found!");
    }

    // Create dashboard scene
    createDashboardScene(primaryStage);

    // Set up login button action
    loginButton.setOnAction(e -> {
      String enteredUsername = usernameField.getText();
      String enteredPassword = passwordField.getText();

      if (enteredUsername.equals("admin") && enteredPassword.equals("123")) {
        // Successful login
        primaryStage.setScene(dashboardScene);
        primaryStage.setTitle("Dashboard");
      } else {
        // Invalid credentials
        AlertUtil.showErrorLoginAlert();
      }
    });

    return scene;
  }

  private ImageView createLogo() {
    ImageView logo = null;
    try {
      logo = new ImageView(new Image(
          Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm()));
      logo.setFitWidth(150);
      logo.setPreserveRatio(true);
    } catch (Exception e) {
      System.out.println("Logo image not found!");
    }
    return logo;
  }

  private ToggleGroup createRoleGroup() {
    ToggleGroup roleGroup = new ToggleGroup();
    RadioButton rbPhucVu = new RadioButton("Phục Vụ");
    RadioButton rbThuNgan = new RadioButton("Thu Ngân");
    RadioButton rbAdmin = new RadioButton("Quản Lý");

    rbPhucVu.setToggleGroup(roleGroup);
    rbThuNgan.setToggleGroup(roleGroup);
    rbAdmin.setToggleGroup(roleGroup);

    rbPhucVu.setSelected(true);  // Default selection
    return roleGroup;
  }

  private HBox createRoleSelectionBox(ToggleGroup roleGroup) {
    RadioButton rbPhucVu = (RadioButton) roleGroup.getToggles().get(0);
    RadioButton rbThuNgan = (RadioButton) roleGroup.getToggles().get(1);
    RadioButton rbAdmin = (RadioButton) roleGroup.getToggles().get(2);
    HBox roleSelectionBox = new HBox(10, rbPhucVu, rbThuNgan, rbAdmin);
    roleSelectionBox.setAlignment(Pos.CENTER);
    return roleSelectionBox;
  }

  private void createDashboardScene(Stage primaryStage) {
    PageHome pageHome = new PageHome();
    Button logoutButton = new Button("Logout");
    VBox dashboardLayout = pageHome.viewHomePage(logoutButton);
    dashboardLayout.setAlignment(Pos.CENTER);
    // Handle logout button click
    logoutButton.setOnAction(event -> {
      // Switch back to the login scene
      primaryStage.setScene(pageLogin(primaryStage));
      primaryStage.setTitle("Login Screen");
    });
    // Create dashboard scene
    dashboardScene = new Scene(dashboardLayout, 400, 300);
  }
}
