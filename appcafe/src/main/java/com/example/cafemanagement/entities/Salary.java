package com.example.cafemanagement.entities;

import java.time.LocalDate;

public class Salary {

    private int id;
    private int staffId;
    private double dailyRate;
    private int daysAttended;
    private double bonus;
    private double deductions;
    private LocalDate paymentDate;
    private double totalSalary;

  public double getTotalSalary() {
    return totalSalary;
  }

  public void setTotalSalary(double totalSalary) {
    this.totalSalary = totalSalary;
  }

  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }

  public double getDeductions() {
    return deductions;
  }

  public void setDeductions(double deductions) {
    this.deductions = deductions;
  }

  public double getBonus() {
    return bonus;
  }

  public void setBonus(double bonus) {
    this.bonus = bonus;
  }

  public int getDaysAttended() {
    return daysAttended;
  }

  public void setDaysAttended(int daysAttended) {
    this.daysAttended = daysAttended;
  }

  public double getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(double dailyRate) {
    this.dailyRate = dailyRate;
  }

  public int getStaffId() {
    return staffId;
  }

  public void setStaffId(int staffId) {
    this.staffId = staffId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
