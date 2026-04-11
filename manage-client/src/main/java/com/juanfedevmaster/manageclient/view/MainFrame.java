package com.juanfedevmaster.manageclient.view;

import com.juanfedevmaster.manageclient.model.Client;
import com.juanfedevmaster.manageclient.model.Product;
import com.juanfedevmaster.manageclient.model.Sale;
import com.juanfedevmaster.manageclient.model.SaleItem;
import com.juanfedevmaster.manageclient.repository.ClientRepository;
import com.juanfedevmaster.manageclient.repository.ProductRepository;
import com.juanfedevmaster.manageclient.repository.SaleRepository;
import com.juanfedevmaster.manageclient.service.ClientService;
import com.juanfedevmaster.manageclient.service.ProductService;
import com.juanfedevmaster.manageclient.service.SaleService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame{
    private JTabbedPane tabbedPanePrincipal;
    private JPanel panel1;
    private JPanel panelClientes;
    private JPanel panelProductos;
    private JPanel panelVentas;
    private JPanel panelClientesformulario;
    private JTextField txtClientId;
    private JTextField txtClientName;
    private JTextField txtClientEmail;
    private JTextField txtClientPhone;
    private JScrollPane JScrollTableClientes;
    private JTable tblClients;
    private JPanel BtnsPanelClientes;
    private JButton btnSaveClient;
    private JButton btnEditClient;
    private JButton btnDeleteClient;
    private JPanel panelProductosform;
    private JTextField txtProductId;
    private JTextField txtProductName;
    private JTextField txtProductSize;
    private JTextField txtProductColor;
    private JTextField txtProductPrice;
    private JTextField txtProductStock;
    private JPanel BtnsPanelProductos;
    private JTable tblProducts;
    private JScrollPane JScrollTableProductos;
    private JButton btnSaveProduct;
    private JButton btnEditProduct;
    private JButton btnDeleteProduct;
    private JPanel PanelVentasform;
    private JComboBox<Client> cbSalesClient;
    private JComboBox<Product> cbSalesProduct;
    private JTextField txtSalesQuantity;
    private JButton btnAddProduct;
    private JScrollPane JScrollVentas;
    private JTable tblSalesDetail;
    private JButton btnFinalizeSale;
    private JLabel lblTotalSale;
    private JLabel LabelClientId;
    private JLabel LabelClientName;
    private JLabel LabelClientEmail;
    private JLabel LabelClientPhone;
    private JLabel LabelProductId;
    private JLabel LabelProductName;
    private JLabel LabelProductSize;
    private JLabel LabelProductColor;
    private JLabel LabelProductPrice;
    private JLabel LabelProductStock;
    private JLabel LabelCantidad;
    private final ClientService clientService;
    private final ProductService productService;
    private final SaleService saleService;
    private final List<SaleItem> currentCart;

    public MainFrame() {
        this.clientService = new ClientService(new ClientRepository());
        this.productService = new ProductService(new ProductRepository());
        this.saleService = new SaleService(new SaleRepository(), productService);
        this.currentCart = new ArrayList<>();
        setTitle("Sistema para venta de ropa");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000,800));
        setLocationRelativeTo(null);

        // Conecta los eventos de la interfaz con sus acciones.
        btnSaveClient.addActionListener(e -> saveClient());
        btnEditClient.addActionListener(e -> editClient());
        btnDeleteClient.addActionListener(e -> deleteClient());
        btnSaveProduct.addActionListener(e -> saveProduct());
        btnEditProduct.addActionListener(e -> editProduct());
        btnDeleteProduct.addActionListener(e -> deleteProduct());
        btnAddProduct.addActionListener(e -> addProductToCart());
        btnFinalizeSale.addActionListener(e -> finalizeSale());
        tblClients.getSelectionModel().addListSelectionListener(this::onClientSelected);
        tblProducts.getSelectionModel().addListSelectionListener(this::onProductSelected);
        initializeSalesTable();
        refreshClientTable();
        refreshProductTable();
        loadSalesCombos();
        updateSaleTotalLabel();
    }

    private void refreshClientTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Correo", "Telefono"}, 0
        );

        for (Client client : clientService.getAllClients()) {
            tableModel.addRow(new Object[]{
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhoneNumber()
            });
        }

        tblClients.setModel(tableModel);
    }

    private void saveClient() {
        try {
            Client client = buildClientFromFields();

            // La vista delega las reglas al servicio.
            clientService.saveClient(client);

            refreshClientTable();
            loadSalesCombos();
            clearClientForm();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un numero entero.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private void editClient() {
        try {
            Client client = buildClientFromFields();
            clientService.updateClient(client);
            refreshClientTable();
            clearClientForm();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un numero entero.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private void deleteClient() {
        try {
            int id = Integer.parseInt(txtClientId.getText().trim());
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas eliminar el cliente con ID " + id + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                clientService.deleteClient(id);
                refreshClientTable();
                loadSalesCombos();
                clearClientForm();
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un numero entero.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private Client buildClientFromFields() {
        int id = Integer.parseInt(txtClientId.getText().trim());
        String name = txtClientName.getText().trim();
        String email = txtClientEmail.getText().trim();
        String phone = txtClientPhone.getText().trim();
        return new Client(id, name, email, phone);
    }

    private void onClientSelected(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = tblClients.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        txtClientId.setText(tblClients.getValueAt(selectedRow, 0).toString());
        txtClientName.setText(tblClients.getValueAt(selectedRow, 1).toString());
        txtClientEmail.setText(tblClients.getValueAt(selectedRow, 2).toString());
        txtClientPhone.setText(tblClients.getValueAt(selectedRow, 3).toString());
    }

    private void clearClientForm() {
        txtClientId.setText("");
        txtClientName.setText("");
        txtClientEmail.setText("");
        txtClientPhone.setText("");
    }

    private void refreshProductTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Talla", "Color", "Precio", "Stock"}, 0
        );

        for (Product product : productService.getAllProducts()) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getSize(),
                    product.getColor(),
                    product.getPrice(),
                    product.getStock()
            });
        }

        tblProducts.setModel(tableModel);
    }

    private void saveProduct() {
        try {
            Product product = buildProductFromFields();

            // La vista delega las reglas al servicio.
            productService.saveProduct(product);

            refreshProductTable();
            loadSalesCombos();
            clearProductForm();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "ID, precio y stock deben ser valores numericos.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private void editProduct() {
        try {
            Product product = buildProductFromFields();
            productService.updateProduct(product);
            refreshProductTable();
            loadSalesCombos();
            refreshSalesTable();
            updateSaleTotalLabel();
            clearProductForm();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "ID, precio y stock deben ser valores numericos.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            int id = Integer.parseInt(txtProductId.getText().trim());
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas eliminar el producto con ID " + id + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                productService.deleteProduct(id);
                refreshProductTable();
                loadSalesCombos();
                removeDeletedProductFromCart(id);
                refreshSalesTable();
                updateSaleTotalLabel();
                clearProductForm();
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un numero entero.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private Product buildProductFromFields() {
        int id = Integer.parseInt(txtProductId.getText().trim());
        String name = txtProductName.getText().trim();
        String size = txtProductSize.getText().trim();
        String color = txtProductColor.getText().trim();
        double price = Double.parseDouble(txtProductPrice.getText().trim());
        int stock = Integer.parseInt(txtProductStock.getText().trim());
        return new Product(id, name, size, color, price, stock);
    }

    private void onProductSelected(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = tblProducts.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        txtProductId.setText(tblProducts.getValueAt(selectedRow, 0).toString());
        txtProductName.setText(tblProducts.getValueAt(selectedRow, 1).toString());
        txtProductSize.setText(tblProducts.getValueAt(selectedRow, 2).toString());
        txtProductColor.setText(tblProducts.getValueAt(selectedRow, 3).toString());
        txtProductPrice.setText(tblProducts.getValueAt(selectedRow, 4).toString());
        txtProductStock.setText(tblProducts.getValueAt(selectedRow, 5).toString());
    }

    private void clearProductForm() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtProductSize.setText("");
        txtProductColor.setText("");
        txtProductPrice.setText("");
        txtProductStock.setText("");
    }

    private void initializeSalesTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Producto", "Talla", "Cantidad", "Precio U.", "Subtotal"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // La tabla solo muestra el detalle del carrito.
                return false;
            }
        };

        tblSalesDetail.setModel(tableModel);
    }

    private void loadSalesCombos() {
        // Recarga los combos para reflejar el estado actual.
        cbSalesClient.removeAllItems();
        cbSalesProduct.removeAllItems();

        for (Client client : clientService.getAllClients()) {
            cbSalesClient.addItem(client);
        }

        for (Product product : productService.getAllProducts()) {
            cbSalesProduct.addItem(product);
        }
    }

    private void addProductToCart() {
        try {
            Product selectedProduct = (Product) cbSalesProduct.getSelectedItem();
            int quantity = Integer.parseInt(txtSalesQuantity.getText().trim());
            int quantityAlreadyInCart = getQuantityInCart(selectedProduct);

            // Suma lo que ya esta en el carrito para no exceder el stock.
            productService.validateStockAvailability(selectedProduct, quantityAlreadyInCart + quantity);

            double subtotal = selectedProduct.getPrice() * quantity;
            SaleItem saleItem = new SaleItem(selectedProduct, quantity, subtotal);
            currentCart.add(saleItem);

            refreshSalesTable();
            updateSaleTotalLabel();
            txtSalesQuantity.setText("");
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un numero entero.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private void finalizeSale() {
        try {
            Client selectedClient = (Client) cbSalesClient.getSelectedItem();
            Sale sale = new Sale(selectedClient, currentCart, calculateCartTotal());

            // El servicio coordina el registro de la venta.
            saleService.finalizeSale(sale);

            clearSaleUI();
            refreshProductTable();
            loadSalesCombos();
            JOptionPane.showMessageDialog(this, "La venta fue registrada correctamente.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    private void refreshSalesTable() {
        DefaultTableModel tableModel = (DefaultTableModel) tblSalesDetail.getModel();
        tableModel.setRowCount(0);

        for (SaleItem item : currentCart) {
            tableModel.addRow(new Object[]{
                    item.getProduct().getName(),
                    item.getProduct().getSize(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    item.getSubtotal()
            });
        }
    }

    private void updateSaleTotalLabel() {
        lblTotalSale.setText(String.format("$ %.2f", calculateCartTotal()));
    }

    private double calculateCartTotal() {
        double total = 0;

        for (SaleItem item : currentCart) {
            total += item.getSubtotal();
        }

        return total;
    }

    private int getQuantityInCart(Product product) {
        if (product == null) {
            return 0;
        }

        int quantity = 0;

        for (SaleItem item : currentCart) {
            if (item.getProduct().getId() == product.getId()) {
                quantity += item.getQuantity();
            }
        }

        return quantity;
    }

    private void removeDeletedProductFromCart(int productId) {
        // Elimina del carrito productos que ya no existen en la lista principal.
        for (int i = currentCart.size() - 1; i >= 0; i--) {
            if (currentCart.get(i).getProduct().getId() == productId) {
                currentCart.remove(i);
            }
        }
    }

    private void clearSaleUI() {
        // Reinicia el formulario despues de registrar la venta.
        currentCart.clear();
        refreshSalesTable();
        updateSaleTotalLabel();
        txtSalesQuantity.setText("");

        if (cbSalesClient.getItemCount() > 0) {
            cbSalesClient.setSelectedIndex(0);
        }

        if (cbSalesProduct.getItemCount() > 0) {
            cbSalesProduct.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
