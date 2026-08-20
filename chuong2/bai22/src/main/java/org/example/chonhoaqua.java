package org.example;

import javax.swing.*;
import java.awt.*;

public class chonhoaqua extends JFrame {
    private JComboBox<String> cbFruits;
    private JLabel lblResult;

    public chonhoaqua() {

        setTitle("Chọn Trái Cây");
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        String[] hoaqua= {"CAM", "CHUỐI", "ĐÀO", "LÊ", "HỒNG", "DƯA"};
        cbFruits = new JComboBox<>(hoaqua);

        lblResult = new JLabel("chưa chọn");
        lblResult.setFont(new Font("SansSerif", Font.BOLD, 14));

        cbFruits.addActionListener(e ->{
            String selectedFruit = (String) cbFruits.getSelectedItem();
            lblResult.setText("bạn chọn :"+ selectedFruit);
        });

        add(new JLabel("Chọn loại trái cây:"));
        add(cbFruits);
        add(lblResult);
    }
}