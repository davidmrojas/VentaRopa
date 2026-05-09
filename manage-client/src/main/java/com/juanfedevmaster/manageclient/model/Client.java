package com.juanfedevmaster.manageclient.model;

// Representa un cliente registrado.
public class Client {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;

    // Crea un cliente vacio.
    public Client() {
    }

    // Crea un cliente con sus datos principales.
    public Client(int id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Devuelve el ID del cliente.
    public int getId() {
        return id;
    }

    // Actualiza el ID del cliente.
    public void setId(int id) {
        this.id = id;
    }

    // Devuelve el nombre del cliente.
    public String getName() {
        return name;
    }

    // Actualiza el nombre del cliente.
    public void setName(String name) {
        this.name = name;
    }

    // Devuelve el correo del cliente.
    public String getEmail() {
        return email;
    }

    // Actualiza el correo del cliente.
    public void setEmail(String email) {
        this.email = email;
    }

    // Devuelve el telefono del cliente.
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Actualiza el telefono del cliente.
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    // Devuelve el texto visible en los desplegables.
    public String toString() {
        // Muestra un texto claro en los desplegables.
        return name + " - " + email;
    }
}
