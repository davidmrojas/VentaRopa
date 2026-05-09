package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Product;
import com.juanfedevmaster.manageclient.repository.IProductRepository;
import java.util.List;

// Aplica las reglas de negocio de productos.
public class ProductService implements IProductService {
    private final IProductRepository productRepository;

    // Recibe el repositorio de productos.
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    // Valida y guarda un producto nuevo.
    public void saveProduct(Product product) {
        validateProduct(product);

        // Valida el ID antes de guardar para evitar duplicados.
        if (productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Ya existe un producto con el ID " + product.getId() + ".");
        }

        productRepository.save(product);
    }

    @Override
    // Valida y actualiza un producto.
    public void updateProduct(Product product) {
        validateProduct(product);
        productRepository.update(product);
    }

    @Override
    // Elimina un producto por ID.
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    // Lista todos los productos.
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    // Verifica que el stock alcance.
    public void validateStockAvailability(Product product, int requestedQuantity) {
        if (product == null) {
            throw new IllegalArgumentException("Debes seleccionar un producto.");
        }

        if (requestedQuantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        if (requestedQuantity > product.getStock()) {
            throw new IllegalArgumentException("No hay suficiente stock disponible.");
        }
    }

    @Override
    // Descuenta stock despues de una venta.
    public void subtractStock(Product product, int soldQuantity) {
        // Actualiza el stock despues de validar la cantidad vendida.
        validateStockAvailability(product, soldQuantity);
        product.setStock(product.getStock() - soldQuantity);
        productRepository.update(product);
    }

    // Valida los datos minimos del producto.
    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Los datos del producto no son válidos.");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        if (product.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }
}
