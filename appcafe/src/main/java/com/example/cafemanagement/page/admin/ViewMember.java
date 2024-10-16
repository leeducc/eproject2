package com.example.cafemanagement.page.admin;

import com.example.cafemanagement.entities.StaffProperty;
import com.example.cafemanagement.service.staff.StaffService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ViewMember {

  private TableView<StaffProperty> tableView;
  private ObservableList<StaffProperty> data;

  public VBox viewTableViewMember(Button button) {
//    primaryStage.setTitle("Inventory Input Form");

    // Labels and fields for form header
//    Label maPNLabel = new Label("Mã PN");
//    TextField maPNField = new TextField("PN0014");
//    maPNField.setDisable(true);
//
//    Label nguoiLapPhieuLabel = new Label("Người lập phiếu");
//    TextField nguoiLapPhieuField = new TextField("Võ Minh Tuấn");
//
//    Label ngayLapPhieuLabel = new Label("Ngày lập phiếu");
//    DatePicker ngayLapPhieuPicker = new DatePicker();
//
//    Label nguoiGiaoLabel = new Label("Người giao");
//    TextField nguoiGiaoField = new TextField("Anh Đạt");
//
//    Label nhaCungCapLabel = new Label("Nhà cung cấp");
//    TextField nhaCungCapField = new TextField("Dasfoods 247 Phan Bội Châu");
//
//    // Header layout
//    GridPane headerGrid = new GridPane();
//    headerGrid.setPadding(new Insets(10));
//    headerGrid.setHgap(10);
//    headerGrid.setVgap(10);
//    headerGrid.add(maPNLabel, 0, 0);
//    headerGrid.add(maPNField, 1, 0);
//    headerGrid.add(nguoiLapPhieuLabel, 2, 0);
//    headerGrid.add(nguoiLapPhieuField, 3, 0);
//    headerGrid.add(ngayLapPhieuLabel, 4, 0);
//    headerGrid.add(ngayLapPhieuPicker, 5, 0);
//    headerGrid.add(nguoiGiaoLabel, 0, 1);
//    headerGrid.add(nguoiGiaoField, 1, 1);
//    headerGrid.add(nhaCungCapLabel, 2, 1);
//    headerGrid.add(nhaCungCapField, 3, 1);

    // TableView for product details
    tableView = new TableView<>();
    tableView.setEditable(true);
    TableColumn<StaffProperty, String> staffIdCol = new TableColumn<>("Mã Nhân Viên");
    staffIdCol.setCellValueFactory(cellData -> cellData.getValue().staffIdProperty());

    TableColumn<StaffProperty, String> nameStaffCol = new TableColumn<>("Tên Nhân Viên");
    nameStaffCol.setCellValueFactory(cellData -> cellData.getValue().nameStaffProperty());

    TableColumn<StaffProperty, String> phoneNumberCol = new TableColumn<>("Liên Hệ");
    phoneNumberCol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());

    TableColumn<StaffProperty, String> roleStaffCol = new TableColumn<>("Vị Trí");
    roleStaffCol.setCellValueFactory(cellData -> cellData.getValue().roleStaffProperty());

    TableColumn<StaffProperty, String> IdCol = new TableColumn<>("ID");
    IdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());

    tableView.getColumns().addAll(staffIdCol, nameStaffCol, phoneNumberCol, roleStaffCol);

    // Sample data
    data = FXCollections.observableArrayList(
        StaffService.viewStaffProperties()
    );

    tableView.setItems(data);

    // Layout for buttons
    HBox buttonBox = new HBox(10);
    buttonBox.setPadding(new Insets(10));
    Button addButton = new Button("Thêm ");
    Button editlButton = new Button("Sửa");
    Button deleteButton = new Button("Xóa ");
    Button saveButton = new Button("Lưu");

    buttonBox.getChildren().addAll(addButton, deleteButton, editlButton, saveButton,button);
//     Event handlers
    addButton.setOnAction(e -> handleAdd());
    editlButton.setOnAction(e -> handleEdit());
    deleteButton.setOnAction(e -> handleDelete());
    saveButton.setOnAction(e -> handleSave());

    // Main layout
    VBox mainLayout = new VBox(10);
    mainLayout.getChildren().addAll(tableView, buttonBox);
    mainLayout.setPadding(new Insets(10));
    mainLayout.getStylesheets().add(getClass().getResource("/css/viewMember.css").toExternalForm());
    return mainLayout;
  }

  private void handleAdd() {
    StaffProperty newStaff = showStaffDialog("Thêm Nhân Viên",
        new StaffProperty("", "", "", "", ""));
    if (newStaff != null) {
      data.add(newStaff);
//      StaffService.addStaffProperty(newStaff); // Ensure you have this method in your StaffService

    }
  }

  private void handleEdit() {
    StaffProperty selectedStaff = tableView.getSelectionModel().getSelectedItem();
    if (selectedStaff != null) {
      StaffProperty updatedStaff = showStaffDialog("Sửa Nhân Viên", selectedStaff);
      if (updatedStaff != null) {
        int index = data.indexOf(selectedStaff);
        data.set(index, updatedStaff);
        StaffService.updateStaff(updatedStaff); // Ensure you have this method in your StaffService
      }
    } else {
      showAlert("Chưa chọn nhân viên để sửa!");
    }
  }

  private void handleDelete() {
    StaffProperty selectedStaff = tableView.getSelectionModel().getSelectedItem();
    if (selectedStaff != null) {
      data.remove(selectedStaff);
//      StaffService.deleteStaffProperty(selectedStaff.getStaffId()); // Ensure you have this method in your StaffService
      StaffService.removeStaff(Integer.parseInt(selectedStaff.getStaffId()));
    } else {
      showAlert("Chưa chọn nhân viên để xóa!");
    }
  }

  private void handleSave() {
    // Implement any saving logic if required, like saving to a database or file
    showAlert("Dữ liệu đã được lưu!");
  }

  private StaffProperty showStaffDialog(String title, StaffProperty staff) {
    Dialog<StaffProperty> dialog = new Dialog<>();
    dialog.setTitle(title);
    dialog.setHeaderText(null);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField staffIdField = new TextField(staff.getStaffId());
    TextField nameField = new TextField(staff.getNameStaff());
    TextField phoneField = new TextField(staff.getPhoneNumber());
    ComboBox roleField = StaffService.getRoleSelectionBox(staff);

    grid.add(new Label("Mã Nhân Viên:"), 0, 0);
    grid.add(staffIdField, 1, 0);
    grid.add(new Label("Tên Nhân Viên:"), 0, 1);
    grid.add(nameField, 1, 1);
    grid.add(new Label("Liên Hệ:"), 0, 2);
    grid.add(phoneField, 1, 2);
    grid.add(new Label("Vị Trí:"), 0, 3);
    grid.add(roleField, 1, 3);

    dialog.getDialogPane().setContent(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    dialog.setResultConverter(button -> {
      if (button == ButtonType.OK) {
        return new StaffProperty(
            staffIdField.getText(),
            nameField.getText(),
            phoneField.getText(),
            roleField.getValue().toString(),
            staff.getId()
        );
      }
      return null;
    });

    return dialog.showAndWait().orElse(null);
  }

  private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}

