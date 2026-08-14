package org.example;

import javax.swing.*;
import java.awt.*;

public class dangnhap extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRole;
    private JCheckBox chkRemember;
    private JButton btnLogin, btnCancel;

    public dangnhap() {
        setTitle("Đăng Nhập Hệ Thống");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setResizable(false);

        // Tạo Panel chính với bố cục GridBagLayout để căn chỉnh đẹp mắt
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8); // Khoảng cách giữa các phần tử

        // 1. Tên đăng nhập (JTextField)
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);

        // 2. Mật khẩu (JPasswordField)
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Mật khẩu:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        // 3. Vai trò / Quyền hạn (JComboBox)
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Quyền đăng nhập:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        String[] roles = {"Sinh viên", "Giảng viên", "Quản trị viên (Admin)"};
        cbRole = new JComboBox<>(roles);
        panel.add(cbRole, gbc);

        // 4. Ghi nhớ đăng nhập (JCheckBox)
        gbc.gridx = 1; gbc.gridy = 3;
        chkRemember = new JCheckBox("Ghi nhớ tài khoản");
        panel.add(chkRemember, gbc);

        // 5. Nút bấm (JButton)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnLogin = new JButton("Đăng nhập");
        btnCancel = new JButton("Hủy");
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);

        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(buttonPanel, gbc);

        add(panel);

        // --- XỬ LÝ SỰ KIỆN ---

        // Nút Đăng nhập
        btnLogin.addActionListener(e -> handleLogin());

        // Nút Hủy
        btnCancel.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            cbRole.setSelectedIndex(0);
            chkRemember.setSelected(false);
        });
    }

    // Logic kiểm tra tài khoản
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = (String) cbRole.getSelectedItem();
        boolean isRemembered = chkRemember.isSelected();

        // 1. Kiểm tra để trống
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ Tên đăng nhập và Mật khẩu!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Tài khoản mẫu cố định (Tên: admin, Mật khẩu: 123456)
        if (username.equals("admin") && password.equals("123456")) {
            String message = "Đăng nhập thành công!\n"
                    + "Quyền: " + role + "\n"
                    + "Trạng thái ghi nhớ: " + (isRemembered ? "Có" : "Không");

            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Tên đăng nhập hoặc Mật khẩu không chính xác!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}