package com.example.cafemanagement.service;

import com.example.cafemanagement.configJDBC.dao.JDBCConnect;
import com.example.cafemanagement.entities.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffService {
  public boolean createUser(Staff object) {
    String sql = "INSERT INTO staff (staff_id, password_hash, name, contact_number) VALUES (?, ?, ?, ?)";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setInt(1, object.getStartId());
      preparedStatement.setString(2, object.getPasswordHash()); // Hash password here
      preparedStatement.setString(3, object.getName());
      preparedStatement.setString(4, object.getContactNumber());

      return preparedStatement.executeUpdate() > 0;

    } catch (SQLException e) {
      System.out.println("Error creating user: " + e.getMessage());
      return false;
    }
  }

}
