package atlantafx.sampler.base.service.cashier;

import atlantafx.sampler.base.entity.common.Category;
import atlantafx.sampler.base.entity.common.Discount;
import atlantafx.sampler.base.entity.common.Product;
import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CashierService {


  public static List<Category> loadCategories() {
    List<Category> categories = new ArrayList<>();
    String sql = "SELECT * FROM category";

    try (Connection connection = JDBCConnect.getJDBCConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {

      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        categories.add(new Category(id, name));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return categories;
  }

  public static List<Product> loadProducts() {
    List<Product> products = new ArrayList<>();
    String sql = "SELECT p.*, d.discount_percentage " +
            "FROM products p " +
            "LEFT JOIN discount d ON p.id = d.product_id " +
            "AND CURDATE() BETWEEN d.start_date AND d.end_date";

    try (Connection connection = JDBCConnect.getJDBCConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {

      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String imageLink = resultSet.getString("image_link");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        double discountPercentage = resultSet.getDouble("discount_percentage");

        // Apply discount if available
        double discountedPrice = price;
        if (discountPercentage > 0) {
          discountedPrice = price * (1 - discountPercentage / 100);
        }

        int categoryId = resultSet.getInt("category_id");
        products.add(new Product(id, imageLink, name, price, discountedPrice, categoryId));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return products;
  }

}
