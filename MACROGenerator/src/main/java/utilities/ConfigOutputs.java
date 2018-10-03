package utilities;

import entities.Signal;
import entities.States;
import entities.sets.VAVs;
import exceptions.WrongFormatException;
import helpers.ExcelHelpers;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maltar on 3.10.2018..
 */
public class ConfigOutputs {
    private static final String DIGITAL_KEY = "#GROUP# DIGITAL";
    private static final String ANALOG_KEY = "#GROUP# ANALOG";

    private static final int DIGITAL_VALUE = 0;
    private static final int ANALOG_VALUE = 1;

    private String path;

    private ArrayList<Signal> digital;
    private ArrayList<Signal> analog;
    private VAVs VAVs;

    private int version;

    private boolean configured;

    private ArrayList<States> pastStates = new ArrayList<>();
    private int nextStatesStringIndex = 0;
    private Sheet sheet;

    public ConfigOutputs(String path) {
        this.path = path;
    }

    public void getConfiguration() throws IOException, WrongFormatException {
        digital = new ArrayList<>();
        analog = new ArrayList<>();

        FileInputStream excelFile = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(excelFile);
        sheet = workbook.getSheetAt(0);

        int currentSignalGroup = -1;

        for (Row row : sheet) {
            Cell cell;
            if (row.getRowNum() == 0) {
                if (row.getCell(0).getCellType() == CellType.STRING) {
                    String[] strs = row.getCell(0).getStringCellValue().split(":");
                    if (strs.length < 2) {
                        throw new WrongFormatException();
                    } else {
                        version = Integer.parseInt(strs[1].trim());
                    }
                } else {
                    throw new WrongFormatException();
                }
            }

            if (row.getRowNum() > 11) {
                if (row.getCell(0) != null) {
                    if (row.getCell(0).getCellType() == CellType.STRING) {
                        String content = row.getCell(0).getStringCellValue();
                        String[] strs = content.split("#");
                        if (strs.length > 1) {
                            content = strs[1];
                            if (content.equals("GROUP")) {
                                switch (row.getCell(0).getStringCellValue()) {
                                    case DIGITAL_KEY:
                                        currentSignalGroup = DIGITAL_VALUE;
                                        break;
                                    case ANALOG_KEY:
                                        currentSignalGroup = ANALOG_VALUE;
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
                int last = row.getLastCellNum();
                if (last >= 21) {
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
                    switch (cell.getCellType()) {
                        case STRING:
                            if (!cell.getStringCellValue().equals("x")) {
                                signal.setScale(Double.parseDouble(cell.getStringCellValue()));
                            }
                            break;
                        case NUMERIC:
                            signal.setScale(cell.getNumericCellValue());
                            break;
                        default:
                            throw new WrongFormatException();
                    }
                    cell = row.getCell(11);
                    switch (cell.getCellType()) {
                        case STRING:
                            if (!cell.getStringCellValue().equals("x")) {
                                throw new WrongFormatException();
                            }
                            break;
                        case NUMERIC:
                            signal.setDataType((int) cell.getNumericCellValue());
                            break;
                        default:
                            throw new WrongFormatException();
                    }

                    if (currentSignalGroup == DIGITAL_VALUE) {
                        ArrayList<String[]> statesStr = new ArrayList<>();
                        for (int i = 12; i <= 20; i++) {
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
                    }

                    switch (currentSignalGroup) {
                        case DIGITAL_VALUE:
                            digital.add(signal);
                            break;
                        case ANALOG_VALUE:
                            analog.add(signal);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }
            }
        }

        VAVs = loadVAVs();

        configured = true;
    }

    private VAVs loadVAVs() throws WrongFormatException {
        final String VAV_NUMBER_REG = "VAV_NUMBER_REG";
        final String VAV_NUMBER_ADDR = "VAV_NUMBER_ADDR";
        final String VAV_STRING_ID_START = "VAV_STRING_ID_START";
        final String VAV_ADDR_START = "VAV_ADDR_START";
        final String VAV_REG = "VAV_REG";
        final String VAV_DATA_TYPE = "VAV_DATA_TYPE";
        final String VAV_SCALE = "VAV_SCALE";
        final String VAV_SPLY_REG_EN_REG = "VAV_SPLY_REG_EN_REG";
        final String VAV_SPLY_REG_EN_ADDR_START = "VAV_SPLY_REG_EN_ADDR_START";

        int numberReg = 0;
        int numberAddr = 0;
        int stringIdStart = 0;
        int reg = 0;
        int addrStart = 0;
        int dataType = 0;
        double scale = 0;
        int splyEnReg = 0;
        int splyEnAddr = 0;

        for (int i = 2; i < 11; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (cell.getCellType() == CellType.STRING) {
                switch (cell.getStringCellValue().trim()) {
                    case VAV_NUMBER_REG:
                        cell = row.getCell(1);
                        numberReg = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case VAV_NUMBER_ADDR:
                        cell = row.getCell(1);
                        numberAddr = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case VAV_STRING_ID_START:
                        cell = row.getCell(1);
                        stringIdStart = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case VAV_ADDR_START:
                        cell = row.getCell(1);
                        addrStart = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case VAV_REG:
                        cell = row.getCell(1);
                        reg = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case VAV_DATA_TYPE:
                        cell = row.getCell(1);
                        dataType = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case VAV_SCALE:
                        cell = row.getCell(1);
                        scale = ExcelHelpers.getDoubleVarValue(cell);
                        break;
                    case VAV_SPLY_REG_EN_REG:
                        cell = row.getCell(1);
                        splyEnReg = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case VAV_SPLY_REG_EN_ADDR_START:
                        cell = row.getCell(1);
                        splyEnAddr = ExcelHelpers.getIntVarValue(cell);
                        break;
                    default:
                        throw new WrongFormatException("No variable name \"" + cell.getStringCellValue() + "\" found in " + path + ".");
                }
            }  else {
                throw new WrongFormatException();
            }
        }
        return new VAVs(numberReg, numberAddr, stringIdStart, reg, addrStart, dataType, scale, splyEnReg, splyEnAddr);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Signal> getDigital() {
        return digital;
    }

    public void setDigital(ArrayList<Signal> digital) {
        this.digital = digital;
    }

    public ArrayList<Signal> getAnalog() {
        return analog;
    }

    public void setAnalog(ArrayList<Signal> analog) {
        this.analog = analog;
    }

    public entities.sets.VAVs getVAVs() {
        return VAVs;
    }

    public void setVAVs(entities.sets.VAVs VAVs) {
        this.VAVs = VAVs;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isConfigured() {
        return configured;
    }

    public void setConfigured(boolean configured) {
        this.configured = configured;
    }
}
