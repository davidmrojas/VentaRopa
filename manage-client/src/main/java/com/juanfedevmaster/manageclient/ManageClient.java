package com.juanfedevmaster.manageclient;

import com.juanfedevmaster.manageclient.view.MainFrame;
import javax.swing.SwingUtilities;

// Punto de entrada de la aplicacion.
public class ManageClient {
    // Inicia la interfaz principal.
    public static void main(String[] args) {
        // Inicia la ventana principal de la aplicacion.
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
