package hades.datatransfer.xls;

import java.io.IOException;

import org.junit.Test;

import hades.datatransfer.xls.XlsReader.WorkbookType;

public class XlsReaderTest {

    @Test
    public void test() throws IOException {
        XlsReader reader = new XlsReader("D:/FDDC_financial_data_20180518/Market Data.xls", WorkbookType.HSSF);
        //XlsReader reader = new XlsReader("D:/result_0.xlsx", WorkbookType.XSSF);
        System.out.println(reader.readMysqlTableCreateSql(0, 0, 1));
        System.out.println(reader.readMysqlTableCreateSql(0, 0));
        reader.writeDataSql(0, 0, "D:/temp.sql");
        reader.close();
    }
}
