package com.example.cafemanagement.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
  private int id;
  private int staffId;
  private LocalDateTime checkIn;
  private LocalDateTime checkOut;
  private LocalDate day;
  private String shift;

  public Attendance() {
  }

  public Attendance(int id, int staffId, LocalDateTime checkIn, LocalDateTime checkOut,
      LocalDate day,
      String shift) {
    this.id = id;
    this.staffId = staffId;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.day = day;
    this.shift = shift;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStaffId() {
    return staffId;
  }

  public void setStaffId(int staffId) {
    this.staffId = staffId;
  }

  public LocalDateTime getCheckIn() {
    return checkIn;
  }

  public void setCheckIn(LocalDateTime checkIn) {
    this.checkIn = checkIn;
  }

  public LocalDateTime getCheckOut() {
    return checkOut;
  }

  public void setCheckOut(LocalDateTime checkOut) {
    this.checkOut = checkOut;
  }

  public LocalDate getDay() {
    return day;
  }

  public void setDay(LocalDate day) {
    this.day = day;
  }

  public String getShift() {
    return shift;
  }

  public void setShift(String shift) {
    this.shift = shift;
  }
}
