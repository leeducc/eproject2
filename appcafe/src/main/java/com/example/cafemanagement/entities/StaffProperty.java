package com.example.cafemanagement.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StaffProperty {
    private final StringProperty staffId;
    private final StringProperty nameStaff;
    private final StringProperty phoneNumber;


  private  StringProperty roleStaff;

  public StaffProperty(String staffId, String nameStaff, String phoneNumber,
      String roleStaff) {
    this.staffId = new SimpleStringProperty(staffId);

    this.nameStaff = new SimpleStringProperty(nameStaff);
    this.phoneNumber =new SimpleStringProperty(phoneNumber);
    this.roleStaff =new SimpleStringProperty(roleStaff);
  }

  public String getStaffId() {
    return staffId.get();
  }

  public StringProperty staffIdProperty() {
    return staffId;
  }

  public String getNameStaff() {
    return nameStaff.get();
  }

  public StringProperty nameStaffProperty() {
    return nameStaff;
  }

  public String getPhoneNumber() {
    return phoneNumber.get();
  }

  public StringProperty phoneNumberProperty() {
    return phoneNumber;
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
// Getters and setters


}
