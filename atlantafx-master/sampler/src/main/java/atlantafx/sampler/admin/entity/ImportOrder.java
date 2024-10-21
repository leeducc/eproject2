package atlantafx.sampler.admin.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class ImportOrder {
    private final IntegerProperty id;              // Corresponds to `id`
    private final IntegerProperty supplierId;      // Corresponds to `supplier_id`
    private final StringProperty deliveryDate;     // Corresponds to `delivery_date`
    private final DoubleProperty totalValue;       // Corresponds to `total_value`
    private final List<ImportOrderDetail> details; // List of order details

    public ImportOrder(int id, int supplierId, String deliveryDate, double totalValue, List<ImportOrderDetail> details) {
        this.id = new SimpleIntegerProperty(id);
        this.supplierId = new SimpleIntegerProperty(supplierId);
        this.deliveryDate = new SimpleStringProperty(deliveryDate);
        this.totalValue = new SimpleDoubleProperty(totalValue);
        this.details = details; // Initialize with the provided list of order details
    }

    // Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public String getDeliveryDate() {
        return deliveryDate.get();
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate.set(deliveryDate);
    }

    public double getTotalValue() {
        return totalValue.get();
    }

    public void setTotalValue(double totalValue) {
        this.totalValue.set(totalValue);
    }

    public List<ImportOrderDetail> getDetails() {
        return details;
    }
}
