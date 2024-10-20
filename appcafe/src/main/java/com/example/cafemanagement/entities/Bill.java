package com.example.cafemanagement.entities;

public class Bill {
  private int id;
  private String nameTable;
  private String productName;
  private int quantity;
  private double price;

  public Bill() {
  }

  // Constructor
  public Bill( String nameTable, String productName, int quantity, double price) {
    this.nameTable = nameTable;
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
  }

  public Bill(String nameTable, String productName, int quantity) {
    this.nameTable = nameTable;
    this.productName = productName;
    this.quantity = quantity;
  }

  // Getter and Setter methods
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNameTable() {
    return nameTable;
  }

  public void setNameTable(String nameTable) {
    this.nameTable = nameTable;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  // Method to calculate the total price
  public double calculateTotalPrice() {
    return quantity * price;
  }

  // toString method for easy printing
  @Override
  public String toString() {
    return "Bill [id=" + id + ", nameTable=" + nameTable + ", productName=" + productName +
        ", quantity=" + quantity + ", price=" + price + ", totalPrice=" + calculateTotalPrice() + "]";
  }
}

