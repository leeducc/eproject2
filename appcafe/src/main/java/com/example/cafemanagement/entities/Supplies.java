package com.example.cafemanagement.entities;

public class Supplies {
  private int id;
  private String supplyCode;
  private String name;
  private String unit;
  private double price;
  private  int quantity;
  private int supplierId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSupplyCode() {
    return supplyCode;
  }

  public void setSupplyCode(String supplyCode) {
    this.supplyCode = supplyCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(int supplierId) {
    this.supplierId = supplierId;
  }
}
