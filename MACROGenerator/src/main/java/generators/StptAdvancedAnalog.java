package generators;

import GUI.MainForm;
import entities.Signal;
import exceptions.ConfigurationNotDoneException;
import utilities.ConfigAdvancedAnalog;
import utilities.ConfigMain;
import utilities.ConfigMainAnalog;

import java.io.*;

/**
 * Created by Maltar on 7.10.2018..
 */
public final class StptAdvancedAnalog {
    private static final String STPT_AVANCED_ANALOG_NAME = "StptAdvancedAnalog_init.txt";
    private static final String VERSION_TAG = "// Excel version: ";
    private static final String OPEN_MACRO_COMMAND = "macro_command main()";
    private static final String END_MACRO_COMMAND = "end macro_command";

    private static final String COMMENT = "// ";

    private StptAdvancedAnalog() {}

    public static File generateStptAdvancedAnalogMacro(ConfigMain configMain, ConfigAdvancedAnalog configAdvancedAnalog, String path, MainForm mainForm) throws ConfigurationNotDoneException, IOException {
        path += "\\" + STPT_AVANCED_ANALOG_NAME;

        if (!configMain.isConfigurated() || !configAdvancedAnalog.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        String line;
        String[] lines;

        bufferedWriter.write(VERSION_TAG + configAdvancedAnalog.getVersion());
        bufferedWriter.newLine();
        bufferedWriter.write(OPEN_MACRO_COMMAND);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "short DISP_STR_ID_START = " + configMain.getDispStrIdStart() + "\n" +
                "short MAX_DATA_PER_SCREEN = " + configMain.getMaxDataPerScreen() + "\n" +
                "\n" +
                "short STRING_ID_START = 6032\n" +
                "short REG_START = 6932\n" +
                "short ADDR_START = 6332\n" +
                "short DATA_TYPE_START = 6632\n" +
                "short SCALE_START = 344\n" +
                "short DISABLED_INPUTS_START = 654\n" +
                "short LOW_HIGH_LIMIT_START = 1270\n" +
                "\n" +
                "short kkID\n" +
                "short stringID\n" +
                "short reg\n" +
                "short addr\n" +
                "short type\n" +
                "bool scale\n" +
                "bool disabled\n" +
                "float lowLimit\n" +
                "float highLimit\n" +
                "\n" +
                "short state\n" +
                "bool enabled\n" +
                "short setpointsCount = 0\n" +
                "\n" +
                "short position\n" +
                "int TPConfigDW0, TPConfigDW1, TPConfigDW2, TPConfigDW3, TPConfigDW4, TPConfigDW5\n" +
                "\n" +
                "bool enable\n" +
                "bool disable\n" +
                "\n" +
                "enable = true\n" +
                "SetData(enable, \"HMI\", \"ShowBufferingSpinner\", 1)\n" +
                "\n" +
                "GetData(kkID, \"HMI\", \"kkID\", 1)\n" +
                "\n" +
                "reg = " + configMain.getTPConfigDW0Reg() + "\n" +
                "addr = " + configMain.getTPConfigDW0Addr() + "\n" +
                "TPConfigDW0 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = " + configMain.getTPConfigDW1Reg() + "\n" +
                "addr = " + configMain.getTPConfigDW1Addr() + "\n" +
                "TPConfigDW1 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = " + configMain.getTPConfigDW2Reg() + "\n" +
                "addr = " + configMain.getTPConfigDW2Addr() + "\n" +
                "TPConfigDW2 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = " + configMain.getTPConfigDW3Reg() + "\n" +
                "addr = " + configMain.getTPConfigDW3Addr() + "\n" +
                "TPConfigDW3 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = " + configMain.getTPConfigDW4Reg() + "\n" +
                "addr = " + configMain.getTPConfigDW4Addr() + "\n" +
                "TPConfigDW4 = GetUnsignedIntFromPLC(kkID, reg, addr)\n";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }
        bufferedWriter.newLine();

        for (Signal signal : configAdvancedAnalog.getSignals()) {
            line = "enabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
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
                    line = "\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "else";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tenabled = false";
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

            line = "\tstringID = " + signal.getStringId() + "\n" +
                    "\treg = " + signal.getSignalReg() + "\n" +
                    "\taddr = " + signal.getSignalAddr() + "\n" +
                    "\ttype = " + signal.getDataType() + "\n" +
                    "\tscale = " + (signal.getScale() != 1.0 ? "true" : "false") + "\n" +
                    "\tdisabled = " + signal.isDisabled() + "\n" +
                    "\tlowLimit = " + signal.getLowLimit() + "\n" +
                    "\thighLimit = " + signal.getHighLimit() + "\n" +
                    "\t\n" +
                    "\tSetData(stringID, \"HMI\", LW, STRING_ID_START + setpointsCount, 1) \n" +
                    "\tSetData(reg, \"HMI\", LW, REG_START + setpointsCount, 1)\n" +
                    "\tSetData(addr, \"HMI\", LW, ADDR_START + setpointsCount, 1)\n" +
                    "\tSetData(type, \"HMI\", LW, DATA_TYPE_START + setpointsCount, 1)\n" +
                    "\tSetData(scale, \"HMI\", LB, SCALE_START + setpointsCount, 1)\n" +
                    "\tSetData(disabled, \"HMI\", LB, DISABLED_INPUTS_START + setpointsCount, 1)\n" +
                    "\tSetData(lowLimit, \"HMI\", LW, LOW_HIGH_LIMIT_START + setpointsCount * 4, 1)\n" +
                    "\tSetData(highLimit, \"HMI\", LW, LOW_HIGH_LIMIT_START + setpointsCount * 4 + 2, 1)\n" +
                    "\t\n" +
                    "\tsetpointsCount = setpointsCount + 1\n" +
                    "end if\n";

            lines = line.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }
        }

        line = "SetData(setpointsCount, \"HMI\", \"SetpointsCount\", 1)\n" +
                "\n" +
                "disable = false\n" +
                "SetData(disable, \"HMI\", \"ShowBufferingSpinner\", 1)\n" +
                "\n" +
                "disable = true\n" +
                "SetData(disable, \"HMI\", \"ScrollLeftDisable\", 1)\n" +
                "SetData(disable, \"HMI\", \"ScrollRightDisable\", 1)\n" +
                "\n" +
                "SYNC_TRIG_MACRO(49) // presentSetpointsAnalog\n";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.write(END_MACRO_COMMAND);

        bufferedWriter.close();

        mainForm.getLogArea().append("StptAdvancedAnalog_init macro done!" + "\n");

        return file;
    }
}
