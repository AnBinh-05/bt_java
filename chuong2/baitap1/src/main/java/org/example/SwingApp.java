package org.example;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SwingApp {
    public static void main(String[] args) {
        // Chạy giao diện trên Event Dispatch Thread (EDT) để đảm bảo an toàn luồng
        SwingUtilities.invokeLater(() -> {
            // 1. Tạo JFrame với tiêu đề "My First Swing App"
            JFrame frame = new JFrame("My First Swing App");

            // Đặt hành động mặc định khi nhấn nút tắt (X) để dừng chương trình
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 2. Đặt kích thước 400x300
            frame.setSize(400, 300);

            // Căn giữa màn hình
            frame.setLocationRelativeTo(null);

            // 3. Tạo JLabel hiển thị "Hello World" và căn giữa theo chiều ngang/dọc
            JLabel label = new JLabel("Hello World", SwingConstants.CENTER);

            // Thêm JLabel vào cửa sổ
            frame.add(label);

            // Hiển thị cửa sổ
            frame.setVisible(true);
        });
    }
}