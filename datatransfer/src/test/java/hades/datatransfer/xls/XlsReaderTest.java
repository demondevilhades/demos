package hades.datatransfer.xls;

import java.io.IOException;

import org.junit.Test;

import hades.datatransfer.xls.XlsReader.WorkbookType;

public class XlsReaderTest {

    @Test
    public void test() throws IOException {
        XlsReader reader = new XlsReader("D:/FDDC_financial_data_20180518/Market Data.xls", WorkbookType.HSSF);
        System.out.println(reader.readHeaderWithDataMysql(0, 0, 1));
        reader.close();
    }
}
