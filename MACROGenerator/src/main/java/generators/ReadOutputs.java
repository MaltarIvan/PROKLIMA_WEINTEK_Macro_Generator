package generators;

import GUI.MainForm;
import entities.Signal;
import exceptions.ConfigurationNotDoneException;
import utilities.ConfigMain;
import utilities.ConfigOutputs;

import java.io.*;

/**
 * Created by Maltar on 3.10.2018..
 */
public final class ReadOutputs {
    private static final String READ_OUTPUTS_NAME = "ReadOutputs.txt";
    private static final String HEADER = "//**\n" +
            "// @Version: 7\n" +
            "// @Author: Ivan Maltar\n" +
            "// Macro koji se ciklički izvodi kada je aktivan bit LB 23 - OutputScreenActive (bit koji označuje da otvoren jedan od ekrana koji prikazuju Outpute-e PLC-a).\n" +
            "// Prvo se svaki signal (ovisno o odabranoj grupi signala) provjerava da li je omogućen (enabled).\n" +
            "// Nakon provjere se svi omogućeni signali te njihova stanja prikazuju na ekranu ovisno o trenutnom ScrollSection-u.\n" +
            "//*\n";
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

    private static final String VAV_NUMBER_REG_DEC = "short VAV_NUMBER_REG = ";
    private static final String VAV_NUMBER_ADDR_DEC = "short VAV_NUMBER_ADDR = ";
    private static final String VAV_STRING_ID_START_DEC = "short VAV_STRING_ID_START = ";
    private static final String VAV_ADDR_START_DEC = "short VAV_ADDR_START = ";
    private static final String VAV_REG_DEC = "short VAV_REG = ";
    private static final String VAV_DATA_TYPE_DEC = "short VAV_DATA_TYPE = ";
    private static final String VAV_SCALE_DEC = "short VAV_SCALE = ";
    private static final String VAV_SPLY_REG_EN_REG_DEC = "short VAV_SPLY_REG_EN_REG = ";
    private static final String VAV_SPLY_REG_EN_ADDR_START_DEC = "short VAV_SPLY_REG_EN_ADDR_START = ";
    private static final String VAV_NUMBER_REG = "VAV_NUMBER_REG";
    private static final String VAV_NUMBER_ADDR = "VAV_NUMBER_ADDR";
    private static final String VAV_STRING_ID_START= "VAV_STRING_ID_START";
    private static final String VAV_ADDR_START = "VAV_ADDR_START";
    private static final String VAV_REG = "VAV_REG";
    private static final String VAV_DATA_TYPE = "VAV_DATA_TYPE";
    private static final String VAV_SCALE = "VAV_SCALE";
    private static final String VAV_SPLY_REG_EN_REG = "VAV_SPLY_REG_EN_REG";
    private static final String VAV_SPLY_REG_EN_ADDR_START = "VAV_SPLY_REG_EN_ADDR_START";

    private static final String COMMENT = "// ";

    private ReadOutputs() {}

    public static File generateReadOutputsMacro(ConfigMain configMain, ConfigOutputs configOutputs, String path, MainForm mainForm) throws IOException, ConfigurationNotDoneException {
        path += "\\" + READ_OUTPUTS_NAME;

        if (!configMain.isConfigurated() || !configOutputs.isConfigured()) {
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

        bufferedWriter.write(VERSION_TAG + configOutputs.getVersion());
        bufferedWriter.newLine();
        bufferedWriter.write(OPEN_MACRO_COMMAND);
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        line = "short DISP_STR_ID_START = " + configMain.getDispStrIdStart() + " // adresa lokacije za stringID prve labele\n" +
                "short DISP_DATA_START = " + configMain.getDispDataStart() + " // adresa lokacije za prvi input display\n" +
                "short MAX_DATA_PER_SCREEN = " + configMain.getMaxDataPerScreen() + " // ** vezan za statesPresent[], dataPresent[]\n" +
                "\n" +
                "short LINE_HIDDEN_START = " + configMain.getLineHiddenStart() + "\n" +
                "\n" +
                "short VAV_ADDR_START = " + configOutputs.getVAVs().getAddrStart() + "\n" +
                "short VAV_REG = " + configOutputs.getVAVs().getReg() + "\n" +
                "short VAV_STRING_ID_START = " + configOutputs.getVAVs().getStringIdStart() + "\n" +
                "\n" +
                "short VAV_SPLY_REG_EN_ADDR_START = " + configOutputs.getVAVs().getSplyEnAddr() + "\n" +
                "\n" +
                "bool digitalAnalog = false\n" +
                "short outputsSelected\n" +
                "bool enabled = false\n" +
                "short state\n" +
                "short stringID\n" +
                "\n" +
                "short kkID\n" +
                "short reg\n" +
                "short addr \n" +
                "short position\n" +
                "int TPConfigDW0, TPConfigDW1, TPConfigDW2, TPConfigDW3, TPConfigDW4, TPConfigDW5\n" +
                "// Polja u kojima se spremaju podaci za prikaz na ekranu, maksimalan broj podatapa postavljen je na 100\n" +
                "short stringIDs[250] // id-evi stringova za labele s lijeve strane\n" +
                "short regType[250]\n" +
                "short statesPresent[9] // stanja digitalnih signala koji se prikazuju ** vezan za MAX_DATA_PER_SCREEN **\n" +
                "short stateStringIDsStart[250] // u string tablici stringovi za stanja idu po redu (npr: prva skupina stanja kreće od 1 (1-off, 2-on), druga skupina stanja kreće od 3 (3-Auto, 4-Fan, 5-Defrost, 6-Cooling, 7-Heating))\n" +
                "short addresses[250]\n" +
                "short dataCounter = 0 // broj podataka\n" +
                "// char data[2000] // 100 x 20 znakova, za dohvat podatka s indeksom N treba dohvatiti string na indeksu N*20 u duljini od 20 znakova (za analogne inpute)\n" +
                "float scales[250] // scale za analogne signale\n" +
                "short dataType [250] // data type za analogne signale\n" +
                "float dataPresent[9] // vrijednost analognih signala koji se prikazuju ** vezan za MAX_DATA_PER_SCREEN **\n" +
                "\n" +
                "short VAVNrEn\n" +
                "\n" +
                "short i\n" +
                "\n" +
                "GetData(kkID, \"HMI\", \"kkID\", 1)\n" +
                "\n" +
                "reg = 4\n" +
                "addr = 831\n" +
                "TPConfigDW0 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = 4\n" +
                "addr = 833\n" +
                "TPConfigDW1 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = 4\n" +
                "addr = 835\n" +
                "TPConfigDW2 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = 4\n" +
                "addr = 837\n" +
                "TPConfigDW3 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "reg = 4\n" +
                "addr = 839\n" +
                "TPConfigDW4 = GetUnsignedIntFromPLC(kkID, reg, addr)\n" +
                "\n" +
                "GetData(outputsSelected, \"HMI\", \"OutputsSelected\", 1) // 0-digital 1-analog 2-vavs\n" +
                "if outputsSelected == 0 then\n" +
                "\tdigitalAnalog = 0\n" +
                "else\n" +
                "\tdigitalAnalog = 1\n" +
                "end if\n";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }
        bufferedWriter.newLine();

        line = "select case outputsSelected";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tcase 0 " + COMMENT + "digital";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        for (Signal signal : configOutputs.getDigital()) {
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

            line = "\t\t\tstringIDs[dataCounter] = " + signal.getStringId() +"\n" +
                    "\t\t\tstateStringIDsStart[dataCounter] = " + (signal.getStates().getStateStringIndex() + 1) + "\n" + // in strings table the string with index 0 is an empty string, states string index must be incremented by 1
                    "\t\t\tregType[dataCounter] = " + signal.getSignalReg() + "\n" +
                    "\t\t\taddresses[dataCounter] = " + signal.getSignalAddr() + "\n" +
                    "\t\t\tdataCounter = dataCounter + 1";

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

        line = "\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tcase 1 " + COMMENT + "analog";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        for (Signal signal : configOutputs.getAnalog()) {
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

            line = "\t\t\tstringIDs[dataCounter] = " + signal.getStringId() + "\n" +
                    "\t\t\tregType[dataCounter] = " + signal.getSignalReg() +"\n" +
                    "\t\t\taddresses[dataCounter] = " + signal.getSignalAddr() +"\n" +
                    "\t\t\tscales[dataCounter] = " + signal.getScale() + "\n" +
                    "\t\t\tdataType[dataCounter] = " + signal.getDataType() + "\n" +
                    "\t\t\tdataCounter = dataCounter + 1";

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

        line = "\tbreak";
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        line = "\tcase 2 " + COMMENT + "vavs";
        bufferedWriter.write(line);
        bufferedWriter.newLine();

        line = "\t\treg = " + configOutputs.getVAVs().getNumberReg() + "\n" +
                "\t\taddr = " + configOutputs.getVAVs().getNumberAddr() + "\n" +
                "\t\tVAVNrEn = GetUnsignedShortFromPLC(kkID, reg, addr)\n" +
                "\t\t\n" +
                "\t\tfor i = 0 to VAVNrEn - 1 step 1\n" +
                "\t\t\tstringIDs[dataCounter] = VAV_STRING_ID_START + i * 2 + 1\n" +
                "\t\t\taddresses[dataCounter] = VAV_ADDR_START + i * 3 + 2\n" +
                "\t\t\tregType[dataCounter] = VAV_REG\n" +
                "\t\t\tdataType[dataCounter] = " + configOutputs.getVAVs().getDataType() + "\n" +
                "\t\t\tscales[dataCounter] = " + configOutputs.getVAVs().getScale() + "\n" +
                "\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\t\t\n" +
                "\t\t\treg = " + configOutputs.getVAVs().getReg() + "\n" +
                "\t\t\taddr = VAV_SPLY_REG_EN_ADDR_START + i\n" +
                "\t\t\tenabled = GetShortFromPLC(kkID, reg, addr)\n" +
                "\t\t\tif enabled then\n" +
                "\t\t\t\tstringIDs[dataCounter] = VAV_STRING_ID_START + i * 2\n" +
                "\t\t\t\taddresses[dataCounter] = VAV_ADDR_START + i * 3 + 1\n" +
                "\t\t\t\tregType[dataCounter] = VAV_REG\n" +
                "\t\t\t\tdataType[dataCounter] = " + configOutputs.getVAVs().getDataType() + "\n" +
                "\t\t\t\tscales[dataCounter] = " + configOutputs.getVAVs().getScale() + "\n" +
                "\t\t\t\tdataCounter = dataCounter + 1\n" +
                "\t\t\tend if\n" +
                "\t\tnext i";

        lines = line.split("\n");
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

        line = "// **Present data on screen**\n" +
                "// calculate scroll sections\n" +
                "short maxScrollSections\n" +
                "maxScrollSections = MaxScrollSections(dataCounter, MAX_DATA_PER_SCREEN) \t\n" +
                "\n" +
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
                "\n" +
                "// present scroll section\n" +
                "SetData(currentSection, \"HMI\", \"CurrentSectionDisplay\", 1)\n" +
                "SetData(maxScrollSections, \"HMI\", \"MaxSectionDisplay\", 1)\n" +
                "\n" +
                "// get data\t\n" +
                "short index = 0\n" +
                "unsigned short usData\n" +
                "short sData\n" +
                "unsigned int uiData\n" +
                "float fData\n" +
                "short address\n" +
                "for i = 0 to MAX_DATA_PER_SCREEN - 1 step 1\n" +
                "\tindex = i + (currentSection - 1) * MAX_DATA_PER_SCREEN\n" +
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
                "disable = false\n" +
                "SetData(disable, \"HMI\", \"ShowBufferingSpinner\", 1)\n" +
                "\n" +
                "// **Present data on screen**\n" +
                "short currentKkID\n" +
                "bool hide\n" +
                "bool currentDigitalAnalog = false\n" +
                "bool outputScreenActive = false\n" +
                "short currentOutputsSelected\n" +
                "GetData(currentKkID, \"HMI\", \"kkID\", 1)\n" +
                "// GetData(currentDigitalAnalog, \"HMI\", \"DigitalAnalog\", 1)\n" +
                "GetData(outputScreenActive, \"HMI\", \"OutputScreenActive\", 1)\n" +
                "GetData(currentOutputsSelected, \"HMI\", \"OutputsSelected\", 1)\n" +
                "// present the data on screen if kkID hasn't changed, digital-analog window hasn't changed and user didn't exit input window since start of the macro\n" +
                "if currentKkID == kkID and currentOutputsSelected == outputsSelected and outputScreenActive  then\n" +
                "\tdisable = false\n" +
                "\tSetData(disable, \"HMI\", \"ShowBufferingSpinner\", 1)\n" +
                "\n" +
                "\tindex = 0\n" +
                "\tfor i = 0 to MAX_DATA_PER_SCREEN - 1 step 1\n" +
                "\t\tindex = i + (currentSection - 1) * MAX_DATA_PER_SCREEN \n" +
                "\t\tSetData(stringIDs[index], \"HMI\", LW, INPUT_STR_ID_START + i, 1)\n" +
                "\t\tif digitalAnalog then // ako je analogno piši podatke iz dataPresent array-a\n" +
                "\t\t\thide = false\n" +
                "\t\t\tSetData(hide, \"HMI\", LB, LINE_HIDDEN_START + i, 1) \n" +
                "\t\t\n" +
                "\t\t\tSetData(dataPresent[i], \"HMI\", LW, INPUT_DISP_START + i * 8, 1)\n" +
                "\t\t\t// izbriši ostale labele i input display-e (u slučaju da se u radu neke komponente isključe)\n" +
                "\t\t\tshort emptyStringID = 0\n" +
                "\t\t\tif index >= dataCounter then\n" +
                "\t\t\t\thide = true\n" +
                "\t\t\t\tSetData(hide, \"HMI\", LB, LINE_HIDDEN_START + i, 1) \n" +
                "\t\t\t\n" +
                "\t\t\t\tfloat sPom = 0\n" +
                "\t\t\t\tSetData(emptyStringID, \"HMI\", LW, INPUT_STR_ID_START + i, 1)\n" +
                "\t\t\t\tSetData(sPom, \"HMI\", LW, INPUT_DISP_START + i * 8, 1)\n" +
                "\t\t\tend if\n" +
                "\t\telse // ako je digitalno piši podatke na temelju statesPresent array-a\n" +
                "\t\t\tstringID = stateStringIDsStart[index] + statesPresent[i]\n" +
                "\t\t\t\n" +
                "\t\t\tSetData(stringID, \"HMI\", LW, INPUT_DISP_START + i * 8, 1)\n" +
                "\t\t\t\n" +
                "\t\t\t// izbriši ostale labele i input display-e (u slučaju da se u radu neke komponente isključe)\n" +
                "\t\t\tif index >= dataCounter then\n" +
                "\t\t\t\tSetData(emptyStringID, \"HMI\", LW, INPUT_STR_ID_START + i, 1)\n" +
                "\t\t\t\tSetData(emptyStringID, \"HMI\", LW, INPUT_DISP_START + i * 8, 1)\n" +
                "\t\t\tend if\t\n" +
                "\t\tend if\n" +
                "\tnext i\t\t\n" +
                "end if\n";

        lines = line.split("\n");
        for (String pom : lines) {
            bufferedWriter.write(pom);
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.write(END_MACRO_COMMAND);

        bufferedWriter.close();

        mainForm.getLogArea().append("ReadOutputs macro done!" + "\n");
        return file;
    }
}
