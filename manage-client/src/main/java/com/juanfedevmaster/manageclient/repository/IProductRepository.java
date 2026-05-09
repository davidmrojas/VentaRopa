package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Product;
import java.util.List;

// Define las operaciones de almacenamiento de productos.
public interface IProductRepository {
    // Guarda un producto.
    void save(Product product);

    // Indica si existe un producto por ID.
    boolean existsById(int id);

    // Actualiza un producto existente.
    void update(Product product);

    // Elimina un producto por ID.
    void deleteById(int id);

    // Lista todos los productos.
    List<Product> getAllProducts();
}
