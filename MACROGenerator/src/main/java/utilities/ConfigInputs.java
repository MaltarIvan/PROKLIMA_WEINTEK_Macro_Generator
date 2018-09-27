package utilities;

import entities.Signal;
import entities.States;
import exceptions.WrongFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utilities.groups.FireDamprs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigInputs {
    private static final String DIGITAL_KEY = "#GROUP# DIGITAL";
    private static final String ANALOG_KEY = "#GROUP# ANALOG";
    private static final String FIRE_DAMPERS_KEY = "#GROUP# FIRE DAMPERS";
    private static final String ROOM_PRESSURES_KEY = "#GROUP# ROOM PRESSURES";
    private static final String VAV_SWITCHES_KEY = "#GROUP# VAV SWITCHES";

    private static final int DIGITAL_VALUE = 0;
    private static final int ANALOG_VALUE = 1;

    private String path;

    private ArrayList<Signal> digital;
    private ArrayList<Signal> analog;
    private ArrayList<Signal> fireDampers;
    private ArrayList<Signal> roomPressures;
    private ArrayList<Signal> VAVSwitches;
    private int version;

    private boolean configured;

    public ConfigInputs(String path) {
        this.path = path;
    }

    public void getConfiguration() throws IOException, WrongFormatException {
        ArrayList<States> pastStates = new ArrayList<>();
        int nextStatesStringIndex = 0;

        digital = new ArrayList<>();
        analog = new ArrayList<>();
        fireDampers = new ArrayList<>();
        roomPressures = new ArrayList<>();
        VAVSwitches = new ArrayList<>();

        FileInputStream excelFile = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

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
            // TODO: 27.9.2018. učitati podatke za grupe inputa (dampr, room, vav)
            if (row.getRowNum() > 24) {
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

    public ArrayList<Signal> getFireDampers() {
        return fireDampers;
    }

    public void setFireDampers(ArrayList<Signal> fireDampers) {
        this.fireDampers = fireDampers;
    }

    public ArrayList<Signal> getRoomPressures() {
        return roomPressures;
    }

    public void setRoomPressuers(ArrayList<Signal> roomPressures) {
        this.roomPressures = roomPressures;
    }

    public ArrayList<Signal> getVAVSwitches() {
        return VAVSwitches;
    }

    public void setVAVSwitches(ArrayList<Signal> VAVSwitches) {
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
