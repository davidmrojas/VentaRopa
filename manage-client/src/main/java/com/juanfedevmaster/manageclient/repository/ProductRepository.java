package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ProductRepository implements IProductRepository {
    private final Map<Integer, Product> products;

    public ProductRepository() {
        this.products = new HashMap<>();
    }

    @Override
    public void save(Product product) {
        // El Map evita recorrer toda la coleccion para ubicar el ID.
        products.put(product.getId(), product);
    }

    @Override
    public boolean existsById(int id) {
        return products.containsKey(id);
    }

    @Override
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
    public void deleteById(int id) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("No se encontró el producto con ID " + id + ".");
        }

        products.remove(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
