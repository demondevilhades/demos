package test.poi;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class POISAXReader extends DefaultHandler {

    protected SharedStringsTable sharedStringsTable;
    protected XSSFReader xssfReader;

    protected int rowIndex = 0;
    protected int cellIndex = 0;
    protected Map<String, Long> qNameMap = new HashMap<String, Long>();

    protected StringBuilder sb = new StringBuilder();
    protected boolean nextIsString = false;
    protected boolean listIsNull = true;

    public POISAXReader(String fileName) throws IOException, OpenXML4JException {
        OPCPackage opcPackage = OPCPackage.open(fileName);
        xssfReader = new XSSFReader(opcPackage);
        sharedStringsTable = xssfReader.getSharedStringsTable();
    }

    public void parseSheet(String relId) throws SAXException, InvalidFormatException, IOException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(this);

        rowIndex = 0;
        cellIndex = 0;
        qNameMap.clear();

        try (InputStream is = xssfReader.getSheet(relId)) {
            InputSource sheetSource = new InputSource(is);
            parser.parse(sheetSource);
        }

        for (Map.Entry<String, Long> entry : qNameMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("row".equals(qName)) {// <row>:开始处理某一行
            rowIndex = Integer.parseInt(attributes.getValue("r"));
        } else if ("c".equals(qName)) {// <c>:一个单元格
            String cellType = attributes.getValue("t");
            if ("s".equals(cellType)) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }

            String r = attributes.getValue("r");
            int firstDigit = -1;
            for (int c = 0; c < r.length(); ++c) {
                if (Character.isDigit(r.charAt(c))) {
                    firstDigit = c;
                    break;
                }
            }
            cellIndex = nameToColumn(r.substring(0, firstDigit));
        } else if ("v".equals(qName)) {// <v>:单元格值
        } else if ("f".equals(qName)) {// <f>:公式表达式标签
        } else if ("is".equals(qName)) {// 内联字符串外部标签
        } else if ("col".equals(qName)) {// 处理隐藏列
        } else if ("dimension".equals(qName)) {// 获得总计录数
        } else if ("sheetData".equals(qName)) {//
        } else if ("sheetFormatPr".equals(qName)) {//
        } else if ("sheetPr".equals(qName)) {//
        } else if ("autoFilter".equals(qName)) {//
        } else if ("selection".equals(qName)) {//
        } else if ("sheetView".equals(qName)) {//
        } else if ("pageMargins".equals(qName)) {//
        } else if ("sheetViews".equals(qName)) {//
        } else if ("worksheet".equals(qName)) {//
        } else if ("cols".equals(qName)) {//
        } else if ("headerFooter".equals(qName)) {//
        } else {
            Long l = qNameMap.get(qName);
            if (l == null) {
                qNameMap.put(qName, 1L);
            } else {
                qNameMap.put(qName, l + 1);
            }
        }
        if (sb.length() > 0) {
            sb = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(sb.toString());
                sb = new StringBuilder(new XSSFRichTextString(sharedStringsTable.getEntryAt(idx)).toString());
            } catch (NumberFormatException e) {
            }
        }
        if ("v".equals(qName)) {// <v>:单元格值
            System.out.println(rowIndex + "\t" + cellIndex + "\t" + sb.toString());
        } else if ("row".equals(qName)) {// <row>:开始处理某一行
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);
    }

    private int nameToColumn(String name) {
        int column = -1;
        for (int i = 0; i < name.length(); ++i) {
            int c = name.charAt(i);
            column = (column + 1) * 26 + c - 'A';
        }
        return column;
    }

    public static void main(String[] args) throws Exception {
        POISAXReader poiStream = new POISAXReader("D:/result.xlsx");
        poiStream.parseSheet("rId1");
    }
}
