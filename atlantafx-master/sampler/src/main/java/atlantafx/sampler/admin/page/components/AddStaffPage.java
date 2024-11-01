package atlantafx.sampler.admin.page.components;

import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.admin.page.OutlinePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

public final class AddStaffPage extends OutlinePage {
    public static final String NAME = "Add Staff";
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private Label messageLabel;

    @Override
    public String getName() {
        return NAME;
    }

    public AddStaffPage() {
        super();
        initialize();
    }

    private void initialize() {
        // Create layout
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // Title label
        Label titleLabel = new Label("Create New Staff");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #00796b;");

        // Message label for success/error feedback
        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");

        // Form fields (excluding staffId and password fields)
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-border-color: #00796b; -fx-border-radius: 5; -fx-pref-width: 300px;");

        TextField contactNumberField = new TextField();
        contactNumberField.setPromptText("Contact Number (10-15 digits)");
        contactNumberField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-border-color: #00796b; -fx-border-radius: 5; -fx-pref-width: 300px;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-border-color: #00796b; -fx-border-radius: 5; -fx-pref-width: 300px;");

        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select Gender");
        genderComboBox.setStyle("-fx-font-size: 16px; -fx-pref-width: 300px;");

        ComboBox<String> roleComboBox = new ComboBox<>();
        loadRoles(roleComboBox); // Load roles from the database
        roleComboBox.setPromptText("Select Role");
        roleComboBox.setStyle("-fx-font-size: 16px; -fx-pref-width: 300px;");

        // Submit button
        Button submitButton = new Button("Create User");
        submitButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 16px;");


        // Button action
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String contactNumber = contactNumberField.getText();
            String email = emailField.getText();
            String gender = genderComboBox.getValue();
            String role = roleComboBox.getValue();

            // Validation logic
            if (name.isEmpty() || contactNumber.isEmpty() || email.isEmpty() || gender == null || role == null) {
                showMessage("Please fill in all fields."); // Show error message
            } else if (!PHONE_PATTERN.matcher(contactNumber).matches()) {
                showMessage("Invalid contact number. Must be 10 to 15 digits.");
            } else if (!EMAIL_PATTERN.matcher(email).matches()) {
                showMessage("Invalid email format.");
            } else {
                if (insertNewUser(name, contactNumber, email, gender, role)) {
                    showMessage("User created successfully!");
                    // Clear fields after submission
                    nameField.clear();
                    contactNumberField.clear();
                    emailField.clear();
                    genderComboBox.getSelectionModel().clearSelection();
                    roleComboBox.getSelectionModel().clearSelection();
                } else {
                    showMessage("Failed to create user."); // Show error message
                }
            }
        });

        // Create a grid pane for the form
        GridPane formLayout = new GridPane();
        formLayout.setVgap(10);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.add(titleLabel, 0, 0, 2, 1); // Title spans two columns
        formLayout.add(messageLabel, 0, 1, 2, 1); // Message label spans two columns
        formLayout.add(nameField, 0, 2);
        formLayout.add(contactNumberField, 0, 3);
        formLayout.add(emailField, 0, 4);
        formLayout.add(genderComboBox, 0, 5);
        formLayout.add(roleComboBox, 0, 6);
        formLayout.add(submitButton, 0, 7);

        // Set the center of the layout to the form
        layout.setCenter(formLayout);

        // Add the layout to the page
        getChildren().add(layout);
    }

    private void loadRoles(ComboBox<String> roleComboBox) {
        Connection conn = JDBCConnect.getJDBCConnection();
        if (conn == null) {
            showAlert("Error", "Database connection failed");
            return;
        }

        String query = "SELECT role_name FROM role"; // Change to the appropriate column name
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String roleName = rs.getString("role_name");
                roleComboBox.getItems().add(roleName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load roles");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean insertNewUser(String name, String contactNumber, String email, String gender, String role) {
        Connection conn = JDBCConnect.getJDBCConnection();
        if (conn == null) {
            showAlert("Error", "Database connection failed");
            return false;
        }

        String defaultPassword = "0000";
        String hashedPassword = BCrypt.hashpw(defaultPassword, BCrypt.gensalt()); // Hash the default password
        String staffId = generateUniqueStaffId(conn);

        if (staffId == null) {
            showAlert("Error", "Failed to generate a unique staff ID.");
            return false;
        }

        String query = "INSERT INTO staff (staff_id, password_hash, name, contact_number, email, gender, role_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM role WHERE role_name = ?))";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, staffId);
            stmt.setString(2, hashedPassword);  // Insert the hashed default password
            stmt.setString(3, name);
            stmt.setString(4, contactNumber);
            stmt.setString(5, email);
            stmt.setString(6, gender);
            stmt.setString(7, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if the user was successfully created

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to create user.");
            return false;

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateUniqueStaffId(Connection conn) {
        String staffId = "";
        Random random = new Random();
        boolean unique = false;

        while (!unique) {
            staffId = "6" + String.format("%07d", random.nextInt(10000000)); // Generate an 8-digit ID starting with 6
            String query = "SELECT COUNT(*) FROM staff WHERE staff_id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, staffId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    unique = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return staffId;
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
