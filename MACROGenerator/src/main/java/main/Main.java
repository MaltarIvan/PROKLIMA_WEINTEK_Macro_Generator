package main;

import GUI.MainForm;
import javax.swing.*;

public class Main {
    private static final String WINDOW_TITLE =  "Macro_Generator";

    public static void main(String args[]) {
        makeGUI();
    }

    private static void makeGUI() {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setContentPane(new MainForm().getMainView());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }
}