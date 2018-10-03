package helpers;

import exceptions.WrongFormatException;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by Maltar on 3.10.2018..
 */
public final class ExcelHelpers {
    private ExcelHelpers() {}

    public static int getIntVarValue(Cell cell) throws WrongFormatException {
        switch (cell.getCellType()) {
            case STRING:
                return Integer.parseInt(cell.getStringCellValue());
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            default:
                throw new WrongFormatException();
        }
    }

    public static double getDoubleVarValue(Cell cell) throws WrongFormatException {
        switch (cell.getCellType()) {
            case STRING:
                return Integer.parseInt(cell.getStringCellValue());
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                throw new WrongFormatException();
        }
    }
}
