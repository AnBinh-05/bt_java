package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;





    public class bai4 extends JFrame {
        private JTextField nhapa;
        private JTextField nhapb;
        private JButton kiemtra;

        private JTextField nhapc;
        private JPanel tamgiac;
        private JLabel ketqua;
        private JLabel kq;


        public bai4() {

            setTitle("Phân Loại Tam Giác");
            setContentPane(tamgiac);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(450,250);
            setLocationRelativeTo(null);

           


            kiemtra.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Đọc dữ liệu từ ô nhập
                        double a = Double.parseDouble(nhapa.getText().trim());
                        double b = Double.parseDouble(nhapb.getText().trim());
                        double c = Double.parseDouble(nhapc.getText().trim());

                        // Kiểm tra điều kiện tồn tại tam giác
                        if (a <= 0 || b <= 0 || c <= 0 || (a + b <= c) || (a + c <= b) || (b + c <= a)) {
                            ketqua.setText(" Không phải tam giác!");
                            ketqua.setForeground(Color.RED);
                            return;
                        }

                        // Kiểm tra điều kiện tam giác vuông (Định lý Pytago)
                        boolean isVuong = (a * a + b * b == c * c) ||
                                (a * a + c * c == b * b) ||
                                (b * b + c * c == a * a);

                        // Phân loại tam giác
                        ketqua.setForeground(new Color(40, 167, 69)); // Màu xanh lá
                        if (a == b && b == c) {
                            ketqua.setText(" Tam giác ĐỀU");
                        } else if (a == b || b == c || a == c) {
                            if (isVuong) {
                                ketqua.setText(" Tam giác VUÔNG CÂN");
                            } else {
                                ketqua.setText(" Tam giác CÂN");
                            }
                        } else if (isVuong) {
                            ketqua.setText(" Tam giác VUÔNG");
                        } else {
                            ketqua.setText(" Tam giác THƯỜNG");
                        }


                    } catch (NumberFormatException ex) {
                        // Hiện hộp thoại báo lỗi nếu nhập sai định dạng số
                        JOptionPane.showMessageDialog(
                                bai4.this,
                                "Vui lòng nhập số hợp lệ vào cả 3 cạnh!",
                                "Lỗi nhập liệu",
                                JOptionPane.ERROR_MESSAGE
                        );

                    }

                }
            });

        }
    }










