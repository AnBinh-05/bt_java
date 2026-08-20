package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class timfile extends JFrame {

    private JTextField txtFilePath;
    private JTextField txtKeyword;
    private JButton btnBrowse;
    private JButton btnSearch;
    private JTextArea txtResult;
    private JLabel lblStatus;

    private File selectedFile;

    public timfile() {
        setTitle("Tìm Kiếm Từ Khóa Trong File - MiHoYo Tools");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. PANEL ĐIỀU KHIỂN (Phía trên)
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dòng 1: Chọn File
        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("Chọn File (.txt):"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtFilePath = new JTextField();
        txtFilePath.setEditable(false);
        topPanel.add(txtFilePath, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        gbc.weightx = 0;
        btnBrowse = new JButton("Duyệt...");
        topPanel.add(btnBrowse, gbc);

        // Dòng 2: Nhập Từ Khóa & Nút Tìm
        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(new JLabel("Từ khóa:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtKeyword = new JTextField();
        topPanel.add(txtKeyword, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        gbc.weightx = 0;
        btnSearch = new JButton("Tìm Kiếm");
        btnSearch.setBackground(new Color(0, 150, 214));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        topPanel.add(btnSearch, gbc);

        add(topPanel, BorderLayout.NORTH);

        // 2. KHIỂN HIỂN THỊ KẾT QUẢ (Ở giữa)
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(txtResult);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Kết quả tìm kiếm"));
        add(scrollPane, BorderLayout.CENTER);

        // 3. THANH TRẠNG THÁI (Phía dưới)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblStatus = new JLabel("Trạng thái: Sẵn sàng");
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 12));
        bottomPanel.add(lblStatus);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- XỬ LÝ SỰ KIỆN NÚT DUYỆT FILE ---
        btnBrowse.addActionListener(e -> chooseFile());

        // --- XỬ LÝ SỰ KIỆN NÚT TÌM KIẾM ---
        btnSearch.addActionListener(e -> executeSearch());
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file văn bản cần tìm kiếm");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            txtFilePath.setText(selectedFile.getAbsolutePath());
        }
    }

    private void executeSearch() {
        if (selectedFile == null || !selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một file .txt hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String keyword = txtKeyword.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa cần tìm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Khóa các nút trong lúc tìm kiếm
        btnSearch.setEnabled(false);
        btnBrowse.setEnabled(false);
        txtResult.setText("");
        lblStatus.setText("Trạng thái: Đang đọc và tìm kiếm...");

        // --- SỬ DỤNG SWINGWORKER ĐỂ XỬ LÝ NỀN ---
        SwingWorker<Integer, String> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws Exception {
                int matchCount = 0;
                int lineNumber = 0;
                String lowerKeyword = keyword.toLowerCase(); // Chuyển từ khóa về chữ thường

                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        // Kiểm tra không phân biệt hoa/thường
                        if (line.toLowerCase().contains(lowerKeyword)) {
                            matchCount++;
                            String resultText = String.format("Dòng %d: %s\n", lineNumber, line);
                            publish(resultText); // Gửi kết quả từng dòng về UI
                        }
                    }
                }
                return matchCount;
            }

            @Override
            protected void process(List<String> chunks) {
                // Nhận từng dòng tìm thấy và append vào JTextArea trên UI Thread
                for (String text : chunks) {
                    txtResult.append(text);
                }
            }

            @Override
            protected void done() {
                try {
                    int totalMatches = get();
                    lblStatus.setText(String.format("Trạng thái: Tìm thấy tổng cộng %d dòng chứa từ khóa '%s'", totalMatches, keyword));
                } catch (Exception ex) {
                    lblStatus.setText("Trạng thái: Có lỗi xảy ra trong quá trình đọc file!");
                    ex.printStackTrace();
                } finally {
                    // Bật lại các nút sau khi hoàn thành
                    btnSearch.setEnabled(true);
                    btnBrowse.setEnabled(true);
                }
            }
        };

        worker.execute(); // Khởi chạy SwingWorker
    }


}