package com.example.cafemanagement.controller;

import static com.example.cafemanagement.enummethod.RoleStaff.fromDisplayName;
import static com.example.cafemanagement.service.HashPassword.checkPassword;

import com.example.cafemanagement.enummethod.RoleStaff;
import com.example.cafemanagement.page.admin.CreateNewUserPage;
import com.example.cafemanagement.page.admin.PageHome;
import com.example.cafemanagement.page.cashier.CashierHomePage;
import com.example.cafemanagement.service.HandleButton;
import com.example.cafemanagement.service.admin.PageLoginService;
import com.example.cafemanagement.service.staff.StaffService;
import com.example.cafemanagement.util.AlertUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class PageLoginController {

  PageLoginService service = new PageLoginService();
  StaffService staffService = new StaffService();
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

    ComboBox<String> roleComboBox = service.createRoleSelectionBox();
    // Login button
    Button loginButton = new Button("ĐĂNG NHẬP");

    // Add elements to layout
    mainLayout.getChildren()
        .addAll(logo, usernameField, passwordField, roleComboBox, loginButton);

    // Create scene and stage
    Scene scene = new Scene(mainLayout, 800, 600);
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
      String roleValueName = roleComboBox.getValue();
      RoleStaff role = fromDisplayName(roleValueName);
      int roleId = staffService.getRoleByValue(String.valueOf(role));
      // Check if username and password fields are not empty
      if (!enteredUsername.trim().isEmpty() && !enteredPassword.trim().isEmpty()
          && staffService.getStaffByUserName(enteredUsername) != null) {
        String passwordHash = staffService.getStaffByUserName(enteredUsername).getPasswordHash();
        String userName = staffService.getStaffByUserName(enteredUsername).getName();
        int checkRoleId = staffService.getStaffByUserName(enteredUsername).getRoleId();
        // Check for admin credentials
        if (enteredUsername.equals("admin") && enteredPassword.equals("123")
            && roleId == checkRoleId) {
          // Successful login for admin
          primaryStage.setScene(service.getDashboardScene());
          primaryStage.setTitle("Dashboard");
        } else if (enteredUsername.equals(userName) && checkPassword(enteredPassword, passwordHash)
            && roleId == checkRoleId) {
          switch (checkRoleId) {
            case 1:
              primaryStage.setScene(service.getDashboardScene());
              primaryStage.setTitle("Dashboard");
              break;
            case 2:
              primaryStage.setScene(service.getDashboardSceneServiceTableOrder());
              break;
            case 3:
              System.out.println("Button 3 clicked!");
              break;
            default:
              System.out.println("Unknown action");
              break;
          }
        } else {
          // Invalid credentials
          AlertUtil.showErrorLoginAlert();
        }
      } else {
        // Empty username or password field
        AlertUtil.showErrorLoginAlert();
      }
    });
    return scene;
  }
  public void createDashboardScene(Stage primaryStage) {
    PageHome pageHome = new PageHome();
    CreateNewUserPage createNewUser = new CreateNewUserPage();
    Button creatStaff = new Button("Tạo Tài Khoản Nhân viên");
    Button comeback = new Button("Quay lại");

    // Tạo nút logout chung
    Button logoutButton = createLogoutButton(primaryStage);

    // Dashboard chính
    VBox dashboardLayout = pageHome.viewHomePage();
    dashboardLayout.getChildren().addAll(creatStaff,logoutButton);
    dashboardLayout.setAlignment(Pos.CENTER);

    // Trang tạo tài khoản nhân viên
    VBox dashboardLayoutCreate = createNewUser.createNewUserPage();
    dashboardLayoutCreate.getChildren().addAll(comeback);


    // Trang dịch vụ (Cashier Home Page)
    VBox dashboardLayoutServiceOrderTable = CashierHomePage.viewTableOrder();
    dashboardLayoutServiceOrderTable.getChildren().addAll(logoutButton);

    // Xử lý nút quay lại
    creatStaff.setOnAction(event -> {
      primaryStage.setScene(service.getDashboardSceneCreate());
      primaryStage.setTitle("Create Screen");
    });
    comeback.setOnAction(event -> {
      primaryStage.setScene(service.getDashboardScene());
      primaryStage.setTitle("Dashboard");
    });

    // Tạo các cảnh cho từng trang
    service.setDashboardScene(new Scene(dashboardLayout, 800, 600));
    service.setDashboardSceneCreate(new Scene(dashboardLayoutCreate, 800, 600));
    service.setDashboardSceneServiceTableOrder(
        new Scene(dashboardLayoutServiceOrderTable, 800, 600));
  }
  public Button createLogoutButton(Stage primaryStage) {
    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(event -> handleLogout(primaryStage));
    return logoutButton;
  }
  private void handleLogout(Stage primaryStage) {
    primaryStage.setScene(pageLogin(primaryStage));
    primaryStage.setTitle("Login Screen");
  }
}

