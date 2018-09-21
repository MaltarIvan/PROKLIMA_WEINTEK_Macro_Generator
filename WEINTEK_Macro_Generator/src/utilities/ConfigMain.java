package utilities;

import exceptions.WrongFormatException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigMain {
    static private int dispStrIdStart;
    static private int maxDataPerScreen;
    static private int stringIdStart;
    static private int regStart;
    static private int addrStart;
    static private int stateIndexStart;
    static private int stateCountStart;
    static private int disabledStart;
    static private int inputDisabledStart;
    static private int stateMonitorStart;
    static private int stateUpdateStart;
    static private int stateStringStart;
    static private int digitalStateSringStart;
    static private int TPConfigDW0Reg;
    static private int TPConfigDW0Addr;
    static private int TPConfigDW1Reg;
    static private int TPConfigDW1Addr;
    static private int TPConfigDW2Reg;
    static private int TPConfigDW2Addr;
    static private int TPConfigDW3Reg;
    static private int TPConfigDW3Addr;
    static private int TPConfigDW4Reg;
    static private int TPConfigDW4Addr;
    static private int StptDigitalReadMacroId;
    static private int languageCount;
    static private String mainConfigPath;

    public ConfigMain(String mainConfigPath) {
        this.mainConfigPath = mainConfigPath;
    }

    static public void getConfiguration() throws FileNotFoundException, IOException, WrongFormatException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(mainConfigPath));
        String line = bufferedReader.readLine();

        String param;
        String value;
        String[] splitted;

        while (line != null) {
            splitted = line.split("=");
            param = splitted[0].trim();
            value = splitted[1].trim();

            switch (param) {
                case "DISP_STR_ID_START":
                    dispStrIdStart = Integer.parseInt(value);
                    break;
                case "MAX_DATA_PER_SCREEN":
                    maxDataPerScreen = Integer.parseInt(value);
                    break;
                case "STRING_ID_START":
                    stringIdStart = Integer.parseInt(value);
                    break;
                case "REG_START":
                    regStart = Integer.parseInt(value);
                    break;
                case "ADDR_START":
                    addrStart = Integer.parseInt(value);
                    break;
                case "STATE_INDEX_START":
                    stateIndexStart = Integer.parseInt(value);
                    break;
                case "STATE_COUNT_START":
                    stateCountStart = Integer.parseInt(value);
                    break;
                case "DISABLED_START":
                    disabledStart = Integer.parseInt(value);
                    break;
                case "INPUT_DISABLE_START":
                    inputDisabledStart = Integer.parseInt(value);
                    break;
                case "STATE_MONITOR_START":
                    stateMonitorStart = Integer.parseInt(value);
                    break;
                case "STATE_UPDATE_START":
                    stateUpdateStart = Integer.parseInt(value);
                    break;
                case "STATE_STRINGS_START":
                    stateStringStart = Integer.parseInt(value);
                    break;
                case "DIGITAL_STATES_STRING_START":
                    digitalStateSringStart = Integer.parseInt(value);
                    break;
                case "TPConfigDW0_REG":
                    TPConfigDW0Reg = Integer.parseInt(value);
                    break;
                case "TPConfigDW0_ADDR":
                    TPConfigDW0Addr = Integer.parseInt(value);
                    break;
                case "TPConfigDW1_REG":
                    TPConfigDW1Reg = Integer.parseInt(value);
                    break;
                case "TPConfigDW1_ADDR":
                    TPConfigDW1Addr = Integer.parseInt(value);
                    break;
                case "TPConfigDW2_REG":
                    TPConfigDW2Reg = Integer.parseInt(value);
                    break;
                case "TPConfigDW2_ADDR":
                    TPConfigDW2Addr = Integer.parseInt(value);
                    break;
                case "TPConfigDW3_REG":
                    TPConfigDW3Reg = Integer.parseInt(value);
                    break;
                case "TPConfigDW3_ADDR":
                    TPConfigDW3Addr = Integer.parseInt(value);
                    break;
                case "TPConfigDW4_REG":
                    TPConfigDW4Reg = Integer.parseInt(value);
                    break;
                case "TPConfigDW4_ADDR":
                    TPConfigDW4Addr = Integer.parseInt(value);
                    break;
                case "StptDigitalRead_MACRO_ID":
                    StptDigitalReadMacroId = Integer.parseInt(value);
                    break;
                case "LANGUAGE_COUNT":
                    languageCount = Integer.parseInt(value);
                    break;
                default:
                    throw new WrongFormatException();
            }

            line = bufferedReader.readLine();
        }

        print();

        bufferedReader.close();
    }

    public static void print() {
        System.out.println(dispStrIdStart);
        System.out.println(maxDataPerScreen);
        System.out.println(stringIdStart);
        System.out.println(regStart);
        System.out.println(addrStart);
        System.out.println(stateIndexStart);
        System.out.println(stateCountStart);
        System.out.println(disabledStart);
        System.out.println(inputDisabledStart);
        System.out.println(stateMonitorStart);
        System.out.println(stateUpdateStart);
        System.out.println(stateStringStart);
        System.out.println(digitalStateSringStart);
        System.out.println(TPConfigDW0Reg);
        System.out.println(TPConfigDW0Addr);
        System.out.println(TPConfigDW1Reg);
        System.out.println(TPConfigDW1Addr);
        System.out.println(TPConfigDW2Reg);
        System.out.println(TPConfigDW2Addr);
        System.out.println(TPConfigDW3Reg);
        System.out.println(TPConfigDW3Addr);
        System.out.println(TPConfigDW4Reg);
        System.out.println(TPConfigDW4Addr);
        System.out.println(StptDigitalReadMacroId);
        System.out.println(languageCount);
    }

    public int getStringIdStart() {
        return stringIdStart;
    }

    public void setStringIdStart(int stringIdStart) {
        ConfigMain.stringIdStart = stringIdStart;
    }

    public int getDispStrIdStart() {
        return dispStrIdStart;
    }

    public void setDispStrIdStart(int dispStrIdStart) {
        this.dispStrIdStart = dispStrIdStart;
    }

    public int getMaxDataPerScreen() {
        return maxDataPerScreen;
    }

    public void setMaxDataPerScreen(int maxDataPerScreen) {
        this.maxDataPerScreen = maxDataPerScreen;
    }

    public int getRegStart() {
        return regStart;
    }

    public void setRegStart(int regStart) {
        this.regStart = regStart;
    }

    public int getAddrStart() {
        return addrStart;
    }

    public void setAddrStart(int addrStart) {
        this.addrStart = addrStart;
    }

    public int getStateIndexStart() {
        return stateIndexStart;
    }

    public void setStateIndexStart(int stateIndexStart) {
        this.stateIndexStart = stateIndexStart;
    }

    public int getStateCountStart() {
        return stateCountStart;
    }

    public void setStateCountStart(int stateCountStart) {
        this.stateCountStart = stateCountStart;
    }

    public int getDisabledStart() {
        return disabledStart;
    }

    public void setDisabledStart(int disabledStart) {
        this.disabledStart = disabledStart;
    }

    public int getInputDisabledStart() {
        return inputDisabledStart;
    }

    public void setInputDisabledStart(int inputDisabledStart) {
        this.inputDisabledStart = inputDisabledStart;
    }

    public int getStateMonitorStart() {
        return stateMonitorStart;
    }

    public void setStateMonitorStart(int stateMonitorStart) {
        this.stateMonitorStart = stateMonitorStart;
    }

    public int getStateUpdateStart() {
        return stateUpdateStart;
    }

    public void setStateUpdateStart(int stateUpdateStart) {
        this.stateUpdateStart = stateUpdateStart;
    }

    public int getStateStringStart() {
        return stateStringStart;
    }

    public void setStateStringStart(int stateStringStart) {
        this.stateStringStart = stateStringStart;
    }

    public int getDigitalStateSringStart() {
        return digitalStateSringStart;
    }

    public void setDigitalStateSringStart(int digitalStateSringStart) {
        this.digitalStateSringStart = digitalStateSringStart;
    }

    public int getTPConfigDW0Reg() {
        return TPConfigDW0Reg;
    }

    public void setTPConfigDW0Reg(int TPConfigDW0Reg) {
        this.TPConfigDW0Reg = TPConfigDW0Reg;
    }

    public int getTPConfigDW0Addr() {
        return TPConfigDW0Addr;
    }

    public void setTPConfigDW0Addr(int TPConfigDW0Addr) {
        this.TPConfigDW0Addr = TPConfigDW0Addr;
    }

    public int getTPConfigDW1Reg() {
        return TPConfigDW1Reg;
    }

    public void setTPConfigDW1Reg(int TPConfigDW1Reg) {
        this.TPConfigDW1Reg = TPConfigDW1Reg;
    }

    public int getTPConfigDW1Addr() {
        return TPConfigDW1Addr;
    }

    public void setTPConfigDW1Addr(int TPConfigDW1Addr) {
        this.TPConfigDW1Addr = TPConfigDW1Addr;
    }

    public int getTPConfigDW2Reg() {
        return TPConfigDW2Reg;
    }

    public void setTPConfigDW2Reg(int TPConfigDW2Reg) {
        this.TPConfigDW2Reg = TPConfigDW2Reg;
    }

    public int getTPConfigDW2Addr() {
        return TPConfigDW2Addr;
    }

    public void setTPConfigDW2Addr(int TPConfigDW2Addr) {
        this.TPConfigDW2Addr = TPConfigDW2Addr;
    }

    public int getTPConfigDW3Reg() {
        return TPConfigDW3Reg;
    }

    public void setTPConfigDW3Reg(int TPConfigDW3Reg) {
        this.TPConfigDW3Reg = TPConfigDW3Reg;
    }

    public int getTPConfigDW3Addr() {
        return TPConfigDW3Addr;
    }

    public void setTPConfigDW3Addr(int TPConfigDW3Addr) {
        this.TPConfigDW3Addr = TPConfigDW3Addr;
    }

    public int getTPConfigDW4Reg() {
        return TPConfigDW4Reg;
    }

    public void setTPConfigDW4Reg(int TPConfigDW4Reg) {
        this.TPConfigDW4Reg = TPConfigDW4Reg;
    }

    public int getTPConfigDW4Addr() {
        return TPConfigDW4Addr;
    }

    public void setTPConfigDW4Addr(int TPConfigDW4Addr) {
        this.TPConfigDW4Addr = TPConfigDW4Addr;
    }

    public int getStptDigitalReadMacroId() {
        return StptDigitalReadMacroId;
    }

    public void setStptDigitalReadMacroId(int stptDigitalReadMacroId) {
        StptDigitalReadMacroId = stptDigitalReadMacroId;
    }

    public int getLanguageCount() {
        return languageCount;
    }

    public void setLanguageCount(int languageCount) {
        this.languageCount = languageCount;
    }

    public String getMainConfigPath() {
        return mainConfigPath;
    }

    public void setMainConfigPath(String mainConfigPath) {
        this.mainConfigPath = mainConfigPath;
    }
}
