package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class diemsv extends JFrame {

    private JTextField txtId, txtName;
    private JTextField[][] scoreFields;
    private String[] subjectNames;
    private JTable table;
    private DefaultTableModel tableModel;

    public diemsv() {
        // 1. Mở Cửa Sổ Cấu Hình Môn Học Hiện Đại
        if (!showCustomSetupDialog()) {
            System.exit(0); // Thoát nếu người dùng bấm Hủy
        }

        setTitle("QUẢN LÝ ĐIỂM SINH VIÊN");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 2. Header Tiêu Đề
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(219, 222, 234));
        headerPanel.setPreferredSize(new Dimension(1050, 50));
        JLabel lblTitle = new JLabel("QUẢN LÝ ĐIỂM SINH VIÊN (" + subjectNames.length + " MÔN HỌC)");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(new Color(0, 192, 255));
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // 3. Panel Nhập Dữ Liệu
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin & điểm số"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Thông tin sinh viên
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Mã SV:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; txtId = new JTextField(12); inputPanel.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Họ Tên:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; txtName = new JTextField(12); inputPanel.add(txtName, gbc);

        // Các ô nhập điểm N môn
        scoreFields = new JTextField[subjectNames.length][3];
        int currentGridY = 2;

        for (int i = 0; i < subjectNames.length; i++) {
            gbc.gridx = 0; gbc.gridy = currentGridY; gbc.gridwidth = 2;
            JLabel lblSub = new JLabel("--- Môn: " + subjectNames[i] + " ---");
            lblSub.setForeground(new Color(0, 120, 215));
            lblSub.setFont(new Font("SansSerif", Font.BOLD, 12));
            inputPanel.add(lblSub, gbc);
            gbc.gridwidth = 1;
            currentGridY++;

            gbc.gridx = 0; gbc.gridy = currentGridY; inputPanel.add(new JLabel("CC (10%):"), gbc);
            gbc.gridx = 1; gbc.gridy = currentGridY; scoreFields[i][0] = new JTextField(5); inputPanel.add(scoreFields[i][0], gbc);
            currentGridY++;

            gbc.gridx = 0; gbc.gridy = currentGridY; inputPanel.add(new JLabel("GK (30%):"), gbc);
            gbc.gridx = 1; gbc.gridy = currentGridY; scoreFields[i][1] = new JTextField(5); inputPanel.add(scoreFields[i][1], gbc);
            currentGridY++;

            gbc.gridx = 0; gbc.gridy = currentGridY; inputPanel.add(new JLabel("CK (60%):"), gbc);
            gbc.gridx = 1; gbc.gridy = currentGridY; scoreFields[i][2] = new JTextField(5); inputPanel.add(scoreFields[i][2], gbc);
            currentGridY++;
        }

        // Nút Bấm
        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        btnPanel.add(btnAdd); btnPanel.add(btnEdit); btnPanel.add(btnDelete);

        gbc.gridx = 0; gbc.gridy = currentGridY; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);
        inputPanel.add(btnPanel, gbc);

        JScrollPane inputScroll = new JScrollPane(inputPanel);
        inputScroll.setPreferredSize(new Dimension(340, 0));
        add(inputScroll, BorderLayout.WEST);

        // 4. Cột JTable
        int totalColumns = 2 + (subjectNames.length * 4);
        String[] columns = new String[totalColumns];
        columns[0] = "Mã SV";
        columns[1] = "Họ Tên";

        int colIdx = 2;
        for (String sub : subjectNames) {
            columns[colIdx++] = sub + " (CC)";
            columns[colIdx++] = sub + " (GK)";
            columns[colIdx++] = sub + " (CK)";
            columns[colIdx++] = sub + " (Tổng)";
        }

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 5. Sự Kiện
        btnAdd.addActionListener(e -> addStudent());
        btnEdit.addActionListener(e -> editStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());
    }

    // CỬA SỔ CẤU HÌNH MÔN HỌC
    private boolean showCustomSetupDialog() {
        JDialog dialog = new JDialog((Frame) null, "Cấu Hình Môn Học", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout(10, 10));

        // Header Dialog
        JPanel header = new JPanel();
        header.setBackground(new Color(204, 207, 220));
        JLabel title = new JLabel("THIẾT LẬP MÔN HỌC");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(new Color(0, 192, 255));
        header.add(title);
        dialog.add(header, BorderLayout.NORTH);

        // Center Panel: Chọn N & Bảng nhập tên môn
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel topChoose = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topChoose.add(new JLabel("Số lượng môn học:"));
        JSpinner spinnerN = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1)); // Mặc định 2 môn
        topChoose.add(spinnerN);
        centerPanel.add(topChoose, BorderLayout.NORTH);

        // Model bảng nhập tên môn
        DefaultTableModel subModel = new DefaultTableModel(new String[]{"STT", "Tên Môn Học"}, 0);
        JTable subTable = new JTable(subModel);
        subTable.setRowHeight(24);
        centerPanel.add(new JScrollPane(subTable), BorderLayout.CENTER);

        // Cập nhật số dòng trong bảng dựa theo Spinner
        Runnable updateTableRows = () -> {
            int n = (int) spinnerN.getValue();
            int currentRows = subModel.getRowCount();
            if (n > currentRows) {
                for (int i = currentRows + 1; i <= n; i++) {
                    subModel.addRow(new Object[]{"Môn " + i, "Môn " + i});
                }
            } else if (n < currentRows) {
                for (int i = currentRows - 1; i >= n; i--) {
                    subModel.removeRow(i);
                }
            }
        };

        updateTableRows.run(); // Chạy lần đầu
        spinnerN.addChangeListener(e -> updateTableRows.run());

        dialog.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Nút bấm
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirm = new JButton("Xác Nhận");
        JButton btnCancel = new JButton("Hủy");

        btnConfirm.setBackground(new Color(0, 150, 214));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFocusPainted(false);

        bottomPanel.add(btnConfirm);
        bottomPanel.add(btnCancel);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        boolean[] isSuccess = {false};

        btnConfirm.addActionListener(e -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < subModel.getRowCount(); i++) {
                String val = subModel.getValueAt(i, 1).toString().trim();
                if (val.isEmpty()) val = "Môn " + (i + 1);
                list.add(val);
            }
            subjectNames = list.toArray(new String[0]);
            isSuccess[0] = true;
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
        return isSuccess[0];
    }

    private double calculateFinalScore(double cc, double gk, double ck) {
        double score = cc * 0.1 + gk * 0.3 + ck * 0.6;
        return Math.round(score * 100.0) / 100.0;
    }

    private void addStudent() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã SV và Họ Tên!");
            return;
        }

        try {
            Object[] rowData = new Object[2 + subjectNames.length * 4];
            rowData[0] = id;
            rowData[1] = name;

            int colIdx = 2;
            for (int i = 0; i < subjectNames.length; i++) {
                double cc = Double.parseDouble(scoreFields[i][0].getText().trim());
                double gk = Double.parseDouble(scoreFields[i][1].getText().trim());
                double ck = Double.parseDouble(scoreFields[i][2].getText().trim());
                double total = calculateFinalScore(cc, gk, ck);

                rowData[colIdx++] = cc;
                rowData[colIdx++] = gk;
                rowData[colIdx++] = ck;
                rowData[colIdx++] = total;
            }

            tableModel.addRow(rowData);
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm số các môn phải là số thực hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa!");
            return;
        }

        String id = txtId.getText().trim();
        String name = txtName.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã SV và Họ Tên!");
            return;
        }

        try {
            tableModel.setValueAt(id, row, 0);
            tableModel.setValueAt(name, row, 1);

            int colIdx = 2;
            for (int i = 0; i < subjectNames.length; i++) {
                double cc = Double.parseDouble(scoreFields[i][0].getText().trim());
                double gk = Double.parseDouble(scoreFields[i][1].getText().trim());
                double ck = Double.parseDouble(scoreFields[i][2].getText().trim());
                double total = calculateFinalScore(cc, gk, ck);

                tableModel.setValueAt(cc, row, colIdx++);
                tableModel.setValueAt(gk, row, colIdx++);
                tableModel.setValueAt(ck, row, colIdx++);
                tableModel.setValueAt(total, row, colIdx++);
            }

            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm số các môn phải là số thực hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa!");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Xác nhận xóa sinh viên này?", "Xóa", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            clearFields();
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row != -1) {
            txtId.setText(tableModel.getValueAt(row, 0).toString());
            txtName.setText(tableModel.getValueAt(row, 1).toString());

            int colIdx = 2;
            for (int i = 0; i < subjectNames.length; i++) {
                scoreFields[i][0].setText(tableModel.getValueAt(row, colIdx++).toString());
                scoreFields[i][1].setText(tableModel.getValueAt(row, colIdx++).toString());
                scoreFields[i][2].setText(tableModel.getValueAt(row, colIdx++).toString());
                colIdx++;
            }
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        for (int i = 0; i < subjectNames.length; i++) {
            scoreFields[i][0].setText("");
            scoreFields[i][1].setText("");
            scoreFields[i][2].setText("");
        }
        table.clearSelection();
    }


}