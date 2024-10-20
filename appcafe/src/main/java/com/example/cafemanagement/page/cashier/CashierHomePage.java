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

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierHomePage {

  static ListView<String> drinkList = new ListView<String>();
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
        .addAll(labelFloorTables, floorTable, upstairTable, labelUpstairTables, buttonUpdate);
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
    TextField searchBar = new TextField();
    searchBar.setPromptText("Nhập mã/Tên món cần tìm");
    searchBar.setPrefWidth(300);
    searchBar.getStyleClass().add("text-field");

    // Search Button
    Button searchButton = new Button("🔍");
    searchButton.getStyleClass().add("button");

    // ComboBox for filtering
    ComboBox<String> comboBox = CashierService.createPayCategoriesSelectionBox();
    comboBox.getItems().add("All");
    comboBox.setValue("All");
    comboBox.getStyleClass().add("combo-box");
    drinkList = CashierService.InitializeProductName();
    // ComboBox action to filter products by category
    comboBox.setOnAction(e -> {
      String selectedFilter = comboBox.getValue();
      if ("All".equals(selectedFilter)) {
        // Lấy danh sách tất cả sản phẩm và cập nhật ListView
        drinkList.setItems(CashierService.InitializeProductName1());
      } else {
        // Lấy danh sách sản phẩm theo danh mục và cập nhật ListView
        drinkList.setItems(CashierService.InitializeProductNameCategory(selectedFilter));
      }
    });

// Search button action to search products by keyword
    searchButton.setOnAction(e -> {
      String searchText = searchBar.getText().trim();
      if (searchText.isEmpty()) {
        drinkList.setItems(CashierService.InitializeProductName1());
      } else {
        drinkList.setItems(CashierService.InitializeProductNameByKey(searchText));
      }
    });

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
      double total = 0;
      // Kiểm tra nếu danh sách Bill null hoặc rỗng
      if (newBill == null || newBill.isEmpty()) {
        AlertUtil.showErrorLoginAlert("Bàn " + CashierHomePage.getTitle() + " chưa có đặt đồ uống");
      } else {
        // Lặp qua danh sách và hiển thị thông tin
        for (Bill bill : newBill) {
          double subTotal = bill.getQuantity() * bill.getPrice();
          HBox billRow = new HBox(10);

          Label billInfo = new Label(
              bill.getProductName() + " - Số lượng: " + bill.getQuantity() + " - Giá: "
                  + bill.getPrice() * bill.getQuantity() + " VND");
          billInfo.getStyleClass().add("bill-container-label");  // Add CSS class for styling

          // Tạo nút sửa
          Button editButton = new Button("Sửa");
          editButton.getStyleClass().add("bill-button");
          editButton.setOnAction(editEvent -> {
            Bill selectedProduct = CashierService.getOrderBillByNameProduct(
                CashierHomePage.getTitle(), bill.getProductName());
            ;
            if (selectedProduct != null) {
              Bill updatedBill = showProductDialog("Thay đổi số lượng", selectedProduct);
              if (updatedBill != null) {
                CashierService.updateOrderBill(
                    updatedBill);
                updateTotalField(totalField, -bill.getPrice() * bill.getQuantity() + bill.getPrice()
                    * updatedBill.getQuantity());// Ensure you have this method in your StaffService
              }
            } else {
              AlertUtil.showErrorLoginAlert("Vui lòng chọn đồ uống để sửa");
            }
          });

          // ... existing code to add a new order
          // Update button color after order

          // Tạo nút xóa
          Button deleteButton = new Button("Xóa");
          deleteButton.getStyleClass().add("bill-button");
          deleteButton.setOnAction(deleteEvent -> {
            // Xóa mục khỏi danh sách hóa đơn
            CashierService.removeOrderBill(bill);
            billContainer.getChildren().remove(billRow);
            updateTotalField(totalField, -bill.getPrice() * bill.getQuantity());
          });

          billRow.getChildren().addAll(billInfo, editButton, deleteButton);
          billContainer.getChildren().add(billRow);
          total += subTotal;
        }
        updateTotalFieldFinal(totalField, total);
      }
    });

    addButton.setOnAction(e -> {
      String selectedDrink = drinkList.getSelectionModel().getSelectedItem();
      int quantity = quantitySpinner.getValue();
      if (selectedDrink != null) {
        Bill billCheck = CashierService.findOrderBillIsExist(CashierHomePage.getTitle(),
            selectedDrink);
        if (billCheck != null) {
          billCheck.setQuantity(quantity + billCheck.getQuantity());
          CashierService.updateOrderBill(billCheck);
          AlertUtil.showErrorLoginAlert("Đồ Uống đã có trong hóa đơn và đã được cập nhật!");
        } else {
          double price = CashierService.getPriceByName(selectedDrink);
          double subTotal = price * quantity;
          bill.setNameTable(CashierHomePage.getTitle());
          bill.setProductName(selectedDrink);
          bill.setQuantity(quantity);
          bill.setPrice(price);
          CashierService.addOrderBill(bill);

          HBox billRow = new HBox(10);
          Label billInfo = new Label(
              bill.getProductName() + " - Số lượng: " + bill.getQuantity() + " - Giá: "
                  + bill.getPrice() * bill.getQuantity() + " VND");
          billInfo.getStyleClass().add("bill-container-label");  // Add CSS class for styling

          // Nút sửa
          Button editButton = new Button("Sửa");
          editButton.getStyleClass().add("bill-button");
          editButton.setOnAction(editEvent -> {
            Bill selectedProduct = CashierService.getOrderBillByNameProduct(
                CashierHomePage.getTitle(), bill.getProductName());
            ;
            if (selectedProduct != null) {
              Bill updatedBill = showProductDialog("Thay đổi số lượng", selectedProduct);
              if (updatedBill != null) {
                CashierService.updateOrderBill(
                    updatedBill);
                updateTotalField(totalField, -bill.getPrice() * bill.getQuantity() + bill.getPrice()
                    * updatedBill.getQuantity());// Ensure you have this method in your StaffService
              }
            } else {
              AlertUtil.showErrorLoginAlert("Vui lòng chọn đồ uống để sửa");
            }
          });

          // Nút xóa
          Button deleteButton = new Button("Xóa");
          deleteButton.getStyleClass().add("bill-button");
          deleteButton.setOnAction(deleteEvent -> {
            CashierService.removeOrderBill(bill);
            billContainer.getChildren().remove(billRow);
            updateTotalField(totalField, -subTotal);
          });

          billRow.getChildren().addAll(billInfo, editButton, deleteButton);
          billContainer.getChildren().add(billRow);

          updateTotalField(totalField, subTotal);
        }
      } else {
        AlertUtil.showErrorLoginAlert("Vui lòng chọn đồ uống");
      }
    });

// Create a ScrollPane for the bill container
    ScrollPane billScrollPane = new ScrollPane(billContainer);
    billScrollPane.setFitToWidth(true); // Ensure it fits the width of the layout
    billScrollPane.setPrefHeight(300); // Set a preferred height for the scroll pane
    billScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show scrollbar as needed

// Create an HBox to hold drinkList and billScrollPane
    HBox containersHBox = new HBox(10); // 10 is the spacing between the two containers

// Set preferred widths to control the ratio
    VBox drinkListContainer = new VBox(drinkList);
    drinkListContainer.setPrefWidth(300); // Set a preferred width for the drink list
    drinkListContainer.getStyleClass().add("drink-list-container");

    VBox billContainerContainer = new VBox(billScrollPane);
    billContainerContainer.setPrefWidth(700); // Set a preferred width for the bill container
    billContainerContainer.getStyleClass().add("bill-container");

    containersHBox.getChildren().addAll(drinkListContainer, billContainerContainer);
// Layout for the entire scene (VBox layout)
    VBox orderLayout = new VBox(10);
    orderLayout.getChildren()
        .addAll(selectedTableLabel, searchBar, searchButton, comboBox, quantityLabel,
            quantitySpinner, addButton, listHasOrdered, containersHBox, totalLabel, totalField,
            methodComboBox);

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

  private static void updateTotalFieldFinal(TextField totalField, double amount) {
    double currentTotal = amount;
//    currentTotal += amount;
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
  private static void addButtonsToGrid(GridPane grid, ArrayList<String> tableNames, Button button,
      Stage primaryStage) {
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
          primaryStage.setScene(
              new Scene(CashierHomePage.viewCheckOrder(primaryStage, button), 800, 600));

          // Update the button color after the order
          updateTableButtonColor(tableButton, tableButton.getText());
        });

        // Add the button to the grid
        grid.add(tableButton, col, row);
        count++;
      }
    }
  }

  private static Bill showProductDialog(String title, Bill bill) {
    Dialog<Bill> dialog = new Dialog<>();
    dialog.setTitle(title);
    dialog.setHeaderText(null);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField staffIdField = new TextField(bill.getProductName());
    TextField nameField = new TextField(bill.getNameTable());
    TextField quantity = new TextField(String.valueOf(bill.getQuantity()));

    grid.add(new Label("Đồ Uống"), 0, 0);
    grid.add(staffIdField, 1, 0);
    grid.add(new Label("Bàn: "), 0, 1);
    grid.add(nameField, 1, 1);
    grid.add(new Label("Số luợng"), 0, 2);
    grid.add(quantity, 1, 2);

    dialog.getDialogPane().setContent(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    dialog.setResultConverter(button -> {
      if (button == ButtonType.OK) {
        return new Bill(
            nameField.getText(),
            staffIdField.getText(),
            Integer.valueOf(quantity.getText())
        );
      }
      return null;
    });

    return dialog.showAndWait().orElse(null);
  }

}



