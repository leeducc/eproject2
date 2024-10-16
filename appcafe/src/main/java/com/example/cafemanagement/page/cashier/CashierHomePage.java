package com.example.cafemanagement.page.cashier;


import com.example.cafemanagement.service.TableCoffeeService;
import com.example.cafemanagement.service.cashier.CashierService;
import com.example.cafemanagement.util.AlertUtil;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierHomePage {

  private static Stage window;
  private static Scene sceneTableSelection;
  private static Scene sceneOrder;
  private static Label selectedTableLabel;  // Khai báo nhãn này để cập nhật khi chọn bàn


  public static VBox viewTableOrder() {
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
    ArrayList<String> floorTables = TableCoffeeService.getNameTable(1);
    addButtonsToGrid(floorTable, floorTables);

    // Thêm các bàn vào khu B (Tầng lầu)
    ArrayList<String> upstairTables = TableCoffeeService.getNameTable(2);
    addButtonsToGrid(upstairTable, upstairTables);

    // Tạo các nhãn cho khu vực bàn
    Label labelFloorTables = new Label("Floor Tables");
    labelFloorTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    Label labelUpstairTables = new Label("Upstair Tables");
    labelUpstairTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    // Thêm các nhãn và khu vực bàn vào layout
    VBox layoutTableSelection = new VBox(10);
    layoutTableSelection.getChildren()
        .addAll(labelFloorTables, floorTable, upstairTable, labelUpstairTables);
    layoutTableSelection.setPadding(new Insets(20));
    layoutTableSelection.setAlignment(Pos.CENTER);
    layoutTableSelection.getStylesheets().add(
        CashierHomePage.class.getResource("/css/stylesCashierHomePage.css").toExternalForm());
    return layoutTableSelection;
  }

  public static void cashierHomePage(Stage primaryStage, VBox mainContainer) {
    window = primaryStage;
    window.setTitle("Quản lý bàn quán cafe");

//
    // Giao diện chọn bàn
    sceneTableSelection = new Scene(mainContainer, 800, 600);

    // ----------- Giao diện thanh toán và chọn đồ uống -----------
    selectedTableLabel = new Label("Bàn: ");
    ListView<String> drinkList = CashierService.InitializeProductName();

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
        double price = CashierService.getPriceByName(selectedDrink);
        double total = Double.parseDouble(totalField.getText());
        double subTotal = price * quantity;

        // Cập nhật hóa đơn
        billArea.appendText(
            selectedDrink + " - Số lượng: " + quantity + " - Giá: " + price * quantity + " VND\n");
        totalField.setText(String.valueOf(total + subTotal));
      } else {
        AlertUtil.showErrorLoginAlert("Vui lòng chọn đồ uống");
      }
    });
    Button backButton = new Button("Quay về");
    backButton.setOnAction(e -> {
      window.setScene(sceneTableSelection); // Quay về giao diện chọn bàn
    });

    // Layout cho giao diện thanh toán
    VBox orderLayout = new VBox(10);
    orderLayout.getChildren()
        .addAll(selectedTableLabel, drinkList, quantityLabel, quantitySpinner, addButton, billArea,
            totalLabel, totalField);
    orderLayout.setPadding(new Insets(20));
    orderLayout.setAlignment(Pos.CENTER);
    orderLayout.getStylesheets().add(
        CashierHomePage.class.getResource("/css/stylesCashierHomePage.css").toExternalForm());

    // Thêm nút "Quay về" vào layout của phần thanh toán
    orderLayout.getChildren().add(backButton);
    sceneOrder = new Scene(orderLayout, 800, 600);

    // Hiển thị giao diện chọn bàn lúc khởi động
    window.setScene(sceneTableSelection);
    window.show();
  }

  // Phương thức để thêm các nút bàn vào GridPane
  private static void addButtonsToGrid(GridPane grid, ArrayList<String> tableNames) {
    int count = 0;
    int rows = 6;
    int cols = 6;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (count >= tableNames.size()) {
          break;
        }
        Button tableButton = new Button(tableNames.get(count));
        tableButton.setPrefSize(90, 90);
        tableButton.setStyle("-fx-background-color: lightgray;");

        // Khi nhấn vào nút bàn, chuyển qua giao diện chọn đồ uống và thanh toán
        tableButton.setOnAction(e -> {
          // Cập nhật nhãn hiển thị bàn đã chọn
          selectedTableLabel.setText(
              "Bàn: " + tableButton.getText());  // Cập nhật trực tiếp nhãn đã có
          // Chuyển sang giao diện order
          window.setScene(sceneOrder);
        });

        grid.add(tableButton, col, row);
        count++;
      }
    }
  }
}



