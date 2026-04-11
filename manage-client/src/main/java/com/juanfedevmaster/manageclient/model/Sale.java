package com.juanfedevmaster.manageclient.model;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private Client client;
    private List<SaleItem> items;
    private double total;

    public Sale() {
        // Inicializa la lista para evitar valores nulos.
        this.items = new ArrayList<>();
    }

    public Sale(Client client, List<SaleItem> items, double total) {
        this.client = client;
        this.items = new ArrayList<>(items);
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<SaleItem> getItems() {
        return new ArrayList<>(items);
    }

    public void setItems(List<SaleItem> items) {
        this.items = new ArrayList<>(items);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
