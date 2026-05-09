package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Sale;
import java.util.List;

public interface ISaleService {
    void finalizeSale(Sale sale);

    List<Sale> getAllSales();
}
