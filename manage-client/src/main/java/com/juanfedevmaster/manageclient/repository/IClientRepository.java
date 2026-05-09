package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Client;
import java.util.List;

// Define las operaciones de almacenamiento de clientes.
public interface IClientRepository {
    // Guarda un cliente.
    void save(Client client);

    // Indica si existe un cliente por ID.
    boolean existsById(int id);

    // Actualiza un cliente existente.
    void update(Client client);

    // Elimina un cliente por ID.
    void deleteById(int id);

    // Lista todos los clientes.
    List<Client> getAllClients();
    
}
