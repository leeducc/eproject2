package com.example.cafemanagement.page.cashier;


import com.example.cafemanagement.entities.Bill;
import com.example.cafemanagement.service.TableCoffeeService;
import com.example.cafemanagement.service.admin.PageLoginService;
import com.example.cafemanagement.service.cashier.CashierService;
import com.example.cafemanagement.util.AlertUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierHomePage {

  static PageLoginService service = new PageLoginService();
  static Bill bill = new Bill();
  private static Label selectedTableLabel;  // Khai báo nhãn này để cập nhật khi chọn bàn

  private static String title;

  public static String getTitle() {
    return title;
  }

  public static void setTitle(String title) {
    CashierHomePage.title = title;
  }
  // Điều kiện để thay đổi màu
  boolean condition = true; // Ví dụ điều kiện



  public static VBox viewTableOrder(Stage primaryStage, Button button) {
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
// Điều kiện để thay đổi màu


    // Thêm các bàn vào khu A (Tầng trệt)
    ArrayList<String> floorTables = TableCoffeeService.getNameTable(1);
    addButtonsToGrid(floorTable, floorTables, button, primaryStage);

    // Thêm các bàn vào khu B (Tầng lầu)
    ArrayList<String> upstairTables = TableCoffeeService.getNameTable(2);
    addButtonsToGrid(upstairTable, upstairTables, button, primaryStage);

    // Tạo các nhãn cho khu vực bàn
    Label labelFloorTables = new Label("Floor Tables");
    labelFloorTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    Label labelUpstairTables = new Label("Upstair Tables");
    labelUpstairTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    Button buttonUpdate = new Button("Cập Nhật Trạng Thái Bàn");
    buttonUpdate.setOnAction(e -> {
      addButtonsToGrid(upstairTable, upstairTables, button, primaryStage);
      addButtonsToGrid(floorTable, floorTables, button, primaryStage);
    });

    // Thêm các nhãn và khu vực bàn vào layout
    VBox layoutTableSelection = new VBox(10);
    layoutTableSelection.getChildren()
        .addAll(labelFloorTables, floorTable, upstairTable, labelUpstairTables,buttonUpdate);
    layoutTableSelection.setPadding(new Insets(20));
    layoutTableSelection.setAlignment(Pos.CENTER);
    layoutTableSelection.getStylesheets().add(
        CashierHomePage.class.getResource("/css/stylesCashierHomePage.css").toExternalForm());
    return layoutTableSelection;
  }

  public static VBox viewCheckOrder(Stage primaryStage, Button backButton) {
    primaryStage.setTitle("Quản lý bàn quán cafe");

    // Giao diện chọn bàn
    selectedTableLabel = new Label(CashierHomePage.getTitle());
    ListView<String> drinkList = CashierService.InitializeProductName();

    Label quantityLabel = new Label("Số lượng:");
    Spinner<Integer> quantitySpinner = new Spinner<>(1, 20, 1);

    Button addButton = new Button("Thêm vào hóa đơn");
    Button listHasOrdered = new Button("Hóa đơn của bàn");

    VBox billContainer = new VBox(10);  // Container chứa các mục hóa đơn

    Label totalLabel = new Label("Tổng cộng:");
    TextField totalField = new TextField();
    totalField.setEditable(false);
    totalField.setText("0");
    ComboBox<String> methodComboBox = service.createPayMethodSelectionBox();

    listHasOrdered.setOnAction(e -> {
      List<Bill> newBill = CashierService.getBillByNameTable(CashierHomePage.getTitle());
      billContainer.getChildren().clear();  // Xóa các mục cũ trước khi thêm lại

      // Kiểm tra nếu danh sách Bill null hoặc rỗng
      if (newBill == null || newBill.isEmpty()) {
        AlertUtil.showErrorLoginAlert("Bàn " + CashierHomePage.getTitle() + " chưa có đặt đồ uống");
      } else {
        // Lặp qua danh sách và hiển thị thông tin
        for (Bill bill : newBill) {
          double subTotal = bill.getQuantity() * bill.getPrice();
          HBox billRow = new HBox(10);

          Label billInfo = new Label(bill.getProductName() + " - Số lượng: " + bill.getQuantity() + " - Giá: "
              + bill.getPrice() * bill.getQuantity() + " VND");

          // Tạo nút sửa
//          Button editButton = new Button("Sửa");
//          editButton.setOnAction(editEvent -> {
//            // Cập nhật thông tin cho việc sửa
//            drinkList.getSelectionModel().select(bill.getProductName());
//            quantitySpinner.getValueFactory().setValue(bill.getQuantity());
//
//            // Khi sửa, cần xóa mục cũ khỏi danh sách hóa đơn trước
////            CashierService.removeOrderBill(bill);
//            billContainer.getChildren().remove(billRow);
//            updateTotalField(totalField, -bill.getPrice() * bill.getQuantity());
//          });

            // ... existing code to add a new order
             // Update button color after order

          // Tạo nút xóa
          Button deleteButton = new Button("Xóa");
          deleteButton.setOnAction(deleteEvent -> {
            // Xóa mục khỏi danh sách hóa đơn
            CashierService.removeOrderBill(bill);
            billContainer.getChildren().remove(billRow);
            updateTotalField(totalField, -bill.getPrice() * bill.getQuantity());
          });

          billRow.getChildren().addAll(billInfo, deleteButton);
          billContainer.getChildren().add(billRow);
          updateTotalField(totalField, subTotal);
        }
      }
    });

    addButton.setOnAction(e -> {
      String selectedDrink = drinkList.getSelectionModel().getSelectedItem();
      int quantity = quantitySpinner.getValue();
      if (selectedDrink != null) {
        double price = CashierService.getPriceByName(selectedDrink);
        double subTotal = price * quantity;
        bill.setNameTable(CashierHomePage.getTitle());
        bill.setProductName(selectedDrink);
        bill.setQuantity(quantity);
        bill.setPrice(price);
        CashierService.addOrderBill(bill);

        HBox billRow = new HBox(10);
        Label billInfo = new Label(selectedDrink + " - Số lượng: " + quantity + " - Giá: " + subTotal + " VND");

        // Nút sửa
//        Button editButton = new Button("Sửa");
//        editButton.setOnAction(editEvent -> {
//          drinkList.getSelectionModel().select(selectedDrink);
//          quantitySpinner.getValueFactory().setValue(quantity);
////          CashierService.removeOrderBill(bill);
//          billContainer.getChildren().remove(billRow);
//          updateTotalField(totalField, -subTotal);
//        });

        // Nút xóa
        Button deleteButton = new Button("Xóa");
        deleteButton.setOnAction(deleteEvent -> {
          CashierService.removeOrderBill(bill);
          billContainer.getChildren().remove(billRow);
          updateTotalField(totalField, -subTotal);
        });

        billRow.getChildren().addAll(billInfo, deleteButton);
        billContainer.getChildren().add(billRow);

        updateTotalField(totalField, subTotal);
      } else {
        AlertUtil.showErrorLoginAlert("Vui lòng chọn đồ uống");
      }
    });

    // Layout cho giao diện thanh toán
    VBox orderLayout = new VBox(10);
    orderLayout.getChildren().addAll(selectedTableLabel, drinkList, quantityLabel, quantitySpinner, addButton,
        listHasOrdered, billContainer, totalLabel, totalField, methodComboBox);
    orderLayout.setPadding(new Insets(20));
    orderLayout.setAlignment(Pos.CENTER);
    orderLayout.getStylesheets().add(
        CashierHomePage.class.getResource("/css/stylesCashierHomePage.css").toExternalForm());

    // Thêm nút "Quay về" vào layout của phần thanh toán
    orderLayout.getChildren().add(backButton);

    return orderLayout;
  }

  // Cập nhật giá trị tổng
  private static void updateTotalField(TextField totalField, double amount) {
    double currentTotal = Double.parseDouble(totalField.getText());
    currentTotal += amount;
    totalField.setText(String.valueOf(currentTotal));
  }



  private static void updateTableButtonColor(Button tableButton, String tableName) {
    List<Bill> newBill = CashierService.getBillByNameTable(tableName);
    if (newBill == null || newBill.isEmpty()) {
      tableButton.setStyle("-fx-background-color: lightgray;");
    } else {
      tableButton.setStyle("-fx-background-color: green;");
    }
  }

  // Modify the addButtonsToGrid method to use the updateTableButtonColor method
  private static void addButtonsToGrid(GridPane grid, ArrayList<String> tableNames, Button button, Stage primaryStage) {
    int count = 0;
    int rows = 6;
    int cols = 6;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (count >= tableNames.size()) {
          break;
        }

        // Create a new button for each table
        Button tableButton = new Button(tableNames.get(count));
        tableButton.setPrefSize(90, 90);

        // Set initial color based on the presence of a bill
        updateTableButtonColor(tableButton, tableNames.get(count));

        // Handle action when the button is pressed
        tableButton.setOnAction(e -> {
          // Update the label to show the selected table
          selectedTableLabel = new Label(tableButton.getText());
          CashierHomePage.setTitle(tableButton.getText());

          // Switch to the order scene
          primaryStage.setScene(new Scene(CashierHomePage.viewCheckOrder(primaryStage, button), 800, 600));

          // Update the button color after the order
          updateTableButtonColor(tableButton, tableButton.getText());
        });

        // Add the button to the grid
        grid.add(tableButton, col, row);
        count++;
      }
    }
  }

}



