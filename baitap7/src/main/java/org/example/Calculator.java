package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JTextField display;
    private JTextArea historyArea;
    private double num1 = 0;
    private String operator = "";
    private boolean startNewInput = true;

    public Calculator() {
        setTitle("Máy Tính Mini");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Phần Lịch sử và Ô hiển thị kết quả
        JPanel topPanel = new JPanel(new BorderLayout());

        // Khu vực Lịch sử
        historyArea = new JTextArea(5, 20);
        historyArea.setEditable(false); // Không cho phép sửa tay vào lịch sử
        historyArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(historyArea); // Thanh cuộn

        // Ô nhập/Kết quả
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);

        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.add(display, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // 2. Phần Bàn phím (Lưới 4x4)
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("SansSerif", Font.BOLD, 20));
            btn.addActionListener(new ButtonClickListener());
            buttonPanel.add(btn);
        }

        // Thêm khoảng cách (padding) cho viền ngoài
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buttonPanel, BorderLayout.CENTER);
    }

    // 3. Lớp xử lý sự kiện khi bấm nút
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // Nếu bấm số
            if ("0123456789".contains(command)) {
                if (startNewInput) {
                    display.setText(command);
                    startNewInput = false;
                } else {
                    display.setText(display.getText() + command);
                }
            }
            // Nếu bấm phép tính
            else if ("+-*/".contains(command)) {
                num1 = Double.parseDouble(display.getText());
                operator = command;
                startNewInput = true;
            }
            // Nếu bấm Dấu Bằng (=)
            else if ("=".equals(command)) {
                if (operator.isEmpty()) return;

                double num2 = Double.parseDouble(display.getText());
                double result = 0;
                boolean error = false;

                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/":
                        if (num2 == 0) {
                            error = true; // Xử lý lỗi chia cho 0
                        } else {
                            result = num1 / num2;
                        }
                        break;
                }

                if (error) {
                    display.setText("Lỗi: Chia cho 0");
                    historyArea.append(num1 + " / 0 = Lỗi\n");
                } else {
                    // Kiểm tra nếu là số nguyên thì bỏ đuôi .0
                    String resultStr = (result == (long) result) ? String.format("%d", (long) result) : String.valueOf(result);
                    String num1Str = (num1 == (long) num1) ? String.format("%d", (long) num1) : String.valueOf(num1);
                    String num2Str = (num2 == (long) num2) ? String.format("%d", (long) num2) : String.valueOf(num2);

                    display.setText(resultStr);
                    historyArea.append(num1Str + " " + operator + " " + num2Str + " = " + resultStr + "\n");
                }

                startNewInput = true;
                operator = "";
            }
            // Nếu bấm Clear (C)
            else if ("C".equals(command)) {
                display.setText("0");
                num1 = 0;
                operator = "";
                startNewInput = true;
            }
        }
    }
}