package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Product;
import java.util.List;

public interface IProductRepository {
    void save(Product product);

    boolean existsById(int id);

    void update(Product product);

    void deleteById(int id);

    List<Product> getAllProducts();
}
