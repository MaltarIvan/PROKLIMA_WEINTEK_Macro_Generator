package utilities;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ConfigSignals {
    private FileInputStream excelFile;

    public ConfigSignals(String path) throws IOException {
        excelFile = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        System.out.println(sheet.getLastRowNum());
    }
}
