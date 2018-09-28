package utilities;

import entities.Signal;
import entities.States;
import entities.sets.FireDamprs;
import entities.sets.RoomPressures;
import entities.sets.VAVSwitches;
import exceptions.WrongFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigInputs {
    private static final String DIGITAL_KEY = "#GROUP# DIGITAL";
    private static final String ANALOG_KEY = "#GROUP# ANALOG";

    private static final int DIGITAL_VALUE = 0;
    private static final int ANALOG_VALUE = 1;

    private String path;

    private ArrayList<Signal> digital;
    private ArrayList<Signal> analog;
    private FireDamprs fireDampers;
    private RoomPressures roomPressures;
    private VAVSwitches VAVSwitches;

    private int version;

    private boolean configured;

    private ArrayList<States> pastStates = new ArrayList<>();
    private int nextStatesStringIndex = 0;
    private Sheet sheet;

    public ConfigInputs(String path) {
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

            if (row.getRowNum() > 27) {
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

        fireDampers = loadFireDamprs();
        roomPressures = loadRoomPressures();
        VAVSwitches = loadVAVSwitches();

        configured = true;
    }

    private FireDamprs loadFireDamprs() throws WrongFormatException {
        final String FIRE_DAMPR_EN_REG = "FIRE_DAMPR_EN_REG";
        final String FIRE_DAMPR_EN_ADDR = "FIRE_DAMPR_EN_ADDR";
        final String FIRE_DAMPR_REG = "FIRE_DAMPR_REG";
        final String FIRE_DAMPR_ADDR_START = "FIRE_DAMPR_ADDR_START";
        final String FIRE_DAMPR_DATA_TYPE = "FIRE_DAMPR_DATA_TYPE";
        final String FIRE_DAMPR_STRING_ID_START = "FIRE_DAMPR_STRING_ID_START";
        final String FIRE_DAMPR_CLOSED_EN_REG = "FIRE_DAMPR_CLOSED_EN_REG";
        final String FIRE_DAMPR_CLOSED_EN_ADDR = "FIRE_DAMPR_CLOSED_EN_ADDR";
        final String STATES = "STATES";
        final String CLOSED_STATES = "CLOSED_STATES";

        int fireDamprEnReg = 0;
        int fireDamprEnAddr = 0;
        int fireDamprReg = 0;
        int fireDamprAddr = 0;
        int fireDamprDataType = 0;
        int fireDamprStringIdStart = 0;
        int fireDamprClosedEnReg = 0;
        int fireDampsClosedEnAddr = 0;
        States states = null;
        States closedStates = null;

        for (int i = 2; i < 12; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (cell.getCellType() == CellType.STRING) {
                switch (cell.getStringCellValue().trim()) {
                    case FIRE_DAMPR_EN_REG:
                        cell = row.getCell(1);
                        fireDamprEnReg = getIntVarValue(cell);
                        break;
                    case FIRE_DAMPR_EN_ADDR:
                        cell = row.getCell(1);
                        fireDamprEnAddr = getIntVarValue(cell);
                        break;
                    case FIRE_DAMPR_REG:
                        cell = row.getCell(1);
                        fireDamprReg = getIntVarValue(cell);
                        break;
                    case FIRE_DAMPR_ADDR_START:
                        cell = row.getCell(1);
                        fireDamprAddr = getIntVarValue(cell);
                        break;
                    case FIRE_DAMPR_DATA_TYPE:
                        cell = row.getCell(1);
                        fireDamprDataType = getIntVarValue(cell);
                        break;
                    case FIRE_DAMPR_STRING_ID_START:
                        cell = row.getCell(1);
                        fireDamprStringIdStart = getIntVarValue(cell);
                        break;
                    case FIRE_DAMPR_CLOSED_EN_REG:
                        cell = row.getCell(1);
                        fireDamprClosedEnReg = getIntVarValue(cell);
                        break;
                    case FIRE_DAMPR_CLOSED_EN_ADDR:
                        cell = row.getCell(1);
                        fireDampsClosedEnAddr = getIntVarValue(cell);
                        break;
                    case STATES:
                        ArrayList<String[]> statesStr = new ArrayList<>();
                        for (int j = 2; j <= 10; j++) {
                            cell = row.getCell(j);
                            if (cell.getCellType() == CellType.STRING) {
                                statesStr.add(cell.getStringCellValue().split("\\*"));
                            } else {
                                throw new WrongFormatException();
                            }
                        }

                        states = new States(statesStr, nextStatesStringIndex);
                        if (pastStates.contains(states)) {
                            States currentStates = pastStates.get(pastStates.indexOf(states));
                            states = currentStates;
                        } else {
                            nextStatesStringIndex += statesStr.get(0).length;
                            pastStates.add(states);
                        }
                        break;
                    case CLOSED_STATES:
                        statesStr = new ArrayList<>();
                        for (int k = 2; k <= 10; k++) {
                            cell = row.getCell(k);
                            if (cell.getCellType() == CellType.STRING) {
                                statesStr.add(cell.getStringCellValue().split("\\*"));
                            } else {
                                throw new WrongFormatException();
                            }
                        }

                        closedStates = new States(statesStr, nextStatesStringIndex);
                        if (pastStates.contains(closedStates)) {
                            States currentStates = pastStates.get(pastStates.indexOf(closedStates));
                            closedStates = currentStates;
                        } else {
                            nextStatesStringIndex += statesStr.get(0).length;
                            pastStates.add(closedStates);
                        }
                        break;
                    default:
                        System.out.println(FIRE_DAMPR_CLOSED_EN_REG);
                        System.out.println(cell.getStringCellValue());
                        System.out.println(FIRE_DAMPR_CLOSED_EN_REG.equals(cell.getStringCellValue()));
                        throw new WrongFormatException("No variable name \"" + cell.getStringCellValue() + "\" found in " + path + ".");
                }
            } else {
                throw new WrongFormatException();
            }
        }

        return new FireDamprs(fireDamprEnReg, fireDamprEnAddr, fireDamprStringIdStart, states, fireDamprAddr, fireDamprReg, fireDamprDataType, fireDamprClosedEnReg, fireDampsClosedEnAddr, closedStates);
    }

    private RoomPressures loadRoomPressures() throws WrongFormatException {
        final String ROOM_PRESSURES_EN_ADDR = "ROOM_PRESSURES_EN_ADDR";
        final String ROOM_PRESSURES_EN_REG = "ROOM_PRESSURES_EN_REG";
        final String ROOM_PRESSURES_REG = "ROOM_PRESSURES_REG";
        final String ROOM_PRESSURES_ADDR_START = "ROOM_PRESSURES_ADDR_START";
        final String ROOM_PRESSURES_DATA_TYPE = "ROOM_PRESSURES_DATA_TYPE";
        final String ROOM_PRESSURES_STRING_ID_START = "ROOM_PRESSURES_STRING_ID_START";
        final String ROOM_PRESSURES_SCALE = "ROOM_PRESSURES_SCALE";

        int enabledReg = 0;
        int enabledAddr = 0;
        int stringIdStart = 0;
        int addrStart = 0;
        int reg = 0;
        int dataType = 0;
        double scale = 0;

        for (int i = 12; i < 19; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (cell.getCellType() == CellType.STRING) {
                switch (cell.getStringCellValue().trim()) {
                    case ROOM_PRESSURES_EN_REG:
                        cell = row.getCell(1);
                        enabledReg = getIntVarValue(cell);
                        break;
                    case ROOM_PRESSURES_EN_ADDR:
                        cell = row.getCell(1);
                        enabledAddr = getIntVarValue(cell);
                        break;
                    case ROOM_PRESSURES_STRING_ID_START:
                        cell = row.getCell(1);
                        stringIdStart = getIntVarValue(cell);
                        break;
                    case ROOM_PRESSURES_ADDR_START:
                        cell = row.getCell(1);
                        addrStart = getIntVarValue(cell);
                        break;
                    case ROOM_PRESSURES_REG:
                        cell = row.getCell(1);
                        reg = getIntVarValue(cell);
                        break;
                    case ROOM_PRESSURES_DATA_TYPE:
                        cell = row.getCell(1);
                        dataType = getIntVarValue(cell);
                        break;
                    case ROOM_PRESSURES_SCALE:
                        cell = row.getCell(1);
                        scale = getDoubleVarValue(cell);
                        break;
                    default:
                        throw new WrongFormatException();
                }
            } else {
                throw new WrongFormatException();
            }
        }

        return new RoomPressures(enabledReg, enabledAddr, stringIdStart, addrStart, reg, dataType, scale);
    }

    private VAVSwitches loadVAVSwitches() throws WrongFormatException {
        final String VAV_SWITCHES_EN_REG = "VAV_SWITCHES_EN_REG";
        final String VAV_SWITCHES_EN_ADDR = "VAV_SWITCHES_EN_ADDR";
        final String VAV_SWITCHES_REG = "VAV_SWITCHES_REG";
        final String VAV_SWITCHES_ADDR_START = "VAV_SWITCHES_ADDR_START";
        final String VAV_SWITCHES_DATA_TYPE = "VAV_SWITCHES_DATA_TYPE";
        final String VAV_SWITCHES_STRING_ID_START = "VAV_SWITCHES_STRING_ID_START";
        final String VAV_SWITCHES_MAX_COUNT = "VAV_SWITCHES_MAX_COUNT";
        final String STATES = "STATES";

        int maxVAVSwitches = 0;
        int enabledReg = 0;
        int enabledAddr = 0;
        int stringIdStart = 0;
        int addrStart = 0;
        int reg = 0;
        int dataType = 0;
        States states = null;

        for (int i = 19; i < 27; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (cell.getCellType() == CellType.STRING) {
                switch (cell.getStringCellValue().trim()) {
                    case VAV_SWITCHES_EN_REG:
                        cell = row.getCell(1);
                        enabledReg = getIntVarValue(cell);
                        break;
                    case VAV_SWITCHES_EN_ADDR:
                        cell = row.getCell(1);
                        enabledAddr = getIntVarValue(cell);
                        break;
                    case VAV_SWITCHES_REG:
                        cell = row.getCell(1);
                        reg = getIntVarValue(cell);
                        break;
                    case VAV_SWITCHES_ADDR_START:
                        cell = row.getCell(1);
                        addrStart = getIntVarValue(cell);
                        break;
                    case VAV_SWITCHES_STRING_ID_START:
                        cell = row.getCell(1);
                        stringIdStart = getIntVarValue(cell);
                        break;
                        case VAV_SWITCHES_DATA_TYPE:
                            cell = row.getCell(1);
                            dataType = getIntVarValue(cell);
                            break;
                    case VAV_SWITCHES_MAX_COUNT:
                        cell = row.getCell(1);
                        maxVAVSwitches = getIntVarValue(cell);
                        break;
                    case STATES:
                        ArrayList<String[]> statesStr = new ArrayList<>();
                        for (int j = 2; j <= 10; j++) {
                            cell = row.getCell(j);
                            if (cell.getCellType() == CellType.STRING) {
                                statesStr.add(cell.getStringCellValue().split("\\*"));
                            } else {
                                throw new WrongFormatException();
                            }
                        }

                        states = new States(statesStr, nextStatesStringIndex);
                        if (pastStates.contains(states)) {
                            States currentStates = pastStates.get(pastStates.indexOf(states));
                            states = currentStates;
                        } else {
                            nextStatesStringIndex += statesStr.get(0).length;
                            pastStates.add(states);
                        }
                        break;
                    default:
                        throw new WrongFormatException();
                }
            } else {
                throw new WrongFormatException();
            }
        }

        return new VAVSwitches(maxVAVSwitches, enabledReg, enabledAddr, stringIdStart, addrStart, reg, dataType, states);
    }

    private int getIntVarValue(Cell cell) throws WrongFormatException {
        switch (cell.getCellType()) {
            case STRING:
                return Integer.parseInt(cell.getStringCellValue());
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            default:
                throw new WrongFormatException();
        }
    }

    private double getDoubleVarValue(Cell cell) throws WrongFormatException {
        switch (cell.getCellType()) {
            case STRING:
                return Integer.parseInt(cell.getStringCellValue());
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                throw new WrongFormatException();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Signal> getDegital() {
        return digital;
    }

    public void setDegital(ArrayList<Signal> degital) {
        this.digital = degital;
    }

    public ArrayList<Signal> getAnalog() {
        return analog;
    }

    public void setAnalog(ArrayList<Signal> analog) {
        this.analog = analog;
    }

    public FireDamprs getFireDampers() {
        return fireDampers;
    }

    public void setFireDampers(FireDamprs fireDampers) {
        this.fireDampers = fireDampers;
    }

    public RoomPressures getRoomPressures() {
        return roomPressures;
    }

    public void setRoomPressuers(RoomPressures roomPressures) {
        this.roomPressures = roomPressures;
    }

    public VAVSwitches getVAVSwitches() {
        return VAVSwitches;
    }

    public void setVAVSwitches(entities.sets.VAVSwitches VAVSwitches) {
        this.VAVSwitches = VAVSwitches;
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
