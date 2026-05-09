package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

// Guarda productos en memoria.
public class ProductRepository implements IProductRepository {
    private final Map<Integer, Product> products;

    // Prepara el almacenamiento de productos.
    public ProductRepository() {
        this.products = new HashMap<>();
    }

    @Override
    // Guarda un producto por ID.
    public void save(Product product) {
        // El mapa evita recorrer toda la coleccion para ubicar el ID.
        products.put(product.getId(), product);
    }

    @Override
    // Verifica si el ID ya existe.
    public boolean existsById(int id) {
        return products.containsKey(id);
    }

    @Override
    // Actualiza los datos de un producto.
    public void update(Product product) {
        Product currentProduct = products.get(product.getId());

        if (currentProduct == null) {
            throw new IllegalArgumentException("No se encontró el producto con ID " + product.getId() + ".");
        }

        currentProduct.setName(product.getName());
        currentProduct.setSize(product.getSize());
        currentProduct.setColor(product.getColor());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setStock(product.getStock());
    }

    @Override
    // Elimina un producto existente.
    public void deleteById(int id) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("No se encontró el producto con ID " + id + ".");
        }

        products.remove(id);
    }

    @Override
    // Devuelve una copia de los productos.
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
