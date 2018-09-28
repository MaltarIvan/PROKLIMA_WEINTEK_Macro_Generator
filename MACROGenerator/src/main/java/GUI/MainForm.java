package GUI;

import exceptions.ConfigurationNotDoneException;
import exceptions.WrongFormatException;
import generators.HMIInit;
import generators.ReadInputs;
import generators.StptDigitalInit;
import utilities.ConfigInputs;
import utilities.ConfigMain;
import utilities.ConfigDigitalStpts;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.channels.FileChannel;

public class MainForm {
    static private final String newLine = "\n";

    private JTextField mainConfigPath;
    private JPanel mainView;
    private JTextField digitalStptPath;
    private JButton getDigitalStpt;
    private JButton generateButton;
    private JButton getMainConfig;
    private JTextArea logArea;
    private JTextField inputsPath;
    private JButton getInputsFileButton;

    private JFileChooser fileChooser;

    public MainForm() {
        fileChooser = new JFileChooser();

        generateButton.addActionListener(new GenerateBtnClicked(this));
        getMainConfig.addActionListener(new GetMainConfigBtnClicked());
        getDigitalStpt.addActionListener(new GetDigitalStptBtnClicked());
        getInputsFileButton.addActionListener(new GetInputsBtnClicked());
    }

    public JTextField getMainConfigPath() {
        return mainConfigPath;
    }

    public void setMainConfigPath(JTextField mainConfigPath) {
        this.mainConfigPath = mainConfigPath;
    }

    public JPanel getMainView() {
        return mainView;
    }

    public void setMainView(JPanel mainView) {
        this.mainView = mainView;
    }

    public JTextField getDigitalStptPath() {
        return digitalStptPath;
    }

    public void setDigitalStptPath(JTextField digitalStptPath) {
        this.digitalStptPath = digitalStptPath;
    }

    public JButton getGetDigitalStpt() {
        return getDigitalStpt;
    }

    public void setGetDigitalStpt(JButton getDigitalStpt) {
        this.getDigitalStpt = getDigitalStpt;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public void setGenerateButton(JButton generateButton) {
        this.generateButton = generateButton;
    }

    public JButton getGetMainConfig() {
        return getMainConfig;
    }

    public void setGetMainConfig(JButton getMainConfig) {
        this.getMainConfig = getMainConfig;
    }

    public JTextArea getLogArea() {
        return logArea;
    }

    public void setLogArea(JTextArea logArea) {
        this.logArea = logArea;
    }

    private class GenerateBtnClicked implements ActionListener {
        MainForm mainForm;

        GenerateBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);
            
            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
            ConfigDigitalStpts configDigitalStpts = new ConfigDigitalStpts(digitalStptPath.getText());
            ConfigInputs configInputs = new ConfigInputs(inputsPath.getText());
            try {
                configMain.getConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            } catch (WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                configDigitalStpts.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                configInputs.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
            
            logArea.append("Config done!" + newLine);

            try {
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                String path;

                fileChooser.setDialogTitle("Choose macros location");
                if (fileChooser.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    HMIInit.generateMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptMainDigitalMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptAdvancedDigitalMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptAdvancedInputsMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptAdvancedFanMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptAdvancedTempMacro(configMain, configDigitalStpts, path, mainForm);
                    ReadInputs.generateReadInputsMacro(configMain, configInputs, path, mainForm);
                }
            } catch (ConfigurationNotDoneException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }
    }

    private class GetMainConfigBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt", "text"));
            fileChooser.setDialogTitle("Choose Main Config file");

            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                mainConfigPath.setText(path);
            }
            fileChooser.resetChoosableFileFilters();
        }
    }

    private class GetDigitalStptBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
            fileChooser.setDialogTitle("Choose Digital Stpt file");

            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                digitalStptPath.setText(path);
            }
            fileChooser.resetChoosableFileFilters();
        }
    }
    
    private class GetInputsBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("EXCEL FILES", "xlsx"));
            fileChooser.setDialogTitle("Choose Inputs file");

            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                inputsPath.setText(path);
            }
            fileChooser.resetChoosableFileFilters();
        }
    }
}
