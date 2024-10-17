package atlantafx.sampler.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderItem {
    private StringProperty supplyCode = new SimpleStringProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private StringProperty unit = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private StringProperty itemName = new SimpleStringProperty();
    private DoubleProperty totalValue = new SimpleDoubleProperty();

    public OrderItem(String supplyCode, double quantity) {
        this.supplyCode.set(supplyCode);
        this.quantity.set((int) quantity);
    }

    public OrderItem() {

    }

    // Getters and Setters
    public String getSupplyCode() {
        return supplyCode.get();
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode.set(supplyCode);
    }

    public Integer getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        // Update total value when quantity changes
        setTotalValue(getPrice() * quantity);
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
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public double getTotalValue() {
        return totalValue.get();
    }

    public void setTotalValue(double totalValue) {
        this.totalValue.set(totalValue);
    }
}
