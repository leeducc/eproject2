package com.example.cafemanagement.page.admin;
import java.sql.Connection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
  public class CreateNewUserPage {
    public VBox createNewUserPage(Button primaryStage) {
      VBox mainLayout = new VBox(20);
      mainLayout.setPadding(new Insets(20));
      mainLayout.setAlignment(Pos.CENTER);
      mainLayout.setStyle("-fx-background-color: #d1b7a1;");

      // Input fields
      TextField staffIdField = new TextField();
      staffIdField.setPromptText("Staff ID");

      PasswordField passwordField = new PasswordField();
      passwordField.setPromptText("Mật khẩu");

      TextField nameField = new TextField();
      nameField.setPromptText("Name");

      TextField contactNumberField = new TextField();
      contactNumberField.setPromptText("Contact number");

      // Label for role selection
      Label roleLabel = new Label("Chức vụ nhân viên:");

      // ComboBox for roles
      ComboBox<String> roleComboBox = new ComboBox<>();
      roleComboBox.getItems().addAll("Phục vụ", "Thu ngân", "Admin");
      roleComboBox.setValue("Phục vụ");  // Default selection

      // Result message text
      Text resultMessage = new Text();

      // Register button
      Button registerButton = new Button("Khởi Tạo");
      registerButton.setStyle(
          "-fx-background-color: #52342e; -fx-text-fill: white; -fx-padding: 10px 20px;");

      mainLayout.getChildren()
          .addAll(staffIdField, passwordField, nameField, contactNumberField, roleLabel,
              roleComboBox, registerButton , resultMessage);
      mainLayout.setPadding(new Insets(20));
      mainLayout.setAlignment(Pos.CENTER);
      mainLayout.getStylesheets().add(getClass().getResource("/css/newUserPage.css").toExternalForm());
      return mainLayout;
    }
  }
