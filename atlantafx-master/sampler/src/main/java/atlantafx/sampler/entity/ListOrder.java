package atlantafx.sampler.entity;

public class ListOrder {
    private String orderNumber;
    private String supplierId;
    private String orderDate;

    public ListOrder(String orderNumber, String supplierId, String orderDate) {
        this.orderNumber = orderNumber;
        this.supplierId = supplierId;
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
