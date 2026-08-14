package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ql extends JFrame {
    private JTextField txtId, txtName, txtGpa;
    private JButton btnAdd, btnEdit, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;

    // Quản lý dữ liệu tách riêng
    private StudentManager manager = new StudentManager();

    public ql() {
        setTitle("Quản Lý Sinh Viên");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel Nhập thông tin (Bắc)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã Sinh Viên:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Họ và Tên:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Điểm GPA:"));
        txtGpa = new JTextField();
        inputPanel.add(txtGpa);

        add(inputPanel, BorderLayout.NORTH);

        // 2. JTable hiển thị dữ liệu (Giữa)
        tableModel = new DefaultTableModel(new String[]{"Mã SV", "Họ Tên", "Điểm GPA"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Sự kiện Click chuột vào dòng trong Bảng để đổ dữ liệu lên ô nhập
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtGpa.setText(tableModel.getValueAt(row, 2).toString());
                txtId.setEditable(false); // Không cho sửa Mã SV khi đang chọn
            }
        });

        // 3. Panel Các nút chức năng (Nam)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        add(buttonPanel, BorderLayout.SOUTH);

        // --- XỬ LÝ SỰ KIỆN ---

        // Nút THÊM
        btnAdd.addActionListener(e -> {
            try {
                String id = txtId.getText().trim();
                String name = txtName.getText().trim();
                double gpa = Double.parseDouble(txtGpa.getText().trim());

                if (id.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không được để trống thông tin!");
                    return;
                }

                Student s = new Student(id, name, gpa);
                if (manager.addStudent(s)) {
                    tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getGpa()});
                    clearInput();
                    JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Mã Sinh Viên đã tồn tại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Điểm GPA phải là số hợp lệ!");
            }
        });

        // Nút SỬA
        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sinh viên trong bảng để sửa!");
                return;
            }

            try {
                String name = txtName.getText().trim();
                double gpa = Double.parseDouble(txtGpa.getText().trim());

                manager.updateStudent(selectedRow, name, gpa);

                // Cập nhật lại JTable
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(gpa, selectedRow, 2);

                clearInput();
                JOptionPane.showMessageDialog(this, "Sửa thông tin thành công!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Điểm GPA phải là số hợp lệ!");
            }
        });

        // Nút XÓA
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sinh viên trong bảng để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sinh viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                manager.deleteStudent(selectedRow);
                tableModel.removeRow(selectedRow); // Xóa khỏi giao diện
                clearInput();
                JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
            }
        });

        // Nút LÀM MỚI
        btnClear.addActionListener(e -> clearInput());
    }

    private void clearInput() {
        txtId.setText("");
        txtName.setText("");
        txtGpa.setText("");
        txtId.setEditable(true);
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ql().setVisible(true);
        });
    }
}