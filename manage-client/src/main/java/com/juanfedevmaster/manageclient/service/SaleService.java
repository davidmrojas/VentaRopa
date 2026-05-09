package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Sale;
import com.juanfedevmaster.manageclient.model.SaleItem;
import com.juanfedevmaster.manageclient.repository.ISaleRepository;
import java.time.LocalDateTime;
import java.util.List;

// Aplica las reglas de negocio de ventas.
public class SaleService implements ISaleService {
    private final ISaleRepository saleRepository;
    private final IProductService productService;

    // Recibe las dependencias de ventas y productos.
    public SaleService(ISaleRepository saleRepository, IProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }

    @Override
    // Finaliza la venta y descuenta stock.
    public void finalizeSale(Sale sale) {
        validateSale(sale);

        // Coordina la venta y delega el descuento de stock.
        for (SaleItem item : sale.getItems()) {
            productService.subtractStock(item.getProduct(), item.getQuantity());
        }

        sale.setCompletedAt(LocalDateTime.now());
        saleRepository.save(sale);
    }

    @Override
    // Lista todas las ventas.
    public List<Sale> getAllSales() {
        return saleRepository.getAllSales();
    }

    // Valida los datos minimos de la venta.
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
