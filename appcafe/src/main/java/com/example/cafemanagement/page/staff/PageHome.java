package com.example.cafemanagement.page.staff;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PageHome {

  public VBox viewHomePage(Button logoutButton) {
    Label welcomeLabel = new Label("Welcome to the Home Page!");
    VBox dashboardLayout = new VBox(10, welcomeLabel, logoutButton);
    dashboardLayout.setAlignment(Pos.CENTER);
    return dashboardLayout;
  }

}
