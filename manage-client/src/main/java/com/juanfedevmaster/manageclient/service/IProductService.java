package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Product;
import java.util.List;

public interface IProductService {
    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(int id);

    List<Product> getAllProducts();

    void validateStockAvailability(Product product, int requestedQuantity);

    void subtractStock(Product product, int soldQuantity);
}
