package com.juanfedevmaster.manageclient.view;

import com.juanfedevmaster.manageclient.model.Client;
import com.juanfedevmaster.manageclient.model.Product;
import com.juanfedevmaster.manageclient.model.Sale;
import com.juanfedevmaster.manageclient.model.SaleItem;
import com.juanfedevmaster.manageclient.repository.ClientRepository;
import com.juanfedevmaster.manageclient.repository.ProductRepository;
import com.juanfedevmaster.manageclient.repository.SaleRepository;
import com.juanfedevmaster.manageclient.service.ClientService;
import com.juanfedevmaster.manageclient.service.IClientService;
import com.juanfedevmaster.manageclient.service.IProductService;
import com.juanfedevmaster.manageclient.service.ISaleService;
import com.juanfedevmaster.manageclient.service.ProductService;
import com.juanfedevmaster.manageclient.service.SaleService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Ventana principal de la aplicacion.
public class MainFrame extends JFrame{
    private static final DateTimeFormatter SALE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private JTabbedPane mainTabbedPane;
    private JPanel contentPanel;
    private JPanel clientsPanel;
    private JPanel productsPanel;
    private JPanel salesPanel;
    private JPanel clientFormPanel;
    private JTextField txtClientId;
    private JTextField txtClientName;
    private JTextField txtClientEmail;
    private JTextField txtClientPhone;
    private JScrollPane clientsTableScrollPane;
    private JTable tblClients;
    private JPanel clientButtonsPanel;
    private JButton btnSaveClient;
    private JButton btnEditClient;
    private JButton btnDeleteClient;
    private JPanel productFormPanel;
    private JTextField txtProductId;
    private JTextField txtProductName;
    private JTextField txtProductSize;
    private JTextField txtProductColor;
    private JTextField txtProductPrice;
    private JTextField txtProductStock;
    private JPanel productButtonsPanel;
    private JTable tblProducts;
    private JScrollPane productsTableScrollPane;
    private JButton btnSaveProduct;
    private JButton btnEditProduct;
    private JButton btnDeleteProduct;
    private JPanel salesFormPanel;
    private JComboBox<Client> cbSalesClient;
    private JComboBox<Product> cbSalesProduct;
    private JTextField txtSalesQuantity;
    private JButton btnAddProduct;
    private JScrollPane salesDetailScrollPane;
    private JTable tblSalesDetail;
    private JButton btnFinalizeSale;
    private JLabel lblTotalSale;
    private JLabel labelClientId;
    private JLabel labelClientName;
    private JLabel labelClientEmail;
    private JLabel labelClientPhone;
    private JLabel labelProductId;
    private JLabel labelProductName;
    private JLabel labelProductSize;
    private JLabel labelProductColor;
    private JLabel labelProductPrice;
    private JLabel labelProductStock;
    private JLabel labelQuantity;
    private JPanel checkoutPanel;
    private JTable tblSalesHistory;
    private JScrollPane scrollHistory;
    private JLabel labelSalesHistory;
    private final IClientService clientService;
    private final IProductService productService;
    private final ISaleService saleService;
    private final List<SaleItem> currentCart;

    // Construye la ventana y carga los datos iniciales.
    public MainFrame() {
        this.clientService = new ClientService(new ClientRepository());
        this.productService = new ProductService(new ProductRepository());
        this.saleService = new SaleService(new SaleRepository(), productService);
        this.currentCart = new ArrayList<>();
        initializeComponents();
        setTitle("Sistema para venta de ropa");
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000,800));
        pack();
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
        refreshSalesHistoryTable();
    }

    // Inicializa la estructura principal de pestañas.
    private void initializeComponents() {
        contentPanel = new JPanel(new BorderLayout());
        mainTabbedPane = new JTabbedPane();

        clientsPanel = createClientsPanel();
        productsPanel = createProductsPanel();
        salesPanel = createSalesPanel();

        mainTabbedPane.addTab("Cliente", clientsPanel);
        mainTabbedPane.addTab("Productos", productsPanel);
        mainTabbedPane.addTab("Ventas", salesPanel);
        contentPanel.add(mainTabbedPane, BorderLayout.CENTER);
    }

    // Crea la pestaña de clientes.
    private JPanel createClientsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        clientFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = createFormConstraints();

        labelClientId = new JLabel("ID del Cliente");
        labelClientName = new JLabel("Nombre del cliente");
        labelClientEmail = new JLabel("Correo");
        labelClientPhone = new JLabel("Telefono");
        txtClientId = new JTextField();
        txtClientName = new JTextField();
        txtClientEmail = new JTextField();
        txtClientPhone = new JTextField();

        addFormRow(clientFormPanel, constraints, 0, labelClientId, txtClientId);
        addFormRow(clientFormPanel, constraints, 1, labelClientName, txtClientName);
        addFormRow(clientFormPanel, constraints, 2, labelClientEmail, txtClientEmail);
        addFormRow(clientFormPanel, constraints, 3, labelClientPhone, txtClientPhone);

        tblClients = new JTable();
        clientsTableScrollPane = new JScrollPane(tblClients);
        clientButtonsPanel = new JPanel(new GridLayout(1, 3, 8, 0));
        btnSaveClient = new JButton("Guardar");
        btnEditClient = new JButton("Editar");
        btnDeleteClient = new JButton("Eliminar");
        clientButtonsPanel.add(btnSaveClient);
        clientButtonsPanel.add(btnEditClient);
        clientButtonsPanel.add(btnDeleteClient);

        panel.add(clientFormPanel, BorderLayout.NORTH);
        panel.add(clientsTableScrollPane, BorderLayout.CENTER);
        panel.add(clientButtonsPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Crea la pestaña de productos.
    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        productFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = createFormConstraints();

        labelProductId = new JLabel("ID del producto");
        labelProductName = new JLabel("Nombre");
        labelProductSize = new JLabel("Talla");
        labelProductColor = new JLabel("Color");
        labelProductPrice = new JLabel("Precio");
        labelProductStock = new JLabel("Stock");
        txtProductId = new JTextField();
        txtProductName = new JTextField();
        txtProductSize = new JTextField();
        txtProductColor = new JTextField();
        txtProductPrice = new JTextField();
        txtProductStock = new JTextField();

        addFormRow(productFormPanel, constraints, 0, labelProductId, txtProductId);
        addFormRow(productFormPanel, constraints, 1, labelProductName, txtProductName);
        addFormRow(productFormPanel, constraints, 2, labelProductSize, txtProductSize);
        addFormRow(productFormPanel, constraints, 3, labelProductColor, txtProductColor);
        addFormRow(productFormPanel, constraints, 4, labelProductPrice, txtProductPrice);
        addFormRow(productFormPanel, constraints, 5, labelProductStock, txtProductStock);

        tblProducts = new JTable();
        productsTableScrollPane = new JScrollPane(tblProducts);
        productButtonsPanel = new JPanel(new GridLayout(1, 3, 8, 0));
        btnSaveProduct = new JButton("Guardar");
        btnEditProduct = new JButton("Editar");
        btnDeleteProduct = new JButton("Eliminar");
        productButtonsPanel.add(btnSaveProduct);
        productButtonsPanel.add(btnEditProduct);
        productButtonsPanel.add(btnDeleteProduct);

        panel.add(productFormPanel, BorderLayout.NORTH);
        panel.add(productsTableScrollPane, BorderLayout.CENTER);
        panel.add(productButtonsPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Crea la pestaña de ventas.
    private JPanel createSalesPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        salesFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = createFormConstraints();

        cbSalesClient = new JComboBox<>();
        cbSalesProduct = new JComboBox<>();
        txtSalesQuantity = new JTextField();
        btnAddProduct = new JButton("Agregar");
        labelQuantity = new JLabel("Cantidad:");

        addFormRow(salesFormPanel, constraints, 0, new JLabel("Cliente"), cbSalesClient);
        addFormRow(salesFormPanel, constraints, 1, new JLabel("Producto"), cbSalesProduct);
        addFormRow(salesFormPanel, constraints, 2, labelQuantity, txtSalesQuantity);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        salesFormPanel.add(btnAddProduct, constraints);

        tblSalesDetail = new JTable();
        salesDetailScrollPane = new JScrollPane(tblSalesDetail);
        checkoutPanel = new JPanel(new BorderLayout(8, 8));
        labelSalesHistory = new JLabel("Historial de Ventas Finalizadas");
        tblSalesHistory = new JTable();
        scrollHistory = new JScrollPane(tblSalesHistory);
        scrollHistory.setPreferredSize(new Dimension(200, 140));

        JPanel salesFooter = new JPanel(new GridLayout(1, 2, 8, 0));
        btnFinalizeSale = new JButton("Finalizar compra");
        lblTotalSale = new JLabel("$ 0.00");
        salesFooter.add(btnFinalizeSale);
        salesFooter.add(lblTotalSale);
        checkoutPanel.add(labelSalesHistory, BorderLayout.NORTH);
        checkoutPanel.add(scrollHistory, BorderLayout.CENTER);
        checkoutPanel.add(salesFooter, BorderLayout.SOUTH);

        panel.add(salesFormPanel, BorderLayout.NORTH);
        panel.add(salesDetailScrollPane, BorderLayout.CENTER);
        panel.add(checkoutPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Crea restricciones base para formularios.
    private GridBagConstraints createFormConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.anchor = GridBagConstraints.WEST;
        return constraints;
    }

    // Agrega una fila al formulario.
    private void addFormRow(JPanel panel, GridBagConstraints constraints, int row, JLabel label, JComponent field) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        panel.add(label, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        field.setPreferredSize(new Dimension(180, field.getPreferredSize().height));
        panel.add(field, constraints);
    }

    // Refresca la tabla de clientes.
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

    // Guarda un cliente nuevo.
    private void saveClient() {
        try {
            validateClientFieldsNotEmpty();
            validateClientIdIsUnique();
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

    // Edita el cliente seleccionado.
    private void editClient() {
        try {
            Client client = buildClientFromFields();
            clientService.updateClient(client);
            refreshClientTable();
            clearClientForm();
            loadSalesCombos();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un numero entero.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    // Elimina el cliente seleccionado.
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

    // Construye un cliente desde el formulario.
    private Client buildClientFromFields() {
        int id;
        try {
            id = Integer.parseInt(txtClientId.getText().trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID del cliente debe ser un número entero.");
        }

        String name = txtClientName.getText().trim();
        String email = txtClientEmail.getText().trim();
        validateClientEmailFormat(email);
        String phone = txtClientPhone.getText().trim();
        return new Client(id, name, email, phone);
    }

    // Valida campos obligatorios del cliente.
    private void validateClientFieldsNotEmpty() {
        if (txtClientId.getText().trim().isEmpty()
                || txtClientName.getText().trim().isEmpty()
                || txtClientEmail.getText().trim().isEmpty()
                || txtClientPhone.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos del cliente son obligatorios.");
        }
    }

    // Valida el formato del correo.
    private void validateClientEmailFormat(String email) {
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }
    }

    // Valida que el ID del cliente no exista.
    private void validateClientIdIsUnique() {
        int id;
        try {
            id = Integer.parseInt(txtClientId.getText().trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID del cliente debe ser un número entero.");
        }

        for (Client client : clientService.getAllClients()) {
            if (client.getId() == id) {
                throw new IllegalArgumentException("Ya existe un cliente con ese ID. Los IDs deben ser diferentes.");
            }
        }
    }

    // Carga el cliente seleccionado en el formulario.
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

    // Limpia el formulario de clientes.
    private void clearClientForm() {
        txtClientId.setText("");
        txtClientName.setText("");
        txtClientEmail.setText("");
        txtClientPhone.setText("");
    }

    // Refresca la tabla de productos.
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

    // Guarda un producto nuevo.
    private void saveProduct() {
        try {
            validateProductFieldsNotEmpty();
            validateProductIdIsUnique();
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

    // Edita el producto seleccionado.
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

    // Elimina el producto seleccionado.
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

    // Construye un producto desde el formulario.
    private Product buildProductFromFields() {
        int id;
        double price;
        int stock;

        try {
            id = Integer.parseInt(txtProductId.getText().trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID del producto debe ser un número entero.");
        }

        String name = txtProductName.getText().trim();
        String size = txtProductSize.getText().trim();
        String color = txtProductColor.getText().trim();

        try {
            price = Double.parseDouble(txtProductPrice.getText().trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El precio del producto debe ser un número válido.");
        }

        try {
            stock = Integer.parseInt(txtProductStock.getText().trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El stock del producto debe ser un número entero.");
        }

        return new Product(id, name, size, color, price, stock);
    }

    // Valida campos obligatorios del producto.
    private void validateProductFieldsNotEmpty() {
        if (txtProductId.getText().trim().isEmpty()
                || txtProductName.getText().trim().isEmpty()
                || txtProductSize.getText().trim().isEmpty()
                || txtProductColor.getText().trim().isEmpty()
                || txtProductPrice.getText().trim().isEmpty()
                || txtProductStock.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos del producto son obligatorios.");
        }
    }

    // Valida que el ID del producto no exista.
    private void validateProductIdIsUnique() {
        int id;
        try {
            id = Integer.parseInt(txtProductId.getText().trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID del producto debe ser un número entero.");
        }

        for (Product product : productService.getAllProducts()) {
            if (product.getId() == id) {
                throw new IllegalArgumentException("Ya existe un producto con ese ID. Los IDs deben ser diferentes.");
            }
        }
    }

    // Carga el producto seleccionado en el formulario.
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

    // Limpia el formulario de productos.
    private void clearProductForm() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtProductSize.setText("");
        txtProductColor.setText("");
        txtProductPrice.setText("");
        txtProductStock.setText("");
    }

    // Inicializa la tabla del carrito.
    private void initializeSalesTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Producto", "Talla", "Cantidad", "Precio U.", "Subtotal"}, 0
        ) {
            @Override
            // Evita edicion directa del carrito.
            public boolean isCellEditable(int row, int column) {
                // La tabla solo muestra el detalle del carrito.
                return false;
            }
        };

        tblSalesDetail.setModel(tableModel);
    }

    // Carga clientes y productos en los desplegables.
    private void loadSalesCombos() {
        // Recarga los desplegables para reflejar el estado actual.
        cbSalesClient.removeAllItems();
        cbSalesProduct.removeAllItems();

        for (Client client : clientService.getAllClients()) {
            cbSalesClient.addItem(client);
        }

        for (Product product : productService.getAllProducts()) {
            cbSalesProduct.addItem(product);
        }
    }

    // Agrega un producto al carrito.
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

    // Finaliza la venta actual.
    private void finalizeSale() {
        try {
            Client selectedClient = (Client) cbSalesClient.getSelectedItem();
            Sale sale = new Sale(selectedClient, currentCart, calculateCartTotal());

            // El servicio coordina el registro de la venta.
            saleService.finalizeSale(sale);

            clearSaleUI();
            refreshSalesHistoryTable();
            refreshProductTable();
            loadSalesCombos();
            JOptionPane.showMessageDialog(this, "La venta fue registrada correctamente.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    // Refresca la tabla del carrito.
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

    // Refresca la tabla del historial.
    private void refreshSalesHistoryTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Fecha y hora", "Cliente", "Total"}, 0
        ) {
            @Override
            // Evita edicion directa del historial.
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Sale> sales = saleService.getAllSales();
        for (int i = 0; i < sales.size(); i++) {
            Sale sale = sales.get(i);
            tableModel.addRow(new Object[]{
                    i + 1,
                    formatSaleCompletedAt(sale),
                    sale.getClient(),
                    sale.getTotal()
            });
        }

        tblSalesHistory.setModel(tableModel);
    }

    // Formatea la fecha de cierre.
    private String formatSaleCompletedAt(Sale sale) {
        if (sale.getCompletedAt() == null) {
            return "";
        }

        return sale.getCompletedAt().format(SALE_DATE_TIME_FORMATTER);
    }

    // Actualiza el total visible de la venta.
    private void updateSaleTotalLabel() {
        lblTotalSale.setText(String.format("$ %.2f", calculateCartTotal()));
    }

    // Calcula el total del carrito.
    private double calculateCartTotal() {
        double total = 0;

        for (SaleItem item : currentCart) {
            total += item.getSubtotal();
        }

        return total;
    }

    // Suma la cantidad de un producto en el carrito.
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

    // Quita del carrito un producto eliminado.
    private void removeDeletedProductFromCart(int productId) {
        // Elimina del carrito productos que ya no existen en la lista principal.
        for (int i = currentCart.size() - 1; i >= 0; i--) {
            if (currentCart.get(i).getProduct().getId() == productId) {
                currentCart.remove(i);
            }
        }
    }

    // Limpia la interfaz de venta.
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

    // Ejecuta la ventana principal.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
