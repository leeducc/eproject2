package atlantafx.sampler.base.entity.common;


import javafx.beans.property.*;

import java.time.LocalDateTime;

public class BillOrder {
    private final IntegerProperty id;
    private final DoubleProperty totalAmount;
    private final ObjectProperty<LocalDateTime> createdAt;

    public BillOrder(int id, double totalAmount, LocalDateTime createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public DoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    public int getId() {
        return id.get();
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }
}
