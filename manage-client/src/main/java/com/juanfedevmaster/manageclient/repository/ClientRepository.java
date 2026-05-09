package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Client;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Guarda clientes en memoria.
public class ClientRepository implements IClientRepository {
    private final Map<Integer, Client> clients;

    // Prepara el almacenamiento de clientes.
    public ClientRepository() {
        this.clients = new HashMap<>();
    }

    @Override
    // Guarda un cliente por ID.
    public void save(Client client) {
        // El mapa permite buscar por ID de forma directa.
        clients.put(client.getId(), client);
    }

    @Override
    // Verifica si el ID ya existe.
    public boolean existsById(int id) {
        return clients.containsKey(id);
    }

    @Override
    // Actualiza los datos de un cliente.
    public void update(Client client) {
        Client currentClient = clients.get(client.getId());

        if (currentClient == null) {
            throw new IllegalArgumentException("No se encontró el cliente con ID " + client.getId() + ".");
        }

        currentClient.setName(client.getName());
        currentClient.setEmail(client.getEmail());
        currentClient.setPhoneNumber(client.getPhoneNumber());
    }

    @Override
    // Elimina un cliente existente.
    public void deleteById(int id) {
        if (!clients.containsKey(id)) {
            throw new IllegalArgumentException("No se encontró el cliente con ID " + id + ".");
        }

        clients.remove(id);
    }

    @Override
    // Devuelve una copia de los clientes.
    public List<Client> getAllClients() {
        return new ArrayList<>(clients.values());
    }
}
