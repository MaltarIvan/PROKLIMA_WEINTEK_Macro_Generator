package generators;

import GUI.MainForm;
import entities.LoopCtrl;
import entities.Signal;
import exceptions.ConfigurationNotDoneException;
import utilities.ConfigLoopCtrls;
import utilities.ConfigMain;
import utilities.ConfigMainAnalog;

import java.io.*;

/**
 * Created by Maltar on 19.10.2018..
 */
public final class LoopList {
    private static final String LOOP_LIST_NAME = "LoopList_init.txt";
    private static final String VERSION_TAG = "// Excel version: ";
    private static final String OPEN_MACRO_COMMAND = "macro_command main()";
    private static final String END_MACRO_COMMAND = "end macro_command";

    private static final String COMMENT = "// ";

    private LoopList() {}

    public static File generateLoopListMacro(ConfigMain configMain, ConfigLoopCtrls configLoopCtrls, String path, MainForm mainForm) throws ConfigurationNotDoneException, IOException {
        path += "\\" + LOOP_LIST_NAME;

        if (!configMain.isConfigurated() || !configLoopCtrls.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        String line;
        String[] lines;

        bufferedWriter.write(VERSION_TAG + configLoopCtrls.getVersion());
        bufferedWriter.newLine();
        bufferedWriter.write(OPEN_MACRO_COMMAND);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "short STRING_ID_START = " + configMain.getStringIdStart() + "\n" +
               "short ADDR_START = " + configMain.getAddrStart() + "\n" +
                "short CASCADE_START = 344\n" +
                "\n" +
                "bool loopCtrl = false\n" +
                "bool loopCtrlFan = false\n" +
                "bool loopCtrlTemp = false\n" +
                "bool loopCtrlHumidity = false\n" +
                "bool loopCtrlRoom = false\n" +
                "short loopCtrlTitle\n" +
                "short loopCtrlCount = 0\n" +
                "\n" +
                "short kkID\n" +
                "\n" +
                "short enabled\n" +
                "short stringID\n" +
                "short reg\n" +
                "short addr\n" +
                "bool isCascade\n" +
                "\n" +
                "short position\n" +
                "int TPConfigDW0, TPConfigDW1, TPConfigDW2, TPConfigDW3, TPConfigDW4, TPConfigDW5\n" +
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
                "reg = 4\n" +
                "addr = 837\n" +
                "TPConfigDW3 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = 4\n" +
                "addr = 839\n" +
                "TPConfigDW4 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "\n" +
                "GetData(loopCtrl, \"HMI\", \"LoopCtrlFan\", 1)\n" +
                "if loopCtrl then\n" +
                "\tloopCtrlFan = true\t\n" +
                "\tloopCtrlTitle = 0\n" +
                "end if\n" +
                "loopCtrl = false\n" +
                "\n" +
                "GetData(loopCtrl, \"HMI\", \"LoopCtrlTemp\", 1)\n" +
                "if loopCtrl then\n" +
                "\tloopCtrlTemp = true\t\n" +
                "\tloopCtrlTitle = 1\n" +
                "end if\n" +
                "loopCtrl = false\n" +
                "\n" +
                "GetData(loopCtrl, \"HMI\", \"LoopCtrlHumidity\", 1)\n" +
                "if loopCtrl then\n" +
                "\tloopCtrlHumidity = true\t\n" +
                "\tloopCtrlTitle = 2\n" +
                "end if\n" +
                "loopCtrl = false\n" +
                "\n" +
                "GetData(loopCtrl, \"HMI\", \"LoopCtrlRoom\", 1)\n" +
                "if loopCtrl then\n" +
                "\tloopCtrlRoom = true\t\n" +
                "\tloopCtrlTitle = 3\n" +
                "end if\n" +
                "\n" +
                "GetData(kkID, \"HMI\", \"kkID\", 1)\n" +
                "\n" +
                "if loopCtrlFan then";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        for (LoopCtrl loopCtrl : configLoopCtrls.getFan()) {
            line = "\tenabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "\t" + COMMENT + loopCtrl.getName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (loopCtrl.isTPConfigDW()) {
                line = "\tposition = " + loopCtrl.getEnableBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\tGETBIT(TPConfigDW" + loopCtrl.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\tif enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() == 0) {
                    line = "\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() != 0) {
                    line = "\treg = " + loopCtrl.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\taddr = " + loopCtrl.getEnableAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tenabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() != 0) {
                    line = "\treg = " + loopCtrl.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\taddr = " + loopCtrl.getEnableAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tstate = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif state " + loopCtrl.getEnableCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\telse";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tend if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            line = "\t\tstringID = " + loopCtrl.getStringId() + "\n" +
                    "\t\taddr = " + loopCtrl.getAddr() + "\n" +
                    "\t\tisCascade = " + loopCtrl.isCascade() + "\n" +
                    "\t\t\n" +
                    "\t\tSetData(stringID, \"HMI\", LW, STRING_ID_START + loopCtrlCount, 1)\n" +
                    "\t\tSetData(addr, \"HMI\", LW, ADDR_START + loopCtrlCount, 1)\n" +
                    "\t\tSetData(isCascade, \"HMI\", LB, CASCADE_START + loopCtrlCount, 1)\n" +
                    "\t\t\n" +
                    "\t\tloopCtrlCount = loopCtrlCount + 1\n" +
                    "\tend if\n\n";

            lines = line.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }
        }

        line = "else if loopCtrlTemp then\n";
        bufferedWriter.write(line);
        for (LoopCtrl loopCtrl : configLoopCtrls.getTemp()) {
            line = "\tenabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "\t" + COMMENT + loopCtrl.getName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (loopCtrl.isTPConfigDW()) {
                line = "\tposition = " + loopCtrl.getEnableBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\tGETBIT(TPConfigDW" + loopCtrl.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\tif enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() == 0) {
                    line = "\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() != 0) {
                    line = "\treg = " + loopCtrl.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\taddr = " + loopCtrl.getEnableAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tenabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() != 0) {
                    line = "\treg = " + loopCtrl.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\taddr = " + loopCtrl.getEnableAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tstate = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif state " + loopCtrl.getEnableCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\telse";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tend if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            line = "\t\tstringID = " + loopCtrl.getStringId() + "\n" +
                    "\t\taddr = " + loopCtrl.getAddr() + "\n" +
                    "\t\tisCascade = " + loopCtrl.isCascade() + "\n" +
                    "\t\t\n" +
                    "\t\tSetData(stringID, \"HMI\", LW, STRING_ID_START + loopCtrlCount, 1)\n" +
                    "\t\tSetData(addr, \"HMI\", LW, ADDR_START + loopCtrlCount, 1)\n" +
                    "\t\tSetData(isCascade, \"HMI\", LB, CASCADE_START + loopCtrlCount, 1)\n" +
                    "\t\t\n" +
                    "\t\tloopCtrlCount = loopCtrlCount + 1\n" +
                    "\tend if\n\n";

            lines = line.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }
        }
        for (LoopCtrl loopCtrl : configLoopCtrls.getTemp()) {
            line = "\tenabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "\t" + COMMENT + loopCtrl.getName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (loopCtrl.isTPConfigDW()) {
                line = "\tposition = " + loopCtrl.getEnableBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\tGETBIT(TPConfigDW" + loopCtrl.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\tif enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() == 0) {
                    line = "\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() != 0) {
                    line = "\treg = " + loopCtrl.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\taddr = " + loopCtrl.getEnableAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tenabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() != 0) {
                    line = "\treg = " + loopCtrl.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\taddr = " + loopCtrl.getEnableAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tstate = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif state " + loopCtrl.getEnableCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\telse";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tend if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            line = "\t\tstringID = " + loopCtrl.getStringId() + "\n" +
                    "\t\taddr = " + loopCtrl.getAddr() + "\n" +
                    "\t\tisCascade = " + loopCtrl.isCascade() + "\n" +
                    "\t\t\n" +
                    "\t\tSetData(stringID, \"HMI\", LW, STRING_ID_START + loopCtrlCount, 1)\n" +
                    "\t\tSetData(addr, \"HMI\", LW, ADDR_START + loopCtrlCount, 1)\n" +
                    "\t\tSetData(isCascade, \"HMI\", LB, CASCADE_START + loopCtrlCount, 1)\n" +
                    "\t\t\n" +
                    "\t\tloopCtrlCount = loopCtrlCount + 1\n" +
                    "\tend if\n\n";

            lines = line.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }
        }

        line = "else if loopCtrlHumidity then\n";
        bufferedWriter.write(line);
        for (LoopCtrl loopCtrl : configLoopCtrls.getHumidity()) {
            line = "\tenabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "\t" + COMMENT + loopCtrl.getName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (loopCtrl.isTPConfigDW()) {
                line = "\tposition = " + loopCtrl.getEnableBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\tGETBIT(TPConfigDW" + loopCtrl.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\tif enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() == 0) {
                    line = "\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() != 0) {
                    line = "\treg = " + loopCtrl.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\taddr = " + loopCtrl.getEnableAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tenabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!loopCtrl.getEnableCondition().equals("x") && loopCtrl.getEnableAddr() != 0) {
                    line = "\treg = " + loopCtrl.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\taddr = " + loopCtrl.getEnableAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tstate = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif state " + loopCtrl.getEnableCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\telse";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tend if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            line = "\t\tstringID = " + loopCtrl.getStringId() + "\n" +
                    "\t\taddr = " + loopCtrl.getAddr() + "\n" +
                    "\t\tisCascade = " + loopCtrl.isCascade() + "\n" +
                    "\t\t\n" +
                    "\t\tSetData(stringID, \"HMI\", LW, STRING_ID_START + loopCtrlCount, 1)\n" +
                    "\t\tSetData(addr, \"HMI\", LW, ADDR_START + loopCtrlCount, 1)\n" +
                    "\t\tSetData(isCascade, \"HMI\", LB, CASCADE_START + loopCtrlCount, 1)\n" +
                    "\t\t\n" +
                    "\t\tloopCtrlCount = loopCtrlCount + 1\n" +
                    "\tend if\n\n";

            lines = line.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }
        }

        line = "else if loopCtrlRoom then\n" +
                "\treg = " + configLoopCtrls.getRoomCtrls().getEnableReg() + "\n" +
                "\taddr = " + configLoopCtrls.getRoomCtrls().getEnableAddr() + "\n" +
                "\tloopCtrlCount = GetUnsignedShortFromPLC(kkID, reg, addr)\n" +
                "\t\n" +
                "\tshort i \n" +
                "\tfor i = 0 to loopCtrlCount - 1 step 1\n" +
                "\t\tstringID = " + configLoopCtrls.getRoomCtrls().getStringIdStart() + " + i\n" +
                "\t\taddr = " + configLoopCtrls.getRoomCtrls().getAddrStart() + " + i * 3\n" +
                "\t\tisCascade = false\n" +
                "\t\t\n" +
                "\t\tSetData(stringID, \"HMI\", LW, STRING_ID_START + i, 1)\n" +
                "\t\tSetData(addr, \"HMI\", LW, ADDR_START + i, 1)\n" +
                "\t\tSetData(isCascade, \"HMI\", LB, CASCADE_START + i, 1)\n" +
                "\tnext i\n" +
                "end if\n" +
                "\n" +
                "SetData(loopCtrlTitle, \"HMI\", \"LoopControllerTitle\", 1)\n" +
                "SetData(loopCtrlCount, \"HMI\", \"StptLoopCtrlsCount\", 1)\n" +
                "\n" +
                "bool disable = true\n" +
                "SetData(disable, \"HMI\", \"ScrollLeftDisable\", 1)\n" +
                "SetData(disable, \"HMI\", \"ScrollRightDisable\", 1)\n" +
                "\n" +
                "SYNC_TRIG_MACRO(77) // PresentLoopCtrlList\n";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.write(END_MACRO_COMMAND);

        bufferedWriter.close();

        mainForm.getLogArea().append("LoopList_init macro done!" + "\n");

        return file;
    }
}
