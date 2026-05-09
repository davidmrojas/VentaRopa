package com.juanfedevmaster.manageclient.model;

// Representa un producto disponible para la venta.
public class Product {
    private int id;
    private String name;
    private String size;
    private String color;
    private double price;
    private int stock;

    // Crea un producto vacio.
    public Product() {
    }

    // Crea un producto con sus datos principales.
    public Product(int id, String name, String size, String color, double price, int stock) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.stock = stock;
    }

    // Devuelve el ID del producto.
    public int getId() {
        return id;
    }

    // Actualiza el ID del producto.
    public void setId(int id) {
        this.id = id;
    }

    // Devuelve el nombre del producto.
    public String getName() {
        return name;
    }

    // Actualiza el nombre del producto.
    public void setName(String name) {
        this.name = name;
    }

    // Devuelve la talla del producto.
    public String getSize() {
        return size;
    }

    // Actualiza la talla del producto.
    public void setSize(String size) {
        this.size = size;
    }

    // Devuelve el color del producto.
    public String getColor() {
        return color;
    }

    // Actualiza el color del producto.
    public void setColor(String color) {
        this.color = color;
    }

    // Devuelve el precio del producto.
    public double getPrice() {
        return price;
    }

    // Actualiza el precio del producto.
    public void setPrice(double price) {
        this.price = price;
    }

    // Devuelve el stock disponible.
    public int getStock() {
        return stock;
    }

    // Actualiza el stock disponible.
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    // Devuelve el texto visible en los desplegables.
    public String toString() {
        // Muestra un texto claro en los desplegables.
        return name + " - " + size;
    }
}
