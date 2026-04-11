package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Client;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository implements IClientRepository {
    private final Map<Integer, Client> clients;

    public ClientRepository() {
        this.clients = new HashMap<>();
    }

    @Override
    public void save(Client client) {
        // El Map permite buscar por ID de forma directa.
        clients.put(client.getId(), client);
    }

    @Override
    public boolean existsById(int id) {
        return clients.containsKey(id);
    }

    @Override
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
    public void deleteById(int id) {
        if (!clients.containsKey(id)) {
            throw new IllegalArgumentException("No se encontró el cliente con ID " + id + ".");
        }

        clients.remove(id);
    }

    @Override
    public List<Client> getAllClients() {
        return new ArrayList<>(clients.values());
    }
}
