package generators;

import GUI.MainForm;
import entities.Signal;
import entities.States;
import exceptions.ConfigurationNotDoneException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import utilities.ConfigInputs;
import utilities.ConfigMain;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class ReadInputs {
    private static final String STRING_TABLE_NAME = "DigitalStatesInputs.xls";
    private static final int TABLE_SECTION_ID = 1;
    private static final String TABLE_SECTION_NAME = "DigitalStatesInputs";

    private static final String READ_INPUTS_NAME = "ReadInputs.txt";
    private static final String HEADER_1 = "//**";
    private static final String HEADER_2 = "// @Version: 7";
    private static final String HEADER_3 = "// @Author: Ivan Maltar";
    private static final String HEADER_4 = "// Macro koji se ciklički izvodi kada je aktivan bit LB 19 - InputScreenActive (bit koji označuje da otvoren jedan od ekrana koji prikazuju Input-e PLC-a).";
    private static final String HEADER_5 = "// Prvo se svaki signal (ovisno o odabranoj grupi signala) provjerava da li je omogućen (enabled).";
    private static final String HEADER_6 = "// Nakon provjere se svi omogućeni signali te njihova stanja prikazuju na ekranu ovisno o trenutnom ScrollSection-u.";
    private static final String HEADER_7 = "//*";
    private static final String VERSION_TAG = "// Excel version: ";
    private static final String OPEN_MACRO_COMMAND = "macro_command main()";
    private static final String END_MACRO_COMMAND = "end macro_command";

    private static final String DISP_STR_ID_START_DEC = "short DISP_STR_ID_START = ";
    private static final String DISP_STR_ID_START = "DISP_STR_ID_START";
    private static final String DISP_DATA_START_DEC = "short DISP_DATA_START = ";
    private static final String DISP_DATA_START = "DISP_DATA_START";
    private static final String MAX_DATA_PER_SCREEN_DEC = "short MAX_DATA_PER_SCREEN = ";
    private static final String MAX_DATA_PER_SCREEN = "MAX_DATA_PER_SCREEN";

    private static final String LINE_HIDDEN_START_DEC = "short LINE_HIDDEN_START = ";
    private static final String LINE_HIDDEN_START = "LINE_HIDDEN_START";

    private static final String FIRE_DAMPR_ADDR_START_DEC = "short FIRE_DAMPR_ADDR_START = ";
    private static final String FIRE_DAMPR_ADDR_START = "FIRE_DAMPR_ADDR_START";
    private static final String FIRE_DAMPR_REG_DEC = "short FIRE_DAMPR_REG = ";
    private static final String FIRE_DAMPR_REG = "FIRE_DAMPR_REG";
    private static final String FIRE_DAMPR_STRING_ID_START_DEC = "short FIRE_DAMPR_STRING_ID_START = ";
    private static final String FIRE_DAMPR_STRING_ID_START = "FIRE_DAMPR_STRING_ID_START";

    private static final String ROOM_PRESS_ADR_START_DEC = "short ROOM_PRESS_ADR_START = ";
    private static final String ROOM_PRESS_ADR_START = "ROOM_PRESS_ADR_START";
    private static final String ROOM_PRESS_REG_DEC = "short ROOM_PRESS_REG = ";
    private static final String ROOM_PRESS_REG = "ROOM_PRESS_REG";
    private static final String ROOM_PRESS_STRING_ID_START_DEC = "short ROOM_PRESS_STRING_ID_START = ";
    private static final String ROOM_PRESS_STRING_ID_START = "ROOM_PRESS_STRING_ID_START";

    private static final String VAV_SWITCHES_EN_ADDR_START_DEC = "short VAV_SWITCHES_EN_ADDR_START = ";
    private static final String VAV_SWITCHES_EN_ADDR_START = "VAV_SWITCHES_EN_ADDR_START";
    private static final String VAV_SWITCHES_EN_REG_DEC = "short VAV_SWITCHES_EN_REG = ";
    private static final String VAV_SWITCHES_EN_REG = "VAV_SWITCHES_EN_REG";

    private static final String VAV_SWITCHES_ADDR_START_DEC = "short VAV_SWITCHES_ADDR_START = ";
    private static final String VAV_SWITCHES_ADDR_START = "VAV_SWITCHES_ADDR_START";
    private static final String VAV_SWITCHES_REG_DEC = "short VAV_SWITCHES_REG = ";
    private static final String VAV_SWITCHES_REG = "VAV_SWITCHES_REG";
    private static final String VAV_SWITCHES_STRING_ID_START_DEC = "short VAV_SWITCHES_STRING_ID_START = ";
    private static final String VAV_SWITCHES_STRING_ID_START = "VAV_SWITCHES_STRING_ID_START";
    private static final String MAX_VAV_SWITCHES_DEC = "short MAX_VAV_SWITCHES = ";
    private static final String MAX_VAV_SWITCHES = "MAX_VAV_SWITCHES";
    private static final String COMMENT = "// ";

    private ReadInputs() {}

    public static File generateReadInputsMacro(ConfigMain configMain, ConfigInputs configInputs, String path, MainForm mainForm) throws ConfigurationNotDoneException, IOException {
        path += "\\" + READ_INPUTS_NAME;

        if (!configMain.isConfigurated() || !configInputs.isConfigured()) {
            throw new ConfigurationNotDoneException();
        }

        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        String line;

        bufferedWriter.write(HEADER_1);
        bufferedWriter.newLine();
        bufferedWriter.write(HEADER_2);
        bufferedWriter.newLine();
        bufferedWriter.write(HEADER_3);
        bufferedWriter.newLine();
        bufferedWriter.write(HEADER_4);
        bufferedWriter.newLine();
        bufferedWriter.write(HEADER_5);
        bufferedWriter.newLine();
        bufferedWriter.write(HEADER_6);
        bufferedWriter.newLine();
        bufferedWriter.write(HEADER_7);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(VERSION_TAG + configInputs.getVersion());
        bufferedWriter.newLine();
        bufferedWriter.write(OPEN_MACRO_COMMAND);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(DISP_STR_ID_START_DEC + configMain.getDispStrIdStart());
        bufferedWriter.newLine();
        bufferedWriter.write(DISP_DATA_START_DEC + configMain.getDispDataStart());
        bufferedWriter.newLine();
        bufferedWriter.write(MAX_DATA_PER_SCREEN_DEC + configMain.getMaxDataPerScreen());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(LINE_HIDDEN_START_DEC + configMain.getLineHiddenStart());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(FIRE_DAMPR_ADDR_START_DEC + configInputs.getFireDampers().getAddrStart());
        bufferedWriter.newLine();
        bufferedWriter.write(FIRE_DAMPR_REG_DEC + configInputs.getFireDampers().getReg());
        bufferedWriter.newLine();
        bufferedWriter.write(FIRE_DAMPR_STRING_ID_START_DEC + configInputs.getFireDampers().getStringIdStart());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(ROOM_PRESS_ADR_START_DEC + configInputs.getRoomPressures().getAddrStart());
        bufferedWriter.newLine();
        bufferedWriter.write(ROOM_PRESS_REG_DEC + configInputs.getRoomPressures().getReg());
        bufferedWriter.newLine();
        bufferedWriter.write(ROOM_PRESS_STRING_ID_START_DEC + configInputs.getRoomPressures().getStringIdStart());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(VAV_SWITCHES_EN_ADDR_START_DEC + configInputs.getVAVSwitches().getEnabledAddr());
        bufferedWriter.newLine();
        bufferedWriter.write(VAV_SWITCHES_EN_REG_DEC + configInputs.getVAVSwitches().getEnabledReg());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(VAV_SWITCHES_ADDR_START_DEC + configInputs.getVAVSwitches().getAddrStart());
        bufferedWriter.newLine();
        bufferedWriter.write(VAV_SWITCHES_REG_DEC + configInputs.getVAVSwitches().getReg());
        bufferedWriter.newLine();
        bufferedWriter.write(VAV_SWITCHES_STRING_ID_START_DEC + configInputs.getVAVSwitches().getStringIdStart());
        bufferedWriter.newLine();
        bufferedWriter.write(MAX_VAV_SWITCHES_DEC + configInputs.getVAVSwitches().getMaxVAVSwitches());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "bool digitalAnalog = false";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short inputsSelected";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "bool enabled = false";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short state";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short stringID";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = "short kkID";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short reg";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short addr";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = "short position";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "int TPConfigDW0, TPConfigDW1, TPConfigDW2, TPConfigDW3, TPConfigDW4, TPConfigDW5";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = COMMENT + "Polja u kojima se spremaju podaci za prikaz na ekranu, maksimalan broj podataka postavljen je na 250";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short stringIDs[250] " + COMMENT + "id-evi stringova za labele s lijeve strane";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short regType[250]";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short statesPresent[9] " + COMMENT + "stanja digitalnih signala koji se prikazuju ** vezan za MAX_DATA_PER_SCREEN **";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short stateStringIDsStart[250] " + COMMENT + "u string tablici stringovi za stanja idu po redu (npr: prva skupina stanja kreće od 1 (1-off, 2-on), druga skupina stanja kreće od 3 (3-Auto, 4-Fan, 5-Defrost, 6-Cooling, 7-Heating))";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short addresses[250]";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short dataCounter = 0 " + COMMENT + "broj podataka";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "float scales[250] " + COMMENT + "scale za analogne signale";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "short dataType [250] " + COMMENT + "data type za analogne signale";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "float dataPresent[9] " + COMMENT + "vrijednost analognih signala koji se prikazuju ** vezan za MAX_DATA_PER_SCREEN **";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = "short FDampNrEn";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "bool FDampClsdFbEn";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "short VAVNrEn";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "short i";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "GetData(kkID, \"HMI\", \"kkID\", 1)";
        bufferedWriter.write(line);
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

        line = "GetData(inputsSelected, \"HMI\", \"InputsSelected\", 1) " + COMMENT + "0-digital 1-analog 2-firedampr 3-roompress 4-vavswitches";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "if inputsSelected == 0 or inputsSelected == 2 or inputsSelected == 4 then";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tdigitalAnalog = 0";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "else";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tdigitalAnalog = 1";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "end if";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "select case inputsSelected";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tcase 0 " + COMMENT + "digital";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        for (Signal signal : configInputs.getDigital()) {
            line = "\t\tenabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "\t\t" + COMMENT + signal.getSignalName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (signal.isTPConfigDW()) {
                line = "\t\tposition = " + signal.getBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\t\tGETBIT(TPConfigDW" + signal.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\t\tif enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() == 0) {
                    line = "\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "\t\treg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\taddr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "\t\treg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\taddr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tstate = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif state " + signal.getEnabledCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\telse";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tenabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tend if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            String code = "\t\t\tstringIDs[dataCounter] = " + signal.getStringId() +"\n" +
                    "\t\t\tstateStringIDsStart[dataCounter] = " + (signal.getStates().getStateStringIndex() + 1) + "\n" + // in strings table the string with index 0 is an empty string, states string index must be incremented by 1
                    "\t\t\tregType[dataCounter] = " + signal.getSignalReg() + "\n" +
                    "\t\t\taddresses[dataCounter] = " + signal.getSignalAddr() + "\n" +
                    "\t\t\tdataCounter = dataCounter + 1";

            String[] lines = code.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }

            line = "\t\tend if";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
        }

        line = "\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tcase 1 " + COMMENT + "analog";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        for (Signal signal : configInputs.getAnalog()) {
            line = "\t\tenabled = false";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = "\t\t" + COMMENT + signal.getSignalName();
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            if (signal.isTPConfigDW()) {
                line = "\t\tposition = " + signal.getBitPosition();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\t\tGETBIT(TPConfigDW" + signal.getEnabledTPConfigDW() + ", enabled, position)";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = "\t\tif enabled then";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            } else {
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() == 0) {
                    line = "\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "\t\treg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\taddr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tenabled = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                if (!signal.getEnabledCondition().equals("x") && signal.getEnabledAddr() != 0) {
                    line = "\t\treg = " + signal.getEnabledReg();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\taddr = " + signal.getEnabledAddr();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tstate = GetShortFromPLC(kkID, reg, addr)";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif state " + signal.getEnabledCondition() + " then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tenabled = true";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\telse";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\t\tenabled = false";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tend if";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    line = "\t\tif enabled then";
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }

            String code = "\t\t\tstringIDs[dataCounter] = " + signal.getStringId() + "\n" +
                    "\t\t\tregType[dataCounter] = " + signal.getSignalReg() +"\n" +
                    "\t\t\taddresses[dataCounter] = " + signal.getSignalAddr() +"\n" +
                    "\t\t\tscales[dataCounter] = " + signal.getScale() + "\n" +
                    "\t\t\tdataType[dataCounter] = " + signal.getDataType() + "\n" +
                    "\t\t\tdataCounter = dataCounter + 1";

            String[] lines = code.split("\n");
            for (String pom : lines) {
                bufferedWriter.write(pom);
                bufferedWriter.newLine();
            }

            line = "\t\tend if";
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
        }

        line = "\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tcase 2 " + COMMENT + "firedampr";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        String code = "\t\treg = " + configInputs.getFireDampers().getNumberEnReg() + "\n" +
                "\t\taddr = " + configInputs.getFireDampers().getNumberEnAddr() + "\n" +
                "\t\tFDampNrEn = GetUnsignedShortFromPLC(kkID, reg, addr)\n" +
                "\t\t\n" +
                "\t\treg = " + configInputs.getFireDampers().getFireDampersClosed().getEnabledReg() + "\n" +
                "\t\taddr = " + configInputs.getFireDampers().getFireDampersClosed().getEnabledAddr() + "\n" +
                "\t\tFDampClsdFbEn = GetUnsignedShortFromPLC(kkID, reg, addr)\n" +
                "\t\t\n" +
                "\t\tfor i = 0 to (FDampNrEn - 1) * 2 step 2\n" +
                "\t\t\tstringIDs[dataCounter] = " + FIRE_DAMPR_STRING_ID_START + " + i\n" +
                "\t\t\tstateStringIDsStart[dataCounter] = " + (configInputs.getFireDampers().getStates().getStateStringIndex() + 1) + "\n" + // in strings table the string with index 0 is an empty string, states string index must be incremented by 1
                "\t\t\taddresses[dataCounter] = " + FIRE_DAMPR_ADDR_START + " + i\n" +
                "\t\t\tregType[dataCounter] = " + FIRE_DAMPR_REG + "\n" +
                "\t\t\tdataType[dataCounter] = " + configInputs.getFireDampers().getDataType() + "\n" +
                "\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\t\tif FDampClsdFbEn then\n" +
                "\t\t\t\tstringIDs[dataCounter] = " + FIRE_DAMPR_STRING_ID_START + " + i + 1\n" +
                "\t\t\t\tstateStringIDsStart[dataCounter] = " + (configInputs.getFireDampers().getFireDampersClosed().getStates().getStateStringIndex() + 1) + "\n" + // in strings table the string with index 0 is an empty string, states string index must be incremented by 1
                "\t\t\t\taddresses[dataCounter] = " + FIRE_DAMPR_ADDR_START + " + i + 1\n" +
                "\t\t\t\tregType[dataCounter] = " + FIRE_DAMPR_REG + "\n" +
                "\t\t\t\tdataType[dataCounter] = " + configInputs.getFireDampers().getDataType() + "\n" +
                "\t\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\t\tend if\n" +
                "\t\tnext i";

        String[] lines = code.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        line = "\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tcase 3 " + COMMENT + "roompress";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        code = "\t\treg = " + configInputs.getRoomPressures().getEnabledReg() + "\n" +
                "\t\taddr = " + configInputs.getRoomPressures().getEnabledAddr() + "\n" +
                "\t\tVAVNrEn = GetUnsignedShortFromPLC(kkID, reg, addr)\n" +
                "\t\t\n" +
                "\t\tfor i = 0 to VAVNrEn - 1 step 1\n" +
                "\t\t\tstringIDs[dataCounter] = " + ROOM_PRESS_STRING_ID_START + " + i\n" +
                "\t\t\taddresses[dataCounter] = " + ROOM_PRESS_ADR_START + " + i * 3\n" +
                "\t\t\tregType[dataCounter] = " + ROOM_PRESS_REG + "\n" +
                "\t\t\tdataType[dataCounter] = " + configInputs.getRoomPressures().getDataType() + "\n" +
                "\t\t\tscales[dataCounter] = " + configInputs.getRoomPressures().getScale() + "\n" +
                "\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\tnext i";

        lines = code.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        line = "\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tcase 4 " + COMMENT + "vavswitches";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        code = "for i = 0 to " + MAX_VAV_SWITCHES + " - 1 step 1\n" +
                "\t\t\treg = " + VAV_SWITCHES_EN_REG + "\n" +
                "\t\t\taddr = " + VAV_SWITCHES_EN_ADDR_START + " + i\n" +
                "\t\t\tenabled = GetShortFromPLC(kkID, reg, addr)\n" +
                "\t\t\tif enabled then\n" +
                "\t\t\t\tstringIDs[dataCounter] = " + VAV_SWITCHES_STRING_ID_START + " + i\n" +
                "\t\t\t\tstateStringIDsStart[dataCounter] = 1\n" +
                "\t\t\t\taddresses[dataCounter] = " + VAV_SWITCHES_ADDR_START + " + i\n" +
                "\t\t\t\tregType[dataCounter] = " + VAV_SWITCHES_REG + "\n" +
                "\t\t\t\tdataType[dataCounter] = " + configInputs.getVAVSwitches().getDataType() + "\n" +
                "\t\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\t\tend if\n" +
                "\t\tnext i";

        lines = code.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        line = "\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = "end select";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        code = "// calculate scroll sections\n" +
                "short maxScrollSections\n" +
                "maxScrollSections = MaxScrollSections(dataCounter, " + MAX_DATA_PER_SCREEN + ") \t\n" +
                "\t\n" +
                "// enable / disable arrow buttons\n" +
                "bool disable = true\n" +
                "bool enable = false\n" +
                "short currentSection = 0\n" +
                "GetData(currentSection, \"HMI\", \"ScrollSection\", 1)\n" +
                "currentSection = currentSection + 1 // currentSection se u memoriji upisuje od 0 pa ga treba uvećati za 1\n" +
                "\n" +
                "if currentSection >= maxScrollSections then\n" +
                "\tSetData(disable, \"HMI\", \"ScrollRightDisable\", 1)\n" +
                "\tshort pom = 0\n" +
                "\tpom = maxScrollSections - 1\n" +
                "\tSetData(pom, \"HMI\", \"ScrollSection\", 1)\n" +
                "\tcurrentSection = pom + 1 // currentSection se u memoriji upisuje od 0 pa ga treba uvećati za 1\n" +
                "else\n" +
                "\tSetData(enable, \"HMI\", \"ScrollRightDisable\", 1)\t\n" +
                "end if\n" +
                "\t\n" +
                "// present scroll section\n" +
                "SetData(currentSection, \"HMI\", \"CurrentSectionDisplay\", 1)\n" +
                "SetData(maxScrollSections, \"HMI\", \"MaxSectionDisplay\", 1)\n" +
                "\t\n" +
                "// get data\t\n" +
                "short index = 0\n" +
                "short sData\n" +
                "unsigned short usData\n" +
                "unsigned int uiData\n" +
                "float fData\n" +
                "for i = 0 to " + MAX_DATA_PER_SCREEN + " - 1 step 1\n" +
                "\tindex = i + (currentSection - 1) * " + MAX_DATA_PER_SCREEN + "\n" +
                "\tif index < dataCounter then\n" +
                "\t\tif digitalAnalog then // ako je analogno čitaj analogne signale i piši ih u dataPresent array\n" +
                "\t\t\tselect case dataType[index]\n" +
                "\t\t\t\tcase 0\n" +
                "\t\t\t\t\tusData = GetUnsignedShortFromPLC(kkID, regType[index], addresses[index])\n" +
                "\t\t\t\t\tfData = UnsignedShort2Float(usData)\n" +
                "\t\t\t\tbreak\n" +
                "\t\t\t\tcase 1\n" +
                "\t\t\t\t\tsData = GetShortFromPLC(kkID, regType[index], addresses[index])\n" +
                "\t\t\t\t\tfData = Short2Float(sData)\n" +
                "\t\t\t\tbreak\n" +
                "\t\t\t\tcase 2\n" +
                "\t\t\t\t\tuiData = GetUnsignedIntFromPLC(kkID, regType[index], addresses[index])\n" +
                "\t\t\t\t\tfData = UnsignedInt2Float(uiData)\n" +
                "\t\t\t\tbreak\n" +
                "\t\t\tend select\n" +
                "\t\t\tdataPresent[i] = fData * scales[index]\n" +
                "\t\telse // ako je digitalno, čitaj digitalne signale i piši iz u statePresent array\n" +
                "\t\t\tstatesPresent[i] = GetShortFromPLC(kkID, regType[index], addresses[index])\n" +
                "\t\tend if\n" +
                "\tend if \n" +
                "next i\n" +
                "\n" +
                "// **Present data on screen**\n" +
                "short currentKkID\n" +
                "bool hide\n" +
                "bool currentDigitalAnalog = false\n" +
                "bool inputScreenActive = false\n" +
                "short currentInputsSelected\n" +
                "GetData(currentKkID, \"HMI\", \"kkID\", 1)\n" +
                "//GetData(currentDigitalAnalog, \"HMI\", \"DigitalAnalog\", 1)\n" +
                "GetData(inputScreenActive, \"HMI\", \"InputScreenActive\", 1)\n" +
                "GetData(currentInputsSelected, \"HMI\", \"InputsSelected\", 1)\n" +
                "// present the data on screen if kkID hasn't changed, digital-analog window hasn't changed and user didn't exit input window since start of the macro\n" +
                "if currentKkID == kkID and inputScreenActive and currentInputsSelected == inputsSelected then\n" +
                "\tdisable = false\n" +
                "\tSetData(disable, \"HMI\", \"ShowBufferingSpinner\", 1)\n" +
                "\n" +
                "\tindex = 0\n" +
                "\tfor i = 0 to " + MAX_DATA_PER_SCREEN + " - 1 step 1\n" +
                "\t\tindex = i + (currentSection - 1) * " + MAX_DATA_PER_SCREEN + " \n" +
                "\t\tSetData(stringIDs[index], \"HMI\", LW, " + DISP_STR_ID_START + " + i, 1)\n" +
                "\t\tif digitalAnalog then // ako je analogno piši podatke iz dataPresent array-a\n" +
                "\t\t\thide = false\n" +
                "\t\t\tSetData(hide, \"HMI\", LB, " + LINE_HIDDEN_START + " + i, 1) \n" +
                "\t\t\t\n" +
                "\t\t\tSetData(dataPresent[i], \"HMI\", LW, " + DISP_DATA_START + " + i * 8, 1)\n" +
                "\t\t\t// izbriši ostale labele i input display-e (u slučaju da se u radu neke komponente isključe)\n" +
                "\t\t\tshort emptyStringID = 0\n" +
                "\t\t\tif index >= dataCounter then\n" +
                "\t\t\t\thide = true\n" +
                "\t\t\t\tSetData(hide, \"HMI\", LB, " + LINE_HIDDEN_START + " + i, 1) \n" +
                "\t\t\t\t\n" +
                "\t\t\t\tfloat fPom = 0.0\n" +
                "\t\t\t\tSetData(emptyStringID, \"HMI\", LW, " + DISP_STR_ID_START + " + i, 1)\n" +
                "\t\t\t\tSetData(fPom, \"HMI\", LW, " + DISP_DATA_START + " + i * 8, 1)\n" +
                "\t\t\tend if\n" +
                "\t\telse // ako je digitalno piši podatke na temelju statesPresent array-a\n" +
                "\t\t\thide = false\n" +
                "\t\t\tSetData(hide, \"HMI\", LB, " + LINE_HIDDEN_START + " + i, 1) \n" +
                "\t\t\n" +
                "\t\t\tstringID = stateStringIDsStart[index] + statesPresent[i]\n" +
                "\t\t\tSetData(stringID, \"HMI\", LW, " + DISP_DATA_START + " + i * 8, 1)\n" +
                "\t\t\t\n" +
                "\t\t\t// izbriši ostale labele i input display-e (u slučaju da se u radu neke komponente isključe)\n" +
                "\t\t\tif index >= dataCounter then\n" +
                "\t\t\t\thide = true\n" +
                "\t\t\t\tSetData(hide, \"HMI\", LB, " + LINE_HIDDEN_START + " + i, 1) \n" +
                "\t\t\t\t\n" +
                "\t\t\t\tSetData(emptyStringID, \"HMI\", LW, " + DISP_STR_ID_START + " + i, 1)\n" +
                "\t\t\t\tSetData(emptyStringID, \"HMI\", LW, " + DISP_DATA_START + " + i * 8, 1)\n" +
                "\t\t\tend if\t\n" +
                "\t\tend if\n" +
                "\tnext i\n" +
                "end if\n";
        lines = code.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }
        bufferedWriter.newLine();

        bufferedWriter.write(END_MACRO_COMMAND);

        bufferedWriter.close();

        mainForm.getLogArea().append("ReadInputs macro done!" + "\n");
        return file;
    }

    public static File generateStringTable(ConfigInputs configInputs, String path, MainForm mainForm) throws IOException {
        path += "\\" + STRING_TABLE_NAME;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Book1");

        HSSFRow firstRow = sheet.createRow(0);
        firstRow.createCell(0).setCellValue(TABLE_SECTION_ID);
        firstRow.createCell(1).setCellValue(TABLE_SECTION_NAME);
        firstRow.createCell(2).setCellValue(0);

        ArrayList<States> states = new ArrayList<>();
        for (Signal signal : configInputs.getDigital()) {
            if (!states.contains(signal.getStates())) {
                states.add(signal.getStates());
            }
        }

        if (!states.contains(configInputs.getFireDampers().getStates())) {
            states.add(configInputs.getFireDampers().getStates());
        }

        Collections.sort(states, new Comparator<States>() {

            @Override
            public int compare(States s1, States s2) {
                return s1.getStateStringIndex() - s2.getStateStringIndex();
            }
        });

        int stringID = 1;

        for (States stts : states) {
            for (int i = 0; i < stts.getStatesStr().get(0).length; i++) {
                HSSFRow row = sheet.createRow(stringID);
                row.createCell(0).setCellValue(TABLE_SECTION_ID);
                row.createCell(2).setCellValue(stringID++);
                for (int j = 0; j < stts.getStatesStr().size(); j++) {
                    row.createCell(3 + j).setCellValue(stts.getStatesStr().get(j)[i]);
                }
            }
        }

        File file = new File(path);
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        mainForm.getLogArea().append("Your excel file has been generated!\n");
        return file;
    }
}
