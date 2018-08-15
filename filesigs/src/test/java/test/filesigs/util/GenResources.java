package test.filesigs.util;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import test.filesigs.FileSignature;
import test.filesigs.FileSignatureUtils;

public class GenResources {

    private final String url = "https://en.wikipedia.org/wiki/List_of_file_signatures";
    private final Logger LOGGER = Logger.getLogger(GenResources.class.getName());
    private final String nbsp = Jsoup.parse("&nbsp;").text();

    @Before
    public void init() throws Exception {
        LOGGER.info("init start.");
        System.setProperty("http.proxyHost", "proxy.zte.com.cn");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("https.proxyHost", "proxy.zte.com.cn");
        System.setProperty("https.proxyPort", "80");

        Document dom = Jsoup.connect(url).get();
        Elements tables = dom.select("table");
        if (tables == null || tables.size() == 0) {
            LOGGER.info("table is null or empty.");
            return;
        }
        Element table = null;
        for (Element element : tables) {
            Elements ths = element.select("> tbody > tr > th");
            if (ths != null && ths.size() > 0 && "Hex signature".equals(ths.get(0).text().trim())) {
                table = element;
                break;
            }
        }
        if (table == null) {
            LOGGER.info("cannot find table.");
            return;
        }
        Elements trs = table.select("> tbody > tr");

        Map<String, FileSignature> map = new HashMap<String, FileSignature>();
        int maxFileSignatureLen = 0;
        try (BufferedFileWriter bfw = new BufferedFileWriter(FileSignatureUtils.RESOURCE_CSV_STR, false);
                CSVPrinter csvPrinter = new CSVPrinter(bfw, CSVFormat.DEFAULT);
                FileOutputStream fos = new FileOutputStream(FileSignatureUtils.RESOURCE_MAP_STR, false);
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            for (Element tr : trs) {
                Elements tds = tr.select("> td");
                if (tds == null || tds.size() == 0) {
                    continue;
                }
                Elements pres = tds.get(0).select("pre");
                String[] hexDignature = new String[pres.size()];
                for (int i = 0; i < hexDignature.length; i++) {
                    hexDignature[i] = pres.get(i).text().replaceAll(nbsp, "").replaceAll(" ", "").replaceAll("\r", "")
                            .replaceAll("\n", "").toLowerCase().trim();
                }

                String offset = tds.get(2).text();
                String[] fileExtension = tds.get(3).text().toLowerCase().split(" ");
                String description = tds.get(4).text();

                FileSignature fileSignature = new FileSignature(hexDignature, offset, fileExtension, description);
                csvPrinter.printRecord(StringUtils.join(hexDignature, ","), offset,
                        StringUtils.join(fileExtension, ","), description);
                csvPrinter.flush();
                for (String str : hexDignature) {
                    map.put(str, fileSignature);
                    maxFileSignatureLen = Math.max(maxFileSignatureLen, str.length() / 2);
                }
            }
            oos.writeObject(map);
            LOGGER.info("init end. maxFileSignatureLen = " + maxFileSignatureLen);
        }
    }

    @Test
    public void test() throws Exception {
        FileSignatureUtils.init();
        String[] fileExtension = FileSignatureUtils.getFileExtension("377abcaf271c");
        System.out.println(Arrays.toString(fileExtension));
    }
}
