package org.example;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator mayTinh = new Calculator();
            mayTinh.setVisible(true);
        });
    }
}