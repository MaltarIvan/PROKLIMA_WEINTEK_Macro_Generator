package main;

import exceptions.ConfigurationNotDoneException;
import exceptions.WrongFormatException;
import generators.HMIInit;
import generators.StptDigitalInit;
import utilities.ConfigMain;
import utilities.ConfigSignals;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private static final String MAIN_CONFIG_BUTTON_LABEL = "Main Config";
    private static final String DIGITAL_STPTS_BUTTON_LABEL = "Digital Stpts";
    private static final String OUTPUT_BUTTON_LABEL = "Output";
    private static final String GENERATE_BUTTON_LABEL = "Generate";

    private static JButton uploadMainConfigButton;
    private static JButton uploadDigitalStptsButton;
    private static JButton outputButton;
    private static JButton generateButton;

    private static final String WINDOW_TITLE =  "Macro_Generator";

    public static void main(String args[]) {
        makeGUI();

        System.out.println("Config start...");
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

        try {
            String path = "output";
            HMIInit.generateMacro(configMain, configSignals, path);
            StptDigitalInit.generateStptMainDigitalMacro(configMain, configSignals, path);
            StptDigitalInit.generateStptAdvancedDigitalMacro(configMain, configSignals, path);
            StptDigitalInit.generateStptAdvancedInputsMacro(configMain, configSignals, path);
            StptDigitalInit.generateStptAdvancedFanMacro(configMain, configSignals, path);
            StptDigitalInit.generateStptAdvancedTempMacro(configMain, configSignals, path);
        } catch (ConfigurationNotDoneException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeGUI() {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));
        Container pane = frame.getContentPane();

        uploadMainConfigButton = new JButton(MAIN_CONFIG_BUTTON_LABEL);
        uploadDigitalStptsButton = new JButton(DIGITAL_STPTS_BUTTON_LABEL);
        outputButton = new JButton(OUTPUT_BUTTON_LABEL);
        generateButton = new JButton(GENERATE_BUTTON_LABEL);

        pane.setLayout(new GridLayout(2, 2));
        pane.add(uploadMainConfigButton, BorderLayout.WEST);
        pane.add(uploadDigitalStptsButton, BorderLayout.WEST);
        pane.add(outputButton, BorderLayout.WEST);
        pane.add(generateButton, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }
}