package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Sale;
import java.util.List;

// Define las operaciones de almacenamiento de ventas.
public interface ISaleRepository {
    // Guarda una venta finalizada.
    void save(Sale sale);

    // Lista todas las ventas.
    List<Sale> getAllSales();
}
