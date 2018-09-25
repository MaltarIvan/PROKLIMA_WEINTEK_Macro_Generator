package utilities;

import entities.Signal;
import entities.States;
import exceptions.WrongFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigSignals {
    private static final String MAIN_DIGITAL_KEY = "#GROUP# MAIN DIGITAL";
    private static final String ADVANCED_DIGITAL_KEY = "#GROUP# ADVANCED DIGITAL";
    private static final String ADVANCED_INPUTS_KEY = "#GROUP# ADVANCED INPUTS";
    private static final String ADVANCED_FAN_KEY = "#GROUP# ADVANCED FAN";
    private static final String ADVANCED_TEMP_KEY = "#GROUP# ADVANCED TEMP";

    private static final int MAIN_DIGITAL_VALUE = 0;
    private static final int ADVANCED_DIGITAL_VALUE = 1;
    private static final int ADVANCED_INPUTS_VALUE = 2;
    private static final int ADVANCED_FAN_VALUE = 3;
    private static final int ADVANCED_TEMP_VALUE = 4;

    private String path;

    private ArrayList<Signal> mainDigital;
    private ArrayList<Signal> advancedDigital;
    private ArrayList<Signal> advancedInputs;
    private ArrayList<Signal> advancedFan;
    private ArrayList<Signal> advancedTemp;

    private boolean configurated;

    public ConfigSignals(String path) {
        this.path = path;
    }

    public void getConfiguration() throws IOException, WrongFormatException {
        ArrayList<States> pastStates = new ArrayList<>();
        int nextStatesStringIndex = 0;

        mainDigital = new ArrayList<>();
        advancedDigital = new ArrayList<>();
        advancedInputs = new ArrayList<>();
        advancedFan = new ArrayList<>();
        advancedTemp = new ArrayList<>();

        FileInputStream excelFile = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        int currentSignalGroup = -1;

        for (Row row : sheet) {
            Cell cell;
            if (row.getRowNum() > 0) {
                if (row.getCell(0) != null) {
                    if (row.getCell(0).getCellType() == CellType.STRING) {
                        String content = row.getCell(0).getStringCellValue();
                        String[] strs = content.split("#");
                        if (strs.length > 1) {
                            content = strs[1];
                            if (content.equals("GROUP")) {
                                switch (row.getCell(0).getStringCellValue()) {
                                    case MAIN_DIGITAL_KEY:
                                        currentSignalGroup = MAIN_DIGITAL_VALUE;
                                        break;
                                    case ADVANCED_DIGITAL_KEY:
                                        currentSignalGroup = ADVANCED_DIGITAL_VALUE;
                                        break;
                                    case ADVANCED_INPUTS_KEY:
                                        currentSignalGroup = ADVANCED_INPUTS_VALUE;
                                        break;
                                    case ADVANCED_FAN_KEY:
                                        currentSignalGroup = ADVANCED_FAN_VALUE;
                                        break;
                                    case ADVANCED_TEMP_KEY:
                                        currentSignalGroup = ADVANCED_TEMP_VALUE;
                                        break;
                                    default:
                                        throw new WrongFormatException();
                                }
                            }
                        }
                    } else {
                        throw new WrongFormatException();
                    }
                }
                if (row.getLastCellNum() == 20) {
                    Signal signal = new Signal();
                    cell = row.getCell(1);
                    if (cell.getCellType() == CellType.STRING) {
                        signal.setSignalName(cell.getStringCellValue());
                    } else {
                        throw new WrongFormatException();
                    }
                    cell = row.getCell(2);
                    switch (cell.getCellType()) {
                        case STRING:
                            signal.setStringId(Integer.parseInt(cell.getStringCellValue()));
                            break;
                        case NUMERIC:
                            signal.setStringId((int) cell.getNumericCellValue());
                            break;
                        default:
                            throw new WrongFormatException();
                    }

                    cell = row.getCell(5);
                    boolean TPConfigDW = true;
                    if (cell == null) {
                        TPConfigDW = false;
                    } else {
                        if (cell.getCellType() == CellType.STRING) {
                            if (cell.getStringCellValue().equals("x")) {
                                TPConfigDW = false;
                            }
                        }
                    }
                    signal.setTPConfigDW(TPConfigDW);
                    if (TPConfigDW) {
                        signal.setEnabledCondition("x");
                        switch (cell.getCellType()) {
                            case STRING:
                                signal.setEnabledTPConfigDW(Integer.parseInt(cell.getStringCellValue()));
                                break;
                            case NUMERIC:
                                signal.setEnabledTPConfigDW((int) cell.getNumericCellValue());
                                break;
                            default:
                                throw new WrongFormatException();
                        }
                        cell = row.getCell(7);
                        switch (cell.getCellType()) {
                            case STRING:
                                signal.setBitPosition(Integer.parseInt(cell.getStringCellValue()));
                                break;
                            case NUMERIC:
                                signal.setBitPosition((int) cell.getNumericCellValue());
                                break;
                            default:
                                throw new WrongFormatException();
                        }
                    } else {
                        cell = row.getCell(3);
                        switch (cell.getCellType()) {
                            case STRING:
                                if (!cell.getStringCellValue().equals("x")) {
                                    signal.setEnabledReg(Integer.parseInt(cell.getStringCellValue()));
                                }
                                break;
                            case NUMERIC:
                                signal.setEnabledReg((int) cell.getNumericCellValue());
                                break;
                            default:
                                throw new WrongFormatException();
                        }

                        cell = row.getCell(4);
                        switch (cell.getCellType()) {
                            case STRING:
                                if (!cell.getStringCellValue().equals("x")) {
                                    signal.setEnabledAddr(Integer.parseInt(cell.getStringCellValue()));
                                }
                                break;
                            case NUMERIC:
                                signal.setEnabledAddr((int) cell.getNumericCellValue());
                                break;
                            default:
                                throw new WrongFormatException();
                        }

                        cell = row.getCell(6);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    signal.setEnabledCondition(cell.getStringCellValue());
                                    break;
                                default:
                                    throw new WrongFormatException();
                            }
                        }
                    }

                    cell = row.getCell(8);
                    switch (cell.getCellType()) {
                        case STRING:
                            signal.setSignalReg(Integer.parseInt(cell.getStringCellValue()));
                            break;
                        case NUMERIC:
                            signal.setSignalReg((int) cell.getNumericCellValue());
                            break;
                        default:
                            throw new WrongFormatException();
                    }
                    cell = row.getCell(9);
                    switch (cell.getCellType()) {
                        case STRING:
                            signal.setSignalAddr(Integer.parseInt(cell.getStringCellValue()));
                            break;
                        case NUMERIC:
                            signal.setSignalAddr((int) cell.getNumericCellValue());
                            break;
                        default:
                            throw new WrongFormatException();
                    }

                    cell = row.getCell(10);
                    if (cell.getCellType() == CellType.BOOLEAN) {
                        if (cell.getBooleanCellValue()) {
                            signal.setDisabled(true);
                        } else {
                            signal.setDisabled(false);
                        }
                    } else {
                        throw new WrongFormatException();
                    }

                    ArrayList<String[]> statesStr = new ArrayList<>();
                    for (int i = 11; i <= 19; i++) {
                        cell = row.getCell(i);
                        if (cell.getCellType() == CellType.STRING) {
                            statesStr.add(cell.getStringCellValue().split("\\*"));
                        } else {
                            throw new WrongFormatException();
                        }
                    }

                    States states = new States(statesStr, nextStatesStringIndex);
                    if (pastStates.contains(states)) {
                        States currentStates = pastStates.get(pastStates.indexOf(states));
                        signal.setStates(currentStates);
                        currentStates.getSignals().add(signal);
                    } else {
                        signal.setStates(states);
                        states.getSignals().add(signal);
                        nextStatesStringIndex += statesStr.get(0).length;
                        pastStates.add(states);
                    }

                    switch (currentSignalGroup) {
                        case MAIN_DIGITAL_VALUE:
                            mainDigital.add(signal);
                            break;
                        case ADVANCED_DIGITAL_VALUE:
                            advancedDigital.add(signal);
                            break;
                        case ADVANCED_INPUTS_VALUE:
                            advancedInputs.add(signal);
                            break;
                        case ADVANCED_FAN_VALUE:
                            advancedFan.add(signal);
                            break;
                        case ADVANCED_TEMP_VALUE:
                            advancedTemp.add(signal);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }
            }
        }
        configurated = true;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Signal> getMainDigital() {
        return mainDigital;
    }

    public void setMainDigital(ArrayList<Signal> mainDigital) {
        this.mainDigital = mainDigital;
    }

    public ArrayList<Signal> getAdvancedDigital() {
        return advancedDigital;
    }

    public void setAdvancedDigital(ArrayList<Signal> advancedDigital) {
        this.advancedDigital = advancedDigital;
    }

    public ArrayList<Signal> getAdvancedInputs() {
        return advancedInputs;
    }

    public void setAdvancedInputs(ArrayList<Signal> advancedInputs) {
        this.advancedInputs = advancedInputs;
    }

    public ArrayList<Signal> getAdvancedFan() {
        return advancedFan;
    }

    public void setAdvancedFan(ArrayList<Signal> advancedFan) {
        this.advancedFan = advancedFan;
    }

    public ArrayList<Signal> getAdvancedTemp() {
        return advancedTemp;
    }

    public void setAdvancedTemp(ArrayList<Signal> advancedTemp) {
        this.advancedTemp = advancedTemp;
    }

    public boolean isConfigurated() {
        return configurated;
    }

    public void setConfigurated(boolean configurated) {
        this.configurated = configurated;
    }
}
