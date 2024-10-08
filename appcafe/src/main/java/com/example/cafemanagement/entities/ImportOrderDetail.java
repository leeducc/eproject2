package com.example.cafemanagement.entities;

import java.time.LocalDateTime;

public class ImportOrderDetail {
  private int id;                  // AI PK
  private int supplierId;          // supplier_id
  private LocalDateTime deliveryDate; // delivery_date
  private double totalValue;

  public ImportOrderDetail(int id, int supplierId, LocalDateTime deliveryDate, double totalValue) {
    this.id = id;
    this.supplierId = supplierId;
    this.deliveryDate = deliveryDate;
    this.totalValue = totalValue;
  }

  public double getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(double totalValue) {
    this.totalValue = totalValue;
  }

  public LocalDateTime getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(LocalDateTime deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public int getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(int supplierId) {
    this.supplierId = supplierId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
