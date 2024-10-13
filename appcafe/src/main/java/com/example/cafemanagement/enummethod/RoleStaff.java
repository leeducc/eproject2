package com.example.cafemanagement.enummethod;

public enum RoleStaff {
  ADMIN("Quản Lý"),
  CASHIER("Thu Ngân"),
  SERVER("Phục Vụ");

  private final String roleStaff;

  RoleStaff(String roleStaff) {
    this.roleStaff = roleStaff;
  }

  public String getRoleValueStaff() {
    return roleStaff;
  }

  // Phương thức để lấy enum từ tên hiển thị
  public static RoleStaff fromDisplayName(String displayName) {
    for (RoleStaff role : RoleStaff.values()) {
      if (role.roleStaff.equals(displayName)) {
        return role;
      }
    }
    throw new IllegalArgumentException("Không tìm thấy vai trò với tên hiển thị: " + displayName);
  }


}
