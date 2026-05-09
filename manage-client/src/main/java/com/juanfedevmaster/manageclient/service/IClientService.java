package com.juanfedevmaster.manageclient.service;

import com.juanfedevmaster.manageclient.model.Client;
import java.util.List;

public interface IClientService {
    void saveClient(Client client);

    void updateClient(Client client);

    void deleteClient(int id);

    List<Client> getAllClients();
}
