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


  public List<Product> getProductsByCategory(int categoryId) {
    List<Product> products = new ArrayList<>();
    String query = "SELECT id, name, price, category_id, image_link FROM products WHERE category_id = ?"; // Adjust the SQL query as per your database structure

    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setInt(1, categoryId); // Set the category ID in the query
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        String imageLink = resultSet.getString("image_link");
        // Assuming Product constructor: Product(int id, String name, double price, int categoryId, String imageLink)
        products.add(new Product(id, name, price, categoryId, imageLink));
      }
    } catch (Exception e) {
      e.printStackTrace(); // Handle exceptions properly in production code
    }

    return products;
  }
  public List<Category> getAllCategories() {
    List<Category> categories = new ArrayList<>();
    String query = "SELECT id, name FROM category"; // Adjust as necessary

    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        categories.add(new Category(id, name));
      }
    } catch (Exception e) {
      e.printStackTrace(); // Handle exceptions properly in production code
    }

    return categories;
  }
  public List<Product> getAllProducts() {
    List<Product> products = new ArrayList<>();
    String query = "SELECT * FROM products";

    try (Connection conn = JDBCConnect.getJDBCConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        String imageLink = rs.getString("image_link");
        int categoryId = rs.getInt("category_id");

        products.add(new Product(id, name, price, imageLink, categoryId));
      }
    } catch (SQLException e) {
      e.printStackTrace(); // Log the exception
    }
    return products;
  }

  public List<String> getAllCategoryNames() {
    return getAllCategories().stream()
            .map(Category::getName)
            .toList(); // Collect names into a list
  }


  public static Discount getValidDiscountForProduct(int productId) {
    String query = "SELECT id, product_id, discount_name, discount_percentage, start_date, end_date " +
            "FROM discount " +
            "WHERE product_id = ? " +
            "AND CURDATE() BETWEEN start_date AND end_date";

    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setInt(1, productId);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return new Discount(
                resultSet.getInt("id"),
                resultSet.getInt("product_id"),
                resultSet.getString("discount_name"),
                resultSet.getBigDecimal("discount_percentage"),
                resultSet.getDate("start_date").toLocalDate(),
                resultSet.getDate("end_date").toLocalDate()
        );
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null; // No valid discount found
  }


}
