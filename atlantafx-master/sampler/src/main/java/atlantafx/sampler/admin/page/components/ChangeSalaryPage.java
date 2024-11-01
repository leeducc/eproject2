package atlantafx.sampler.admin.page.components;

import atlantafx.sampler.admin.page.OutlinePage;

import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.base.entity.staff.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ChangeSalaryPage extends OutlinePage {
    public static final String NAME = "Change Salary";

    private TableView<Role> tableView;
    private ObservableList<Role> roleData;

    @Override
    public String getName() {
        return NAME;
    }

    public ChangeSalaryPage() {
        super();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // Title Label
        Label titleLabel = new Label(NAME);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        layout.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.TOP_LEFT);

        // TableView for displaying role data
        tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<Role, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Match the property name in Role class

        TableColumn<Role, String> roleNameColumn = new TableColumn<>("Role Name");
        roleNameColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));

        TableColumn<Role, Double> basicSalaryColumn = new TableColumn<>("Basic Salary");
        basicSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));

        TableColumn<Role, Double> allowanceColumn = new TableColumn<>("Allowance");
        allowanceColumn.setCellValueFactory(new PropertyValueFactory<>("allowance"));

        tableView.getColumns().addAll(idColumn, roleNameColumn, basicSalaryColumn, allowanceColumn);



        // Save Changes Button
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 10px 20px;");
        saveButton.setOnAction(e -> saveChanges());

        // Layout for the Table and Button
        VBox vbox = new VBox(10, tableView, saveButton);
        vbox.setAlignment(Pos.CENTER);
        layout.setCenter(vbox);

        // Add layout to the main scene
        this.getChildren().add(layout);
    }

    private void loadData() {
        roleData = FXCollections.observableArrayList(); // Initialize the observable list

        String query = "SELECT id, role_name, basic_salary, allowance FROM role";
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String roleName = resultSet.getString("role_name");
                double basicSalary = resultSet.getDouble("basic_salary");
                double allowance = resultSet.getDouble("allowance");

                // Create a new Role object and add it to the list
                roleData.add(new Role(id, roleName, basicSalary, allowance));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        tableView.setItems(roleData); // Set the items in the table view
    }



    private void saveChanges() {
        String updateQuery = "UPDATE role SET basic_salary = ?, allowance = ? WHERE id = ?";
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            for (Role role : roleData) {
                // Set parameters for the update statement
                preparedStatement.setDouble(1, role.getBasicSalary());
                preparedStatement.setDouble(2, role.getAllowance());
                preparedStatement.setInt(3, role.getId());

                // Execute the update
                preparedStatement.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Changes saved successfully!");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving changes: " + e.getMessage());
            alert.showAndWait();
        }
    }



}
