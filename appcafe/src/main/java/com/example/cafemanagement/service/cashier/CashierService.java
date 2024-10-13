package com.example.cafemanagement.service.cashier;

import com.example.cafemanagement.configJDBC.dao.JDBCConnect;
import com.example.cafemanagement.entities.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Control;
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
}
