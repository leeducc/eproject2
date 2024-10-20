package com.example.cafemanagement.service.cashier;

import com.example.cafemanagement.configJDBC.dao.JDBCConnect;
import com.example.cafemanagement.entities.Bill;
import com.example.cafemanagement.entities.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class CashierService {
  public static List<Products> getAllProducts() {
    List<Products> products = new ArrayList<>();
    String sql = "SELECT * FROM products";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        Products product = new Products(resultSet.getString("image_link"),
            resultSet.getString("category"),
            resultSet.getString("name"),
            resultSet.getDouble("price")
        );
        products.add(product);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return products;
  }
  public static ListView<String> InitializeProductName() {
    List<Products> listProducts = CashierService.getAllProducts();
    ListView<String> productNames = new ListView<>();
    for (Products product : listProducts) {
      productNames.getItems().add(product.getName());
    }
    return productNames;  // Now returning the ListView
  }
  public static ObservableList<String> InitializeProductName1() {
    ObservableList<String> products = FXCollections.observableArrayList();
    List<Products> listProducts = CashierService.getAllProducts();
    for (Products product : listProducts) {
      products.add(product.getName());
    }
    return products;
  }

  public static ObservableList<String> InitializeProductNameByKey(String key) {
    ObservableList<String> products = FXCollections.observableArrayList();
    List<Products> listProducts = CashierService.getProductsByKey(key);
    for (Products product : listProducts) {
      products.add(product.getName());
    }
    return products;
  }

  public static ObservableList<String> InitializeProductNameCategory(String category) {
    ObservableList<String> products = FXCollections.observableArrayList();
    List<Products> listProducts = CashierService.getProductsByCategory(category);
    for (Products product : listProducts) {
      products.add(product.getName());
    }
    return products;
  }
  public static Products getProductsByProductName(String nameProduct) {
    Products product = null;
    String sql = "SELECT * FROM products WHERE name =?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, nameProduct);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        product = new Products(resultSet.getString("image_link"),
            resultSet.getString("category"),
            resultSet.getString("name"),
            resultSet.getDouble("price")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return product;
  }
  public static double getPriceByName(String name) {
    Products product = getProductsByProductName(name);
    return product.getPrice();
  }
  public static boolean addOrderBill(Bill bill) {
    String sql = " INSERT INTO bill(nameTable,productName,quantity,price)VALUES(?,?,?,?)";
    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, bill.getNameTable()); // Replace with the actual table name
      preparedStatement.setString(2, bill.getProductName()); // Replace with the actual product name
      preparedStatement.setInt(3, bill.getQuantity()); // Replace with the actual quantity
      preparedStatement.setDouble(4, bill.getPrice()); // Replace with the actual price
      return preparedStatement.executeUpdate() > 0;
      } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  public static Bill getOrderBillByNameProduct(String nameTable, String productName) {
    Bill bill = new Bill();
    String sql = "SELECT * FROM bill WHERE nameTable =? AND productName =?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, nameTable); // Replace with the actual table name
      preparedStatement.setString(2, productName); // Replace with the actual product name
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        bill = new Bill(resultSet.getString("nameTable"),
            resultSet.getString("productName"),
            resultSet.getInt("quantity"),
            resultSet.getDouble("price")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bill;

  }
  public static boolean updateOrderBill(Bill bill){
    String sql = "UPDATE bill SET quantity =? WHERE nameTable = ? AND productName = ?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, bill.getQuantity()); // Replace with the actual quantity
      preparedStatement.setString(2, bill.getNameTable()); // Replace with the actual table name
      preparedStatement.setString(3, bill.getProductName()); // Replace with the actual product name
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  public static Bill findOrderBillIsExist(String  tableName, String productName){
    Bill bill = null;
    String sql = "SELECT * FROM bill WHERE productName =? AND nameTable =?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, productName); // Replace with the actual product name
      preparedStatement.setString(2, tableName); // Replace with the actual table name
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        bill = new Bill(resultSet.getString("nameTable"),
            resultSet.getString("productName"),
            resultSet.getInt("quantity"),
            resultSet.getDouble("price")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bill;
  }

  public static List<Bill> getBillByNameTable(String tableName) {
    List<Bill> bills = new ArrayList<>(); // Khởi tạo danh sách Bill
    String sql = "SELECT * FROM bill WHERE nameTable = ?"; // Truy vấn với nameTable

    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      // Đặt giá trị tham số cho truy vấn
      preparedStatement.setString(1, tableName);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        // Vòng lặp qua các kết quả của ResultSet
        while (resultSet.next()) {
          // Khởi tạo đối tượng Bill từ kết quả truy vấn
          Bill bill = new Bill(// Lấy giá trị id
              resultSet.getString("nameTable"),     // Lấy giá trị nameTable
              resultSet.getString("productName"),   // Lấy giá trị productName
              resultSet.getInt("quantity"),         // Lấy giá trị quantity
              resultSet.getDouble("price")          // Lấy giá trị price
          );
          // Thêm Bill vào danh sách
          bills.add(bill);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bills; // Trả về danh sách Bill
  }
  public static void removeOrderBill(Bill bill) {
    String sql = "DELETE FROM bill WHERE nameTable = ? AND productName = ? AND quantity = ?";

    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      // Thiết lập các tham số cho câu lệnh xóa
      preparedStatement.setString(1, bill.getNameTable());
      preparedStatement.setString(2, bill.getProductName());
      preparedStatement.setInt(3, bill.getQuantity());

      // Thực thi câu lệnh xóa
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Hóa đơn đã được xóa thành công.");
      } else {
        System.out.println("Không tìm thấy hóa đơn để xóa.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Lỗi khi xóa hóa đơn: " + e.getMessage());
    }
  }
  public static List<String> getAllCategoriesProduct(){
    List<String> categories = new ArrayList<>();
    String sql = "SELECT DISTINCT category FROM products";
    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        categories.add(resultSet.getString("category"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return categories;
  }
  public static ComboBox createPayCategoriesSelectionBox() {
    List<String> categories = new ArrayList<>();
    ComboBox<String> ctComboBox = new ComboBox<>();
    categories = CashierService.getAllCategoriesProduct();
    for (String category : categories) {
      ctComboBox.getItems().add(category);
    }
    return ctComboBox;
  }

  public static List<Products> getProductsByCategory(String category) {
    List<Products> products = new ArrayList<>();
    String sql = "SELECT * FROM products WHERE category =?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, category);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Products product = new Products(resultSet.getString("image_link"),
            resultSet.getString("category"),
            resultSet.getString("name"),
            resultSet.getDouble("price")
        );
        products.add(product);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return products;
  }
  public static List<Products> getProductsByKey(String key) {
    List<Products> products = new ArrayList<>();
    String sql = "SELECT * FROM products WHERE name LIKE?";
    try (Connection connection = JDBCConnect.getJDBCConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, "%" + key + "%");
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Products product = new Products(resultSet.getString("image_link"),
            resultSet.getString("category"),
            resultSet.getString("name"),
            resultSet.getDouble("price")
        );
        products.add(product);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return products;
  }
}
