package com.example.cafemanagement.entities;

public class DeliverOderDetail {
  private int Id;
  private int doId;
  private int supplyId;
  private int quantity;

  public DeliverOderDetail() {
  }

  public DeliverOderDetail(int id, int doId, int supplyId, int quantity) {
    Id = id;
    this.doId = doId;
    this.supplyId = supplyId;
    this.quantity = quantity;
  }

  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public int getDoId() {
    return doId;
  }

  public void setDoId(int doId) {
    this.doId = doId;
  }

  public int getSupplyId() {
    return supplyId;
  }

  public void setSupplyId(int supplyId) {
    this.supplyId = supplyId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
