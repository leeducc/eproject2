package com.example.cafemanagement.page.admin;

import com.example.cafemanagement.entities.StaffProperty;
import com.example.cafemanagement.service.staff.StaffService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ViewMember {

  private TableView<StaffProperty> tableView;
  private ObservableList<StaffProperty> data;

  public VBox viewTableViewMember() {
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

    tableView.getColumns().addAll(staffIdCol, nameStaffCol, phoneNumberCol, roleStaffCol);

    // Sample data
    data = FXCollections.observableArrayList(
        StaffService.viewStaffProperties()
    );

    tableView.setItems(data);

    // Layout for buttons
    HBox buttonBox = new HBox(10);
    buttonBox.setPadding(new Insets(10));
    Button addButton = new Button("Thêm dòng");
    Button deleteButton = new Button("Xóa dòng");
    Button saveButton = new Button("Lưu");
    Button cancelButton = new Button("Hủy bỏ");

    buttonBox.getChildren().addAll(addButton, deleteButton, saveButton, cancelButton);

    // Main layout
    VBox mainLayout = new VBox(10);
    mainLayout.getChildren().addAll( tableView, buttonBox);
    mainLayout.setPadding(new Insets(10));
    mainLayout.getStylesheets().add(getClass().getResource("/css/viewMember.css").toExternalForm());

//    Scene scene = new Scene(mainLayout, 800, 600);
//    primaryStage.setScene(scene);
//    primaryStage.show();
    return mainLayout;
  }

}
