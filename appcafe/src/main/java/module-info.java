module com.example.cafemanagemnet {
  requires javafx.graphics;
  requires jbcrypt;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires jdk.jfr;
  requires com.google.protobuf; // Thêm yêu cầu module java.sql


  opens com.example.cafemanagement.controller to javafx.fxml;
  exports com.example.cafemanagement;

  exports com.example.cafemanagement.controller;
  exports com.example.cafemanagement.service.staff;

  opens com.example.cafemanagement to javafx.fxml;
  exports com.example.cafemanagement.page.cashier;

  opens com.example.cafemanagement.page.admin to javafx.fxml;
  opens com.example.cafemanagement.page.cashier to javafx.fxml;
  opens com.example.cafemanagement.page.staff to javafx.fxml;

  // Export the package containing your application class
  exports com.example.cafemanagement.page.admin to javafx.graphics;
  exports com.example.cafemanagement.service;
  opens com.example.cafemanagement.service to javafx.fxml;
  exports com.example.cafemanagement.service.admin;
  opens com.example.cafemanagement.service.admin to javafx.fxml;
  opens com.example.cafemanagement.service.staff to javafx.fxml;
}