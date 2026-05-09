package com.juanfedevmaster.manageclient.model;

// Representa un producto dentro de una venta.
public class SaleItem {
    private Product product;
    private int quantity;
    private double subtotal;

    // Crea un item vacio.
    public SaleItem() {
    }

    // Crea un item con producto, cantidad y subtotal.
    public SaleItem(Product product, int quantity, double subtotal) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    // Devuelve el producto vendido.
    public Product getProduct() {
        return product;
    }

    // Actualiza el producto vendido.
    public void setProduct(Product product) {
        this.product = product;
    }

    // Devuelve la cantidad vendida.
    public int getQuantity() {
        return quantity;
    }

    // Actualiza la cantidad vendida.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Devuelve el subtotal del item.
    public double getSubtotal() {
        return subtotal;
    }

    // Actualiza el subtotal del item.
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
