package atlantafx.sampler.base.service.cashier;


import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;
import atlantafx.sampler.base.entity.common.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableCoffeeService {

  private static List<Tables> tablesList = new ArrayList<Tables>();

  public static List<Tables> getAllTable() {
    List<Tables> tables = new ArrayList<>();
    String sql = "SELECT * FROM tables";
    try (Connection connection = JDBCConnect.getJDBCConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
        sql); ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {

        Tables table = new Tables(resultSet.getString("name"),
            resultSet.getInt("status_id"));
        tables.add(table);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tables;
  }
  public static boolean updateStatusTable(int status, String tableName){
    String sql = "UPDATE tables SET status_id = ? WHERE name =?";
    try (Connection connection = JDBCConnect.getJDBCConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, status);
      preparedStatement.setString(2, tableName);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  public static Tables getTableByName(String tableName) {
    Tables table = null;
    String sql = "SELECT * FROM tables WHERE name =?";
    try (Connection connection = JDBCConnect.getJDBCConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, tableName);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        table = new Tables(resultSet.getString("name"), resultSet.getInt("status_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return table;
  }

  public static ArrayList<String> getNameTable(int number) {
    tablesList = TableCoffeeService.getAllTable();
    ArrayList<String> tableNames = new ArrayList<>();
    if (tablesList.size() > 12) {
      int index = 12;
      switch (number) {
        case 1:
          for (int i = 0; i < index; i++) {
            tableNames.add(tablesList.get(i).getName());
          }
          break;

        case 2:
          for (int i = 12; i < tablesList.size(); i++) { // Fixed
            tableNames.add(tablesList.get(i).getName());
          }
          break;

        default:
          // Optionally handle other cases or do nothing
          break;
      }
    } else {
      int index = tablesList.size();
      if (number == 1) {
        for (int i = 0; i < index; i++) {
          tableNames.add(tablesList.get(i).getName());
        }
      }
    }
    return tableNames;  // Now returning the List
  }

}
