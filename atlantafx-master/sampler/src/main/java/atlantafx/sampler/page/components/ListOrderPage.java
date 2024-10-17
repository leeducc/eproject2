package atlantafx.sampler.page.components;
import atlantafx.sampler.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.entity.ListOrder;
import atlantafx.sampler.entity.OrderItem;
import atlantafx.sampler.page.OutlinePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public final class ListOrderPage extends OutlinePage {
    public static final String NAME = "Orders";

    private TableView<ListOrder> table;
    private Pagination pagination;
    private ObservableList<ListOrder> allOrders;

    @Override
    public String getName() {
        return NAME;
    }

    public ListOrderPage() {
        super();
        createTable();
        loadData();
    }

    // Tạo bảng TableView
    private void createTable() {
        table = new TableView<>();
        initializeTableColumns();
        setupPagination();

        VBox layout = new VBox(table, pagination);
        getChildren().addAll(layout);
    }

    // Thiết lập các cột cho TableView
    private void initializeTableColumns() {
        // Cột mã đơn hàng
        TableColumn<ListOrder, String> orderNumberCol = new TableColumn<>("Order Number");
        orderNumberCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrderNumber()));

        // Cột mã nhà cung cấp
        TableColumn<ListOrder, String> supplierIdCol = new TableColumn<>("Supplier ID");
        supplierIdCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSupplierId()));

        // Cột ngày đặt hàng
        TableColumn<ListOrder, String> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrderDate()));

        // Cột nút Xem Chi Tiết
        TableColumn<ListOrder, Void> viewDetailsCol = new TableColumn<>("Actions");
        viewDetailsCol.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("Xem chi tiết");

            {
                viewButton.setOnAction(event -> {
                    ListOrder order = getTableView().getItems().get(getIndex());
                    showOrderDetails(order.getOrderNumber());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        });

        // Thêm các cột vào bảng
        table.getColumns().addAll(orderNumberCol, supplierIdCol, orderDateCol, viewDetailsCol);
    }

    // Hàm để hiển thị chi tiết đơn hàng khi nhấn nút "Xem chi tiết"
    private void showOrderDetails(String orderNumber) {
        // Lấy chi tiết đơn hàng từ cơ sở dữ liệu
        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();

        try (Connection connection = JDBCConnect.getJDBCConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM delivery_order_items WHERE order_number = '" + orderNumber + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String supplyCode = resultSet.getString("supply_code");
                double quantity = resultSet.getDouble("quantity");

                orderItems.add(new OrderItem(supplyCode, quantity));
            }

            // Tạo một cửa sổ Dialog để hiển thị chi tiết đơn hàng
            Dialog<Void> detailsDialog = new Dialog<>();
            detailsDialog.setTitle("Chi tiết đơn hàng: " + orderNumber);

            TableView<OrderItem> itemsTable = new TableView<>(orderItems);

            // Cột mã sản phẩm
            TableColumn<OrderItem, String> supplyCodeCol = new TableColumn<>("Supply Code");
            supplyCodeCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSupplyCode()));

            // Cột số lượng
            TableColumn<OrderItem, String> quantityCol = new TableColumn<>("Quantity");
            quantityCol.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getQuantity())));

            itemsTable.getColumns().addAll(supplyCodeCol, quantityCol);

            // Thêm bảng chi tiết vào Dialog
            VBox dialogContent = new VBox(itemsTable);
            detailsDialog.getDialogPane().setContent(dialogContent);
            detailsDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            detailsDialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load dữ liệu từ cơ sở dữ liệu
    private void loadData() {
        allOrders = FXCollections.observableArrayList();

        try (Connection connection = JDBCConnect.getJDBCConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM delivery_order";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String orderNumber = resultSet.getString("order_number");
                String supplierId = resultSet.getString("supplier_id");
                String orderDate = resultSet.getString("order_date");

                allOrders.add(new ListOrder(orderNumber, supplierId, orderDate));
            }

            table.setItems(allOrders);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupPagination() {
        pagination = new Pagination();
        pagination.setPageFactory(this::createPage);
    }

    // Hàm xử lý phân trang nếu cần (hiện tại chỉ hiển thị tất cả)
    private Node createPage(int pageIndex) {
        return table;
    }
}
