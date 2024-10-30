package atlantafx.sampler.base.service.cashier;


import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.base.entity.common.Bill;
import atlantafx.sampler.base.entity.common.BillDetail;
import atlantafx.sampler.base.entity.common.PaymentMethod;
import atlantafx.sampler.base.entity.common.Product;
import atlantafx.sampler.base.enummethod.Payment;
import atlantafx.sampler.base.util.AlertUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;

public class CashierService {
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("image_link"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("category_id") // Lấy category_id đúng
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    public static ListView<String> InitializeProductName() {
        List<Product> listProducts = CashierService.getAllProducts();
        ListView<String> productNames = new ListView<>();
        for (Product product : listProducts) {
            productNames.getItems().add(product.getName());
        }
        return productNames;  // Now returning the ListView
    }

    public static ObservableList<String> InitializeProductName1() {
        ObservableList<String> products = FXCollections.observableArrayList();
        List<Product> listProducts = CashierService.getAllProducts();
        for (Product product : listProducts) {
            products.add(product.getName());
        }
        return products;
    }

    public static ObservableList<String> InitializeProductNameByKey(String key) {
        ObservableList<String> products = FXCollections.observableArrayList();
        List<Product> listProducts = CashierService.getProductsByKey(key);
        for (Product product : listProducts) {
            products.add(product.getName());
        }
        return products;
    }

    public static ObservableList<String> InitializeProductNameCategory(String category) {
        ObservableList<String> products = FXCollections.observableArrayList();
        List<Product> listProducts = CashierService.getProductsByCategory(category);
        for (Product product : listProducts) {
            products.add(product.getName());
        }
        return products;
    }

    public static Product getProductByProductName(String nameProduct) {
        Product product = null;
        String sql = "SELECT * FROM products WHERE name = ?";
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nameProduct);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = new Product(
                        resultSet.getInt("id"), // Lấy id từ bảng
                        resultSet.getString("image_link"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("category_id") // Lấy category_id đúng
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static double getPriceByName(String name) {
        Product product = getProductByProductName(name);
        return product.getPrice();
    }

    public static boolean addOrderBill(Bill bill) {
        String sql = "INSERT INTO bill (table_id, total_amount, payment_method_id, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bill.getTableId()); // Sử dụng id của bàn
            preparedStatement.setDouble(2, bill.getTotalAmount()); // Tổng số tiền
            preparedStatement.setInt(3, bill.getPaymentMethodId()); // Phương thức thanh toán
            preparedStatement.setTimestamp(4, bill.getCreatedAt()); // Thời gian tạo hóa đơn

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateOrderBill(Bill bill) {
        String sql = "UPDATE bill_detail SET quantity = ? WHERE product_id = ? AND bill_id = (SELECT id FROM bill WHERE table_id = ?)";
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bill.getBillDetails().get(0).getQuantity()); // Giả sử bạn cập nhật số lượng cho sản phẩm đầu tiên trong danh sách chi tiết hóa đơn
            preparedStatement.setInt(2, bill.getBillDetails().get(0).getProductId()); // ID của sản phẩm
            preparedStatement.setInt(3, bill.getTableId()); // ID của bàn

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bill findOrderBillIsExist(String tableName, String productName) {
        Bill bill = null;
        String sql = "SELECT b.*, bd.quantity, bd.price FROM bill b " +
                "JOIN bill_detail bd ON b.id = bd.bill_id " +
                "WHERE b.table_id = (SELECT id FROM table WHERE name = ?) " +
                "AND bd.product_id = (SELECT id FROM products WHERE name = ?)";

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tableName); // Tên bàn
            preparedStatement.setString(2, productName); // Tên sản phẩm
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                bill = new Bill();
                bill.setId(resultSet.getInt("id")); // ID của hóa đơn
                bill.setTableId(resultSet.getInt("table_id")); // ID của bàn
                bill.setTotalAmount(resultSet.getDouble("total_amount")); // Tổng số tiền
                bill.setPaymentMethodId(resultSet.getInt("payment_method_id")); // Phương thức thanh toán
                bill.setCreatedAt(resultSet.getTimestamp("created_at")); // Thời gian tạo hóa đơn

                // Bạn có thể thêm chi tiết hóa đơn vào bill nếu cần
                BillDetail detail = new BillDetail();
                detail.setQuantity(resultSet.getInt("quantity"));
                detail.setPrice(resultSet.getDouble("price"));
                bill.setBillDetails(Collections.singletonList(detail)); // Giả sử chỉ có một chi tiết
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    public static boolean resetOrderBill(String tableName) {
        // Đầu tiên, lấy id của các hóa đơn liên quan đến bàn
        String getBillIdSql = "SELECT id FROM bill WHERE table_id = (SELECT id FROM table WHERE name = ?)";
        String deleteBillDetailSql = "DELETE FROM bill_detail WHERE bill_id = ?";
        String deleteBillSql = "DELETE FROM bill WHERE table_id = (SELECT id FROM table WHERE name = ?)";

        Connection connection = null; // Khai báo kết nối bên ngoài
        try {
            connection = JDBCConnect.getJDBCConnection();
            // Bắt đầu một transaction
            connection.setAutoCommit(false);

            // Lấy ID của hóa đơn liên quan đến bàn
            try (PreparedStatement getBillIdStatement = connection.prepareStatement(getBillIdSql)) {
                getBillIdStatement.setString(1, tableName);
                ResultSet resultSet = getBillIdStatement.executeQuery();

                // Xóa các chi tiết hóa đơn trước
                while (resultSet.next()) {
                    int billId = resultSet.getInt("id");
                    try (PreparedStatement deleteDetailStatement = connection.prepareStatement(deleteBillDetailSql)) {
                        deleteDetailStatement.setInt(1, billId);
                        deleteDetailStatement.executeUpdate();
                    }
                }
            }

            // Sau đó, xóa hóa đơn
            try (PreparedStatement deleteBillStatement = connection.prepareStatement(deleteBillSql)) {
                deleteBillStatement.setString(1, tableName);
                boolean result = deleteBillStatement.executeUpdate() > 0;
                // Commit transaction
                connection.commit();
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Nếu có lỗi, rollback transaction
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            // Đảm bảo rằng kết nối được đóng
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }


    public static List<Bill> getBillByNameTable(String tableName) {
        List<Bill> bills = new ArrayList<>(); // Khởi tạo danh sách Bill
        String sql = "SELECT b.*, bd.quantity, bd.price FROM bill b " +
                "JOIN bill_detail bd ON b.id = bd.bill_id " +
                "WHERE b.table_id = (SELECT id FROM table WHERE name = ?)"; // Truy vấn với nameTable

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Đặt giá trị tham số cho truy vấn
            preparedStatement.setString(1, tableName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Vòng lặp qua các kết quả của ResultSet
                while (resultSet.next()) {
                    // Khởi tạo đối tượng Bill từ kết quả truy vấn
                    Bill bill = new Bill();
                    bill.setId(resultSet.getInt("id")); // ID của hóa đơn
                    bill.setTableId(resultSet.getInt("table_id")); // ID của bàn
                    bill.setTotalAmount(resultSet.getDouble("total_amount")); // Tổng số tiền
                    bill.setPaymentMethodId(resultSet.getInt("payment_method_id")); // Phương thức thanh toán
                    bill.setCreatedAt(resultSet.getTimestamp("created_at")); // Thời gian tạo hóa đơn

                    // Khởi tạo BillDetail từ kết quả truy vấn
                    BillDetail detail = new BillDetail();
                    detail.setQuantity(resultSet.getInt("quantity")); // Số lượng sản phẩm
                    detail.setPrice(resultSet.getDouble("price")); // Giá sản phẩm
                    detail.setProductId(resultSet.getInt("product_id")); // ID của sản phẩm

                    // Thêm BillDetail vào Bill
                    bill.getBillDetails().add(detail); // Giả sử bạn có phương thức getBillDetails() để thêm chi tiết
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills; // Trả về danh sách Bill
    }

    public static void removeOrderBill(Bill bill, String tableName) {
        // SQL để xóa chi tiết hóa đơn dựa trên bill_id
        String deleteBillDetailSql = "DELETE FROM bill_detail WHERE bill_id = (SELECT id FROM bill WHERE table_id = (SELECT id FROM table WHERE name = ?) AND id = ?)";

        // SQL để xóa hóa đơn
        String deleteBillSql = "DELETE FROM bill WHERE table_id = (SELECT id FROM table WHERE name = ?) AND id = ?";

        try (Connection connection = JDBCConnect.getJDBCConnection()) {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Xóa chi tiết hóa đơn trước
            try (PreparedStatement deleteDetailStatement = connection.prepareStatement(deleteBillDetailSql)) {
                deleteDetailStatement.setString(1, tableName); // Tên bàn
                deleteDetailStatement.setInt(2, bill.getId()); // ID của hóa đơn

                int detailRowsAffected = deleteDetailStatement.executeUpdate();
                if (detailRowsAffected > 0) {
                    System.out.println("Chi tiết hóa đơn đã được xóa thành công.");
                } else {
                    System.out.println("Không tìm thấy chi tiết hóa đơn để xóa.");
                }
            }

            // Xóa hóa đơn
            try (PreparedStatement deleteBillStatement = connection.prepareStatement(deleteBillSql)) {
                deleteBillStatement.setString(1, tableName); // Tên bàn
                deleteBillStatement.setInt(2, bill.getId()); // ID của hóa đơn

                int billRowsAffected = deleteBillStatement.executeUpdate();
                if (billRowsAffected > 0) {
                    System.out.println("Hóa đơn đã được xóa thành công.");
                } else {
                    System.out.println("Không tìm thấy hóa đơn để xóa.");
                }
            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi xóa hóa đơn: " + e.getMessage());
        }
    }


    public static List<String> getAllCategoriesProduct() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT name FROM category"; // Thay đổi truy vấn để lấy tên danh mục từ bảng category
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categories.add(resultSet.getString("name")); // Lấy giá trị tên danh mục
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories; // Trả về danh sách danh mục
    }


    public static ComboBox createPayCategoriesSelectionBox() {
        List<String> categories = new ArrayList<>();
        ComboBox<String> ctComboBox = new ComboBox<>();
        categories = CashierService.getAllCategoriesProduct();
        for (String category : categories) {
            ctComboBox.getItems().add(category);
        }
        return ctComboBox;
    }

    public static List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();

        // Lấy category_id từ tên danh mục
        int categoryId = -1; // Mặc định là -1 nếu không tìm thấy
        String categorySql = "SELECT id FROM category WHERE name = ?";

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement categoryStatement = connection.prepareStatement(categorySql)) {
            categoryStatement.setString(1, category);
            ResultSet categoryResultSet = categoryStatement.executeQuery();

            if (categoryResultSet.next()) {
                categoryId = categoryResultSet.getInt("id"); // Lấy category_id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Nếu tìm thấy category_id, lấy danh sách sản phẩm
        if (categoryId != -1) {
            String productSql = "SELECT * FROM products WHERE category_id = ?";
            try (Connection connection = JDBCConnect.getJDBCConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(productSql)) {
                preparedStatement.setInt(1, categoryId); // Sử dụng category_id
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Product product = new Product(
                            resultSet.getInt("id"), // ID của sản phẩm
                            resultSet.getString("image_link"),  // Liên kết hình ảnh
                            resultSet.getString("name"),             // Tên sản phẩm
                            resultSet.getDouble("price"),            // Giá sản phẩm
                            resultSet.getInt(categoryId)                              // categoryId
                    );
                    products.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return products; // Trả về danh sách sản phẩm
    }


    public static List<Product> getProductsByKey(String key) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + key + "%"); // Thực hiện tìm kiếm theo tên sản phẩm
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),                  // ID của sản phẩm
                        resultSet.getString("image_link"),       // Liên kết hình ảnh
                        resultSet.getString("name"),             // Tên sản phẩm
                        resultSet.getDouble("price"),            // Giá sản phẩm
                        resultSet.getInt("category_id")        // ID của danh mục sản phẩm
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products; // Trả về danh sách sản phẩm
    }

    //
    public static boolean addNewProduct(Product obj) {
        String sql = "INSERT INTO products(image_link, name, price, category_id) VALUES(?,?,?,?)";

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, obj.getImageLink());    // Liên kết hình ảnh
            preparedStatement.setString(2, obj.getName());        // Tên sản phẩm
            preparedStatement.setDouble(3, obj.getPrice());      //Giá sản phẩm
            preparedStatement.setInt(4, obj.getCategoryId());   // ID danh mục

            return preparedStatement.executeUpdate() > 0;                // Thực hiện lệnh thêm
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }


    public static boolean deleteProductsByProductName(String name) {
        String sql = "DELETE FROM products WHERE name = ?";

        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name); // Đặt tên sản phẩm vào truy vấn

            int rowsAffected = preparedStatement.executeUpdate(); // Thực hiện xóa
            if (rowsAffected > 0) {
                System.out.println("Sản phẩm đã được xóa thành công.");
                return true; // Trả về true nếu xóa thành công
            } else {
                System.out.println("Không tìm thấy sản phẩm để xóa.");
                return false; // Trả về false nếu không tìm thấy sản phẩm
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public static List<PaymentMethod> getAllPayMethod() {
        List<PaymentMethod> Payments = new ArrayList<>();
        String sql = "SELECT * FROM payment_method";
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                PaymentMethod method = new PaymentMethod(
                        resultSet.getInt("id"),
                        Payment.valueOf(resultSet.getString("method"))
                );
                Payments.add(method);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Payments;
    }

    public void createProductGrid(List<Product> productList, GridPane gridPane) {
        int column = 0;
        int row = 0;
        gridPane.getChildren().clear();

        for (Product product : productList) {
            String imagePath = product.getImageLink();

            if (imagePath != null && getClass().getResource(imagePath) != null) {
                Image productImage = new Image(getClass().getResource(imagePath).toExternalForm(), 180, 175, false, false);
                ImageView imageView = new ImageView(productImage);

                Label nameLabel = new Label(product.getName());
                nameLabel.setFont(new Font("Arial", 18));
                nameLabel.getStyleClass().add("label-bold");

                Label priceLabel = new Label("$" + product.getPrice());
                priceLabel.setFont(new Font("Arial", 14));
                priceLabel.getStyleClass().add("label-price");

                Button editButton = new Button("Sửa");
                Button deleteButton = new Button("Xóa");

                // Chức năng chỉnh sửa sản phẩm
                editButton.setOnAction(e -> {
                    Optional<Product> editedProduct = showEditProductDialog(product);
                    editedProduct.ifPresent(updatedProduct -> {
                        CashierService.updateProduct(updatedProduct);  // Lưu vào cơ sở dữ liệu
                        productList.set(productList.indexOf(product), updatedProduct); // Cập nhật danh sách
                        createProductGrid(productList, gridPane); // Tải lại lưới sau khi cập nhật
                        System.out.println("Sửa thành công");
                    });
                });

                // Chức năng xóa sản phẩm
                deleteButton.setOnAction(e -> {
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationDialog.setTitle("Xác Nhận Xóa");
                    confirmationDialog.setHeaderText("Bạn có chắc chắn muốn xóa sản phẩm này?");
                    confirmationDialog.setContentText("Hành động này không thể hoàn tác.");
                    confirmationDialog.getDialogPane().getStylesheets().add(
                            getClass().getResource("/css/cssDiaLog.css").toExternalForm()
                    );

                    Optional<ButtonType> result = confirmationDialog.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        String filePath = "sampler/src/main/resources" + product.getImageLink();
                        File file = new File(filePath);
                        if (file.exists()) {
                            if (file.delete()) {
                                CashierService.deleteProductsByProductName(product.getName());
                                productList.remove(product);  // Cập nhật danh sách
                                createProductGrid(productList, gridPane); // Tải lại lưới sau khi xóa
                                AlertUtil.showErrorAlert("Xóa Thành Công");
                            } else {
                                AlertUtil.showErrorAlert("Lỗi Hệ Thống");
                            }
                        } else {
                            System.out.println("File không tồn tại.");
                        }
                    } else {
                        System.out.println("Người dùng đã hủy thao tác xóa.");
                    }
                });

                HBox buttonBox = new HBox(10, editButton, deleteButton);
                buttonBox.setAlignment(Pos.CENTER);

                VBox productBox = new VBox(10, imageView, nameLabel, priceLabel, buttonBox);
                productBox.setPadding(new Insets(10));
                productBox.setAlignment(Pos.CENTER);
                productBox.getStyleClass().add("product-box");
                productBox.setOnMouseEntered(ev -> productBox.setStyle("-fx-background-color: #f0f0f0;"));
                productBox.setOnMouseExited(ev -> productBox.setStyle("-fx-background-color: #f9f9f9;"));

                gridPane.add(productBox, column, row);
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } else {
                System.out.println("Đường dẫn ảnh không hợp lệ cho sản phẩm: " + product.getName());
            }
        }
    }

    // Dialog chỉnh sửa sản phẩm
    public Optional<Product> showEditProductDialog(Product product) {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Chỉnh sửa sản phẩm");
        dialog.setHeaderText("Chỉnh sửa thông tin sản phẩm");

        // Tạo các trường nhập liệu
        TextField productId = new TextField(String.valueOf(product.getId()));
        TextField categoryField = new TextField(String.valueOf(product.getCategoryId())); // Sử dụng categoryId
        TextField nameField = new TextField(product.getName());
        TextField priceField = new TextField(String.valueOf(product.getPrice()));
        Button chooseImageButton = new Button("Chọn ảnh mới");
        Label imageLabel = new Label(product.getImageLink());

        File[] selectedFile = {null}; // Biến lưu trữ file mới được chọn

        chooseImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn ảnh sản phẩm");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            selectedFile[0] = fileChooser.showOpenDialog(dialog.getOwner());
            if (selectedFile[0] != null) {
                imageLabel.setText(selectedFile[0].getName());
            }
        });

        // Tạo GridPane để bố trí các trường nhập liệu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Danh mục:"), 0, 0);
        grid.add(categoryField, 1, 0);
        grid.add(new Label("Tên sản phẩm:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Giá:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("Ảnh:"), 0, 3);
        grid.add(chooseImageButton, 1, 3);
        grid.add(imageLabel, 2, 3);

        dialog.getDialogPane().setContent(grid);
        ButtonType saveButtonType = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == saveButtonType) {
                // Kiểm tra thông tin nhập vào
                if (categoryField.getText().isEmpty() || nameField.getText().isEmpty() || priceField.getText().isEmpty()) {
                    AlertUtil.showErrorAlert("Vui lòng nhập đầy đủ thông tin.");
                    return null;
                }

                try {
                    double parsedPrice = Double.parseDouble(priceField.getText());
                    String imagePath = product.getImageLink();

                    // Kiểm tra và xóa ảnh cũ nếu đã chọn ảnh mới
                    if (selectedFile[0] != null && selectedFile[0].exists()) {
                        // Xóa ảnh cũ
                        File oldFile = new File("sampler/src/main/resources" + product.getImageLink());
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }

                        // Sao chép ảnh mới vào thư mục
                        File destinationFile = new File("sampler/src/main/resources/images/products/" + selectedFile[0].getName());
                        Files.copy(selectedFile[0].toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        // Cập nhật đường dẫn ảnh mới
                        imagePath = "/images/products/" + selectedFile[0].getName();
                    }

                    // Tạo đối tượng sản phẩm đã chỉnh sửa
                    Product updatedProduct = new Product(
                            Integer.parseInt(productId.getText()), // ID sản phẩm
                            imagePath,                                                   // Đường dẫn ảnh
                            nameField.getText(),                                        // Tên sản phẩm
                            parsedPrice,                                               // Giá sản phẩm
                            Integer.parseInt(categoryField.getText())                 // ID danh mục
                    );

                    CashierService.updateProduct(updatedProduct); // Lưu sản phẩm đã chỉnh sửa

                    return updatedProduct;

                } catch (NumberFormatException e) {
                    AlertUtil.showErrorAlert("Giá không hợp lệ.");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    AlertUtil.showErrorAlert("Lỗi khi sao chép tệp.");
                }
            }
            return null; // Trả về null nếu không lưu
        });

        return dialog.showAndWait(); // Hiển thị dialog và chờ kết quả
    }



    private static boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, price=?, image_link=?, category_id=? WHERE id=?"; // Cập nhật thêm category_id
        try (Connection connection = JDBCConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getImageLink());
            statement.setInt(4, product.getCategoryId()); // Thiết lập category_id
            statement.setInt(5, product.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product updated successfully.");
                return true; // Trả về true nếu cập nhật thành công
            } else {
                System.out.println("No product found with the specified ID.");
                return false; // Trả về false nếu không tìm thấy sản phẩm để cập nhật
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public static ComboBox createPayMethodSelectionBox() {
        List<PaymentMethod> Payments = new ArrayList<>();
        ComboBox<String> payComboBox = new ComboBox<>();
        Payments = CashierService.getAllPayMethod();
        for (PaymentMethod Payment : Payments) {
            Payment currentMethod = atlantafx.sampler.base.enummethod.Payment.fromDisplayName(Payment.getMethod().getStatus());
            payComboBox.getItems().add(currentMethod.getStatus());
            payComboBox.setValue(currentMethod.getStatus());
        }
        return payComboBox;
    }
//  public Optional<Products> showEditProductDialog(Products product) {
//    // Tạo dialog để chỉnh sửa sản phẩm
//    Dialog<Products> dialog = new Dialog<>();
//    dialog.setTitle("Chỉnh sửa sản phẩm");
//    dialog.setHeaderText("Chỉnh sửa thông tin sản phẩm");
//
//    // Các trường nhập liệu cho sản phẩm
//    TextField categoryField = new TextField(product.getCategory());
//    TextField nameField = new TextField(product.getName());
//    TextField priceField = new TextField(String.valueOf(product.getPrice()));
//    Button chooseImageButton = new Button("Chọn ảnh mới");
//    Label imageLabel = new Label(product.getImageLink());
//
//    File[] selectedFile = {null}; // Lưu file được chọn
//
//    // Nút chọn ảnh
//    chooseImageButton.setOnAction(e -> {
//      FileChooser fileChooser = new FileChooser();
//      fileChooser.setTitle("Chọn ảnh sản phẩm");
//      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
//      selectedFile[0] = fileChooser.showOpenDialog(dialog.getOwner());
//      if (selectedFile[0] != null) {
//        imageLabel.setText(selectedFile[0].getName());
//      }
//    });
//
//    // Tạo lưới hiển thị thông tin trong dialog
//    GridPane grid = new GridPane();
//    grid.setHgap(10);
//    grid.setVgap(10);
//    grid.add(new Label("Danh mục:"), 0, 0);
//    grid.add(categoryField, 1, 0);
//    grid.add(new Label("Tên sản phẩm:"), 0, 1);
//    grid.add(nameField, 1, 1);
//    grid.add(new Label("Giá:"), 0, 2);
//    grid.add(priceField, 1, 2);
//    grid.add(new Label("Ảnh:"), 0, 3);
//    grid.add(chooseImageButton, 1, 3);
//    grid.add(imageLabel, 2, 3);
//
//    dialog.getDialogPane().setContent(grid);
//
//    // Thêm các nút lưu và hủy
//    ButtonType saveButtonType = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
//    dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
//
//    // Logic khi nhấn nút "Lưu"
//    dialog.setResultConverter(button -> {
//      if (button == saveButtonType) {
//        // Kiểm tra xem các trường có trống không
//        if (categoryField.getText() == null || categoryField.getText().isEmpty() ||
//            nameField.getText() == null || nameField.getText().isEmpty() ||
//            priceField.getText() == null || priceField.getText().isEmpty()) {
//          AlertUtil.showErrorAlert("Vui lòng nhập đầy đủ thông tin.");
//          return null;
//        }
//
//        try {
//          // Chuyển đổi giá từ String sang Double
//          double parsedPrice = Double.parseDouble(priceField.getText());
//
//          // Xử lý file ảnh mới (nếu có)
//          String imagePath = product.getImageLink(); // Giữ nguyên ảnh cũ nếu không thay đổi
//          if (selectedFile[0] != null && selectedFile[0].exists()) {
//            // Xóa ảnh cũ
//            File oldFile = new File("sampler/src/main/resources" + product.getImageLink());
//            if (oldFile.exists()) oldFile.delete();
//
//            // Sao chép ảnh mới
//            File destinationFile = new File("sampler/src/main/resources/images/products/" + selectedFile[0].getName());
//            Files.copy(selectedFile[0].toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//            // Cập nhật đường dẫn ảnh mới
//            imagePath = "/images/products/" + selectedFile[0].getName();
//          }
//
//          // Trả về đối tượng sản phẩm đã chỉnh sửa
//          return new Products(
//              imagePath,
//              categoryField.getText(),
//              nameField.getText(),
//              parsedPrice
//          );
//
//        } catch (NumberFormatException e) {
//          AlertUtil.showErrorAlert("Giá không hợp lệ.");
//        } catch (IOException ioException) {
//          ioException.printStackTrace();
//          AlertUtil.showErrorAlert("Lỗi khi sao chép tệp.");
//        }
//      }
//      return null;
//    });
//
//    return dialog.showAndWait();
//  }

}
