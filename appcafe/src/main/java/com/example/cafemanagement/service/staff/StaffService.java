package com.example.cafemanagement.service.staff;

import static com.example.cafemanagement.enummethod.RoleStaff.ADMIN;
import static com.example.cafemanagement.enummethod.RoleStaff.fromDisplayName;

import com.example.cafemanagement.configJDBC.dao.JDBCConnect;
import com.example.cafemanagement.entities.Role;
import com.example.cafemanagement.entities.Staff;
import com.example.cafemanagement.entities.StaffProperty;
import com.example.cafemanagement.enummethod.RoleStaff;
import com.google.protobuf.StringValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;

public class StaffService {

  public boolean createUser(Staff object) {
    String sql = "INSERT INTO staff (staff_id, password_hash, name, contact_number,role_id) VALUES (?, ?, ?, ?,?)";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setInt(1, object.getStartId());
      preparedStatement.setString(2, object.getPasswordHash()); // Hash password here
      preparedStatement.setString(3, object.getName());
      preparedStatement.setString(4, object.getContactNumber());
      preparedStatement.setInt(5, object.getRoleId());

      return preparedStatement.executeUpdate() > 0;

    } catch (SQLException e) {
      System.out.println("Error creating user: " + e.getMessage());
      return false;
    }
  }

  public List<Role> getAllRole() {
    List<Role> roles = new ArrayList<>();
    String sql = "SELECT * FROM role";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        Role role = new Role(
            resultSet.getInt("id"),
            RoleStaff.valueOf(resultSet.getString("role_name"))
        );
        roles.add(role);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return roles;
  }

  public static int getRoleByValue(String value) {
    int roleId = -1;
    String sql = "SELECT id FROM role WHERE role_name =?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, value);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        roleId = resultSet.getInt("id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return roleId;
  }

  public Staff getStaffByUserName(String userName) {
    Staff staff = null;
    String sql = "SELECT * FROM staff WHERE name =?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, userName);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        staff = new Staff(
            resultSet.getInt("staff_id"),
            resultSet.getString("password_hash"),
            resultSet.getString("name"),
            resultSet.getString("contact_number"),
            resultSet.getInt("role_id")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return staff;
  }

//  public List<Staff> getAllStaff(){
//    List<Staff> staffList = new ArrayList<>();
//    String sql = "SELECT * FROM staff";
//    try (Connection connection = JDBCConnect.getJDBCConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        ResultSet resultSet = preparedStatement.executeQuery()) {
//      while (resultSet.next()) {
//        Staff staff = new Staff(
//            resultSet.getInt("staff_id"),
//            resultSet.getString("password_hash"),
//            resultSet.getString("name"),
//            resultSet.getString("contact_number"),
//            resultSet.getInt("role_id")
//        );
//        staffList.add(staff);
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return staffList;
//  }

  public static List<StaffProperty> viewStaffProperties() {
    List<StaffProperty> staffProperties = new ArrayList<>();
    RoleStaff role = null;
    String sql = "SELECT s.id, s.staff_id, s.name, s.contact_number, r.role_name FROM staff s JOIN role r ON s.role_id = r.id";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        StaffProperty staffProperty = new StaffProperty(
            resultSet.getString("staff_id"),
            resultSet.getString("name"),
            resultSet.getString("contact_number"),
            resultSet.getString("role_name"),
            String.valueOf(resultSet.getInt("id"))
        );
        switch (staffProperty.getRoleStaff()) {
          case "ADMIN":
            staffProperty.setRoleStaff(role.ADMIN.getRoleValueStaff());
            break;
          case "CASHIER":
            staffProperty.setRoleStaff(role.CASHIER.getRoleValueStaff());
            break;
          case "SERVER":
            staffProperty.setRoleStaff(role.SERVER.getRoleValueStaff());
            break;
          default:
        }
        staffProperties.add(staffProperty);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return staffProperties;
  }

  public static boolean removeStaff(int staffId) {
    String sql = "DELETE FROM staff WHERE staff_id =?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, staffId);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean updateStaff(StaffProperty obj) {
    String sql = "UPDATE staff SET staff_id =? , name = ?, contact_number =?, role_id =? WHERE id =?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, obj.getStaffId());
      preparedStatement.setString(2, obj.getNameStaff());
      preparedStatement.setString(3, obj.getPhoneNumber());
      preparedStatement.setInt(4,
          StaffService.getRoleByValue(StaffService.getKeyRoleByValue(obj.getRoleStaff())));
      preparedStatement.setInt(5, Integer.parseInt(obj.getId()));
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static String getKeyRoleByValue(String roleNameString) {
    List<Role> roles = new ArrayList<>();
    String roleName = roleNameString;
    StaffService staffService = new StaffService();
    roles = staffService.getAllRole();
    for (Role role : roles) {
      RoleStaff currentRole = fromDisplayName(role.getRoleStaff().getRoleValueStaff());
      switch (roleNameString) {
        case "Quản Lý":
          roleName = "ADMIN";
          break;
        case "Thu Ngân":
          roleName = "CASHIER";
          break;
        case "Phục Vụ":
          roleName = "SERVER";
          break;
        default:
      }
    }
    return roleName;
  }

  public static ComboBox getRoleSelectionBox(StaffProperty obj) {
    List<Role> roles = new ArrayList<>();
    StaffService staffService = new StaffService();
    ComboBox<String> roleComboBox = new ComboBox<>();
    roles = staffService.getAllRole();
    for (Role role : roles) {
      RoleStaff currentRole = fromDisplayName(role.getRoleStaff().getRoleValueStaff());
      roleComboBox.getItems().add(currentRole.getRoleValueStaff());
      switch (obj.getRoleStaff()) {
        case "Quản Lý":
          roleComboBox.setValue(currentRole.ADMIN.getRoleValueStaff());
          break;
        case "Thu Ngân":
          roleComboBox.setValue(currentRole.CASHIER.getRoleValueStaff());
          break;
        case "Phục Vụ":
          roleComboBox.setValue(currentRole.SERVER.getRoleValueStaff());
          break;
        default:
      }
    }
    return roleComboBox;
  }
}


