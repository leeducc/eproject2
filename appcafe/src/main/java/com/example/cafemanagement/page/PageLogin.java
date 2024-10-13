//package com.example.cafemanagement.page;
//
//import static com.example.cafemanagement.enummethod.RoleStaff.fromDisplayName;
//import static com.example.cafemanagement.service.HashPassword.checkPassword;
//
//import com.example.cafemanagement.controller.PageLoginController;
//import com.example.cafemanagement.enummethod.RoleStaff;
//import com.example.cafemanagement.service.admin.PageLoginService;
//import com.example.cafemanagement.service.staff.StaffService;
//import com.example.cafemanagement.util.AlertUtil;
//import java.util.Objects;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class PageLogin {
//  PageLoginService service = new PageLoginService();
//  StaffService staffService = new StaffService();
//  PageLoginController ctrl = new PageLoginController();
//  public Scene pageLogin(Stage primaryStage) {
//    VBox mainLayout = new VBox(20);
//    mainLayout.setPadding(new Insets(20));
//    mainLayout.setAlignment(Pos.CENTER);
//
//    // Add logo
//    ImageView logo = service.createLogo();
//
//    // Username and Password fields
//    TextField usernameField = new TextField();
//    usernameField.setPromptText("Tên đăng nhập");
//
//    PasswordField passwordField = new PasswordField();
//    passwordField.setPromptText("Mật khẩu");
//
//    ComboBox<String> roleComboBox = service.createRoleSelectionBox();
//    // Login button
//    Button loginButton = new Button("ĐĂNG NHẬP");
//
//    // Add elements to layout
//    mainLayout.getChildren()
//        .addAll(logo, usernameField, passwordField, roleComboBox, loginButton);
//
//    // Create scene and stage
//    Scene scene = new Scene(mainLayout, 800, 600);
//    primaryStage.setTitle("Coffee Login");
//    primaryStage.setScene(scene);
//    primaryStage.show();
//
//    // Load CSS
//    try {
//      scene.getStylesheets().add(
//          Objects.requireNonNull(getClass().getResource("/css/cssfilepagelogin.css"))
//              .toExternalForm());
//    } catch (Exception e) {
//      System.out.println("CSS file not found!");
//    }
//
//    // Create dashboard scene
//
//    // Set up login button action
//    loginButton.setOnAction(e -> {
//      String enteredUsername = usernameField.getText();
//      String enteredPassword = passwordField.getText();
//      String roleValueName = roleComboBox.getValue();
//      RoleStaff role = fromDisplayName(roleValueName);
//      int roleId = staffService.getRoleByValue(String.valueOf(role));
//      ctrl.createDashboardScene(primaryStage);
//      // Check if username and password fields are not empty
//      if (!enteredUsername.trim().isEmpty() && !enteredPassword.trim().isEmpty()
//          && staffService.getStaffByUserName(enteredUsername) != null) {
//        String passwordHash = staffService.getStaffByUserName(enteredUsername).getPasswordHash();
//        String userName = staffService.getStaffByUserName(enteredUsername).getName();
//        int checkRoleId = staffService.getStaffByUserName(enteredUsername).getRoleId();
//        // Check for admin credentials
//        if (enteredUsername.equals("admin") && enteredPassword.equals("123")
//            && roleId == checkRoleId) {
//          // Successful login for admin
//          primaryStage.setScene(service.getDashboardScene());
//          primaryStage.setTitle("Dashboard");
//        } else if (enteredUsername.equals(userName) && checkPassword(enteredPassword, passwordHash)
//            && roleId == checkRoleId) {
//          switch (checkRoleId) {
//            case 1:
//              primaryStage.setScene(service.getDashboardScene());
//              primaryStage.setTitle("Dashboard");
//              break;
//            case 2:
//              primaryStage.setScene(service.getDashboardSceneServiceTableOrder());
//              break;
//            case 3:
//              System.out.println("Button 3 clicked!");
//              break;
//            default:
//              System.out.println("Unknown action");
//              break;
//          }
//        } else {
//          // Invalid credentials
//          AlertUtil.showErrorLoginAlert();
//        }
//      } else {
//        // Empty username or password field
//        AlertUtil.showErrorLoginAlert();
//      }
//    });
//    return scene;
//  }
//}
