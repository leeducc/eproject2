package com.example.cafemanagement.page.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PageHomeAdmin {

  public VBox viewHomePage(Button button1, Button button2, Button button3, Button button4) {
    TableView<MenuItem> table = new TableView<>();

    // Define columns
    TableColumn<MenuItem, String> typeCol = new TableColumn<>("Loại món");
    typeCol.setCellValueFactory(data -> data.getValue().styleProperty());

    TableColumn<MenuItem, String> idCol = new TableColumn<>("Mã món");
    idCol.setCellValueFactory(data -> data.getValue().idProperty());

    TableColumn<MenuItem, String> nameCol = new TableColumn<>("Tên món");
    nameCol.setCellValueFactory(data -> data.getValue().idProperty());

    TableColumn<MenuItem, String> groupCol = new TableColumn<>("Nhóm thực đơn");
    groupCol.setCellValueFactory(data -> data.getValue().idProperty());

    TableColumn<MenuItem, String> unitCol = new TableColumn<>("Đơn vị tính");
    unitCol.setCellValueFactory(data -> data.getValue().idProperty());
    table.getColumns().addAll(typeCol, idCol, nameCol, groupCol, unitCol);

    // Sample data
    ObservableList<MenuItem> items = FXCollections.observableArrayList(
        new MenuItem(),
        new MenuItem()
    );
    table.setItems(items);
    // Layout
    HBox buttonBox = new HBox(10);
    buttonBox.setPadding(new Insets(10));
    buttonBox.getChildren().addAll(button1, button2, button3, button4);
    VBox dashboardLayout = new VBox(10);
    dashboardLayout.getChildren().addAll(new MenuBar(), table, buttonBox);
    dashboardLayout.setPadding(new Insets(20));
    dashboardLayout.setAlignment(Pos.CENTER);
    dashboardLayout.getStylesheets().add(getClass().getResource("/css/viewMember.css").toExternalForm());
    return dashboardLayout;
  }

}
