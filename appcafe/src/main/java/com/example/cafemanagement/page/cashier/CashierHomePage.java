package com.example.cafemanagement.page.cashier;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class CashierHomePage {
    private Stage window;
    private Scene sceneTableSelection, sceneOrder;
    private Label selectedTableLabel;
    private Button selectedTableButton;
    private Button orderButton, paymentButton; // Nút Order và Thanh toán
    private Map<Button, String> tableStatusMap; // Bản đồ lưu trạng thái của từng bàn
    private double orderTotalAmount = 0; // Lưu tổng số tiền order

    public void cashierHomePage(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Quản lý bàn quán cafe");

        tableStatusMap = new HashMap<>();  // Khởi tạo bản đồ trạng thái của các bàn

        // ----------- Giao diện chọn bàn -----------
        GridPane floorTable = new GridPane();
        floorTable.setPadding(new Insets(10));
        floorTable.setHgap(10);
        floorTable.setVgap(10);
        floorTable.setAlignment(Pos.CENTER);

        GridPane upstairTable = new GridPane();
        upstairTable.setPadding(new Insets(10));
        upstairTable.setHgap(10);
        upstairTable.setVgap(10);
        upstairTable.setAlignment(Pos.CENTER);

        // Thêm các bàn vào khu A (Tầng trệt)
        String[] floorTables = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        addButtonsToGrid(floorTable, floorTables);

        // Thêm các bàn vào khu B (Tầng lầu)
        String[] upstairTables = {
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
                "35", "36"};
        addButtonsToGrid(upstairTable, upstairTables);

        // Tạo các nhãn cho khu vực bàn
        Label labelFloorTables = new Label("Floor Tables");
        labelFloorTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label labelUpstairTables = new Label("Upstair Tables");
        labelUpstairTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Thêm các nhãn và khu vực bàn vào layout
        VBox layoutTableSelection = new VBox(20);
        layoutTableSelection.setPadding(new Insets(20));
        layoutTableSelection.getChildren().addAll(labelFloorTables, floorTable, labelUpstairTables, upstairTable);

        // Giao diện chọn bàn
        sceneTableSelection = new Scene(layoutTableSelection, 1280, 720);

        // ----------- Giao diện thanh toán và chọn đồ uống -----------
        selectedTableLabel = new Label("Bàn: ");  // Khởi tạo nhãn mặc định
        ListView<String> drinkList = new ListView<>();
        drinkList.getItems().addAll("Cà phê đen", "Cà phê sữa", "Trà sữa", "Nước cam", "Nước ngọt");

        Label quantityLabel = new Label("Số lượng:");
        Spinner<Integer> quantitySpinner = new Spinner<>(1, 20, 1);

        Button addButton = new Button("Thêm vào hóa đơn");

        TextArea billArea = new TextArea();
        billArea.setEditable(false);

        Label totalLabel = new Label("Tổng cộng:");
        TextField totalField = new TextField();
        totalField.setEditable(false);
        totalField.setText("0");

        addButton.setOnAction(e -> {
            String selectedDrink = drinkList.getSelectionModel().getSelectedItem();
            int quantity = quantitySpinner.getValue();
            if (selectedDrink != null) {
                double price = getPrice(selectedDrink);
                double subTotal = price * quantity;

                // Cập nhật hóa đơn
                billArea.appendText(selectedDrink + " - Số lượng: " + quantity + " - Giá: " + price * quantity + " VND\n");
                orderTotalAmount += subTotal; // Cập nhật tổng số tiền order
                totalField.setText(String.valueOf(orderTotalAmount)); // Cập nhật tổng tiền trên giao diện
            }
        });


        orderButton = new Button("Order");
        orderButton.setOnAction(e -> {
            if (selectedTableButton != null) {
                // Cập nhật trạng thái bàn thành "Đang sử dụng"
                selectedTableButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                tableStatusMap.put(selectedTableButton, "in use");

                // Ẩn nút "Order" và hiển thị nút "Thanh toán"
                orderButton.setVisible(false);
                paymentButton.setVisible(true);

                // Quay lại danh sách bàn sau khi order
                window.setScene(sceneTableSelection);
            }
        });

        paymentButton = new Button("Thanh toán");
        paymentButton.setVisible(false);  // Mặc định ẩn nút "Thanh toán"
        paymentButton.setOnAction(e -> {
            if (selectedTableButton != null) {
                // Mở cửa sổ thanh toán
                showPaymentWindow();
            }
        });

        Button backButton = new Button("Quay về");
        backButton.setOnAction(e -> {
            window.setScene(sceneTableSelection); // Quay về giao diện chọn bàn
        });

        // Layout cho giao diện thanh toán
        VBox orderLayout = new VBox(10);
        orderLayout.setPadding(new Insets(20));
        orderLayout.getChildren().addAll(selectedTableLabel, drinkList, quantityLabel, quantitySpinner, addButton, billArea, totalLabel, totalField, orderButton, paymentButton, backButton);

        sceneOrder = new Scene(orderLayout, 1280, 720);

        // Hiển thị giao diện chọn bàn lúc khởi động
        window.setScene(sceneTableSelection);
        window.show();
    }

    // Phương thức để thêm các nút bàn vào GridPane
    private void addButtonsToGrid(GridPane grid, String[] tableNames) {
        int count = 0;
        int rows = 6;
        int cols = 6;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (count >= tableNames.length) break;
                Button tableButton = new Button(tableNames[count]);
                tableButton.setPrefSize(70, 70);
                tableButton.setStyle("-fx-background-color: lightgray;");

                // Mặc định trạng thái là "empty"
                tableStatusMap.put(tableButton, "empty");

                // Khi nhấn vào nút bàn, chuyển qua giao diện chọn đồ uống và thanh toán
                tableButton.setOnAction(e -> {
                    String status = tableStatusMap.get(tableButton);
                    if (!"waiting clean".equals(status)) {  // Kiểm tra nếu không phải "chờ dọn"
                        selectedTableLabel.setText("Bàn: " + tableButton.getText());
                        selectedTableButton = tableButton;  // Lưu trữ nút bàn đã chọn

                        if ("in use".equals(status)) {
                            // Nếu bàn đang sử dụng, hiển thị nút "Thanh toán" và ẩn nút "Order"
                            orderButton.setVisible(false);
                            paymentButton.setVisible(true);
                        } else if ("empty".equals(status)) {
                            // Nếu bàn trống, hiển thị nút "Order" và ẩn nút "Thanh toán"
                            orderButton.setVisible(true);
                            paymentButton.setVisible(false);
                        }

                        // Chuyển sang giao diện order
                        window.setScene(sceneOrder);
                    }
                });

                grid.add(tableButton, col, row);
                count++;
            }
        }
    }

    // Phương thức tính giá dựa trên đồ uống
    private double getPrice(String drink) {
        return switch (drink) {
            case "Cà phê đen" -> 20000;
            case "Cà phê sữa" -> 25000;
            case "Trà sữa" -> 30000;
            case "Nước cam" -> 15000;
            case "Nước ngọt" -> 10000;
            default -> 0;
        };
    }

    // Phương thức hiển thị cửa sổ thanh toán
    private void showPaymentWindow() {
        Stage paymentWindow = new Stage();
        paymentWindow.initModality(Modality.APPLICATION_MODAL);
        paymentWindow.setTitle("Phương thức thanh toán");

        Label label = new Label("Chọn phương thức thanh toán:");

        // Các nút chọn phương thức thanh toán
        Button cashButton = new Button("Tiền mặt");
        Button bankTransferButton = new Button("Chuyển khoản");
        Button vnpayButton = new Button("VNPAY");
        Button momoButton = new Button("MoMo");
        Button posButton = new Button("POS");

        // Xử lý sự kiện khi chọn phương thức thanh toán
        cashButton.setOnAction(e -> handlePaymentMethod("Tiền mặt", paymentWindow));
        bankTransferButton.setOnAction(e -> handlePaymentMethod("Chuyển khoản", paymentWindow));
        vnpayButton.setOnAction(e -> handlePaymentMethod("VNPAY", paymentWindow));
        momoButton.setOnAction(e -> handlePaymentMethod("MoMo", paymentWindow));
        posButton.setOnAction(e -> handlePaymentMethod("POS", paymentWindow));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, cashButton, bankTransferButton, vnpayButton, momoButton, posButton);

        Scene scene = new Scene(layout, 300, 300);
        paymentWindow.setScene(scene);
        paymentWindow.showAndWait();
    }

    // Phương thức xử lý thanh toán và hiển thị màn hình nhập số tiền
    private void handlePaymentMethod(String method, Stage paymentWindow) {
        paymentWindow.close();  // Đóng cửa sổ chọn phương thức thanh toán

        // Hiển thị cửa sổ nhập số tiền thanh toán
        showPaymentAmountWindow(method);
    }

    // Phương thức hiển thị màn hình nhập số tiền
    private void showPaymentAmountWindow(String method) {
        Stage amountWindow = new Stage();
        amountWindow.initModality(Modality.APPLICATION_MODAL);
        amountWindow.setTitle("Thanh toán - " + method);

        Label remainingLabel = new Label("Số tiền còn lại:");
        TextField remainingAmountField = new TextField();
        remainingAmountField.setEditable(false);
        remainingAmountField.setText(String.valueOf(orderTotalAmount));

        Label paymentLabel = new Label("Số tiền thanh toán:");
        TextField paymentAmountField = new TextField();

        Label messageLabel = new Label();

        Button payButton = new Button("Thanh toán");
        payButton.setOnAction(e -> {
            try {
                double remainingAmount = Double.parseDouble(remainingAmountField.getText());
                double paymentAmount = Double.parseDouble(paymentAmountField.getText());

                if (remainingAmount == paymentAmount) {
                    messageLabel.setText("Thanh toán thành công!");
                    messageLabel.setStyle("-fx-text-fill: green;");

                    // Cập nhật trạng thái bàn thành "Chờ dọn"
                    if (selectedTableButton != null) {
                        selectedTableButton.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
                        tableStatusMap.put(selectedTableButton, "waiting clean");
                        window.setScene(sceneTableSelection);
                    }

                    amountWindow.close();
                } else {
                    messageLabel.setText("Số tiền thanh toán không khớp!");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Vui lòng nhập số tiền hợp lệ!");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(remainingLabel, remainingAmountField, paymentLabel, paymentAmountField, payButton, messageLabel);

        Scene scene = new Scene(layout, 300, 300);
        amountWindow.setScene(scene);

        // Đặt focus cho cả hai trường
        remainingAmountField.requestFocus();
        paymentAmountField.requestFocus();

        amountWindow.showAndWait();
    }
}







