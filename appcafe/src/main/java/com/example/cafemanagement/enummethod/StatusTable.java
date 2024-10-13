package com.example.cafemanagement.enummethod;

import java.util.Arrays;

public enum StatusTable {
  AVAILABLE("Có Chỗ"),
  CLEANING("Đang dọn"),
  OCCUPIED("Hết Chỗ");
  private String status;

  StatusTable(String status) {
    this.status = status;
  }
//  public static void main(String [] args) {
//    System.out.println(StatusTable.AVAILABLE.status);
//    System.out.println(StatusTable.CLEANING.status);
//    System.out.println(StatusTable.OCCUPIED.status);
//  }
}


