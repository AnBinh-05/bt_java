package org.example;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class WelcomeApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Welcome");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);


            frame.setVisible(true);


            JOptionPane.showMessageDialog(
                    frame,
                    "Welcome to Java Swing",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );


            System.exit(0);
        });
    }
}