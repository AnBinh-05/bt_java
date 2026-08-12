package org.example;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.io.File;

public class ImageViewerApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Image Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



            String imagePath = "anh.jpg";


            File imgFile = new File(imagePath);
            if (!imgFile.exists()) {
                JOptionPane.showMessageDialog(null,
                        "Không tìm thấy file ảnh tại: " + imgFile.getAbsolutePath(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }




            ImageIcon imageIcon = new ImageIcon(imagePath);
            JLabel imageLabel = new JLabel(imageIcon);


            frame.add(imageLabel);


            frame.pack();


            frame.setLocationRelativeTo(null);


            frame.setVisible(true);
        });
    }
}