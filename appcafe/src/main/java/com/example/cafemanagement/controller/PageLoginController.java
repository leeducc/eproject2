//package com.example.cafemanagement.controller;
//
//
//import com.example.cafemanagement.page.PageLogin;
//import com.example.cafemanagement.page.admin.CreateNewUserPage;
//import com.example.cafemanagement.page.staff.PageHome;
//import com.example.cafemanagement.service.PageLoginService;
//import java.util.Objects;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.ToggleGroup;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//
//public class PageLoginController {
//  PageLogin pgLogin = new PageLogin();
//  PageLoginService pgLoginService = new PageLoginService();
//  public void controllerScreen(){
//    try {
//      scene.getStylesheets().add(
//          Objects.requireNonNull(getClass().getResource("/css/cssfilepagelogin.css"))
//              .toExternalForm());
//    } catch (Exception e) {
//      System.out.println("CSS file not found!");
//    }
//
//    // Create dashboard scene
//    createDashboardScene(primaryStage);
//
//    // Set up login button action
//    loginButton.setOnAction(e -> {
//      String enteredUsername = usernameField.getText();
//      String enteredPassword = passwordField.getText();
//
//      if (enteredUsername.equals("admin") && enteredPassword.equals("123")) {
//        // Successful login
//        primaryStage.setScene(service.getDashboardScene());
//        primaryStage.setTitle("Dashboard");
//      } else {
//        // Invalid credentials
//        AlertUtil.showErrorLoginAlert();
//      }
//    });
//  }
//  private void createDashboardScene(Stage primaryStage) {
//    PageHome pageHome = new PageHome();
//    CreateNewUserPage createNewUser = new CreateNewUserPage();
//    Button logoutButton = new Button("Logout");
//    Button creatStaff = new Button("Tạo Tài Khoản Nhân viên");
//    VBox dashboardLayout = pageHome.viewHomePage(logoutButton, creatStaff);
//    dashboardLayout.setAlignment(Pos.CENTER);
//    VBox dashboardLayoutCreate = createNewUser.createNewUserPage(creatStaff);
//    // Handle logout button click
//    logoutButton.setOnAction(event -> {
//      // Switch back to the login scene
//      primaryStage.setScene(pgLogin.pageLogin(primaryStage));
//      primaryStage.setTitle("Login Screen");
//    });
//    creatStaff.setOnAction(event -> {
//      primaryStage.setScene(pgLoginService.getDashboardSceneCreate());
//      primaryStage.setTitle("Create Screen");
//    })
//    ;
//    // Create dashboard scene
//    pgLoginService.setDashboardScene(new Scene(dashboardLayout, 400, 300)) ;
//    pgLoginService.getDashboardScene().getStylesheets().add(getClass().getResource("/css/stylessAdminPage.css").toExternalForm());
//    pgLoginService.setDashboardSceneCreate(new Scene(dashboardLayoutCreate, 400, 300));
//  }
//}
