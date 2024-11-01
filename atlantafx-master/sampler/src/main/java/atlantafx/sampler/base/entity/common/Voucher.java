package atlantafx.sampler.base.entity.common;

import java.time.LocalDate;

public class Voucher {
  private int id;
  private String voucherCode;
  private String voucherName;
  private int voucherPercentage;
  private LocalDate startDate;
  private LocalDate endDate;
  private int statusId;

  public enum Status {
    ACTIVE, INACTIVE, USED
  }

  public Voucher(int id, String voucherCode, String voucherName, int voucherPercentage, LocalDate startDate, LocalDate endDate, int statusId) {
    this.id = id;
    this.voucherCode = voucherCode;
    this.voucherName = voucherName;
    this.voucherPercentage = voucherPercentage;
    this.startDate = startDate;
    this.endDate = endDate;
    this.statusId = statusId;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getVoucherCode() {
    return voucherCode;
  }

  public void setVoucherCode(String voucherCode) {
    this.voucherCode = voucherCode;
  }

  public String getVoucherName() {
    return voucherName;
  }

  public void setVoucherName(String voucherName) {
    this.voucherName = voucherName;
  }

  public int getVoucherPercentage() {
    return voucherPercentage;
  }

  public void setVoucherPercentage(int voucherPercentage) {
    this.voucherPercentage = voucherPercentage;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public int getStatus() {
    return statusId;
  }

  public void setStatus(int statusId) {
    this.statusId = statusId;
  }

  @Override
  public String toString() {
    return "Voucher{" +
        "id=" + id +
        ", voucherCode='" + voucherCode + '\'' +
        ", voucherName='" + voucherName + '\'' +
        ", voucherPercentage=" + voucherPercentage +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", status=" + statusId +
        '}';
  }
}