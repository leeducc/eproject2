package com.example.cafemanagement.page;

import com.example.cafemanagement.page.admin.CreateNewUserPage;
import com.example.cafemanagement.page.staff.PageHome;
import com.example.cafemanagement.service.PageLoginService;
import com.example.cafemanagement.util.AlertUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class PageLogin {

  PageLoginService service = new PageLoginService();


  public Scene pageLogin(Stage primaryStage) {
    VBox mainLayout = new VBox(20);
    mainLayout.setPadding(new Insets(20));
    mainLayout.setAlignment(Pos.CENTER);

    // Add logo
    ImageView logo = service.createLogo();

    // Username and Password fields
    TextField usernameField = new TextField();
    usernameField.setPromptText("Tên đăng nhập");

    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Mật khẩu");

    // Role selection
    ToggleGroup roleGroup = service.createRoleGroup();

    // Login button
    Button loginButton = new Button("ĐĂNG NHẬP");

    // Add elements to layout
    mainLayout.getChildren()
        .addAll(logo, usernameField, passwordField, service.createRoleSelectionBox(roleGroup), loginButton);

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
        primaryStage.setScene(service.getDashboardScene());
        primaryStage.setTitle("Dashboard");
      } else {
        // Invalid credentials
        AlertUtil.showErrorLoginAlert();
      }
    });

    return scene;
  }

  private void createDashboardScene(Stage primaryStage) {
    PageHome pageHome = new PageHome();
    CreateNewUserPage createNewUser = new CreateNewUserPage();
    Button logoutButton = new Button("Logout");
    Button creatStaff = new Button("Tạo Tài Khoản Nhân viên");
    VBox dashboardLayout = pageHome.viewHomePage(logoutButton, creatStaff);
    dashboardLayout.setAlignment(Pos.CENTER);
    VBox dashboardLayoutCreate = createNewUser.createNewUserPage(creatStaff);
    // Handle logout button click
    logoutButton.setOnAction(event -> {
      // Switch back to the login scene
      primaryStage.setScene(pageLogin(primaryStage));
      primaryStage.setTitle("Login Screen");
    });
    creatStaff.setOnAction(event -> {
      primaryStage.setScene(service.getDashboardSceneCreate());
      primaryStage.setTitle("Create Screen");
    })
    ;
    // Create dashboard scene
    service.setDashboardScene(new Scene(dashboardLayout, 400, 300)) ;
    service.getDashboardScene().getStylesheets().add(getClass().getResource("/css/stylessAdminPage.css").toExternalForm());
    service.setDashboardSceneCreate(new Scene(dashboardLayoutCreate, 400, 300));
  }
}
