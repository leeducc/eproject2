package com.example.cafemanagement.entities;

import com.example.cafemanagement.enummethod.StatusTable;

public class TableStatus {
  private int tableId;
  private StatusTable satatus;

  public TableStatus(int tableId, StatusTable satatus) {
    this.tableId = tableId;
    this.satatus = satatus;
  }

  public StatusTable getSatatus() {
    return satatus;
  }

  public void setSatatus(StatusTable satatus) {
    this.satatus = satatus;
  }

  public int getTableId() {
    return tableId;
  }

  public void setTableId(int tableId) {
    this.tableId = tableId;
  }
}
