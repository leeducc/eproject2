module com.example.cafemanagemnet {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires net.synedra.validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires eu.hansolo.tilesfx;
  requires com.almasb.fxgl.all;
  requires java.desktop;
  requires java.sql;

  opens com.example.cafemanagement to javafx.fxml;
  exports com.example.cafemanagement;
  exports com.example.cafemanagement.page.cashier;

  opens com.example.cafemanagement.page.admin to javafx.fxml;
  opens com.example.cafemanagement.page.cashier to javafx.fxml;
  opens com.example.cafemanagement.page.staff to javafx.fxml;

  // Export the package containing your application class
  exports com.example.cafemanagement.page.admin to javafx.graphics;
  opens com.example.cafemanagement.controller to javafx.fxml;
  exports com.example.cafemanagement.controller;
  exports com.example.cafemanagement.service;
  opens com.example.cafemanagement.service to javafx.fxml;
}