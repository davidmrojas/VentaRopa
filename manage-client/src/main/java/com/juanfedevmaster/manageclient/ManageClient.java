package com.juanfedevmaster.manageclient;

import com.juanfedevmaster.manageclient.view.MainFrame;
import javax.swing.SwingUtilities;

public class ManageClient {
    public static void main(String[] args) {
        // Inicia la ventana principal de la aplicacion.
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
