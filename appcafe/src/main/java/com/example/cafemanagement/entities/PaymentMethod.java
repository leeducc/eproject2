package com.example.cafemanagement.entities;

import com.example.cafemanagement.enummethod.Payment;

public class PaymentMethod {
  private int id;                      // AI PK
  private Payment method;

  public int getId() {
    return id;
  }

  public PaymentMethod(int id, Payment method) {
    this.id = id;
    this.method = method;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Payment getMethod() {
    return method;
  }

  public void setMethod(Payment method) {
    this.method = method;
  }
}

