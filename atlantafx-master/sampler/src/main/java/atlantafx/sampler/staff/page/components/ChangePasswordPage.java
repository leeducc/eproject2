package atlantafx.sampler.staff.page.components;

import atlantafx.sampler.staff.page.OutlinePage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public final class ChangePasswordPage extends OutlinePage {
    public static final String NAME = "Change Password Page";

    private PasswordField currentPasswordField;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;

    @Override
    public String getName() {
        return NAME;
    }

    public ChangePasswordPage() {
        super();
        createForm();
    }

    private void createForm() {
        // Create fields for current password, new password, and confirm password
        currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Current Password");

        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm New Password");

        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setOnAction(e -> handleChangePassword());

        // Create a grid to layout the fields
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));

        formGrid.add(currentPasswordField, 0, 0);
        formGrid.add(newPasswordField, 0, 1);
        formGrid.add(confirmPasswordField, 0, 2);
        formGrid.add(changePasswordButton, 0, 3);

        // Set the layout for the page
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(formGrid);

        // Add the layout to the scene graph
        getChildren().add(layout);
    }

    private void handleChangePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "New password and confirmation do not match.");
            return;
        }

        // Here you would typically check the current password and update it
        // For this example, we just show a success alert
        showAlert("Success", "Password changed successfully!");
        clearFields();
    }

    private void clearFields() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
