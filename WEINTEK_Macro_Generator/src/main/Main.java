package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String args[]) {
        JFrame frame = new JFrame("Hello World!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));
        Container pane = frame.getContentPane();
        JLabel label = new JLabel();
        label.setText("Hello World!");
        pane.add(label);
        frame.pack();
        frame.setVisible(true);
    }
}
