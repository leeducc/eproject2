package com.example.cafemanagement.entities;

import java.time.LocalDateTime;

public class Order {
  private int id;                      // AI PK
  private int tableId;                 // table_id
  private LocalDateTime date;          // date
  private int voucherId;               // voucher_id
  private double totalValue;            // total_value
  private int paymentMethodId;

  public Order(int id, int tableId, LocalDateTime date, int voucherId, double totalValue,
      int paymentMethodId) {
    this.id = id;
    this.tableId = tableId;
    this.date = date;
    this.voucherId = voucherId;
    this.totalValue = totalValue;
    this.paymentMethodId = paymentMethodId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTableId() {
    return tableId;
  }

  public void setTableId(int tableId) {
    this.tableId = tableId;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public int getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(int voucherId) {
    this.voucherId = voucherId;
  }

  public double getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(double totalValue) {
    this.totalValue = totalValue;
  }

  public int getPaymentMethodId() {
    return paymentMethodId;
  }

  public void setPaymentMethodId(int paymentMethodId) {
    this.paymentMethodId = paymentMethodId;
  }
}
