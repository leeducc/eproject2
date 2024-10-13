package com.example.cafemanagement.service.staff;

import com.example.cafemanagement.configJDBC.dao.JDBCConnect;
import com.example.cafemanagement.entities.Role;
import com.example.cafemanagement.entities.Staff;
import com.example.cafemanagement.enummethod.RoleStaff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
  public int getRoleByValue(String value) {
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

  public Staff getStaffByUserName(String userName){
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


}
