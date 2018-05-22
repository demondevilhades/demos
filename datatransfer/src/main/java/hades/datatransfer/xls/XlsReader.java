package hades.datatransfer.xls;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hades.bg.datatype.MysqlDataType;
import hades.datatransfer.util.BufferedFileWriter;
import hades.datatransfer.util.DateUtils;

public class XlsReader implements Closeable {
    enum WorkbookType {
        HSSF, XSSF, SXSSF
    }

    private static final double MIN_DIFF = Math.pow(0.1, 10);

    Workbook workbook;
    File file;

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
        file = new File(xlsFilePath);
    }

    private List<Cell> readHeader(int sheetIndex, int headerRowNum) {
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

    private List<Column> readColumnWithHeaderData(int sheetIndex, int headerRowNum, int dataRowNum) {
        List<Cell> headerList = readHeader(sheetIndex, headerRowNum);
        List<Column> columnList = new ArrayList<>(headerList.size());
        for (int i = 0, size = headerList.size(); i < size; i++) {
            Cell cell = headerList.get(i);
            columnList.add(cell == null ? null : new Column(formatDBName(cell.getStringCellValue())));
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

    private List<Column> readColumnWithHeaderData(int sheetIndex, int headerRowNum) {
        List<Cell> headerList = readHeader(sheetIndex, headerRowNum);
        List<Column> columnList = new ArrayList<>(headerList.size());
        for (int i = 0, size = headerList.size(); i < size; i++) {
            Cell cell = headerList.get(i);
            columnList.add(cell == null ? null : new Column(formatDBName(cell.getStringCellValue())));
        }

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int lastRowNum = sheet.getLastRowNum();
        short firstCellNum;
        short lastCellNum;
        Row row;
        List<Cell> dataList = new ArrayList<>();
        for (int rowNum = headerRowNum + 1; rowNum <= lastRowNum; rowNum++) {
            row = sheet.getRow(rowNum);
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
                        if (column.clazz != null) {
                            if (column.clazz != double.class) {
                                if (column.clazz != int.class) {
                                    throw new RuntimeException("rowNum=" + rowNum + ", i=" + i);
                                }
                                double value = cell.getNumericCellValue();
                                if (Math.abs(value - ((Double) value).longValue()) > MIN_DIFF) {
                                    column.clazz = double.class;
                                }
                            }
                        } else {
                            double value = cell.getNumericCellValue();
                            if (Math.abs(value - ((Double) value).longValue()) > MIN_DIFF) {
                                column.clazz = double.class;
                            } else {
                                column.clazz = int.class;
                            }
                        }
                        break;
                    case STRING:
                        if (column.clazz != null) {
                            if (column.clazz != String.class) {
                                if (column.clazz != Date.class) {
                                    throw new RuntimeException("rowNum=" + rowNum + ", i=" + i);
                                }
                                String value = cell.getStringCellValue();
                                if (!DateUtils.isDate(value)) {
                                    column.clazz = String.class;
                                    column.size = Math.max(column.size, value.length());
                                }
                            }
                        } else {
                            String value = cell.getStringCellValue();
                            if (DateUtils.isDate(value)) {
                                column.clazz = Date.class;
                            } else {
                                column.clazz = String.class;
                                column.size = Math.max(column.size, value.length());
                            }
                        }
                        break;
                    default:
                    }
                }
            }
            dataList.clear();
        }
        return columnList;
    }

    public String readMysqlTableCreateSql(int sheetIndex, int headerRowNum) {
        List<Column> columnList = readColumnWithHeaderData(sheetIndex, headerRowNum);
        return readMysqlTableCreateSql(columnList);
    }

    public String readMysqlTableCreateSql(int sheetIndex, int headerRowNum, int dataRowNum) {
        List<Column> columnList = readColumnWithHeaderData(sheetIndex, headerRowNum, dataRowNum);
        return readMysqlTableCreateSql(columnList);
    }

    private String readMysqlTableCreateSql(List<Column> columnList) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(formatDBName(file.getName().substring(0, file.getName().lastIndexOf("."))))
                .append(" (\r\nID INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT");
        for (Column column : columnList) {
            if (column != null) {
                sb.append(",\r\n").append(column.name).append(" ");
                if (column.clazz == double.class) {
                    sb.append(MysqlDataType.DECIMAL).append("(?,?)");
                } else if (column.clazz == int.class) {
                    sb.append(MysqlDataType.INT).append("(11)");
                } else if (column.clazz == String.class) {
                    sb.append(MysqlDataType.VARCHAR).append("(").append(column.size == -1 ? "?" : column.size)
                            .append(")");
                } else {
                    sb.append("?");
                }
            }
        }
        sb.append("\r\n);");
        return sb.toString();
    }

    public void writeDataSql(int sheetIndex, int headerRowNum, String outputPath) throws IOException {
        BufferedFileWriter bfw = null;
        try {
            bfw = new BufferedFileWriter(outputPath);
            readData2Writer(sheetIndex, headerRowNum, bfw);
        } finally {
            if (bfw != null) {
                bfw.close();
            }
        }
    }

    private void readData2Writer(int sheetIndex, int headerRowNum, BufferedFileWriter bfw) throws IOException {
        List<Cell> headerList = readHeader(sheetIndex, headerRowNum);

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int lastRowNum = sheet.getLastRowNum();
        short firstCellNum;
        short lastCellNum;
        Row row;
        Cell cell;
        Cell headerCell;
        String stringValue;
        StringBuilder sb0;
        StringBuilder sb1;
        List<Cell> dataList = new ArrayList<>();
        for (int rowNum = headerRowNum + 1; rowNum <= lastRowNum; rowNum++) {
            row = sheet.getRow(rowNum);
            firstCellNum = row.getFirstCellNum();
            lastCellNum = row.getLastCellNum();
            sb0 = new StringBuilder();
            sb0.append("INSERT INTO ")
                    .append(formatDBName(file.getName().substring(0, file.getName().lastIndexOf(".")))).append(" ( ");
            sb1 = new StringBuilder();
            sb1.append(" VALUES ( ");
            for (short cellnum = firstCellNum; cellnum <= lastCellNum; cellnum++) {
                dataList.add(row.getCell(cellnum));
            }
            sb0.append(formatDBName(headerList.get(0).getStringCellValue()));
            stringValue = getCellStringValue(dataList.get(0));
            if (stringValue == null) {
                sb1.append("NULL");
            } else if (stringValue.contains("'")) {
                sb1.append("\"").append(stringValue).append("\"");
            } else {
                sb1.append("'").append(stringValue).append("'");
            }
            for (int i = 1, size = headerList.size(); i < size; i++) {
                headerCell = headerList.get(i);
                if (headerCell != null) {
                    cell = dataList.get(i);
                    sb0.append(", ").append(formatDBName(headerCell.getStringCellValue()));
                    sb1.append(", ");
                    stringValue = getCellStringValue(cell);
                    if (stringValue == null) {
                        sb1.append("NULL");
                    } else if (stringValue.contains("'")) {
                        sb1.append("\"").append(stringValue).append("\"");
                    } else {
                        sb1.append("'").append(stringValue).append("'");
                    }
                }
            }
            sb0.append(" )").append(sb1).append(" );");
            bfw.writeLine(sb0.toString());
            dataList.clear();
        }
    }

    @Override
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

    private String formatDBName(String name) {
        return name.toUpperCase().replaceAll(" ", "_");
    }

    private String getCellStringValue(Cell cell) {
        String value = null;
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = String.valueOf(cell.getNumericCellValue());
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case BLANK:
                value = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
            }
        }
        return value;
    }

    static class Column {
        String name;
        Class<?> clazz;
        int size = -1;

        Column() {
        }

        Column(String name) {
            this.name = name;
        }
    }
}
