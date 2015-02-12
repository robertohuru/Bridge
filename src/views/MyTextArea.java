/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class MyTextArea extends JPanel {

    final ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/JUBif.jpg"));
    Image image = imageIcon.getImage();
    private BufferedImage background;
    public MyTextArea(){
        setOpaque(false);
        
        
    }
    public void paintComponent(Graphics g) {
        
        g.drawImage(image, 0, 0, this);
        //g.dispose();
        super.paintComponent(g);
    }
}
