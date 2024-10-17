package atlantafx.sampler.page.components;

import atlantafx.sampler.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.entity.OrderItem;
import atlantafx.sampler.page.OutlinePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;

public final class OrderPage extends OutlinePage {
    public static final String NAME = "Order";

    private TableView<OrderItem> orderTable;
    private ObservableList<OrderItem> orderItems;
    private TextField supplierCodeField;
    private Label supplierNameLabel;
    private Button checkOrderButton;
    private DatePicker orderDatePicker;
    private Button saveOrderButton;

    @Override
    public String getName() {
        return NAME;
    }

    public OrderPage() {
        super();
        initializeUI();
        orderItems = FXCollections.observableArrayList();
        createOrderTableColumns();
        addEmptyRows(100); // Add 100 empty rows
    }

    private void initializeUI() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new javafx.geometry.Insets(20));

        supplierCodeField = new TextField();
        supplierCodeField.setPromptText("Nhập Mã Nhà Cung Cấp");
        supplierCodeField.textProperty().addListener((observable, oldValue, newValue) -> checkSupplier(newValue));
        mainLayout.getChildren().add(supplierCodeField);

        supplierNameLabel = new Label("Tên Nhà Cung Cấp: ");
        mainLayout.getChildren().add(supplierNameLabel);

        orderTable = new TableView<>();
        orderTable.setEditable(true); // Allow editing in table cells
        createOrderTableColumns(); // Create table columns

        // Wrap TableView in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(orderTable);
        scrollPane.setFitToWidth(true); // Make it fit to the width of the parent
        scrollPane.setPrefHeight(300); // Set a fixed height for the scrollable area
        mainLayout.getChildren().add(scrollPane);

        orderDatePicker = new DatePicker();
        orderDatePicker.setPromptText("Chọn Ngày Đặt Hàng");
        mainLayout.getChildren().add(orderDatePicker);

        checkOrderButton = new Button("Kiểm Tra Đơn Hàng");
        checkOrderButton.setOnAction(e -> checkOrder());
        mainLayout.getChildren().add(checkOrderButton);
        saveOrderButton = new Button("Lưu Đơn Hàng");
        saveOrderButton.setOnAction(e -> saveOrder());
        mainLayout.getChildren().add(saveOrderButton);


        Scene scene = new Scene(mainLayout, 600, 500);
        getChildren().add(mainLayout);
    }

    private void createOrderTableColumns() {
        if (!orderTable.getColumns().isEmpty()) {
            return; // Prevent adding columns again
        }

        // Mã Hàng column (editable)
        TableColumn<OrderItem, String> supplyCodeColumn = new TableColumn<>("Mã Hàng");
        supplyCodeColumn.setCellValueFactory(new PropertyValueFactory<>("supplyCode"));
        supplyCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        supplyCodeColumn.setEditable(true);

        // Số Lượng column (editable)
        TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Số Lượng");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setEditable(true);

        // Đơn Vị column (not editable, fetched from SQL)
        TableColumn<OrderItem, String> unitColumn = new TableColumn<>("Đơn Vị");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unitColumn.setEditable(false);

        // Giá column (not editable, fetched from SQL)
        TableColumn<OrderItem, Double> priceColumn = new TableColumn<>("Giá");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setEditable(false);

        // Tên Hàng Hóa column (not editable, fetched from SQL)
        TableColumn<OrderItem, String> itemNameColumn = new TableColumn<>("Tên Hàng Hóa");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemNameColumn.setEditable(false);

        // Tổng Giá Trị column (calculated)
        TableColumn<OrderItem, Double> totalValueColumn = new TableColumn<>("Tổng Giá Trị");
        totalValueColumn.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
        totalValueColumn.setEditable(false);
        // Thêm cột "Xóa dòng"
        TableColumn<OrderItem, Void> deleteColumn = new TableColumn<>("Xóa");

        deleteColumn.setCellFactory(col -> new TableCell<OrderItem, Void>() {
            private final Button deleteButton = new Button("Xóa");

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        // Lấy index của dòng hiện tại
                        int index = getIndex();
                        // Xóa OrderItem tại index
                        orderItems.remove(index);
                        // Cập nhật bảng
                        orderTable.setItems(orderItems);
                    });
                }
            }
        });

        // Add columns to the table
        orderTable.getColumns().addAll(supplyCodeColumn, quantityColumn, unitColumn, priceColumn, itemNameColumn, totalValueColumn, deleteColumn);

        // Handle edit commit for supply code
        supplyCodeColumn.setOnEditCommit(event -> {
            String newSupplyCode = event.getNewValue();
            int rowIndex = event.getTablePosition().getRow();
            OrderItem orderItem = orderItems.get(rowIndex);
            orderItem.setSupplyCode(newSupplyCode);
            updateItemDetails(orderItem); // Fetch updated item details
        });
        quantityColumn.setOnEditCommit(event -> {
            Integer newQuantity = event.getNewValue();
            int rowIndex = event.getTablePosition().getRow();
            OrderItem orderItem = orderItems.get(rowIndex);
            orderItem.setQuantity(newQuantity); // Cập nhật số lượng
            orderItem.setTotalValue(orderItem.getPrice() * newQuantity); // Cập nhật tổng giá trị
            orderTable.refresh(); // Làm mới bảng để hiển thị thay đổi
        });
        supplyCodeColumn.setOnEditCommit(event -> {
            String newSupplyCode = event.getNewValue();
            int rowIndex = event.getTablePosition().getRow();
            OrderItem orderItem = orderItems.get(rowIndex);
            orderItem.setSupplyCode(newSupplyCode);
        });
    }

    private void addEmptyRows(int count) {
        for (int i = 0; i < count; i++) {
            orderItems.add(new OrderItem()); // Add an empty row
        }
        orderTable.setItems(orderItems);
    }

    private void checkSupplier(String supplierId) {
        if (supplierId.isEmpty()) {
            supplierNameLabel.setText("Tên Nhà Cung Cấp: ");
            return;
        }

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT name FROM suppliers WHERE suppliers_id = ?")) {
            statement.setString(1, supplierId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String supplierName = resultSet.getString("name");
                supplierNameLabel.setText("Tên Nhà Cung Cấp: " + supplierName);
            } else {
                supplierNameLabel.setText("Nhà Cung Cấp Không Tồn Tại");
                orderItems.clear(); // Clear order items if supplier doesn't exist
                addEmptyRows(100); // Add 100 empty rows if supplier doesn't exist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateItemDetails(OrderItem orderItem) {
        // Fetch unit, price, and item name from the supplies table and calculate total value
        if (orderItem.getSupplyCode() == null || orderItem.getSupplyCode().isEmpty()) {
            orderItem.setItemName("");
            orderItem.setUnit("");
            orderItem.setPrice(0.0);
            orderItem.setTotalValue(0.0);
            return;
        }

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT name, unit, price FROM supplies WHERE supply_code = ? AND suppliers_id = ?")) {
            statement.setString(1, orderItem.getSupplyCode());
            statement.setString(2, supplierCodeField.getText()); // Use entered supplier ID
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Set the values from the database
                orderItem.setItemName(resultSet.getString("name"));
                orderItem.setUnit(resultSet.getString("unit"));
                double price = resultSet.getDouble("price");
                orderItem.setPrice(price);
                orderItem.setTotalValue(price * orderItem.getQuantity());
            } else {
                // If no matching item found, set default values and alert
                orderItem.setItemName("Mặt Hàng Không Tồn Tại");
                orderItem.setUnit("");
                orderItem.setPrice(0.0);
                orderItem.setTotalValue(0.0);
                // Show alert if item is not found
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh Báo");
                alert.setHeaderText(null);
                alert.setContentText("Nhà Cung Cấp Không Cung Cấp Mặt Hàng Này");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private void checkOrder() {
        // Cập nhật thông tin cho từng OrderItem trước khi hiển thị chi tiết đơn hàng
        for (OrderItem orderItem : orderItems) {
            updateItemDetails(orderItem); // Gọi hàm cập nhật thông tin
        }
        orderTable.setItems(orderItems);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông Báo");
        alert.setHeaderText(null); // Không cần tiêu đề
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void saveOrder() {
        String supplierId = supplierCodeField.getText();
        if (supplierId.isEmpty()) {
            showAlert("Nhà cung cấp chưa được chọn.");
            return;
        }

        // Tạo order_number
        String orderNumber = generateOrderNumber();

        // Lưu đơn hàng vào bảng delivery_order
        try (Connection connection = JDBCConnect.getJDBCConnection()) {
            try {
                connection.setAutoCommit(false);  // Tắt chế độ auto-commit

                // Chèn vào bảng delivery_order
                String insertOrderSQL = "INSERT INTO delivery_order (order_number, supplier_id, order_date) VALUES (?, ?, ?)";
                PreparedStatement orderStmt = connection.prepareStatement(insertOrderSQL);
                orderStmt.setString(1, orderNumber);
                orderStmt.setString(2, supplierId);
                orderStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                orderStmt.executeUpdate();

                // Chèn vào bảng delivery_order_items
                String insertItemsSQL = "INSERT INTO delivery_order_items (order_number, supply_code, quantity) VALUES (?, ?, ?)";
                PreparedStatement itemsStmt = connection.prepareStatement(insertItemsSQL);

                for (OrderItem item : orderItems) {
                    if (item.getQuantity() > 0) {
                        itemsStmt.setString(1, orderNumber);
                        itemsStmt.setString(2, item.getSupplyCode());
                        itemsStmt.setDouble(3, item.getQuantity());
                        itemsStmt.executeUpdate();
                    }
                }

                // Chỉ commit nếu auto-commit bị tắt
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }

            } catch (SQLException e) {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }
                throw e;
            }


            connection.commit(); // Hoàn tất giao dịch
            sendOrderEmail(supplierId, orderNumber); // Gửi email cho nhà cung cấp
            showAlert("Đơn hàng đã được lưu thành công với số " + orderNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi trong quá trình lưu đơn hàng!");
        }
    }

    private String generateOrderNumber() {
        String orderNumber;
        do {
            orderNumber = "40" + String.format("%08d", new Random().nextInt(100000000));
        } while (isOrderNumberExists(orderNumber)); // Kiểm tra sự tồn tại của order_number
        return orderNumber;
    }

    private boolean isOrderNumberExists(String orderNumber) {
        String sql = "SELECT COUNT(*) FROM delivery_order WHERE order_number = ?";

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, orderNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Nếu có ít nhất 1 bản ghi thì order_number đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
        return false; // Nếu không có bản ghi nào, trả về false
    }




    private boolean orderNumberExists(String orderNumber) {
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM delivery_order WHERE order_number = ?")) {
            statement.setString(1, orderNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getLastOrderId(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT LAST_INSERT_ID()")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return -1; // Không tìm thấy ID
    }

    private void sendOrderEmail(String supplierId, String orderNumber) {
        String email = getSupplierEmail(supplierId);
        if (email != null) {
            // Tạo file PDF từ danh sách hàng đặt
            createOrderPdf(orderNumber);

            // Gửi email
            // EmailUtil.sendEmail(email, "Đơn hàng mới", "Quán gửi đơn hàng với mã " + orderNumber, "path/to/pdf");
        }
    }

    private String getSupplierEmail(String supplierId) {
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT email FROM suppliers WHERE suppliers_id = ?")) {
            statement.setString(1, supplierId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy email
    }

    private void createOrderPdf(String orderNumber) {
        // Sử dụng thư viện như iText để tạo file PDF
        // Lưu danh sách hàng đặt và order_number vào file PDF
    }

}
