package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Sale;
import java.util.List;

// Define las reglas de negocio para ventas.
public interface ISaleService {
    // Finaliza y registra una venta.
    void finalizeSale(Sale sale);

    // Lista todas las ventas.
    List<Sale> getAllSales();
}
