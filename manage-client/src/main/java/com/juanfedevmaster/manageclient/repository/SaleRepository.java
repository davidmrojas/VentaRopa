package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Sale;
import java.util.ArrayList;
import java.util.List;

// Guarda ventas en memoria.
public class SaleRepository implements ISaleRepository {
    private final List<Sale> sales;

    // Prepara el almacenamiento de ventas.
    public SaleRepository() {
        // Guarda las ventas registradas en memoria.
        this.sales = new ArrayList<>();
    }

    @Override
    // Guarda una venta finalizada.
    public void save(Sale sale) {
        sales.add(sale);
    }

    @Override
    // Devuelve una copia de las ventas.
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }
}
