package com.example.cafemanagement.page.admin;

import static com.example.cafemanagement.enummethod.RoleStaff.fromDisplayName;

import com.example.cafemanagement.service.HashPassword;
import com.example.cafemanagement.entities.Staff;
import com.example.cafemanagement.enummethod.RoleStaff;
import com.example.cafemanagement.service.admin.PageLoginService;
import com.example.cafemanagement.service.staff.StaffService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CreateNewUserPage {

  StaffService staffService = new StaffService();
  PageLoginService service = new PageLoginService();

  public VBox createNewUserPage(Button button) {
    VBox mainLayout = new VBox(20);
    mainLayout.setPadding(new Insets(20));
    mainLayout.setAlignment(Pos.CENTER);

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
    ComboBox<String> roleComboBox = service.createRoleSelectionBox();

    // Result message text
    Text resultMessage = new Text();

    // Register button
    Button registerButton = new Button("Khởi Tạo");

    registerButton.setOnAction(event -> {
      int staffId = Integer.parseInt(staffIdField.getText());
      String password = HashPassword.hashPassword(passwordField.getText());
      String name = nameField.getText();
      String contactNumber = contactNumberField.getText();
      String roleValueName = roleComboBox.getValue();
      RoleStaff role = fromDisplayName(roleValueName);

      int roleId = staffService.getRoleByValue(String.valueOf(role));

      boolean result = staffService.createUser(
          new Staff(staffId, password, name, contactNumber, roleId));
      if (result) {
        resultMessage.setText("Tạo tài khoản thành công!");
//        resultMessage.setText(String.valueOf(roleId));

      } else {
        resultMessage.setText("Tạo tài khoản thất bại!");
      }
    });

    mainLayout.getChildren()
        .addAll(staffIdField, passwordField, nameField, contactNumberField, roleLabel,
            roleComboBox, registerButton, button,resultMessage);
    mainLayout.setPadding(new Insets(20));
    mainLayout.setAlignment(Pos.CENTER);
    mainLayout.getStylesheets()
        .add(getClass().getResource("/css/newUserPage.css").toExternalForm());
    return mainLayout;
  }
}
