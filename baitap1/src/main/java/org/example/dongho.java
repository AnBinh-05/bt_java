package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class dongho extends JFrame {
    private JTextField txtSeconds;
    private JButton btnStart;
    private JLabel lblTimer;
    private SwingWorker<Void, Integer> worker;

    public dongho() {
        setTitle("Đồng Hồ Đếm Ngược");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        // 1. Panel nhập thời gian (số giây)
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Nhập số giây:"));
        txtSeconds = new JTextField(10);
        inputPanel.add(txtSeconds);

        // 2. Panel chứa Nút Bắt đầu
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnStart = new JButton("Bắt đầu");
        buttonPanel.add(btnStart);

        // 3. Label hiển thị thời gian còn lại
        lblTimer = new JLabel("00", SwingConstants.CENTER);
        lblTimer.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTimer.setForeground(Color.RED);

        add(inputPanel);
        add(buttonPanel);
        add(lblTimer);

        // Sự kiện khi bấm nút Bắt đầu
        btnStart.addActionListener(e -> dem());
    }

    private void dem() {
        try {
            int totalSeconds = Integer.parseInt(txtSeconds.getText().trim());

            if (totalSeconds <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số giây lớn hơn 0!");
                return;
            }

            // Vô hiệu hóa nút và ô nhập liệu trong lúc đếm ngược
            btnStart.setEnabled(false);
            txtSeconds.setEditable(false);

            // Khởi tạo SwingWorker: <Void: Loại kết quả trả về cuối cùng, Integer: Loại dữ liệu cập nhật liên tục>
            worker = new SwingWorker<Void, Integer>() {

                // Hàm chạy luồng nền (Background Thread) - Không làm đóng băng UI
                @Override
                protected Void doInBackground() throws Exception {
                    for (int i = totalSeconds; i >= 0; i--) {
                        publish(i); // Gửi giá trị số giây còn lại sang process()
                        Thread.sleep(1000); // Tạm dừng 1 giây
                    }
                    return null;
                }

                // Hàm chạy trên EDT (Event Dispatch Thread) - Cập nhật giao diện an toàn
                @Override
                protected void process(List<Integer> chunks) {
                    int currentSecond = chunks.get(chunks.size() - 1);
                    lblTimer.setText(String.valueOf(currentSecond));
                }

                // Hàm thực thi khi hoàn thành tác vụ
                @Override
                protected void done() {
                    lblTimer.setText("Hết giờ!");
                    btnStart.setEnabled(true);
                    txtSeconds.setEditable(true);
                    JOptionPane.showMessageDialog(dongho.this, "Đã hết thời gian!");
                }
            };

            // Khởi chạy SwingWorker
            worker.execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số giây nhập vào phải là một số nguyên hợp lệ!");
        }
    }
}