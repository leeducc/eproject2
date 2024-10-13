package com.example.cafemanagement.controller;

import static com.example.cafemanagement.enummethod.RoleStaff.fromDisplayName;
import static com.example.cafemanagement.service.HashPassword.checkPassword;

import com.example.cafemanagement.enummethod.RoleStaff;
import com.example.cafemanagement.page.admin.CreateNewUserPage;
import com.example.cafemanagement.page.admin.PageHomeAdmin;
import com.example.cafemanagement.page.cashier.CashierHomePage;
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
    if (logo == null) {
      System.out.println("Logo is null, check service.createLogo()");
      logo = new ImageView(); // Nếu logo bị null, thêm một ImageView trống
    }

    // Username and Password fields
    TextField usernameField = new TextField();
    usernameField.setPromptText("Tên đăng nhập");

    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Mật khẩu");

    // Role selection ComboBox
    ComboBox<String> roleComboBox = service.createRoleSelectionBox();
    if (roleComboBox == null) {
      System.out.println("Role ComboBox is null, check service.createRoleSelectionBox()");
      roleComboBox = new ComboBox<>();
    }

    // Login button
    Button loginButton = new Button("ĐĂNG NHẬP");

    // Add elements to layout
    mainLayout.getChildren().addAll(logo, usernameField, passwordField, roleComboBox, loginButton);
    createDashboardScene(primaryStage);
    // Create scene and stage
    Scene scene = new Scene(mainLayout, 800, 600);
    primaryStage.setTitle("Coffee Login");
    primaryStage.setScene(scene);
    primaryStage.show();

    // Load CSS
    try {
      scene.getStylesheets().add(
          Objects.requireNonNull(getClass().getResource("/css/cssfilepagelogin.css")).toExternalForm());
    } catch (Exception e) {
      System.out.println("CSS file not found!");
    }

    // Set up login button action
    ComboBox<String> finalRoleComboBox = roleComboBox;
    loginButton.setOnAction(e -> {
      String enteredUsername = usernameField.getText();
      String enteredPassword = passwordField.getText();
      String roleValueName = finalRoleComboBox.getValue();

      if (roleValueName == null) {
        AlertUtil.showErrorLoginAlert("Please select a role.");
        return;
      }

      RoleStaff role = fromDisplayName(roleValueName);
      int roleId = staffService.getRoleByValue(String.valueOf(role));

      // Check if username and password fields are not empty
      if (!enteredUsername.trim().isEmpty() && !enteredPassword.trim().isEmpty()
          && staffService.getStaffByUserName(enteredUsername) != null) {
        String passwordHash = staffService.getStaffByUserName(enteredUsername).getPasswordHash();
        String userName = staffService.getStaffByUserName(enteredUsername).getName();
        int checkRoleId = staffService.getStaffByUserName(enteredUsername).getRoleId();

        // Check for admin credentials
        if ((enteredUsername.equals("admin") && enteredPassword.equals("123")) && roleId == checkRoleId) {
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
              CashierHomePage.cashierHomePage(primaryStage,service.getDashboardVBoxServiceTableOrder());
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
          AlertUtil.showErrorLoginAlert("\"Tên đăng nhập hoặc mật khẩu không chính xác.\\nVui lòng nhập lại!\"");
        }
      } else {
        // Empty username or password field
        AlertUtil.showErrorLoginAlert("\"Tên đăng nhập hoặc mật khẩu không chính xác.\\nVui lòng nhập lại!\"");
      }
    });

    return scene;
  }

  public void createDashboardScene(Stage primaryStage) {
    PageHomeAdmin pageHomeAdmin = new PageHomeAdmin();
    CreateNewUserPage createNewUser = new CreateNewUserPage();
    Button creatStaff = new Button("Tạo Tài Khoản Nhân viên");
    Button comeback = new Button("Quay lại");
    Button nextPage = new Button("Trang Order");

    // Tạo nút logout chung
    Button logoutButton = createLogoutButton(primaryStage);
    Button logoutButton1 = createLogoutButton(primaryStage);

    // Dashboard chính
    VBox dashboardLayout = pageHomeAdmin.viewHomePage();
    if (dashboardLayout == null) {
      throw new NullPointerException("Dashboard layout is null");
    }
    dashboardLayout.getChildren().addAll(creatStaff,nextPage, logoutButton1);
    dashboardLayout.setAlignment(Pos.CENTER);

    // Trang tạo tài khoản nhân viên
    VBox dashboardLayoutCreate = createNewUser.createNewUserPage(comeback);
    if (dashboardLayoutCreate == null) {
      throw new NullPointerException("Create account layout is null");
    }

    // Trang dịch vụ (Cashier Home Page)

   VBox dashboardLayoutServiceOrder = CashierHomePage.viewTableOrder();
    dashboardLayoutServiceOrder.getChildren().addAll(logoutButton);

    // Xử lý nút quay lại
    nextPage.setOnAction(event -> {
      CashierHomePage.cashierHomePage(primaryStage,service.getDashboardVBoxServiceTableOrder());
    });
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
    service.setDashboardVBoxServiceTableOrder(dashboardLayoutServiceOrder);
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
