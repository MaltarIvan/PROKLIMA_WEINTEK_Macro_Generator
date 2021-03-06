package GUI;

import exceptions.ConfigurationNotDoneException;
import exceptions.WrongFormatException;
import generators.*;
import main.Main;
import utilities.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainForm {
    static private final String newLine = "\n";

    private JTextField mainConfigPath;
    private JPanel mainView;
    private JTextField digitalStptPath;
    private JButton getDigitalStpt;
    private JButton getMainConfig;
    private JTextArea logArea;
    private JTextField inputsPath;
    private JButton getInputsFileButton;
    private JTextField outputsPath;
    private JButton getOutputsFileButton;
    private JTextField stptMainAnalogPath;
    private JTextField stptAdvancedAnalogPath;
    private JTextField loopCtrlsPath;
    private JButton getMainAnalogFileButton;
    private JButton getAdvancedAnalogFileButton;
    private JButton getLoopCtrlsFileButton;
    private JButton generateDigitalStptButton;
    private JButton generateAllMacros;
    private JButton generateInputsButton;
    private JButton generateOutputsButton;
    private JButton generateMainAnalogButton;
    private JButton generateAdvancedAnalogButton;
    private JButton generateLoopCtrlsButton;
    private JTextField trendsPath;
    private JButton getTrendsFileButton;
    private JButton generateTrendsButton;

    private JFileChooser fileChooser;

    public MainForm() {
        fileChooser = new JFileChooser();

        getMainConfig.addActionListener(new GetMainConfigBtnClicked());
        getDigitalStpt.addActionListener(new GetDigitalStptBtnClicked());
        getInputsFileButton.addActionListener(new GetInputsBtnClicked());
        getOutputsFileButton.addActionListener(new GetOutputsBtnClicked());
        getMainAnalogFileButton.addActionListener(new GetMainAnalogStptBtnClicked());
        getAdvancedAnalogFileButton.addActionListener(new GetAdvancedAnalogStptBtnClicked());
        getLoopCtrlsFileButton.addActionListener(new GetLoopCtrlsBtnClicked());
        getTrendsFileButton.addActionListener(new GetTrendsBtnClicked());

        generateDigitalStptButton.addActionListener(new GenerateDigitalBtnClicked(this));
        generateInputsButton.addActionListener(new GenerateInputsBtnClicked(this));
        generateOutputsButton.addActionListener(new GenerateOutputsBtnClicked(this));
        generateTrendsButton.addActionListener(new GenerateTrendsBtnClicked(this));
        generateMainAnalogButton.addActionListener(new GenerateMainAnalogBtnClicked(this));
        generateAdvancedAnalogButton.addActionListener(new GenerateAdvancedAnalogBtnClicked(this));
        generateLoopCtrlsButton.addActionListener(new GenerateLoopCtrlsBtnClicked(this));
        generateAllMacros.addActionListener(new GenerateAllBtnClicked(this));
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

    public JTextField getInputsPath() {
        return inputsPath;
    }

    public void setInputsPath(JTextField inputsPath) {
        this.inputsPath = inputsPath;
    }

    public JButton getGetInputsFileButton() {
        return getInputsFileButton;
    }

    public void setGetInputsFileButton(JButton getInputsFileButton) {
        this.getInputsFileButton = getInputsFileButton;
    }

    public JTextField getOutputsPath() {
        return outputsPath;
    }

    public void setOutputsPath(JTextField outputsPath) {
        this.outputsPath = outputsPath;
    }

    public JButton getGetOutputsFileButton() {
        return getOutputsFileButton;
    }

    public void setGetOutputsFileButton(JButton getOutputsFileButton) {
        this.getOutputsFileButton = getOutputsFileButton;
    }

    public JTextField getStptMainAnalogPath() {
        return stptMainAnalogPath;
    }

    public void setStptMainAnalogPath(JTextField stptMainAnalogPath) {
        this.stptMainAnalogPath = stptMainAnalogPath;
    }

    public JTextField getStptAdvancedAnalogPath() {
        return stptAdvancedAnalogPath;
    }

    public void setStptAdvancedAnalogPath(JTextField stptAdvancedAnalogPath) {
        this.stptAdvancedAnalogPath = stptAdvancedAnalogPath;
    }

    public JTextField getLoopCtrlsPath() {
        return loopCtrlsPath;
    }

    public void setLoopCtrlsPath(JTextField loopCtrlsPath) {
        this.loopCtrlsPath = loopCtrlsPath;
    }

    public JButton getGetMainAnalogFileButton() {
        return getMainAnalogFileButton;
    }

    public void setGetMainAnalogFileButton(JButton getMainAnalogFileButton) {
        this.getMainAnalogFileButton = getMainAnalogFileButton;
    }

    public JButton getGetAdvancedAnalogFileButton() {
        return getAdvancedAnalogFileButton;
    }

    public void setGetAdvancedAnalogFileButton(JButton getAdvancedAnalogFileButton) {
        this.getAdvancedAnalogFileButton = getAdvancedAnalogFileButton;
    }

    public JButton getGetLoopCtrlsFileButton() {
        return getLoopCtrlsFileButton;
    }

    public void setGetLoopCtrlsFileButton(JButton getLoopCtrlsFileButton) {
        this.getLoopCtrlsFileButton = getLoopCtrlsFileButton;
    }

    public JButton getGenerateDigitalStptButton() {
        return generateDigitalStptButton;
    }

    public void setGenerateDigitalStptButton(JButton generateDigitalStptButton) {
        this.generateDigitalStptButton = generateDigitalStptButton;
    }

    public JButton getGenerateAllMacros() {
        return generateAllMacros;
    }

    public void setGenerateAllMacros(JButton generateAllMacros) {
        this.generateAllMacros = generateAllMacros;
    }

    public JButton getGenerateInputsButton() {
        return generateInputsButton;
    }

    public void setGenerateInputsButton(JButton generateInputsButton) {
        this.generateInputsButton = generateInputsButton;
    }

    public JButton getGenerateOutputsButton() {
        return generateOutputsButton;
    }

    public void setGenerateOutputsButton(JButton generateOutputsButton) {
        this.generateOutputsButton = generateOutputsButton;
    }

    public JButton getGenerateMainAnalogButton() {
        return generateMainAnalogButton;
    }

    public void setGenerateMainAnalogButton(JButton generateMainAnalogButton) {
        this.generateMainAnalogButton = generateMainAnalogButton;
    }

    public JButton getGenerateAdvancedAnalogButton() {
        return generateAdvancedAnalogButton;
    }

    public void setGenerateAdvancedAnalogButton(JButton generateAdvancedAnalogButton) {
        this.generateAdvancedAnalogButton = generateAdvancedAnalogButton;
    }

    public JButton getGenerateLoopCtrlsButton() {
        return generateLoopCtrlsButton;
    }

    public void setGenerateLoopCtrlsButton(JButton generateLoopCtrlsButton) {
        this.generateLoopCtrlsButton = generateLoopCtrlsButton;
    }

    public JTextField getTrendsPath() {
        return trendsPath;
    }

    public void setTrendsPath(JTextField trendsPath) {
        this.trendsPath = trendsPath;
    }

    public JButton getGetTrendsFileButton() {
        return getTrendsFileButton;
    }

    public void setGetTrendsFileButton(JButton getTrendsFileButton) {
        this.getTrendsFileButton = getTrendsFileButton;
    }

    private class GenerateDigitalBtnClicked implements ActionListener {
        private MainForm mainForm;

        GenerateDigitalBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);
            
            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
            ConfigDigitalStpts configDigitalStpts = new ConfigDigitalStpts(digitalStptPath.getText());
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
                }
            } catch (ConfigurationNotDoneException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }
    }

    private class GenerateInputsBtnClicked implements ActionListener {
        private MainForm mainForm;

        public GenerateInputsBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);

            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
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
                configInputs.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                String path;

                fileChooser.setDialogTitle("Choose macros location");
                if (fileChooser.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    ReadInputs.generateReadInputsMacro(configMain, configInputs, path, mainForm);
                    ReadInputs.generateStringTable(configInputs, path, mainForm);
                }
            } catch (ConfigurationNotDoneException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }
    }

    private class GenerateOutputsBtnClicked implements ActionListener {
        private MainForm mainForm;

        public GenerateOutputsBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);

            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
            ConfigOutputs configOutputs = new ConfigOutputs(outputsPath.getText());
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
                configOutputs.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                String path;

                fileChooser.setDialogTitle("Choose macros location");
                if (fileChooser.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    ReadOutputs.generateReadOutputsMacro(configMain, configOutputs, path, mainForm);
                    ReadOutputs.generateStringTable(configOutputs, path, mainForm);
                }
            } catch (ConfigurationNotDoneException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }
    }

    private class GenerateTrendsBtnClicked implements ActionListener {
        private MainForm mainForm;

        public GenerateTrendsBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);

            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
            ConfigTrends configTrends = new ConfigTrends(trendsPath.getText());
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
                configTrends.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                String path;

                fileChooser.setDialogTitle("Choose macros location");
                if (fileChooser.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    SetupTrends.generateSetupTrendsMacro(configMain, configTrends, path, mainForm);
                }
            } catch (ConfigurationNotDoneException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }
    }

    private class GenerateMainAnalogBtnClicked implements ActionListener {
        private MainForm mainForm;

        public GenerateMainAnalogBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);

            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
            ConfigMainAnalog configMainAnalog = new ConfigMainAnalog(stptMainAnalogPath.getText());
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
                configMainAnalog.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                String path;

                fileChooser.setDialogTitle("Choose macros location");
                if (fileChooser.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    StptMainAnalog.generateStptMainAnalogMacro(configMain, configMainAnalog, path, mainForm);
                }
            } catch (ConfigurationNotDoneException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }
    }

    private class GenerateAdvancedAnalogBtnClicked implements ActionListener {
        private MainForm mainForm;

        public GenerateAdvancedAnalogBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);

            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
            ConfigAdvancedAnalog configAdvancedAnalog = new ConfigAdvancedAnalog(stptAdvancedAnalogPath.getText());
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
                configAdvancedAnalog.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                String path;

                fileChooser.setDialogTitle("Choose macros location");
                if (fileChooser.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    StptAdvancedAnalog.generateStptAdvancedAnalogMacro(configMain, configAdvancedAnalog, path, mainForm);
                }
            } catch (ConfigurationNotDoneException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            } catch (IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }
    }

    private class GenerateLoopCtrlsBtnClicked implements ActionListener {
        private MainForm mainForm;

        public GenerateLoopCtrlsBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);

            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
            ConfigLoopCtrls configLoopCtrls = new ConfigLoopCtrls(loopCtrlsPath.getText());
            try {
                configMain.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                configLoopCtrls.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                String path;

                fileChooser.setDialogTitle("Choose macros location");
                if (fileChooser.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    LoopList.generateLoopListMacro(configMain, configLoopCtrls, path, mainForm);
                }
            } catch (ConfigurationNotDoneException | IOException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }
        }
    }

    private class GenerateAllBtnClicked implements ActionListener {
        private MainForm mainForm;

        public GenerateAllBtnClicked(MainForm mainForm) {
            this.mainForm = mainForm;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            logArea.append("Config start..." + newLine);
            ConfigMain configMain = new ConfigMain(mainConfigPath.getText());
            ConfigInputs configInputs = new ConfigInputs(inputsPath.getText());
            ConfigOutputs configOutputs = new ConfigOutputs(outputsPath.getText());
            ConfigMainAnalog configMainAnalog = new ConfigMainAnalog(stptMainAnalogPath.getText());
            ConfigAdvancedAnalog configAdvancedAnalog = new ConfigAdvancedAnalog(stptAdvancedAnalogPath.getText());
            ConfigDigitalStpts configDigitalStpts = new ConfigDigitalStpts(digitalStptPath.getText());
            ConfigLoopCtrls configLoopCtrls = new ConfigLoopCtrls(loopCtrlsPath.getText());
            ConfigTrends configTrends = new ConfigTrends(trendsPath.getText());

            try {
                configMain.getConfiguration();
                configInputs.getConfiguration();
                configOutputs.getConfiguration();
                configMainAnalog.getConfiguration();;
                configAdvancedAnalog.getConfiguration();
                configDigitalStpts.getConfiguration();
                configLoopCtrls.getConfiguration();
                configTrends.getConfiguration();
            } catch (IOException | WrongFormatException e) {
                e.printStackTrace();
                logArea.append(e.getMessage() + newLine);
            }

            try {
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                String path;

                fileChooser.setDialogTitle("Choose macros location");
                if (fileChooser.showSaveDialog(mainView) == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    HMIInit.generateMacro(configMain, configDigitalStpts, path, mainForm);
                    LoopList.generateLoopListMacro(configMain, configLoopCtrls, path, mainForm);
                    ReadInputs.generateReadInputsMacro(configMain, configInputs, path, mainForm);
                    ReadInputs.generateStringTable(configInputs, path, mainForm);
                    ReadOutputs.generateReadOutputsMacro(configMain, configOutputs, path, mainForm);
                    ReadOutputs.generateStringTable(configOutputs, path, mainForm);
                    SetupTrends.generateSetupTrendsMacro(configMain, configTrends, path, mainForm);
                    StptMainAnalog.generateStptMainAnalogMacro(configMain, configMainAnalog, path, mainForm);
                    StptAdvancedAnalog.generateStptAdvancedAnalogMacro(configMain, configAdvancedAnalog, path, mainForm);
                    StptDigitalInit.generateStptMainDigitalMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptAdvancedDigitalMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptAdvancedInputsMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptAdvancedFanMacro(configMain, configDigitalStpts, path, mainForm);
                    StptDigitalInit.generateStptAdvancedTempMacro(configMain, configDigitalStpts, path, mainForm);
                }
            } catch (ConfigurationNotDoneException | IOException e) {
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

    private class GetOutputsBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("EXCEL FILES", "xlsx"));
            fileChooser.setDialogTitle("Choose Outputs file");

            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                outputsPath.setText(path);
            }
            fileChooser.resetChoosableFileFilters();
        }
    }

    private class GetMainAnalogStptBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("EXCEL FILES", "xlsx"));
            fileChooser.setDialogTitle("Choose Main Analog file");

            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                stptMainAnalogPath.setText(path);
            }
            fileChooser.resetChoosableFileFilters();
        }
    }

    private class GetAdvancedAnalogStptBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("EXCEL FILES", "xlsx"));
            fileChooser.setDialogTitle("Choose Advanced Analog file");

            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                stptAdvancedAnalogPath.setText(path);
            }
            fileChooser.resetChoosableFileFilters();
        }
    }

    private class GetLoopCtrlsBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("EXCEL FILES", "xlsx"));
            fileChooser.setDialogTitle("Choose Loop Ctrls file");

            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                loopCtrlsPath.setText(path);
            }
            fileChooser.resetChoosableFileFilters();
        }
    }

    private class GetTrendsBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("EXCEL FILES", "xlsx"));
            fileChooser.setDialogTitle("Choose Trends file");

            int returnVal = fileChooser.showOpenDialog(mainView);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                trendsPath.setText(path);
            }
            fileChooser.resetChoosableFileFilters();
        }
    }
}
