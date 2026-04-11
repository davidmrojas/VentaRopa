package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Sale;
import com.juanfedevmaster.manageclient.model.SaleItem;
import com.juanfedevmaster.manageclient.repository.ISaleRepository;

public class SaleService {
    private final ISaleRepository saleRepository;
    private final ProductService productService;

    public SaleService(ISaleRepository saleRepository, ProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }

    public void finalizeSale(Sale sale) {
        validateSale(sale);

        // Coordina la venta y delega el descuento de stock.
        for (SaleItem item : sale.getItems()) {
            productService.subtractStock(item.getProduct(), item.getQuantity());
        }

        saleRepository.save(sale);
    }

    private void validateSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("La venta no es válida.");
        }

        if (sale.getClient() == null) {
            throw new IllegalArgumentException("Debes seleccionar un cliente.");
        }

        if (sale.getItems().isEmpty()) {
            throw new IllegalArgumentException("Debes agregar al menos un producto.");
        }

        if (sale.getTotal() < 0) {
            throw new IllegalArgumentException("El total de la venta no es válido.");
        }
    }
}
