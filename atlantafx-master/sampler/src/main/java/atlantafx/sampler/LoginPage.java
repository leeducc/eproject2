package atlantafx.sampler;

import atlantafx.sampler.admin.layout.AdminApp;
import atlantafx.sampler.base.service.AuthService;
import atlantafx.sampler.base.service.UserSession;
import atlantafx.sampler.cashier.layout.CashierApp;
import atlantafx.sampler.staff.layout.StaffApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos; // Import Pos
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font; // Import Font

import java.util.Objects;

public class LoginPage extends HBox {

    private Stage stage;

    public LoginPage(Stage stage) {
        this.stage = stage;
        setupUI();
    }

    private void setupUI() {
        // Left Pane with Image
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Resources.getResourceAsStream("images/login.jpg"))));
        imageView.setFitWidth(400);
        imageView.setFitHeight(500); // Set height to fill the pane
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true); // Smooth scaling

        StackPane leftPane = new StackPane(imageView);
        leftPane.setPrefSize(400, 500); // Ensure the left pane has a preferred size
        leftPane.setStyle("-fx-background-color: #FFFFFF;"); // Set background color (or whatever fits your design)

        // Right Pane with Login Form
        GridPane rightPane = new GridPane();
        rightPane.setPadding(new Insets(20)); // Adjust padding as needed
        rightPane.setVgap(10); // Reduced vertical gap
        rightPane.setHgap(10);
        rightPane.getStyleClass().add("login-form");
        rightPane.setAlignment(Pos.CENTER); // Center align the grid pane contents

        // Set Column Constraints for Right Pane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40); // Adjust as needed
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(60); // Adjust as needed
        rightPane.getColumnConstraints().addAll(column1, column2);

        Label welcomeLabel = new Label("Welcome!");
        welcomeLabel.setFont(new Font(36)); // Increase font size to 36
        welcomeLabel.setTextFill(javafx.scene.paint.Color.BLUE); // Set text color to blue
        welcomeLabel.setPadding(new Insets(0, 0, 15, 0));

        // Input Labels and Fields
        Label userLabel = new Label("Username");
        userLabel.setFont(new Font(16)); // Increase font size for username label
        TextField userField = new TextField();
        userField.setPromptText("Enter username");
        userField.setPrefWidth(200); // Set preferred width to stretch

        Label passLabel = new Label("Password");
        passLabel.setFont(new Font(16)); // Increase font size for password label
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter password");
        passField.setPrefWidth(200); // Set preferred width to stretch

        Label roleLabel = new Label("Role");
        roleLabel.setFont(new Font(16)); // Increase font size for role label
        ComboBox<String> roleSelector = new ComboBox<>();
        roleSelector.getItems().addAll("Admin", "Cashier", "Staff");
        roleSelector.setValue("Select role");
        roleSelector.setPrefWidth(200); // Set preferred width to stretch
        roleSelector.getStyleClass().add("combo-box");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");
        loginButton.setOnAction(event -> {
            String username = userField.getText();
            String password = passField.getText();
            String role = roleSelector.getValue();

            // Authentication logic
            AuthService authService = new AuthService();
            boolean isAuthenticated = authService.authenticate(username, password, role);
            if (isAuthenticated) {
                if ("Admin".equals(role)) {
                    onLoginSuccess(new AdminApp());
                } else if ("Staff".equals(role)) {
                    UserSession.getInstance().setStaffId(username);
                    onLoginSuccess(new StaffApp());
                } else if ("Cashier".equals(role)) {
                    onLoginSuccess(new CashierApp());
                }
            } else {
                System.out.println("Login failed!");
            }
        });

        // Add controls to the right pane
        rightPane.add(welcomeLabel, 0, 0, 2, 1); // Add welcome label spanning two columns
        rightPane.add(userLabel, 0, 1);
        rightPane.add(userField, 1, 1);
        rightPane.add(passLabel, 0, 2);
        rightPane.add(passField, 1, 2);
        rightPane.add(roleLabel, 0, 3);
        rightPane.add(roleSelector, 1, 3);
        rightPane.add(loginButton, 1, 4);

        // Main layout with image on the left and login form on the right
        this.getChildren().addAll(leftPane, rightPane);
        this.setPrefSize(900, 500);
        this.setStyle("-fx-background-color: #FFFFFF;"); // Set a consistent background color for the whole layout
    }

    private void onLoginSuccess(Pane app) {
        System.out.println("Login successful!");
        var scene = new Scene(app, AdminApp.MIN_WIDTH + 80, 768);
        stage.setScene(scene);
    }
}
