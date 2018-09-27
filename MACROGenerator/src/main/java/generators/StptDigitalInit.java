package generators;

import GUI.MainForm;
import entities.Signal;
import exceptions.ConfigurationNotDoneException;
import utilities.ConfigMain;
import utilities.ConfigDigitalStpts;

import java.io.*;
import java.util.ArrayList;

public final class StptDigitalInit {
    private static final String STPT_MAIN_DIGITAL_NAME = "StptMainDigital_init.txt";
    private static final String STPT_ADVANCED_DIGITAL_NAME = "StptAdvancedDigital_init.txt";
    private static final String STPT_ADVANCED_INPUTS_NAME = "StptAdvancedInputs_init.txt";
    private static final String STPT_ADVANCED_FAN_NAME = "StptAdvancedFan_init.txt";
    private static final String STPT_ADVANCED_TEMP_NAME = "StptAdvancedTemp_init.txt";

    private static final String VERSION_TAG = "// Version: ";
    private static final String OPEN_MACRO_COMMAND = "macro_command main()";
    private static final String END_MACRO_COMMAND = "end macro_command";
    private static final String DISP_STR_ID_START_DEC = "short DISP_STR_ID_START = ";
    private static final String DISP_STR_ID_START = "DISP_STR_ID_START";
    private static final String MAX_DATA_PER_SCREEN_DEC = "short MAX_DATA_PER_SCREEN = ";
    private static final String MAX_DATA_PER_SCREEN = "MAX_DATA_PER_SCREEN";
    private static final String STRING_ID_START_DEC = "short STRING_ID_START = ";
    private static final String STRING_ID_START = "STRING_ID_START";
    private static final String REG_START_DEC = "short REG_START = ";
    private static final String REG_START = "REG_START";
    private static final String ADDR_START_DEC = "short ADDR_START = ";
    private static final String ADDR_START = "ADDR_START";
    private static final String STATE_INDEX_START_DEC = "short STATE_INDEX_START = ";
    private static final String STATE_INDEX_START = "STATE_INDEX_START";
    private static final String STATE_COUNT_START_DEC = "short STATE_COUNT_START = ";
    private static final String STATE_COUNT_START = "STATE_COUNT_START";
    private static final String DISABLED_START_DEC = "short DISABLED_START = ";
    private static final String DISABLED_START = "DISABLED_START";
    private static final String DIGITAL_STATES_STRINGS_START_DEC = "short DIGITAL_STATES_STRINGS_START = ";
    private static final String COMMENT = "// ";

    private StptDigitalInit() {}

    public static File generateStptMainDigitalMacro(ConfigMain configMain, ConfigDigitalStpts configDigitalStpts, String path, MainForm mainForm) throws IOException, ConfigurationNotDoneException {
        path += "\\" + STPT_MAIN_DIGITAL_NAME;

        if (!configMain.isConfigurated() || !configDigitalStpts.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = generateMacro(configMain, configDigitalStpts.getVersion(), configDigitalStpts.getMainDigital(), path);
        mainForm.getLogArea().append("StptMainDigital_init macro done!" + "\n");

        return file;
    }

    public static File generateStptAdvancedDigitalMacro(ConfigMain configMain, ConfigDigitalStpts configDigitalStpts, String path, MainForm mainForm) throws IOException, ConfigurationNotDoneException {
        path += "\\" + STPT_ADVANCED_DIGITAL_NAME;

        if (!configMain.isConfigurated() || !configDigitalStpts.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = generateMacro(configMain, configDigitalStpts.getVersion(), configDigitalStpts.getAdvancedDigital(), path);
        mainForm.getLogArea().append("StptAdvancedDigital_init macro done!" + "\n");

        return file;
    }

    public static File generateStptAdvancedInputsMacro(ConfigMain configMain, ConfigDigitalStpts configDigitalStpts, String path, MainForm mainForm) throws IOException, ConfigurationNotDoneException {
        path += "\\" + STPT_ADVANCED_INPUTS_NAME;

        if (!configMain.isConfigurated() || !configDigitalStpts.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = generateMacro(configMain, configDigitalStpts.getVersion(), configDigitalStpts.getAdvancedInputs(), path);
        mainForm.getLogArea().append("StptAdvancedInputs_init macro done!" + "\n");

        return file;
    }

    public static File generateStptAdvancedFanMacro(ConfigMain configMain, ConfigDigitalStpts configDigitalStpts, String path, MainForm mainForm) throws IOException, ConfigurationNotDoneException {
        path += "\\" + STPT_ADVANCED_FAN_NAME;

        if (!configMain.isConfigurated() || !configDigitalStpts.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = generateMacro(configMain, configDigitalStpts.getVersion(), configDigitalStpts.getAdvancedFan(), path);
        mainForm.getLogArea().append("StptAdvancedFan_init macro done!" + "\n");
        return file;
    }

    public static File generateStptAdvancedTempMacro(ConfigMain configMain, ConfigDigitalStpts configDigitalStpts, String path, MainForm mainForm) throws IOException, ConfigurationNotDoneException {
        path += "\\" + STPT_ADVANCED_TEMP_NAME;

        if (!configMain.isConfigurated() || !configDigitalStpts.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = generateMacro(configMain, configDigitalStpts.getVersion(), configDigitalStpts.getAdvancedTemp(), path);
        mainForm.getLogArea().append("StptAdvancedTemp_init macro done!" + "\n");
        return file;
    }

    private static File generateMacro(ConfigMain configMain, int version, ArrayList<Signal> signals, String path) throws IOException {
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        String line;

        bufferedWriter.write(VERSION_TAG + version);
        bufferedWriter.newLine();
        bufferedWriter.write(OPEN_MACRO_COMMAND);
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(DISP_STR_ID_START_DEC + configMain.getDispStrIdStart());
        bufferedWriter.newLine();
        bufferedWriter.write(DIGITAL_STATES_STRINGS_START_DEC + configMain.getDigitalStateStringStart());
        bufferedWriter.newLine();
        bufferedWriter.write(MAX_DATA_PER_SCREEN_DEC + configMain.getMaxDataPerScreen());
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(STRING_ID_START_DEC + configMain.getStringIdStart());
        bufferedWriter.newLine();
        bufferedWriter.write(REG_START_DEC + configMain.getRegStart());
        bufferedWriter.newLine();
        bufferedWriter.write(ADDR_START_DEC + configMain.getAddrStart());
        bufferedWriter.newLine();
        bufferedWriter.write(STATE_INDEX_START_DEC + configMain.getStateIndexStart());
        bufferedWriter.newLine();
        bufferedWriter.write(STATE_COUNT_START_DEC + configMain.getStateCountStart());
        bufferedWriter.newLine();
        bufferedWriter.write(DISABLED_START_DEC + configMain.getDisabledStart());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write("short kkID");
        bufferedWriter.newLine();
        bufferedWriter.write("short stringID");
        bufferedWriter.newLine();
        bufferedWriter.write("short reg");
        bufferedWriter.newLine();
        bufferedWriter.write("short addr");
        bufferedWriter.newLine();
        bufferedWriter.write("short index");
        bufferedWriter.newLine();
        bufferedWriter.write("short count");
        bufferedWriter.newLine();
        bufferedWriter.write("bool disabled");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write("short state");
        bufferedWriter.newLine();
        bufferedWriter.write("bool enabled");
        bufferedWriter.newLine();
        bufferedWriter.write("short setpointsCount = 0");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write("short position");
        bufferedWriter.newLine();
        bufferedWriter.write("int TPConfigDW0, TPConfigDW1, TPConfigDW2, TPConfigDW3, TPConfigDW4, TPConfigDW5");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write("bool enable");
        bufferedWriter.newLine();
        bufferedWriter.write("bool disable");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write("enable = true");
        bufferedWriter.newLine();
        bufferedWriter.write("SetData(enable, \"HMI\", \"ShowBufferingSpinner\", 1)");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write("GetData(kkID, \"HMI\", \"kkID\", 1)");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "reg = " + configMain.getTPConfigDW0Reg();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "addr = " + configMain.getTPConfigDW0Addr();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.write("TPConfigDW0 = GetUnsignedIntFromPLC(kkID, reg, addr)");
        bufferedWriter.newLine();
        line = "reg = " + configMain.getTPConfigDW1Reg();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "addr = " + configMain.getTPConfigDW1Addr();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.write("TPConfigDW1 = GetUnsignedIntFromPLC(kkID, reg, addr)");
        bufferedWriter.newLine();
        line = "reg = " + configMain.getTPConfigDW2Reg();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "addr = " + configMain.getTPConfigDW2Addr();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.write("TPConfigDW2 = GetUnsignedIntFromPLC(kkID, reg, addr)");
        bufferedWriter.newLine();
        line = "reg = " + configMain.getTPConfigDW3Reg();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "addr = " + configMain.getTPConfigDW3Addr();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.write("TPConfigDW3 = GetUnsignedIntFromPLC(kkID, reg, addr)");
        bufferedWriter.newLine();
        line = "reg = " + configMain.getTPConfigDW4Reg();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "addr = " + configMain.getTPConfigDW4Addr();
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.write("TPConfigDW4 = GetUnsignedIntFromPLC(kkID, reg, addr)");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        for (Signal signal : signals) {
            line = COMMENT + signal.getSignalName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (signal.isTPConfigDW()) {
                line = "position = " + signal.getBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "GETBIT(TPConfigDW" + signal.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "if enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() == 0) {
                    line = "enabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "if enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "reg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "addr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "enabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "if enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "reg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "addr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "state = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "if state " + signal.getEnabledCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "    enabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "else";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "    enabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "end if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "if enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            line = "    stringID = " + signal.getStringId();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    reg = " + signal.getSignalReg();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    addr = " + signal.getSignalAddr();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    index = " + signal.getStates().getStateStringIndex();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    count = " + signal.getStates().getStatesStr().get(0).length;
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    disabled = " + signal.isDisabled();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            line = "    SetData(stringID, \"HMI\", LW, " + STRING_ID_START + " + setpointsCount, 1)";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    SetData(reg, \"HMI\", LW, " + REG_START + " + setpointsCount, 1)";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    SetData(addr, \"HMI\", LW, " + ADDR_START + " + setpointsCount, 1)";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    SetData(index, \"HMI\", LW, " + STATE_INDEX_START + " + setpointsCount, 1)";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    SetData(count, \"HMI\", LW, " + STATE_COUNT_START + " + setpointsCount, 1)";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "    SetData(disabled, \"HMI\", LB, " + DISABLED_START + " + setpointsCount, 1)";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            line = "    setpointsCount = setpointsCount + 1";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "end if";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
        }

        bufferedWriter.write("SetData(setpointsCount, \"HMI\", \"SetpointsCount\", 1)");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write("disable = false");
        bufferedWriter.newLine();
        bufferedWriter.write("SetData(disable, \"HMI\", \"ShowBufferingSpinner\", 1)");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write("disable = true");
        bufferedWriter.newLine();
        bufferedWriter.write("SetData(disable, \"HMI\", \"ScrollLeftDisable\", 1)");
        bufferedWriter.newLine();
        bufferedWriter.write("SetData(disable, \"HMI\", \"ScrollRightDisable\", 1)");
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "ASYNC_TRIG_MACRO(" + configMain.getStptDigitalReadMacroId() + ")";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(END_MACRO_COMMAND);

        bufferedWriter.close();

        return file;
    }
}
