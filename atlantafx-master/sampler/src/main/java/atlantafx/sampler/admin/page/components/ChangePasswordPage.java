package atlantafx.sampler.admin.page.components;

import atlantafx.sampler.admin.page.OutlinePage;
import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.base.util.PasswordUtils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ChangePasswordPage extends OutlinePage {
    public static final String NAME = "Thay đổi mật khẩu nhân viên";

    private ComboBox<String> staffComboBox;
    private PasswordField newPasswordField;
    private Button changePasswordButton;

    @Override
    public String getName() {
        return NAME;
    }

    public ChangePasswordPage() {
        super();
        initializeUI();
        loadStaffData();
    }

    private void initializeUI() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // Title Label at the Top
        Label titleLabel = new Label(NAME);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        layout.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.TOP_LEFT);

        // Center Layout for Form Inputs
        GridPane formLayout = new GridPane();
        formLayout.setVgap(10);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.CENTER);

        // Staff ComboBox
        staffComboBox = new ComboBox<>();
        staffComboBox.setPromptText("Select Staff");
        staffComboBox.setMaxWidth(250); // Match the width to the password field

        // New Password Field
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter New Password");
        newPasswordField.setMaxWidth(250); // Consistent width

        // Change Password Button
        changePasswordButton = new Button("Change Password");
        changePasswordButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 16px;");
        changePasswordButton.setOnAction(e -> handleChangePassword());

        // Add components to form layout
        formLayout.add(staffComboBox, 0, 0);
        formLayout.add(newPasswordField, 0, 1);
        formLayout.add(changePasswordButton, 0, 2);

        // Set the form layout in the center of BorderPane
        layout.setCenter(formLayout);

        // Add BorderPane layout to the main scene
        this.getChildren().add(layout);
    }


    private void loadStaffData() {
        // Load staff names from the database and add them to the ComboBox
        try (Connection connection = JDBCConnect.getJDBCConnection();
             var statement = connection.createStatement()) {

            var resultSet = statement.executeQuery("SELECT staff_id, name FROM staff");

            while (resultSet.next()) {
                String staffId = resultSet.getString("staff_id");
                String name = resultSet.getString("name");
                staffComboBox.getItems().add(staffId + " - " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load staff data.");
        }
    }

    private void handleChangePassword() {
        String selectedStaff = staffComboBox.getValue();
        String newPassword = newPasswordField.getText();

        if (selectedStaff == null || newPassword.isEmpty()) {
            showAlert("Warning", "Please select a staff and enter a new password.");
            return;
        }

        String staffId = selectedStaff.split(" - ")[0];
        updatePasswordInDatabase(staffId, newPassword);
    }

    private void updatePasswordInDatabase(String staffId, String newPassword) {
        String updateQuery = "UPDATE staff SET password_hash = ? WHERE staff_id = ?";

        try (Connection connection = atlantafx.sampler.base.configJDBC.dao.JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Use PasswordUtils to hash the password
            preparedStatement.setString(1, PasswordUtils.hashPassword(newPassword));
            preparedStatement.setString(2, staffId);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert("Success", "Password updated successfully.");
            } else {
                showAlert("Error", "Failed to update password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error occurred while updating password.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
