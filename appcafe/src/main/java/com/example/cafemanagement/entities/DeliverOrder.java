package com.example.cafemanagement.entities;

public class DeliverOrder {
  private int id;
  private int supplierId;

  public DeliverOrder() {
  }

  public DeliverOrder(int id, int supplierId) {
    this.id = id;
    this.supplierId = supplierId;
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
}
