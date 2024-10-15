package com.example.cafemanagement.entities;

public class Staff {
  private int id;
  private int startId;
  private String passwordHash;
  private String name;
  private String contactNumber;
  private int roleId;

  public Staff(int staffId, String password, String name, String contactNumber, int roleId) {
    this.startId = staffId;
    this.passwordHash = password;
    this.name = name;
    this.contactNumber = contactNumber;
    this.roleId = roleId;
  }

  public Staff(int startId, String name, int roleId) {
    this.startId = startId;
    this.name = name;
    this.roleId = roleId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStartId() {
    return startId;
  }

  public void setStartId(int startId) {
    this.startId = startId;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }
}
