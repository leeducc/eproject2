package com.example.cafemanagement.service.admin;

import static com.example.cafemanagement.enummethod.RoleStaff.fromDisplayName;

import com.example.cafemanagement.entities.PaymentMethod;
import com.example.cafemanagement.entities.Role;
import com.example.cafemanagement.entities.StaffProperty;
import com.example.cafemanagement.enummethod.Payment;
import com.example.cafemanagement.enummethod.RoleStaff;
import com.example.cafemanagement.service.staff.StaffService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class PageLoginService {

  private Scene dashboardSceneCoffeeMenu;
  private Scene dashboardScene;
  private Scene dashboardSceneCreate;
  private Scene dashboardVBoxServiceTableOrder;
  private Scene dashboardSceneHr;
  private Scene dashboardVBoxServiceTableOrderDetail;
  private Scene dashboardVBoxServiceTableOrderForCashier;

  public Scene getDashboardVBoxServiceTableOrderForCashier() {
    return dashboardVBoxServiceTableOrderForCashier;
  }

  public void setDashboardVBoxServiceTableOrderForCashier(
      Scene dashboardVBoxServiceTableOrderForCashier) {
    this.dashboardVBoxServiceTableOrderForCashier = dashboardVBoxServiceTableOrderForCashier;
  }

  public Scene getDashboardVBoxServiceTableOrderDetail() {
    return dashboardVBoxServiceTableOrderDetail;
  }

  public void setDashboardVBoxServiceTableOrderDetail(
      Scene dashboardVBoxServiceTableOrderDetail) {
    this.dashboardVBoxServiceTableOrderDetail = dashboardVBoxServiceTableOrderDetail;
  }

  public Scene getDashboardSceneCoffeeMenu() {
    return dashboardSceneCoffeeMenu;
  }

  public void setDashboardSceneCoffeeMenu(Scene dashboardSceneCoffeeMenu) {
    this.dashboardSceneCoffeeMenu = dashboardSceneCoffeeMenu;
  }


  public Scene getDashboardVBoxServiceTableOrder() {
    return dashboardVBoxServiceTableOrder;
  }

  public void setDashboardVBoxServiceTableOrder(Scene dashboardVBoxServiceTableOrder) {
    this.dashboardVBoxServiceTableOrder = dashboardVBoxServiceTableOrder;
  }

  public Scene getDashboardSceneHr() {
    return dashboardSceneHr;
  }

  public void setDashboardSceneHr(Scene dashboardSceneHr) {
    this.dashboardSceneHr = dashboardSceneHr;
  }

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

  public ComboBox createRoleSelectionBox() {
    List<Role> roles = new ArrayList<>();
    StaffService staffService = new StaffService();
    ComboBox<String> roleComboBox = new ComboBox<>();
    roles = staffService.getAllRole();
    for (Role role : roles) {
      RoleStaff currentRole = fromDisplayName(role.getRoleStaff().getRoleValueStaff());
      roleComboBox.getItems().add(currentRole.getRoleValueStaff());
      roleComboBox.setValue(currentRole.getRoleValueStaff());
    }
    return roleComboBox;
  }
  public ComboBox createPayMethodSelectionBox() {
    List<PaymentMethod> Payments = new ArrayList<>();
    StaffService staffService = new StaffService();
    ComboBox<String> payComboBox = new ComboBox<>();
    Payments = staffService.getAllPayMethod();
    for (PaymentMethod Payment : Payments) {
      Payment currentMethod = com.example.cafemanagement.enummethod.Payment.fromDisplayName(Payment.getMethod().getStatus());
      payComboBox.getItems().add(currentMethod.getStatus());
      payComboBox.setValue(currentMethod.getStatus());
    }
    return payComboBox;
  }

}
