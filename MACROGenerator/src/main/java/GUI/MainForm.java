package GUI;

import exceptions.ConfigurationNotDoneException;
import exceptions.WrongFormatException;
import generators.HMIInit;
import generators.StptDigitalInit;
import utilities.ConfigMain;
import utilities.ConfigSignals;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainForm {
    static private final String newLine = "\n";

    private JTextField mainConfigPath;
    private JPanel mainView;
    private JTextField digitalStptPath;
    private JButton getDigitalStpt;
    private JButton generateButton;
    private JButton getMainConfig;
    private JTextArea logArea;

    private JFileChooser fileChooser;

    public MainForm() {
        fileChooser = new JFileChooser();

        generateButton.addActionListener(new GenerateBtnClicked(this));
        getMainConfig.addActionListener(new GetMainConfigBtnClicked());
        getDigitalStpt.addActionListener(new GetDigitalStptBtnClicked());
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
            try {
                configMain.getConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            } catch (WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage());
            }

            ConfigSignals configSignals = new ConfigSignals(digitalStptPath.getText());
            try {
                configSignals.getConfiguration();
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
                    HMIInit.generateMacro(configMain, configSignals, path, mainForm);
                    StptDigitalInit.generateStptMainDigitalMacro(configMain, configSignals, path, mainForm);
                    StptDigitalInit.generateStptAdvancedDigitalMacro(configMain, configSignals, path, mainForm);
                    StptDigitalInit.generateStptAdvancedInputsMacro(configMain, configSignals, path, mainForm);
                    StptDigitalInit.generateStptAdvancedFanMacro(configMain, configSignals, path, mainForm);
                    StptDigitalInit.generateStptAdvancedTempMacro(configMain, configSignals, path, mainForm);
                }
            } catch (ConfigurationNotDoneException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }

        private void saveFile(File file) {
            fileChooser.setSelectedFile(file);
            int returnVal = fileChooser.showSaveDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                logArea.append("Saving: " + file.getName() + " to " + file.getAbsolutePath() + "." + newLine);
                File outputFile = fileChooser.getSelectedFile();
                FileChannel source = null;
                FileChannel destination = null;
                try {
                    FileWriter fileWriter = new FileWriter(outputFile);
                    source = new FileInputStream(file).getChannel();
                    destination = new FileOutputStream(outputFile).getChannel();
                    destination.transferFrom(source, 0, source.size());
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logArea.append(e.getMessage() + newLine);
                } finally {
                    if (source != null) {
                        try {
                            source.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (destination != null) {
                        try {
                            destination.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private class GetMainConfigBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                mainConfigPath.setText(path);
            }
        }
    }

    private class GetDigitalStptBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                digitalStptPath.setText(path);
            }
        }
    }
}
