package atlantafx.sampler.base.entity.common;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Discount {
    private int id; // Corresponds to the 'id' column
    private int productId; // Corresponds to the 'product_id' column
    private String discountName; // Corresponds to the 'discount_name' column
    private BigDecimal discountPercentage; // Corresponds to the 'discount_percentage' column
    private LocalDate startDate; // Corresponds to the 'start_date' column
    private LocalDate endDate; // Corresponds to the 'end_date' column

    // Constructor
    public Discount(int id, int productId, String discountName, BigDecimal discountPercentage,
                    LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.productId = productId;
        this.discountName = discountName;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isValid() {
        LocalDate today = LocalDate.now();
        return (today.isEqual(startDate) || today.isAfter(startDate)) &&
                (today.isEqual(endDate) || today.isBefore(endDate));
    }


    // Getters and Setters
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

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
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
}
