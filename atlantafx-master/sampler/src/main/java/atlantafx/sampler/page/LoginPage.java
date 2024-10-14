package atlantafx.sampler.page;

import atlantafx.sampler.page.components.HomePage;
import atlantafx.sampler.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage {
    private final AuthService authService = new AuthService();
    private TextField usernameField;
    private PasswordField passwordField;

    public void show(Stage primaryStage) {
        BorderPane layout = new BorderPane();

        // Right side - Input fields
        GridPane inputPane = new GridPane();
        inputPane.setPadding(new Insets(20));
        inputPane.setVgap(10);
        inputPane.setHgap(10);
        inputPane.setAlignment(Pos.CENTER);

        // Username Field
        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        inputPane.add(usernameLabel, 0, 0);
        inputPane.add(usernameField, 1, 0);

        // Password Field
        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        inputPane.add(passwordLabel, 0, 1);
        inputPane.add(passwordField, 1, 1);

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            handleLogin(usernameField.getText(), passwordField.getText(), primaryStage);
        });
        inputPane.add(loginButton, 1, 2);

        layout.setCenter(inputPane);

        // Create Scene and Show Stage
        Scene scene = new Scene(layout, 800, 768);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleLogin(String username, String password, Stage primaryStage) {
        // Authenticate user
        if (authService.authenticate(username, password)) {
            HomePage homePage = new HomePage();
            Scene homeScene = new Scene(homePage, 800, 768);
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home Page");
        } else {
            highlightInvalidInputs(username, password);
        }
    }

    private void highlightInvalidInputs(String username, String password) {
        // Reset previous error styles
        usernameField.setStyle("");
        passwordField.setStyle("");

        if (username.isEmpty()) {
            usernameField.setStyle("-fx-border-color: red;");
        }
        if (password.isEmpty()) {
            passwordField.setStyle("-fx-border-color: red;");
        }

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Invalid Credentials", "Please check your username and password.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
