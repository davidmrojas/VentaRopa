package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Sale;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository implements ISaleRepository {
    private final List<Sale> sales;

    public SaleRepository() {
        // Guarda las ventas registradas en memoria.
        this.sales = new ArrayList<>();
    }

    @Override
    public void save(Sale sale) {
        sales.add(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }
}
