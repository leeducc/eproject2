package com.example.cafemanagement.service;

import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class PageLoginService {
  private Scene dashboardScene;
  private Scene dashboardSceneCreate;

  public Scene getDashboardScene() {
    return dashboardScene;
  }

  public void setDashboardScene(Scene dashboardScene) {
    this.dashboardScene = dashboardScene;
  }

  public Scene getDashboardSceneCreate() {
    return dashboardSceneCreate;
  }

  public void setDashboardSceneCreate(Scene dashboardSceneCreate) {
    this.dashboardSceneCreate = dashboardSceneCreate;
  }

  public ImageView createLogo() {
    ImageView logo = null;
    try {
      logo = new ImageView(new Image(
          Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm()));
      logo.setFitWidth(150);
      logo.setPreserveRatio(true);
    } catch (Exception e) {
      System.out.println("Logo image not found!");
    }
    return logo;
  }
  public ToggleGroup createRoleGroup() {
    ToggleGroup roleGroup = new ToggleGroup();
    RadioButton rbPhucVu = new RadioButton("Phục Vụ");
    RadioButton rbThuNgan = new RadioButton("Thu Ngân");
    RadioButton rbAdmin = new RadioButton("Quản Lý");

    rbPhucVu.setToggleGroup(roleGroup);
    rbThuNgan.setToggleGroup(roleGroup);
    rbAdmin.setToggleGroup(roleGroup);

    rbPhucVu.setSelected(true);  // Default selection
    return roleGroup;
  }

  public HBox createRoleSelectionBox(ToggleGroup roleGroup) {
    RadioButton rbPhucVu = (RadioButton) roleGroup.getToggles().get(0);
    RadioButton rbThuNgan = (RadioButton) roleGroup.getToggles().get(1);
    RadioButton rbAdmin = (RadioButton) roleGroup.getToggles().get(2);
    HBox roleSelectionBox = new HBox(10, rbPhucVu, rbThuNgan, rbAdmin);
    roleSelectionBox.setAlignment(Pos.CENTER);
    return roleSelectionBox;
  }
}
