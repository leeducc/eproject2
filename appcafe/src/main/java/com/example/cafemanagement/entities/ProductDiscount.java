package com.example.cafemanagement.entities;

import java.time.LocalDateTime;

public class ProductDiscount {
  private int id;                      // AI PK
  private int productId;               // product_id
  private double discount;              // discount
  private LocalDateTime discountStart; // discount_start
  private LocalDateTime discountEnd;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
  }

  public LocalDateTime getDiscountStart() {
    return discountStart;
  }

  public void setDiscountStart(LocalDateTime discountStart) {
    this.discountStart = discountStart;
  }

  public LocalDateTime getDiscountEnd() {
    return discountEnd;
  }

  public void setDiscountEnd(LocalDateTime discountEnd) {
    this.discountEnd = discountEnd;
  }
}
