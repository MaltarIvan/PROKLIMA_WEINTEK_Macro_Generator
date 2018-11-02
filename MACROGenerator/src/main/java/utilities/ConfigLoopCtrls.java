package utilities;

import entities.LoopCtrl;
import entities.sets.RoomCtrls;
import exceptions.WrongFormatException;
import helpers.excel.ExcelHelpers;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maltar on 9.10.2018..
 */
public class ConfigLoopCtrls {
    private static final String FAN_KEY = "#GROUP# FAN";
    private static final String TEMP_KEY = "#GROUP# TEMP";
    private static final String HUMIDITY_KEY = "#GROUP# HUMIDITY";

    private static final int FAN_VALUE = 0;
    private static final int TEMP_VALUE = 1;
    private static final int HUMIDITY_VALUE = 2;

    private String path;

    private ArrayList<LoopCtrl> fan;
    private ArrayList<LoopCtrl> temp;
    private ArrayList<LoopCtrl> humidity;
    private RoomCtrls roomCtrls;

    private int version;

    private boolean configured;

    public ConfigLoopCtrls(String path) {
        this.path = path;
    }

    public void getConfiguration() throws IOException, WrongFormatException {
        fan = new ArrayList<>();
        temp = new ArrayList<>();
        humidity = new ArrayList<>();

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

            if (row.getRowNum() > 6) {
                if (row.getCell(0) != null) {
                    if (row.getCell(0).getCellType() == CellType.STRING) {
                        String content = row.getCell(0).getStringCellValue();
                        String[] strs = content.split("#");
                        if (strs.length > 1) {
                            content = strs[1];
                            if (content.equals("GROUP")) {
                                switch (row.getCell(0).getStringCellValue()) {
                                    case FAN_KEY:
                                        currentSignalGroup = FAN_VALUE;
                                        break;
                                    case TEMP_KEY:
                                        currentSignalGroup = TEMP_VALUE;
                                        break;
                                    case HUMIDITY_KEY:
                                        currentSignalGroup = HUMIDITY_VALUE;
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
                if (row.getLastCellNum() >= 10) {
                    LoopCtrl loopCtrl = new LoopCtrl();
                    cell = row.getCell(1);
                    if (cell.getCellType() == CellType.STRING) {
                        loopCtrl.setName(cell.getStringCellValue());
                    } else {
                        throw new WrongFormatException();
                    }
                    cell = row.getCell(2);
                    switch (cell.getCellType()) {
                        case STRING:
                            loopCtrl.setStringId(Integer.parseInt(cell.getStringCellValue()));
                            break;
                        case NUMERIC:
                            loopCtrl.setStringId((int) cell.getNumericCellValue());
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
                    loopCtrl.setTPConfigDW(TPConfigDW);
                    if (TPConfigDW) {
                        loopCtrl.setEnableCondition("x");
                        switch (cell.getCellType()) {
                            case STRING:
                                loopCtrl.setEnabledTPConfigDW(Integer.parseInt(cell.getStringCellValue()));
                                break;
                            case NUMERIC:
                                loopCtrl.setEnabledTPConfigDW((int) cell.getNumericCellValue());
                                break;
                            default:
                                throw new WrongFormatException();
                        }
                        cell = row.getCell(7);
                        switch (cell.getCellType()) {
                            case STRING:
                                loopCtrl.setEnableBitPosition(Integer.parseInt(cell.getStringCellValue()));
                                break;
                            case NUMERIC:
                                loopCtrl.setEnableBitPosition((int) cell.getNumericCellValue());
                                break;
                            default:
                                throw new WrongFormatException();
                        }
                    } else {
                        cell = row.getCell(3);
                        switch (cell.getCellType()) {
                            case STRING:
                                if (!cell.getStringCellValue().equals("x")) {
                                    loopCtrl.setEnabledReg(Integer.parseInt(cell.getStringCellValue()));
                                }
                                break;
                            case NUMERIC:
                                loopCtrl.setEnabledReg((int) cell.getNumericCellValue());
                                break;
                            default:
                                throw new WrongFormatException();
                        }
                        cell = row.getCell(4);
                        switch (cell.getCellType()) {
                            case STRING:
                                if (!cell.getStringCellValue().equals("x")) {
                                    loopCtrl.setEnableAddr(Integer.parseInt(cell.getStringCellValue()));
                                }
                                break;
                            case NUMERIC:
                                loopCtrl.setEnableAddr((int) cell.getNumericCellValue());
                                break;
                            default:
                                throw new WrongFormatException();
                        }
                        cell = row.getCell(6);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    loopCtrl.setEnableCondition(cell.getStringCellValue());
                                    break;
                                default:
                                    throw new WrongFormatException();
                            }
                        }
                    }

                    cell = row.getCell(8);
                    switch (cell.getCellType()) {
                        case STRING:
                            loopCtrl.setAddr(Integer.parseInt(cell.getStringCellValue()));
                            break;
                        case NUMERIC:
                            loopCtrl.setAddr((int) cell.getNumericCellValue());
                            break;
                        default:
                            throw new WrongFormatException();
                    }
                    cell = row.getCell(9);
                    if (cell.getCellType() == CellType.BOOLEAN) {
                        loopCtrl.setCascade(cell.getBooleanCellValue());
                    } else {
                        throw new WrongFormatException();
                    }

                    switch (currentSignalGroup) {
                        case FAN_VALUE:
                            fan.add(loopCtrl);
                            break;
                        case TEMP_VALUE:
                            temp.add(loopCtrl);
                            break;
                        case HUMIDITY_VALUE:
                            humidity.add(loopCtrl);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }
            }
        }

        roomCtrls = loadRoomCtrls(sheet);

        configured = true;
    }

    private RoomCtrls loadRoomCtrls(Sheet sheet) throws WrongFormatException {
        final String ROOM_EN_REG = "ROOM_EN_REG";
        final String ROOM_EN_ADDR = "ROOM_EN_ADDR";
        final String ROOM_STRING_ID_START = "ROOM_STRING_ID_START";
        final String ROOM_ADDR_START = "ROOM_ADDR_START";

        int enabledReg = 0;
        int enabledAddr = 0;
        int stringIdStart = 0;
        int addrStart = 0;

        for (int i = 2; i < 6; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (cell.getCellType() == CellType.STRING) {
                switch (cell.getStringCellValue().trim()) {
                    case ROOM_EN_REG:
                        cell = row.getCell(1);
                        enabledReg = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case ROOM_EN_ADDR:
                        cell = row.getCell(1);
                        enabledAddr = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case ROOM_STRING_ID_START:
                        cell = row.getCell(1);
                        stringIdStart = ExcelHelpers.getIntVarValue(cell);
                        break;
                    case ROOM_ADDR_START:
                        cell = row.getCell(1);
                        addrStart = ExcelHelpers.getIntVarValue(cell);
                        break;
                        default:
                            throw new WrongFormatException("No variable name \"" + cell.getStringCellValue() + "\" found in " + path + ".");
                }
            }
        }

        return new RoomCtrls(enabledReg, enabledAddr, stringIdStart, addrStart);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<LoopCtrl> getFan() {
        return fan;
    }

    public void setFan(ArrayList<LoopCtrl> fan) {
        this.fan = fan;
    }

    public ArrayList<LoopCtrl> getTemp() {
        return temp;
    }

    public void setTemp(ArrayList<LoopCtrl> temp) {
        this.temp = temp;
    }

    public ArrayList<LoopCtrl> getHumidity() {
        return humidity;
    }

    public void setHumidity(ArrayList<LoopCtrl> humidity) {
        this.humidity = humidity;
    }

    public RoomCtrls getRoomCtrls() {
        return roomCtrls;
    }

    public void setRoomCtrls(RoomCtrls roomCtrls) {
        this.roomCtrls = roomCtrls;
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
