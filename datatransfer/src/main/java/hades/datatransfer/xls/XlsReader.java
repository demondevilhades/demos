package hades.datatransfer.xls;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hades.bg.datatype.MysqlDataType;

public class XlsReader implements Closeable {
    enum WorkbookType {
        HSSF, XSSF, SXSSF
    }

    private static final double MIN_DIFF = Math.pow(0.1, 10);

    Workbook workbook;

    public XlsReader(String xlsFilePath, WorkbookType type) throws IOException {
        switch (type) {
        case HSSF:
            this.workbook = new HSSFWorkbook(new FileInputStream(xlsFilePath));
            break;
        case XSSF:
            this.workbook = new XSSFWorkbook(xlsFilePath);
            break;
        case SXSSF:
            this.workbook = new SXSSFWorkbook(new XSSFWorkbook(xlsFilePath));
            break;
        default:
            throw new RuntimeException("type error : " + type);
        }
    }

    public List<Cell> readHeader(int sheetIndex, int headerRowNum) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        short firstCellNum;
        short lastCellNum;
        Row row;
        List<Cell> headerList = new ArrayList<>();
        row = sheet.getRow(headerRowNum);
        firstCellNum = row.getFirstCellNum();
        lastCellNum = row.getLastCellNum();
        for (short cellnum = firstCellNum; cellnum <= lastCellNum; cellnum++) {
            headerList.add(row.getCell(cellnum));
        }
        return headerList;
    }

    public String readHeaderWithDataMysql(int sheetIndex, int headerRowNum, int dataRowNum) {
        List<Column> columnList = readHeaderWithData(sheetIndex, headerRowNum, dataRowNum);
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE TABBLE_NAME (\r\nid int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT");
        for (Column column : columnList) {
            if (column != null) {
                sb.append(",\r\n").append(column.name).append(" ");
                if (column.clazz == double.class) {
                    sb.append(MysqlDataType.DECIMAL).append("(?,?)");
                } else if (column.clazz == int.class) {
                    sb.append(MysqlDataType.INTEGER).append("(?)");
                } else if (column.clazz == String.class) {
                    sb.append(MysqlDataType.VARCHAR).append("(?)");
                } else {
                    sb.append("?");
                }
            }
        }
        sb.append("\r\n);");
        return sb.toString();
    }

    public List<Column> readHeaderWithData(int sheetIndex, int headerRowNum, int dataRowNum) {
        List<Cell> headerList = readHeader(sheetIndex, headerRowNum);
        List<Column> columnList = new ArrayList<>(headerList.size());
        for (int i = 0, size = headerList.size(); i < size; i++) {
            Cell cell = headerList.get(i);
            columnList.add(cell == null ? null : new Column(cell.getStringCellValue()));
        }

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        short firstCellNum;
        short lastCellNum;
        Row row;
        List<Cell> dataList = new ArrayList<>();
        row = sheet.getRow(dataRowNum);
        firstCellNum = row.getFirstCellNum();
        lastCellNum = row.getLastCellNum();
        for (short cellnum = firstCellNum; cellnum <= lastCellNum; cellnum++) {
            dataList.add(row.getCell(cellnum));
        }
        for (int i = 0, size = dataList.size(); i < size; i++) {
            Cell cell = dataList.get(i);
            if (cell != null) {
                Column column = columnList.get(i);
                switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    double value = cell.getNumericCellValue();
                    if (Math.abs(value - ((Double) value).longValue()) > MIN_DIFF) {
                        column.clazz = double.class;
                    } else {
                        column.clazz = int.class;
                    }
                case STRING:
                    column.clazz = String.class;
                default:
                }
            }
        }
        return columnList;
    }

    public void read(int sheetIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        short firstCellNum;
        short lastCellNum;
        Row row;
        Cell cell;
        for (int rownum = firstRowNum; rownum <= lastRowNum; rownum++) {
            row = sheet.getRow(rownum);
            firstCellNum = row.getFirstCellNum();
            lastCellNum = row.getLastCellNum();
            for (short cellnum = firstCellNum; cellnum <= lastCellNum; cellnum++) {
                cell = row.getCell(cellnum);
                // TODO
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

    static class Column {
        String name;
        Class<?> clazz;
        int size;

        Column() {
        }

        Column(String name) {
            this.name = name;
        }
    }
}
