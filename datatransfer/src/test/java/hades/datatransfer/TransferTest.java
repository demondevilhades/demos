package hades.datatransfer;

import hades.datatransfer.dao.output.MysqlSQLOutputDao;
import hades.datatransfer.xls.XlsReader.WorkbookType;
import hades.datatransfer.xls.input.XlsSQLInputReader;

import org.junit.Test;

public class TransferTest {

    @Test
    public void test() throws Exception {
        MysqlSQLOutputDao mysqlSQLOutputDao = new MysqlSQLOutputDao();
        XlsSQLInputReader xlsSQLInputReader = new XlsSQLInputReader("D:/FDDC_financial_data_20180518/Market Data.xls",
                WorkbookType.HSSF, 0, 0);
        Transfer.transfer(xlsSQLInputReader, mysqlSQLOutputDao);
    }
}
