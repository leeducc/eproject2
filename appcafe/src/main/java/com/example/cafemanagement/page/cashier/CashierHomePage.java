package com.example.cafemanagement.page.cashier;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierHomePage {
    private Stage window;
    private Scene sceneTableSelection, sceneOrder;
    private Label selectedTableLabel;  // Khai báo nhãn này để cập nhật khi chọn bàn

    public void cashierHomePage(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Quản lý bàn quán cafe");

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
        sceneTableSelection = new Scene(layoutTableSelection, 1600, 900);

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
                double total = Double.parseDouble(totalField.getText());
                double subTotal = price * quantity;

                // Cập nhật hóa đơn
                billArea.appendText(selectedDrink + " - Số lượng: " + quantity + " - Giá: " + price * quantity + " VND\n");
                totalField.setText(String.valueOf(total + subTotal));
            }
        });

        Button backButton = new Button("Quay về");
        backButton.setOnAction(e -> {
            window.setScene(sceneTableSelection); // Quay về giao diện chọn bàn
        });

        // Layout cho giao diện thanh toán
        VBox orderLayout = new VBox(10);
        orderLayout.setPadding(new Insets(20));
        orderLayout.getChildren().addAll(selectedTableLabel, drinkList, quantityLabel, quantitySpinner, addButton, billArea, totalLabel, totalField);

        // Thêm nút "Quay về" vào layout của phần thanh toán
        orderLayout.getChildren().add(backButton);
        sceneOrder = new Scene(orderLayout, 1600, 900);

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

                // Khi nhấn vào nút bàn, chuyển qua giao diện chọn đồ uống và thanh toán
                tableButton.setOnAction(e -> {
                    // Cập nhật nhãn hiển thị bàn đã chọn
                    selectedTableLabel.setText("Bàn: " + tableButton.getText());  // Cập nhật trực tiếp nhãn đã có
                    // Chuyển sang giao diện order
                    window.setScene(sceneOrder);
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
}



