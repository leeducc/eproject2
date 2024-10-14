package atlantafx.sampler.service;

import atlantafx.sampler.configJDBC.dao.JDBCConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    private static final String QUERY_SELECT_PASSWORD = "SELECT password_hash FROM admin WHERE username = ?";

    public boolean authenticate(String username, String password) {

        try (Connection conn = JDBCConnect.getJDBCConnection()) {
            if (conn == null) {
                showAlert("Error", "Database connection failed", AlertType.ERROR);
                return false;
            }

            String storedPasswordHash = getPasswordHash(conn, username);
            if (storedPasswordHash != null) {
                if (isPasswordValid(password, storedPasswordHash)) {
                    showAlert("Success", "Authentication successful", AlertType.INFORMATION);
                    return true;
                } else {
                    showAlert("Error", "Invalid password", AlertType.ERROR);
                }
            } else {
                showAlert("Error", "Username not found", AlertType.ERROR);
            }

        } catch (SQLException e) {
            showAlert("Error", "An error occurred while processing your request: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }

        return false;
    }


    private String getPasswordHash(Connection conn, String username) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(QUERY_SELECT_PASSWORD)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("password_hash") : null;
        }
    }

    private boolean isPasswordValid(String inputPassword, String storedPasswordHash) {
        return inputPassword.equals(storedPasswordHash); // Consider using a hashing mechanism
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
