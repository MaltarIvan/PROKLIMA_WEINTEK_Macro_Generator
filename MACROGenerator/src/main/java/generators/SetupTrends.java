package generators;

import GUI.MainForm;
import entities.Signal;
import exceptions.ConfigurationNotDoneException;
import utilities.ConfigMain;
import utilities.ConfigTrends;

import java.io.*;

/**
 * Created by Maltar on 4.10.2018..
 */
public final class SetupTrends {
    private static final String SETUP_TRENDS_NAME = "SetupTrends_init.txt";
    private static final String HEADER = "//**\n" +
            "// @Version: 1\n" +
            "// @Author: Ivan Maltar\n" +
            "// Macro koji se pokreće pri otvaranju ekrana SetupTrendsStd (67) i pri promjeni ScrollSection-a u tom ekranu.\n" +
            "// Svaki signal se provjerava da li je omogućen (enabled) te, ako je omogućen, njegovi parametri se spremaju u memoriju HMI-a za naknadni odabir za prikaz na grafu (Trendu). (samo pri ulasku u ekran)\n" +
            "// Nakon toga se omogućeni signali prikazuju na ekranu ovisno o ScrollSection-u \n" +
            "//*\n";
    private static final String VERSION_TAG = "// Excel version: ";
    private static final String OPEN_MACRO_COMMAND = "macro_command main()";
    private static final String END_MACRO_COMMAND = "end macro_command";

    private static final String COMMENT = "// ";

    private SetupTrends() {}

    public static File generateSetupTrendsMacro(ConfigMain configMain, ConfigTrends configTrends, String path, MainForm mainForm) throws ConfigurationNotDoneException, IOException {
        path += "\\" + SETUP_TRENDS_NAME;

        if (!configMain.isConfigurated() || !configTrends.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        String line;
        String[] lines;

        lines = HEADER.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        bufferedWriter.write(VERSION_TAG + configTrends.getVersion());
        bufferedWriter.newLine();
        bufferedWriter.write(OPEN_MACRO_COMMAND);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "\n" +
                "short DISP_STR_ID_START = " + configMain.getDispStrIdStart() + "\n" +
                "short TRENDS_STR_ID_START = 6032\n" +
                "short TRENDS_ADDR_START = 6332\n" +
                "short TRENDS_TYPE_START = 6632\n" +
                "short TRENDS_SCALE_START = 5388\n" +
                "short TRENDS_REG_START = 6932\n" +
                "short CHECK_TREND_START = 35\n" +
                "short ENABLED_TRENDS_START = 44\n" +
                "short ENABLED_TRENDS_TEMP_START = 992\n" +
                "short MAX_TRENDS_PER_SCREEN = " + configMain.getMaxDataPerScreen() + "\n" +
                "short MAX_SIGNAL_COUNT = 300 \n" +
                "\n" +
                "short LINE_HIDDEN_START = " + configMain.getLineHiddenStart() + "\n" +
                "\n" +
                "short ROOM_PRESS_ADR_START = " + configTrends.getRoomPressures().getAddrStart() + "\n" +
                "short ROOM_PRESS_REG = " + configTrends.getRoomPressures().getReg() + "\n" +
                "short ROOM_PRESS_STRING_ID_START = " + configTrends.getRoomPressures().getStringIdStart() + "\n" +
                "\n" +
                "short VAV_ADDR_START = " + configTrends.getVAVs().getAddrStart() + "\n" +
                "short VAV_REG = " + configTrends.getVAVs().getReg() + "\n" +
                "short VAV_STRING_ID_START = " + configTrends.getVAVs().getStringIdStart() + "\n" +
                "\n" +
                "short VAV_SPLY_REG_EN_ADDR_START = " + configTrends.getVAVs().getSplyEnAddr() + "\n" +
                "\n" +
                "short stringID\n" +
                "short addr\n" +
                "short type \n" +
                "float scale \n" +
                "bool trendOn\n" +
                "short reg\n" +
                "short kkID\n" +
                "short kkType\n" +
                "short scrollSection\n" +
                "\n" +
                "short position\n" +
                "int TPConfigDW0, TPConfigDW1, TPConfigDW2, TPConfigDW3, TPConfigDW4, TPConfigDW5\n" +
                "\n" +
                "short dataCounter = 0\n" +
                "\n" +
                "short FDampNrEn\n" +
                "bool enabled = false\n" +
                "bool enable = true\n" +
                "bool disable = true\n" +
                "bool set = true\n" +
                "bool reset = false\n" +
                "bool setupTrendsInitDone\n" +
                "bool bufferingSpinner\n" +
                "\n" +
                "short VAVNrEn\n" +
                "\n" +
                "short i\n" +
                "short state\n" +
                "\n" +
                "GetData(setupTrendsInitDone, \"HMI\", \"CheckingSignalsDone\", 1)\n" +
                "SetData(enable, \"HMI\", \"SetupTrendsScreenActive\", 1)\n" +
                "\n" +
                "GetData(scrollSection, \"HMI\", \"ScrollSection\", 1)\n" +
                "GetData(kkID, \"HMI\", \"kkID\", 1)\n" +
                "GetData(kkType, \"HMI\", \"kkType\", 1)\n" +
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
                "TPConfigDW4 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "\n" +
                "if not setupTrendsInitDone then // učitaj podatke ako oni prethodno nisu učitani\n" +
                "\tbufferingSpinner = true\n" +
                "\tSetData(bufferingSpinner, \"HMI\", \"ShowBufferingSpinner\", 1)\n" +
                "\t// get data\n" +
                "\tselect case kkType\n" +
                "\t\tcase 0 // standard";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        for (Signal signal : configTrends.getStandard()) {
            line = "\t\t\tenabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "\t\t\t" + COMMENT + signal.getSignalName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (signal.isTPConfigDW()) {
                line = "\t\t\tposition = " + signal.getBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\t\t\tGETBIT(TPConfigDW" + signal.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\t\t\tif enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() == 0) {
                    line = "\t\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "\t\t\treg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\taddr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tenabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "\t\t\treg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\taddr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tstate = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tif state " + signal.getEnabledCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\telse";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\t\tenabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tend if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            line = "\t\t\t\tstringID = " + signal.getStringId() + "\n" +
                    "\t\t\t\taddr = " + signal.getSignalAddr() + "\n" +
                    "\t\t\t\ttype = " + signal.getDataType() + "\n" +
                    "\t\t\t\tscale = " + signal.getScale() + "\n" +
                    "\t\t\t\treg = " + signal.getSignalReg() + "\n" +
                    "\t\t\t\tSetData(stringID, \"HMI\", LW, TRENDS_STR_ID_START + dataCounter, 1)\n" +
                    "\t\t\t\tSetData(addr, \"HMI\", LW, TRENDS_ADDR_START + dataCounter, 1)\n" +
                    "\t\t\t\tSetData(type, \"HMI\", LW, TRENDS_TYPE_START + dataCounter, 1)\n" +
                    "\t\t\t\tSetData(scale, \"HMI\", LW, TRENDS_SCALE_START + dataCounter * 2, 1)\n" +
                    "\t\t\t\tSetData(reg, \"HMI\", LW, TRENDS_REG_START + dataCounter, 1)\n" +
                    "\t\t\t\tdataCounter = dataCounter + 1";

            lines = line.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }

            line = "\t\t\tend if";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
        }

        line = "\t\t\t// Room related values\n" +
                "\t\t\treg = " + configTrends.getVAVs().getNumberReg() + "\n" +
                "\t\t\taddr = " + configTrends.getVAVs().getNumberAddr() + "\n" +
                "\t\t\tVAVNrEn = GetUnsignedShortFromPLC(kkID, reg, addr)\n" +
                "\t\t\t\t\t\n" +
                "\t\t\tfor i = 0 to VAVNrEn - 1 step 1\n" +
                "\t\t\t\t// room press\n" +
                "\t\t\t\tstringID = ROOM_PRESS_STRING_ID_START + i\n" +
                "\t\t\t\taddr = ROOM_PRESS_ADR_START + i * 3\n" +
                "\t\t\t\ttype = " + configTrends.getRoomPressures().getDataType() + "\n" +
                "\t\t\t\tscale = " + configTrends.getRoomPressures().getScale() + "\n" +
                "\t\t\t\treg = ROOM_PRESS_REG\n" +
                "\t\t\t\tSetData(stringID, \"HMI\", LW, TRENDS_STR_ID_START + dataCounter, 1)\n" +
                "\t\t\t\tSetData(addr, \"HMI\", LW, TRENDS_ADDR_START + dataCounter, 1)\n" +
                "\t\t\t\tSetData(type, \"HMI\", LW, TRENDS_TYPE_START + dataCounter, 1)\n" +
                "\t\t\t\tSetData(scale, \"HMI\", LW, TRENDS_SCALE_START + dataCounter * 2, 1)\n" +
                "\t\t\t\tSetData(reg, \"HMI\", LW, TRENDS_REG_START + dataCounter, 1)\n" +
                "\t\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\t\t\n" +
                "\t\t\t\t// sply vav reg\n" +
                "\t\t\t\treg = " + configTrends.getVAVs().getSplyEnReg() + "\n" +
                "\t\t\t\taddr = VAV_SPLY_REG_EN_ADDR_START + i\n" +
                "\t\t\t\tenabled = GetShortFromPLC(kkID, reg, addr)\n" +
                "\t\t\t\tif enabled then\n" +
                "\t\t\t\t\tstringID = VAV_STRING_ID_START + i * 2\n" +
                "\t\t\t\t\taddr = VAV_ADDR_START + i * 3 + 1\n" +
                "\t\t\t\t\ttype = " + configTrends.getVAVs().getDataType() + "\n" +
                "\t\t\t\t\tscale = " + configTrends.getVAVs().getScale() + "\n" +
                "\t\t\t\t\treg = VAV_REG\n" +
                "\t\t\t\t\tSetData(stringID, \"HMI\", LW, TRENDS_STR_ID_START + dataCounter, 1)\n" +
                "\t\t\t\t\tSetData(addr, \"HMI\", LW, TRENDS_ADDR_START + dataCounter, 1)\n" +
                "\t\t\t\t\tSetData(type, \"HMI\", LW, TRENDS_TYPE_START + dataCounter, 1)\n" +
                "\t\t\t\t\tSetData(scale, \"HMI\", LW, TRENDS_SCALE_START + dataCounter * 2, 1)\n" +
                "\t\t\t\t\tSetData(reg, \"HMI\", LW, TRENDS_REG_START + dataCounter, 1)\n" +
                "\t\t\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\t\t\tend if\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t// ex vav reg\n" +
                "\t\t\t\tstringID = VAV_STRING_ID_START + i * 2 + 1\n" +
                "\t\t\t\taddr = VAV_ADDR_START + i * 3 + 2\n" +
                "\t\t\t\ttype = " + configTrends.getVAVs().getDataType() + "\n" +
                "\t\t\t\tscale = " + configTrends.getVAVs().getScale() + "\n" +
                "\t\t\t\treg = VAV_REG\n" +
                "\t\t\t\tSetData(stringID, \"HMI\", LW, TRENDS_STR_ID_START + dataCounter, 1)\n" +
                "\t\t\t\tSetData(addr, \"HMI\", LW, TRENDS_ADDR_START + dataCounter, 1)\n" +
                "\t\t\t\tSetData(type, \"HMI\", LW, TRENDS_TYPE_START + dataCounter, 1)\n" +
                "\t\t\t\tSetData(scale, \"HMI\", LW, TRENDS_SCALE_START + dataCounter * 2, 1)\n" +
                "\t\t\t\tSetData(reg, \"HMI\", LW, TRENDS_REG_START + dataCounter, 1)\n" +
                "\t\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\t\tnext i";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        line = "\t\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\t\tcase 1 " + COMMENT + "pool";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        for (Signal signal : configTrends.getPool()) {
            line = "\t\t\tenabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "\t\t\t" + COMMENT + signal.getSignalName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (signal.isTPConfigDW()) {
                line = "\t\t\tposition = " + signal.getBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\t\t\tGETBIT(TPConfigDW" + signal.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\t\t\tif enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() == 0) {
                    line = "\t\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "\t\t\treg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\taddr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tenabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "\t\t\treg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\taddr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tstate = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tif state " + signal.getEnabledCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\telse";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\t\tenabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tend if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            line = "\t\t\t\tstringID = " + signal.getStringId() + "\n" +
                    "\t\t\t\taddr = " + signal.getSignalAddr() + "\n" +
                    "\t\t\t\ttype = " + signal.getDataType() + "\n" +
                    "\t\t\t\tscale = " + signal.getScale() + "\n" +
                    "\t\t\t\treg = " + signal.getSignalReg() + "\n" +
                    "\t\t\t\tSetData(stringID, \"HMI\", LW, TRENDS_STR_ID_START + dataCounter, 1)\n" +
                    "\t\t\t\tSetData(addr, \"HMI\", LW, TRENDS_ADDR_START + dataCounter, 1)\n" +
                    "\t\t\t\tSetData(type, \"HMI\", LW, TRENDS_TYPE_START + dataCounter, 1)\n" +
                    "\t\t\t\tSetData(scale, \"HMI\", LW, TRENDS_SCALE_START + dataCounter * 2, 1)\n" +
                    "\t\t\t\tSetData(reg, \"HMI\", LW, TRENDS_REG_START + dataCounter, 1)\n" +
                    "\t\t\t\tdataCounter = dataCounter + 1";

            lines = line.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }

            line = "\t\tend if";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
        }

        line = "\t\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = "\tend select";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = "\tSetData(dataCounter, \"HMI\", \"TrendsCount\", 1)\n" +
                "\tsetupTrendsInitDone = true\n" +
                "\tSetData(setupTrendsInitDone, \"HMI\", \"CheckingSignalsDone\", 1) // označi da su podaci učitani (u SetupTrends_dinit se postavlja na false)\n" +
                "\tbufferingSpinner = false\n" +
                "\tSetData(bufferingSpinner, \"HMI\", \"ShowBufferingSpinner\", 1)\n" +
                "end if\n" +
                "\n" +
                "\n" +
                "// **Present on screen**\n" +
                "// calculate scroll sections\n" +
                "GetData(dataCounter, \"HMI\", \"TrendsCount\", 1)\n" +
                "\n" +
                "short maxScrollSections\n" +
                "maxScrollSections = MaxScrollSections(dataCounter, MAX_TRENDS_PER_SCREEN) \n" +
                "\n" +
                "short currentSection = 0\n" +
                "GetData(currentSection, \"HMI\", \"ScrollSection\", 1)\n" +
                "currentSection = currentSection + 1 // currentSection se u memoriji upisuje od 0 pa ga treba uvećati za 1\n" +
                "\n" +
                "// enable / disable arrow buttons\n" +
                "enable = false\n" +
                "if currentSection >= maxScrollSections then\n" +
                "\tSetData(disable, \"HMI\", \"ScrollRightDisable\", 1)\n" +
                "\tshort pom = 0\n" +
                "\tpom = maxScrollSections - 1\n" +
                "\tSetData(pom, \"HMI\", \"ScrollSection\", 1)\n" +
                "\tcurrentSection = pom + 1 // currentSection se u memoriji upisuje od 0 pa ga treba uvećati za 1\n" +
                "else\n" +
                "\tSetData(enable, \"HMI\", \"ScrollRightDisable\", 1)\t\n" +
                "end if\n" +
                "if currentSection <= 1 then\n" +
                "\tSetData(disable, \"HMI\", \"ScrollLeftDisable\", 1)\n" +
                "\tpom = 0\n" +
                "\tSetData(pom, \"HMI\", \"ScrollSection\", 1)\n" +
                "\tcurrentSection == pom\n" +
                "else\n" +
                "\tSetData(enable, \"HMI\", \"ScrollLeftDisable\", 1)\t\n" +
                "end if\n" +
                "\n" +
                "// present scroll section\n" +
                "SetData(currentSection, \"HMI\", \"CurrentSectionDisplay\", 1)\n" +
                "SetData(maxScrollSections, \"HMI\", \"MaxSectionDisplay\", 1)\n" +
                "\n" +
                "short index = 0\n" +
                "short emptyStringID = 0\n" +
                "disable = false\n" +
                "bool hide\n" +
                "for i = 0 to MAX_TRENDS_PER_SCREEN - 1 step 1\n" +
                "\thide = false\n" +
                "\tSetData(hide, \"HMI\", LB, LINE_HIDDEN_START + i, 1) \n" +
                "\n" +
                "\tindex = i + (currentSection - 1) * MAX_TRENDS_PER_SCREEN \n" +
                "\tGetData(stringID, \"HMI\", LW, TRENDS_STR_ID_START + index, 1)\n" +
                "\tGetData(trendOn, \"HMI\", LB, ENABLED_TRENDS_START + index, 1)\n" +
                "\t\n" +
                "\tSetData(trendOn, \"HMI\", LB, CHECK_TREND_START + i, 1)\n" +
                "\tSetData(stringID, \"HMI\", LW, DISP_STR_ID_START + i, 1)\n" +
                "\t//izbriši ostatak\n" +
                "\tif index >= dataCounter then\n" +
                "\t\thide = true\n" +
                "\t\tSetData(hide, \"HMI\", LB, LINE_HIDDEN_START + i, 1) \n" +
                "\t\t\t\t\n" +
                "\t\tSetData(emptyStringID, \"HMI\", LW, DISP_STR_ID_START + i, 1)\n" +
                "\t\tSetData(disable, \"HMI\", LW, CHECK_TREND_START + i, 1)\n" +
                "\tend if\n" +
                "next i\n" +
                "\n" +
                "SetData(scrollSection, \"HMI\", \"ScrollSection\", 1)\n" +
                "\n" +
                "//setupTrendsInit = false\n" +
                "//SetData(setupTrendsInit, \"HMI\", \"SetupTrendsInit\", 1)\n" +
                "\n" +
                "// kopiranje prije odabranih trendova u pomoćnu memoriju za kasniju detekciju promjene odabranih trendova\n" +
                "for i = 0 to MAX_SIGNAL_COUNT - 1 step 1\n" +
                "\tGetData(enabled, \"HMI\", LB, ENABLED_TRENDS_START + i, 1)\n" +
                "\tSetData(enabled, \"HMI\", LB, ENABLED_TRENDS_TEMP_START + i, 1)\n" +
                "next i\n";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.write(END_MACRO_COMMAND);

        bufferedWriter.close();

        mainForm.getLogArea().append("SetupTrends_init macro done!" + "\n");

        return file;
    }
}
