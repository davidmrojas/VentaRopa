package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Sale;
import java.util.List;

public interface ISaleRepository {
    void save(Sale sale);

    List<Sale> getAllSales();
}
