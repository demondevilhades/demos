package test.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * read excel
 * 
 * @author zs
 */
public class LoadFile {

    private final String path = "F:/document/fund/sample.xlsx";

    public void load() throws IOException {
        FileInputStream fis = null;
        HSSFWorkbook workbook = null;
        try {
            fis = new FileInputStream(new File(path));
            workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);
                    System.out.print(cell.getStringCellValue());
                    System.out.print("\t");
                }
                System.out.println();
            }
        } catch (OfficeXmlFileException e) {
            System.err.println("use method loadX");
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    public void loadX() throws IOException {
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;
        try {
            fis = new FileInputStream(new File(path));
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row == null) {
                    System.out.println();
                    continue;
                }
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    String value = null;
                    switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        value = String.valueOf(cell.getNumericCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_FORMULA:
                        value = cell.getCellFormula();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
                        value = String.valueOf(cell.getStringCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_ERROR:
                        value = String.valueOf(cell.getErrorCellValue());
                        break;
                    default:
                        break;
                    }
                    System.out.print(value);
                    System.out.print("\t");
                }
                System.out.println();
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}
