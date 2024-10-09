package com.example.cafemanagement.entities;

import com.example.cafemanagement.enummethod.RoleStaff;

public class Role {
  private int id;
  private RoleStaff RoleStaff;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public RoleStaff getRoleStaff() {
    return RoleStaff;
  }

  public void setRoleStaff(RoleStaff roleStaff) {
    RoleStaff = roleStaff;
  }
}
