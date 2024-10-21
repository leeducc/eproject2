package atlantafx.sampler;

import atlantafx.sampler.admin.layout.AdminApp;
import atlantafx.sampler.base.service.AuthService;
import atlantafx.sampler.base.service.UserSession;
import atlantafx.sampler.staff.layout.StaffApp;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginPage extends Pane {

    private Stage stage;

    public LoginPage(Stage stage) {
        this.stage = stage;
        setupUI();
    }

    public LoginPage() {

    }

    private void setupUI() {
        // Create the left side with an image
        Pane leftPane = new Pane();
        leftPane.setStyle("-fx-background-image: url(''); " +
                "-fx-background-size: cover;");

        // Create the right side with input fields
        GridPane rightPane = new GridPane();
        rightPane.setPadding(new Insets(20));
        rightPane.setVgap(10);
        rightPane.setHgap(10);

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        Label roleLabel = new Label("Role:");
        ComboBox<String> roleSelector = new ComboBox<>();
        roleSelector.getItems().addAll("Admin", "Staff");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String username = userField.getText();
            String password = passField.getText();
            String role = roleSelector.getValue();

            // Sử dụng AuthService để xác thực
            AuthService authService = new AuthService();
            boolean isAuthenticated = authService.authenticate(username, password, role);
            if (isAuthenticated) {
                if ("Admin".equals(role)) {
                    onLoginSuccess(new AdminApp());
                } else if ("Staff".equals(role)) {
                    UserSession.getInstance().setStaffId(username);
                    onLoginSuccess(new StaffApp());
                }
            } else {
                System.out.println("Login failed!");
            }

        });

        rightPane.add(userLabel, 0, 0);
        rightPane.add(userField, 1, 0);
        rightPane.add(passLabel, 0, 1);
        rightPane.add(passField, 1, 1);
        rightPane.add(roleLabel, 0, 2);
        rightPane.add(roleSelector, 1, 2);
        rightPane.add(loginButton, 1, 3);

        // Arrange panes side by side
        HBox mainLayout = new HBox(leftPane, rightPane);
        getChildren().add(mainLayout);
    }

    private void onLoginSuccess(Pane app) {
        System.out.println("Login successful!");
        // Load the main application window
        var scene = new Scene(app, AdminApp.MIN_WIDTH + 80, 768);
        stage.setScene(scene);
    }
}
