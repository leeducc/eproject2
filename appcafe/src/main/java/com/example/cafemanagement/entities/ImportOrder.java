package com.example.cafemanagement.entities;

import java.time.LocalDateTime;

public class ImportOrder {
  private int id;
  private int supplierId;
  private LocalDateTime deliveryDate;
  private double totalValue;

  public ImportOrder(int id, int supplierId, LocalDateTime deliveryDate, double totalValue) {
    this.id = id;
    this.supplierId = supplierId;
    this.deliveryDate = deliveryDate;
    this.totalValue = totalValue;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(int supplierId) {
    this.supplierId = supplierId;
  }

  public LocalDateTime getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(LocalDateTime deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public double getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(double totalValue) {
    this.totalValue = totalValue;
  }
}
