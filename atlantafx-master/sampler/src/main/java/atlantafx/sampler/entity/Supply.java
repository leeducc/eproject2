package atlantafx.sampler.entity;


public class Supply {
    private final String supplyCode;
    private final String name;
    private final String unit;
    private final double price;
    private final double quantity;
    private final String supplierName;

    public Supply(String supplyCode, String name, String unit, double price, double quantity, String supplierName) {
        this.supplyCode = supplyCode;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
    }

    public String getSupplyCode() { return supplyCode; }
    public String getName() { return name; }
    public String getUnit() { return unit; }
    public double getPrice() { return price; }
    public double getQuantity() { return quantity; }
    public String getSupplierName() { return supplierName; }

    // Calculate total value
    public double getTotalValue() {
        return price * quantity;
    }
}
