package atlantafx.sampler.cashier.page.components;

import atlantafx.sampler.base.entity.common.Bill;
import atlantafx.sampler.base.service.cashier.CashierService;
import atlantafx.sampler.base.util.AlertUtil;
import atlantafx.sampler.cashier.page.OutlinePage;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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


public class OrderListPage extends OutlinePage {

  public static final String NAME = "Order List";
  static ListView<String> drinkList = new ListView<String>();
  static Bill bill = new Bill();

  @Override
  public String getName() {
    return NAME;
  }

  public OrderListPage() {
    super();
    viewCheckOrder();
  }

  public void viewCheckOrder() {

    // Giao diện chọn bàn
//        Button backButton = new Button("Quay trở lại");
    Label selectedTableLabel = new Label(TableListPage.getTitle());
    TextField searchBar = new TextField();
    searchBar.setPromptText("Nhập mã/Tên món cần tìm");
    searchBar.setPrefWidth(300);
    searchBar.getStyleClass().add("text-field");

    Button checkOut = new Button("Thanh Toán");
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
    searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
      String searchText = newValue.trim();

      if (searchText.isEmpty()) {
        // If the search text is empty, show the full product list
        drinkList.setItems(CashierService.InitializeProductName1());
      } else {
        // Filter the product list based on the search text
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
    ComboBox<String> methodComboBox = CashierService.createPayMethodSelectionBox();

    listHasOrdered.setOnAction(e ->updateAllBill(billContainer,totalField) );

    addButton.setOnAction(e -> {
      if (TableListPage.getTitle() != null) {
        String selectedDrink = drinkList.getSelectionModel().getSelectedItem();
        int quantity = quantitySpinner.getValue();
        if (selectedDrink != null) {
          Bill billCheck = CashierService.findOrderBillIsExist(TableListPage.getTitle(),
              selectedDrink);
          if (billCheck != null) {
            billCheck.setQuantity(quantity + billCheck.getQuantity());
            CashierService.updateOrderBill(billCheck);
            AlertUtil.showErrorAlert(
                "Đồ Uống đã có trong hóa đơn và đã được cập nhật!");
          } else {
            double price = CashierService.getPriceByName(selectedDrink);
            double subTotal = price * quantity;
            bill.setNameTable(TableListPage.getTitle());
            bill.setProductName(selectedDrink);
            bill.setQuantity(quantity);
            bill.setPrice(price);
            CashierService.addOrderBill(bill);

            HBox billRow = new HBox(10);
            Label billInfo = new Label(
                bill.getProductName() + " - Số lượng: " + bill.getQuantity()
                    + " - Giá: "
                    + bill.getPrice() * bill.getQuantity() + " VND");
            billInfo.getStyleClass()
                .add("bill-container-label");  // Add CSS class for styling

            // Nút sửa
            Button editButton = new Button("Sửa");
            editButton.getStyleClass().add("bill-button");
            editButton.setOnAction(editEvent -> {
              Bill selectedProduct = CashierService.getOrderBillByNameProduct(
                  TableListPage.getTitle(), bill.getProductName());
              ;
              if (selectedProduct != null) {
                Bill updatedBill = showProductDialog("Thay đổi số lượng",
                    selectedProduct);
                if (updatedBill != null) {
                  CashierService.updateOrderBill(
                      updatedBill);
                  updateTotalField(totalField,
                      -bill.getPrice() * bill.getQuantity() + bill.getPrice()
                          * updatedBill.getQuantity());// Ensure you have this method in your StaffService
                }
              } else {
                AlertUtil.showErrorAlert("Vui lòng chọn đồ uống để sửa");
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
          AlertUtil.showErrorAlert("Vui lòng chọn đồ uống");
        }
      } else {
        AlertUtil.showErrorAlert("Vui lòng chọn bàn");
      }
    });
    checkOut.setOnAction(e -> {
      updateAllBill(billContainer,totalField);
      double totalAmount = Double.parseDouble(totalField.getText().toString());
      if (totalAmount > 0) {
        String selectedMethod = methodComboBox.getValue();
        if (selectedMethod != null) {
          AlertUtil.showErrorAlert(
              "Thanh toán thành công!\n"
                  + "Tổng tiền: "
                  + totalField.getText().toString() + " VND\n"
                  + "Thành tiền: "
                  + totalAmount + " VND\n"
                  + "Phương thức thanh toán: "
                  + selectedMethod);
          CashierService.resetOrderBill(TableListPage.getTitle());
          billContainer.getChildren().removeAll();
          updateTotalField(totalField, 0);
          // Reset all order bills and update UI
        }
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
        .addAll(selectedTableLabel, searchBar, comboBox, quantityLabel,
            quantitySpinner, addButton, listHasOrdered, containersHBox, totalLabel, totalField,
            methodComboBox, checkOut);
    getChildren().add(orderLayout);
    orderLayout.setPadding(new Insets(20));
    orderLayout.setAlignment(Pos.CENTER);
    orderLayout.getStylesheets().add(
        TableListPage.class.getResource("/css/stylesCashierHomePage.css").toExternalForm());

  }

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
  public void updateAllBill(VBox billContainer,TextField totalField){
    {
      List<Bill> newBill = CashierService.getBillByNameTable(TableListPage.getTitle());
      billContainer.getChildren().clear();  // Xóa các mục cũ trước khi thêm lại
      double total = 0;
      // Kiểm tra nếu danh sách Bill null hoặc rỗng
      if (newBill == null || newBill.isEmpty()) {
        AlertUtil.showErrorAlert("Bàn " + TableListPage.getTitle() + " chưa có đặt đồ uống");
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
                TableListPage.getTitle(), bill.getProductName());
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
              AlertUtil.showErrorAlert("Vui lòng chọn đồ uống để sửa");
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
    }
  }

}