package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Client;
import com.juanfedevmaster.manageclient.repository.IClientRepository;
import java.util.List;

public class ClientService {
    private final IClientRepository clientRepository;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void registrarCliente(int id, String name, String email, String phoneNumber) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }

        Client client = new Client(id, name, email, phoneNumber);
        clientRepository.save(client);
    }

    public void saveClient(Client client) {
        validateClient(client);

        // Valida el ID antes de guardar para evitar duplicados.
        if (clientRepository.existsById(client.getId())) {
            throw new IllegalArgumentException("Ya existe un cliente con el ID " + client.getId() + ".");
        }

        clientRepository.save(client);
    }

    public void updateClient(Client client) {
        validateClient(client);
        clientRepository.update(client);
    }

    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

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
