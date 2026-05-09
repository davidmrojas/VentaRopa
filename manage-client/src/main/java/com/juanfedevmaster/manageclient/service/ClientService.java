package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Client;
import com.juanfedevmaster.manageclient.repository.IClientRepository;
import java.util.List;

// Aplica las reglas de negocio de clientes.
public class ClientService implements IClientService {
    private final IClientRepository clientRepository;

    // Recibe el repositorio de clientes.
    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    // Valida y guarda un cliente nuevo.
    public void saveClient(Client client) {
        validateClient(client);

        // Valida el ID antes de guardar para evitar duplicados.
        if (clientRepository.existsById(client.getId())) {
            throw new IllegalArgumentException("Ya existe un cliente con el ID " + client.getId() + ".");
        }

        clientRepository.save(client);
    }

    @Override
    // Valida y actualiza un cliente.
    public void updateClient(Client client) {
        validateClient(client);
        clientRepository.update(client);
    }

    @Override
    // Elimina un cliente por ID.
    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }

    @Override
    // Lista todos los clientes.
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    // Valida los datos minimos del cliente.
    private void validateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Los datos del cliente no son válidos.");
        }

        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
    }
}
