package hades.datatransfer.xls.input;

import hades.datatransfer.Input;
import hades.datatransfer.Output;
import hades.datatransfer.xls.XlsReader;

import java.io.IOException;

public class XlsSQLInputReader extends XlsReader implements Input<String> {

    private int sheetIndex;
    private int headerRowNum;

    public XlsSQLInputReader(String xlsFilePath, WorkbookType type, int sheetIndex, int headerRowNum)
            throws IOException {
        super(xlsFilePath, type);
        this.sheetIndex = sheetIndex;
        this.headerRowNum = headerRowNum;
    }

    @Override
    public void inputWapper(Output<String> output) throws Exception {
        readData2SQL(sheetIndex, headerRowNum, new SQLWapper() {

            @Override
            public void wapper(String sql) throws IOException {
                try {
                    output.output(sql);
                } catch (Exception e) {
                    throw new IOException(e);
                }
            }
        });
    }

    @Override
    public String input() throws Exception {
        throw new UnsupportedOperationException();
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getHeaderRowNum() {
        return headerRowNum;
    }

    public void setHeaderRowNum(int headerRowNum) {
        this.headerRowNum = headerRowNum;
    }
}
