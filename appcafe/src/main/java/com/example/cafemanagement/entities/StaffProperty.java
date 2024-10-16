package com.example.cafemanagement.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StaffProperty {
  private StringProperty id;
  private StringProperty staffId;
  private StringProperty nameStaff;
  private StringProperty phoneNumber;
  private StringProperty roleStaff;



  public StaffProperty(String staffId, String nameStaff, String phoneNumber,
      String roleStaff, String id) {
    this.staffId = new SimpleStringProperty(staffId);
    this.roleStaff = new SimpleStringProperty(roleStaff);
    this.nameStaff = new SimpleStringProperty(nameStaff);
    this.phoneNumber = new SimpleStringProperty(phoneNumber);
    this.id = new SimpleStringProperty(id);
  }

  public String getId() {
    return id.get();
  }

  public StringProperty idProperty() {
    return id;
  }

  public void setId(String id) {
    this.id.set(id);
  }

  public String getRoleStaff() {
    return roleStaff.get();
  }

  public StringProperty roleStaffProperty() {
    return roleStaff;
  }

  public void setRoleStaff(String roleStaff) {
    this.roleStaff.set(roleStaff);
  }

  public String getPhoneNumber() {
    return phoneNumber.get();
  }

  public StringProperty phoneNumberProperty() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber.set(phoneNumber);
  }

  public String getNameStaff() {
    return nameStaff.get();
  }

  public StringProperty nameStaffProperty() {
    return nameStaff;
  }

  public void setNameStaff(String nameStaff) {
    this.nameStaff.set(nameStaff);
  }

  public String getStaffId() {
    return staffId.get();
  }

  public StringProperty staffIdProperty() {
    return staffId;
  }

  public void setStaffId(String staffId) {
    this.staffId.set(staffId);
  }


}
