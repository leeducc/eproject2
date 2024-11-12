package atlantafx.sampler.admin.page.components;

import atlantafx.sampler.admin.page.OutlinePage;
import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.base.util.PasswordUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeCashierCredentials extends OutlinePage {
    public static final String NAME = "Change Cashier Username and Password";

    private Label messageLabel;

    @Override
    public String getName() {
        return NAME;
    }

    public ChangeCashierCredentials() {
        super();
        initializeUI();
    }

    private void initializeUI() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // Title Label
        Label titleLabel = new Label(NAME);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        layout.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.TOP_LEFT);

        // Message label for feedback
        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        // Input fields
        TextField newUsernameField = new TextField();
        newUsernameField.setPromptText("New Username");
        newUsernameField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 300px;");

        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Current Password");
        currentPasswordField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 300px;");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        newPasswordField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 300px;");

        // Update button
        Button updateButton = new Button("Update Username & Password");
        updateButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 16px;");

        updateButton.setOnAction(event -> {
            String newUsername = newUsernameField.getText();
            String currentPassword = currentPasswordField.getText();
            String newPassword = newPasswordField.getText();

            if (newUsername.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty()) {
                showMessage("Please fill in all fields.");
            } else if (updateCredentials(newUsername, currentPassword, newPassword)) {
                showMessage("Username and password updated successfully.");
                newUsernameField.clear();
                currentPasswordField.clear();
                newPasswordField.clear();
            } else {
                showMessage("Current password is incorrect.");
            }
        });

        // Layout setup
        GridPane formLayout = new GridPane();
        formLayout.setVgap(10);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.add(newUsernameField, 0, 0);
        formLayout.add(currentPasswordField, 0, 1);
        formLayout.add(newPasswordField, 0, 2);
        formLayout.add(updateButton, 0, 3);
        formLayout.add(messageLabel, 0, 4);

        layout.setCenter(formLayout);
        layout.getStylesheets().add(getClass().getResource("/css/listTable.css").toExternalForm());
        getChildren().add(layout);
    }

    private boolean updateCredentials(String newUsername, String currentPassword, String newPassword) {
        try (Connection conn = JDBCConnect.getJDBCConnection()) {
            if (conn == null) {
                showAlert("Error", "Database connection failed");
                return false;
            }

            // Retrieve the current hashed password for the specific cashier ID (assuming ID is known, e.g., ID=1 for this example)
            String query = "SELECT password_hash FROM cashier WHERE id = 1"; // replace with actual cashier ID as needed
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String hashedPassword = rs.getString("password_hash");
                    if (PasswordUtils.checkPassword(currentPassword, hashedPassword)) {
                        // Update both username and password
                        String updateQuery = "UPDATE cashier SET username = ?, password_hash = ? WHERE id = 1";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setString(1, newUsername);
                            String newHashedPassword = PasswordUtils.hashPassword(newPassword);
                            updateStmt.setString(2, newHashedPassword);
                            updateStmt.executeUpdate();
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update username or password");
        }
        return false;
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
