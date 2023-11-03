package com.example.shop.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private UUID uuid;

    private Timestamp timestamp;

    private Integer sum;

    private UUID clientId;

    private List<String> products;

    public Order() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getUuid(), order.getUuid()) && Objects.equals(
                getTimestamp(),
                order.getTimestamp()
        ) && Objects.equals(getSum(), order.getSum()) && Objects.equals(
                getClientId(),
                order.getClientId()
        ) && Objects.equals(getProducts(), order.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getTimestamp(), getSum(), getClientId(), getProducts());
    }
}
