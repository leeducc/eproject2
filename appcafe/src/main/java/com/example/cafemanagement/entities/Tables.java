package com.example.cafemanagement.entities;

import com.example.cafemanagement.enummethod.StatusTable;

public class Tables {
  private int id;
  private String name;
  private StatusTable satatus;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public StatusTable getSatatus() {
    return satatus;
  }

  public void setSatatus(StatusTable satatus) {
    this.satatus = satatus;
  }
}
