package org.example;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.*;

public class SumApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {


            JFrame frame = new JFrame("Tính Tổng 2 Số");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLayout(new FlowLayout());
            frame.setLocationRelativeTo(null);


            JLabel label1 = new JLabel("Số A:");
            JTextField txtNum1 = new JTextField(10);

            JLabel label2 = new JLabel("Số B:");
            JTextField txtNum2 = new JTextField(10);

            JButton btnSum = new JButton("Tính Tổng");

            JLabel lblResult = new JLabel("Kết quả: ");

            btnSum.setBackground(new Color(0, 123, 255));
            btnSum.setForeground(Color.WHITE);
            btnSum.setFocusPainted(false);

            // 2. Định dạng chữ đậm (Bold) cho kết quả
            lblResult.setFont(new Font("Arial", Font.BOLD, 14));


            btnSum.addActionListener(e -> {
                try {

                    double a = Double.parseDouble(txtNum1.getText().trim());
                    double b = Double.parseDouble(txtNum2.getText().trim());


                    double sum = a + b;
                    lblResult.setText("Kết quả: " + sum);

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Vui lòng chỉ nhập số hợp lệ!",
                            "Lỗi nhập liệu",
                            JOptionPane.ERROR_MESSAGE
                    );
                    lblResult.setText("Kết quả: ");
                }
            });


            frame.add(label1);
            frame.add(txtNum1);
            frame.add(label2);
            frame.add(txtNum2);
            frame.add(btnSum);
            frame.add(lblResult);

            frame.setVisible(true);
        });
    }
}