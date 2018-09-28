package generators;

import GUI.MainForm;
import entities.Signal;
import exceptions.ConfigurationNotDoneException;
import utilities.ConfigInputs;
import utilities.ConfigMain;

import java.io.*;
import java.util.ArrayList;

public final class ReadInputs {
    private static final String READ_INPUTS_NAME = "ReadInputs.txt";
    private static final String HEADER_1 = "//**";
    private static final String HEADER_2 = "// @Version: 7";
    private static final String HEADER_3 = "// @Author: Ivan Maltar";
    private static final String HEADER_4 = "// Macro koji se ciklički izvodi kada je aktivan bit LB 19 - InputScreenActive (bit koji označuje da otvoren jedan od ekrana koji prikazuju Input-e PLC-a).";
    private static final String HEADER_5 = "// Prvo se svaki signal (ovisno o odabranoj grupi signala) provjerava da li je omogućen (enabled).";
    private static final String HEADER_6 = "// Nakon provjere se svi omogućeni signali te njihova stanja prikazuju na ekranu ovisno o trenutnom ScrollSection-u.";
    private static final String HEADER_7 = "//*";
    private static final String VERSION_TAG = "// Version: ";
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
    private static final String ROOM_PRESS_REG_DEC = "short FIRE_DAMPR_REG = ";
    private static final String ROOM_PRESS_REG = "FIRE_DAMPR_REG";
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

        bufferedWriter.write(VAV_SWITCHES_ADDR_START_DEC + configInputs.getVAVSwitches().getEnabledAddr());
        bufferedWriter.newLine();
        bufferedWriter.write(VAV_SWITCHES_EN_ADDR_START_DEC + configInputs.getVAVSwitches().getEnabledAddr());
        bufferedWriter.newLine();
        bufferedWriter.write(VAV_SWITCHES_EN_ADDR_START_DEC + configInputs.getVAVSwitches().getEnabledAddr());
        bufferedWriter.newLine();
        bufferedWriter.write(VAV_SWITCHES_EN_REG_DEC + configInputs.getVAVSwitches().getEnabledReg());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        bufferedWriter.write(END_MACRO_COMMAND);

        bufferedWriter.close();

        mainForm.getLogArea().append("ReadInputs macro done!" + "\n");
        return file;
    }
}
