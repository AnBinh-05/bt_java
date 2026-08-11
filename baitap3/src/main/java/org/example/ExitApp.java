package org.example;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;

public class ExitApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Tạo JFrame kích thước 300x200
            JFrame frame = new JFrame("Exit Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 60)); // Căn giữa nút theo chiều ngang và chiều dọc
            frame.setLocationRelativeTo(null);

            
            JButton btnExit = new JButton("Exit");



            btnExit.addActionListener(e -> {
                System.exit(0);
            });

            frame.add(btnExit);
            frame.setVisible(true);
        });
    }
}