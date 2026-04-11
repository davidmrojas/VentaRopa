package com.juanfedevmaster.manageclient.repository;

import com.juanfedevmaster.manageclient.model.Client;
import java.util.List;

public interface IClientRepository {
    void save(Client client);

    boolean existsById(int id);

    void update(Client client);

    void deleteById(int id);

    List<Client> getAllClients();
    
}
