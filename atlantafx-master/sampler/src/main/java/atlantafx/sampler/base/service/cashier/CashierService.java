package atlantafx.sampler.base.service.cashier;

import atlantafx.sampler.base.entity.common.Category;
import atlantafx.sampler.base.entity.common.Discount;
import atlantafx.sampler.base.entity.common.Product;
import atlantafx.sampler.base.configJDBC.dao.JDBCConnect;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashierService {
  public static Map<Product, Integer> loadTemporaryOrderForTable(String tableName) {
    Map<Product, Integer> order = new HashMap<>();

    String query = "SELECT product_id, quantity FROM temporary_order WHERE table_name = ?";
    try (Connection conn = JDBCConnect.getJDBCConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, tableName);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        int productId = rs.getInt("product_id");
        int quantity = rs.getInt("quantity");

        Product product = loadProductById(productId); // Add a method to load product by ID
        if (product != null) {
          order.put(product, quantity);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return order;
  }
  public static Product loadProductById(int productId) {
    String query = "SELECT id, name, price, category_id FROM products WHERE id = ?";
    Product product = null;

    try (Connection conn = JDBCConnect.getJDBCConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, productId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        int categoryId = rs.getInt("category_id");

        product = new Product(id, name, price, categoryId);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return product;
  }

  public static void saveProductToTemporaryOrder(String tableName, Product product, int quantity) {
    String query = """
            INSERT INTO temporary_order (table_name, product_id, quantity)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
        """;

    try (Connection conn = JDBCConnect.getJDBCConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, tableName);
      stmt.setInt(2, product.getId());
      stmt.setInt(3, quantity);
      stmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void removeProductFromTemporaryOrder(String tableName, Product product) {
    String query = "DELETE FROM temporary_order WHERE table_name = ? AND product_id = ?";

    try (Connection conn = JDBCConnect.getJDBCConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, tableName);
      stmt.setInt(2, product.getId());
      stmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

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


  // Fetch all products from the database
  public List<Product> getAllProducts() {
    List<Product> products = new ArrayList<>();
    String sql = "SELECT id, image_link, name, price, category_id FROM products";

    try (Connection connection = JDBCConnect.getJDBCConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {

      while (resultSet.next()) {
        Product product = new Product(
                resultSet.getInt("id"),
                resultSet.getString("image_link"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getInt("category_id")
        );
        products.add(product);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return products;
  }

  // Add a new product to the database
  public boolean addProduct(Product product) {
    String sql = "INSERT INTO products (image_link, name, price, category_id) VALUES (?, ?, ?, ?)";

    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setString(1, product.getImageLink());
      preparedStatement.setString(2, product.getName());
      preparedStatement.setBigDecimal(3, BigDecimal.valueOf(product.getPrice()));
      preparedStatement.setInt(4, product.getCategoryId());

      int rowsAffected = preparedStatement.executeUpdate();
      return rowsAffected > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Update an existing product in the database
  public boolean updateProduct(Product product) {
    String sql = "UPDATE products SET image_link = ?, name = ?, price = ?, category_id = ? WHERE id = ?";

    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setString(1, product.getImageLink());
      preparedStatement.setString(2, product.getName());
      preparedStatement.setBigDecimal(3, BigDecimal.valueOf(product.getPrice()));
      preparedStatement.setInt(4, product.getCategoryId());
      preparedStatement.setInt(5, product.getId());

      int rowsAffected = preparedStatement.executeUpdate();
      return rowsAffected > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Delete a product from the database
  public boolean deleteProduct(int productId) {
    String sql = "DELETE FROM products WHERE id = ?";

    try (Connection connection = JDBCConnect.getJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      preparedStatement.setInt(1, productId);
      int rowsAffected = preparedStatement.executeUpdate();
      return rowsAffected > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}
