package utilities;

import entities.Signal;
import exceptions.WrongFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maltar on 7.10.2018..
 */
public class ConfigAdvancedAnalog {
    private String path;

    private ArrayList<Signal> signals;

    private int version;
    private boolean configured;

    private Sheet sheet;

    public ConfigAdvancedAnalog(String path) {
        this.path = path;
    }

    public void getConfiguration() throws IOException, WrongFormatException {
        signals = new ArrayList<>();

        FileInputStream excelFile = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(excelFile);
        sheet = workbook.getSheetAt(0);

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

            if (row.getRowNum() > 1) {
                int last = row.getLastCellNum();
                if (last >= 15) {
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

                    cell = row.getCell(11);
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

                    cell = row.getCell(12);
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

                    cell = row.getCell(13);
                    switch (cell.getCellType()) {
                        case STRING:
                            signal.setLowLimit(Double.parseDouble(cell.getStringCellValue()));
                            break;
                        case NUMERIC:
                            signal.setLowLimit(cell.getNumericCellValue());
                            break;
                        default:
                            throw new WrongFormatException();
                    }

                    cell = row.getCell(14);
                    switch (cell.getCellType()) {
                        case STRING:
                            signal.setHighLimit(Double.parseDouble(cell.getStringCellValue()));
                            break;
                        case NUMERIC:
                            signal.setHighLimit(cell.getNumericCellValue());
                            break;
                        default:
                            throw new WrongFormatException();
                    }
                    signals.add(signal);
                }
            }
        }
        configured = true;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Signal> getSignals() {
        return signals;
    }

    public void setSignals(ArrayList<Signal> signals) {
        this.signals = signals;
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
