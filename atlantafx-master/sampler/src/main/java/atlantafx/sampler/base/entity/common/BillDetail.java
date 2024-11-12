package atlantafx.sampler.base.entity.common;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BillDetail {
    private  IntegerProperty id;
    private  IntegerProperty billId;
    private  IntegerProperty productId;
    private  IntegerProperty quantity;
    private  DoubleProperty price;
    private  StringProperty productName;  // Added for product name

    public BillDetail(int id, int billId, int productId, int quantity, double price, String productName) {
        this.id = new SimpleIntegerProperty(id);
        this.billId = new SimpleIntegerProperty(billId);
        this.productId = new SimpleIntegerProperty(productId);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.productName = new SimpleStringProperty(productName);  // Initialized product name
    }

    public BillDetail() {
        this.id = new SimpleIntegerProperty();
        this.billId = new SimpleIntegerProperty();
        this.productId = new SimpleIntegerProperty();
        this.quantity = new SimpleIntegerProperty();
        this.price = new SimpleDoubleProperty();
        this.productName = new SimpleStringProperty();
    }

    public BillDetail(String productName, int quantity, double price) {
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getBillId() {
        return billId.get();
    }

    public void setBillId(int billId) {
        this.billId.set(billId);
    }

    public int getProductId() {
        return productId.get();
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    // JavaFX Property Getters
    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty billIdProperty() {
        return billId;
    }

    public IntegerProperty productIdProperty() {
        return productId;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public StringProperty productNameProperty() {
        return productName;
    }
}
