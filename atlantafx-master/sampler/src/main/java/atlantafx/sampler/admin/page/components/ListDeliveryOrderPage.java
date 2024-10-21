package atlantafx.sampler.admin.page.components;

import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.admin.entity.ListDeliveryOrder;
import atlantafx.sampler.admin.page.OutlinePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

import java.sql.*;

public final class ListDeliveryOrderPage extends OutlinePage {
    public static final String NAME = "Delivery Orders";

    private TableView<ListDeliveryOrder> table;
    private Pagination pagination;

    @Override
    public String getName() {
        return NAME;
    }

    public ListDeliveryOrderPage() {
        super();
        createTable();
        loadData();
    }

    private void createTable() {
        table = new TableView<>();
        initializeTableColumns();
        setupPagination();

        // Ensure the table has a preferred size
        table.setPrefWidth(800);
        table.setPrefHeight(600);

        VBox layout = new VBox(table, pagination);
        layout.setPrefWidth(800);
        layout.setPrefHeight(600);
        getChildren().addAll(layout);
    }

    private void initializeTableColumns() {
        table.getColumns().addAll(
                createTableColumn("Order Number", "orderNumber"),
                createTableColumn("Supplier ID", "supplierId"),
                createTableColumn("Order Date", "orderDate"),
                createTableColumn("Status", "status")
        );
    }

    private TableColumn<ListDeliveryOrder, ?> createTableColumn(String title, String property) {
        TableColumn<ListDeliveryOrder, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private void setupPagination() {
        pagination = new Pagination();
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createPage);
    }

    private StackPane createPage(int pageIndex) {
        ObservableList<ListDeliveryOrder> data = loadDataForPage(pageIndex);
        table.setItems(data);
        return new StackPane(table);
    }

    private ObservableList<ListDeliveryOrder> loadDataForPage(int pageIndex) {
        ObservableList<ListDeliveryOrder> orders = FXCollections.observableArrayList();
        Connection connection = JDBCConnect.getJDBCConnection();

        String query = "SELECT order_number, supplier_id, order_date, status FROM delivery_order ";
        query += "LIMIT 10 OFFSET " + (pageIndex * 10);

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                orders.add(new ListDeliveryOrder(
                        resultSet.getString("order_number"),
                        resultSet.getString("supplier_id"),
                        resultSet.getTimestamp("order_date").toLocalDateTime(),
                        resultSet.getString("status")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Loaded " + orders.size() + " orders for page " + pageIndex); // Debugging line
        return orders;
    }

    private void loadData() {
        String countQuery = "SELECT COUNT(*) AS total FROM delivery_order"; // Adjust your table name
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(countQuery);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int totalRecords = resultSet.getInt("total");
                pagination.setPageCount((int) Math.ceil((double) totalRecords / 10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
