package com.juanfedevmaster.manageclient.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Representa una venta finalizada o en proceso.
public class Sale {
    private Client client;
    private List<SaleItem> items;
    private double total;
    private LocalDateTime completedAt;

    // Crea una venta vacia.
    public Sale() {
        // Inicializa la lista para evitar valores nulos.
        this.items = new ArrayList<>();
    }

    // Crea una venta con cliente, articulos y total.
    public Sale(Client client, List<SaleItem> items, double total) {
        this.client = client;
        this.items = new ArrayList<>(items);
        this.total = total;
    }

    // Devuelve el cliente de la venta.
    public Client getClient() {
        return client;
    }

    // Actualiza el cliente de la venta.
    public void setClient(Client client) {
        this.client = client;
    }

    // Devuelve una copia de los articulos.
    public List<SaleItem> getItems() {
        return new ArrayList<>(items);
    }

    // Actualiza los articulos de la venta.
    public void setItems(List<SaleItem> items) {
        this.items = new ArrayList<>(items);
    }

    // Devuelve el total de la venta.
    public double getTotal() {
        return total;
    }

    // Actualiza el total de la venta.
    public void setTotal(double total) {
        this.total = total;
    }

    // Devuelve la fecha y hora de cierre.
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    // Actualiza la fecha y hora de cierre.
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
