package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Product;
import java.util.List;

// Define las reglas de negocio para productos.
public interface IProductService {
    // Guarda un producto nuevo.
    void saveProduct(Product product);

    // Actualiza un producto existente.
    void updateProduct(Product product);

    // Elimina un producto por ID.
    void deleteProduct(int id);

    // Lista todos los productos.
    List<Product> getAllProducts();

    // Valida que exista stock suficiente.
    void validateStockAvailability(Product product, int requestedQuantity);

    // Descuenta unidades del stock.
    void subtractStock(Product product, int soldQuantity);
}
