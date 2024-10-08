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

  opens com.example.cafemanagemnet to javafx.fxml;
  exports com.example.cafemanagemnet;
  exports com.example.cafemanagemnet.page;
  opens com.example.cafemanagemnet.page to javafx.fxml;
}