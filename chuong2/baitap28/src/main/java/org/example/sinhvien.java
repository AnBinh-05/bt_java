package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class sinhvien extends JFrame {
    private JTable table;

    public sinhvien(){
        setTitle("DANH SÁCH SINH VIÊN");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] thongtin = {"Tên: ", "Tuổi: ", "Điểm: "};

        Object[][] data = {
                {"Nguyễn Tâm Công", 21, 9.6 },
                {"Nguyễn Việt Anh", 22, 8,8},
                {"Bùi Xuân Huấn", 90, 9.5},
                {"Khánh Sky", 80, 5.5},
                {"Vua Quạt", 50, 7.8}
        };

        DefaultTableModel model = new DefaultTableModel(data, thongtin);
        table = new JTable(model);



        table.setFont(new Font("SansSerì", Font.PLAIN, 14));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

    }

}
