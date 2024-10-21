package atlantafx.sampler.admin.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderItem {
    private static SimpleStringProperty supplyCode;
    private SimpleStringProperty itemName;
    private SimpleStringProperty supplierName;
    private SimpleIntegerProperty quantity; // Change to SimpleIntegerProperty
    private SimpleStringProperty unit;
    private SimpleDoubleProperty price; // Change to SimpleDoubleProperty
    private SimpleDoubleProperty totalValue; // Change to SimpleDoubleProperty

    // Constructor không tham số
    public OrderItem() {
        this.supplyCode = new SimpleStringProperty();
        this.itemName = new SimpleStringProperty();
        this.supplierName = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty(1); // Khởi tạo số lượng mặc định là 1
        this.price = new SimpleDoubleProperty();
        this.totalValue = new SimpleDoubleProperty();
    }

    // Constructor có tham số
    public OrderItem(String supplyCode, double price) {
        this();
        setSupplyCode(supplyCode);
        setPrice(price);
    }

    // Getter và Setter cho các thuộc tính
    public static String getSupplyCode() {
        return supplyCode.get();
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode.set(supplyCode);
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getSupplierName() {
        return supplierName.get();
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        updateTotalValue(); // Cập nhật giá trị tổng
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
        updateTotalValue(); // Cập nhật giá trị tổng khi giá thay đổi
    }

    public double getTotalValue() {
        return totalValue.get();
    }

    public void setTotalValue(double totalValue) {
        this.totalValue.set(totalValue); // Optional setter if you need to set it directly
    }

    private void updateTotalValue() {
        this.totalValue.set(price.get() * quantity.get()); // Cập nhật giá trị tổng
    }

    public static String getSupplyCode(ListDeliveryOrder listDeliveryOrder) {
        return supplyCode.get();
    }
}
