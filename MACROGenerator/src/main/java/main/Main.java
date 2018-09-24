package main;

import entities.States;
import exceptions.WrongFormatException;
import utilities.ConfigMain;
import utilities.ConfigSignals;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final String WINDOW_TITLE =  "Macro_Generator";

    public static void main(String args[]) {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));
        Container pane = frame.getContentPane();
        JLabel label = new JLabel();
        label.setText("Hello World");
        pane.add(label);
        frame.pack();
        frame.setVisible(true);

        ConfigMain configMain = new ConfigMain("config\\config.txt");
        try {
            configMain.getConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }

        ConfigSignals configSignals = new ConfigSignals("config\\Signals.xlsx");
        try {
            configSignals.getConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
        System.out.println("Config done!");
    }
}