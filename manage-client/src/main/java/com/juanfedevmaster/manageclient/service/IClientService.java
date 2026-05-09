package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Client;
import java.util.List;

// Define las reglas de negocio para clientes.
public interface IClientService {
    // Guarda un cliente nuevo.
    void saveClient(Client client);

    // Actualiza un cliente existente.
    void updateClient(Client client);

    // Elimina un cliente por ID.
    void deleteClient(int id);

    // Lista todos los clientes.
    List<Client> getAllClients();
}
