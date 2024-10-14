package atlantafx.sampler.page.components;

import atlantafx.sampler.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.entity.Supply;
import atlantafx.sampler.page.OutlinePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class InventoryPage extends OutlinePage {
    public static final String NAME = "Inventory";

    private TableView<Supply> table;
    private Pagination pagination;
    private ObservableList<Supply> allSupplies;

    private ComboBox<String> supplierFilter;
    private TextField searchField;
    private Button resetButton;

    @Override
    public String getName() {
        return NAME;
    }

    public InventoryPage() {
        super();
        createTable();
        loadSuppliers();
        loadData();
    }

    private void createTable() {
        table = new TableView<>();

        // Define columns
        TableColumn<Supply, String> supplyCodeCol = new TableColumn<>("Supply Code");
        supplyCodeCol.setCellValueFactory(new PropertyValueFactory<>("supplyCode"));

        TableColumn<Supply, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Supply, String> unitCol = new TableColumn<>("Unit");
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Supply, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Supply, Double> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Supply, String> supplierNameCol = new TableColumn<>("Supplier Name");
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("supplierName"));

        TableColumn<Supply, Double> totalValueCol = new TableColumn<>("Total Value");
        totalValueCol.setCellValueFactory(new PropertyValueFactory<>("totalValue"));

        // Add columns to table
        table.getColumns().addAll(supplyCodeCol, nameCol, unitCol, priceCol, quantityCol, supplierNameCol, totalValueCol);

        // Set pagination
        pagination = new Pagination();
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createPage);

        // Create filter controls
        createFilterControls();

        // Layout
        VBox layout = new VBox(supplierFilter, searchField, resetButton, pagination);
        layout.getChildren().addAll(table);
        getChildren().addAll(layout);
    }

    private void createFilterControls() {
        // Supplier filter dropdown
        supplierFilter = new ComboBox<>();
        supplierFilter.setOnAction(e -> updateTable());

        // Search field for supply name
        searchField = new TextField();
        searchField.setPromptText("Search by name");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateTable());

        // Reset button
        resetButton = new Button("Reset Filters");
        resetButton.setOnAction(e -> resetFilters());
    }

    private void loadSuppliers() {
        Connection connection = JDBCConnect.getJDBCConnection();
        String query = "SELECT name FROM suppliers";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                supplierFilter.getItems().add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private StackPane createPage(int pageIndex) {
        // Load filtered data for the current page
        ObservableList<Supply> data = loadDataForPage(pageIndex);
        table.setItems(data);
        return new StackPane(table);
    }

    private ObservableList<Supply> loadDataForPage(int pageIndex) {
        ObservableList<Supply> supplies = FXCollections.observableArrayList();
        Connection connection = JDBCConnect.getJDBCConnection();

        StringBuilder query = new StringBuilder("SELECT s.id, s.supply_code, s.name, s.unit, s.price, s.quantity, su.name AS supplier_name " +
                "FROM supplies s JOIN suppliers su ON s.supplier_id = su.id ");

        String whereClause = "";
        if (supplierFilter.getValue() != null && !supplierFilter.getValue().isEmpty()) {
            whereClause += "WHERE su.name = '" + supplierFilter.getValue() + "' ";
        }

        if (!searchField.getText().isEmpty()) {
            if (!whereClause.isEmpty()) {
                whereClause += "AND ";
            } else {
                whereClause += "WHERE ";
            }
            whereClause += "s.name LIKE '%" + searchField.getText() + "%' ";
        }

        query.append(whereClause);
        query.append("LIMIT 10 OFFSET ").append(pageIndex * 10);

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query.toString())) {
            while (resultSet.next()) {
                supplies.add(new Supply(
                        resultSet.getString("supply_code"),
                        resultSet.getString("name"),
                        resultSet.getString("unit"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("quantity"),
                        resultSet.getString("supplier_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplies;
    }

    private void loadData() {
        // Load all data to update pagination
        Connection connection = JDBCConnect.getJDBCConnection();
        String countQuery = "SELECT COUNT(*) AS total FROM supplies";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(countQuery)) {
            if (resultSet.next()) {
                int totalRecords = resultSet.getInt("total");
                pagination.setPageCount((int) Math.ceil((double) totalRecords / 10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTable() {
        loadData(); // Refresh data based on filter/sorting
        createPage(0); // Reset to the first page after filtering
    }

    private void resetFilters() {
        supplierFilter.setValue(null);
        searchField.clear();
        updateTable(); // Refresh the table with all data
    }
}
