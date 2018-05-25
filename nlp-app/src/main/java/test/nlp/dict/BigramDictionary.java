package test.nlp.dict;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import test.nlp.Constant;
import test.nlp.io.FileReader;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

/**
 * 消歧词典
 */
public class BigramDictionary extends Dictionary {

    public static final String FILE_BIGRAM_DIC = "bigram.dic";

    private static final String SEPARATOR = "@";

    private static volatile BigramDictionary instance = new BigramDictionary();

    private Table<String, String, Integer> bigrams;

    public static BigramDictionary getInstance() {
        return instance;
    }

    public void read() {
        try (InputStream in = new FileInputStream(new File(libraryPath, FILE_BIGRAM_DIC));) {
            read(in);
            LOG.info("Load bigram.dic ok.");
        } catch (Exception e) {
            throw new RuntimeException("Load bigram.dic error.", e);
        }
    }

    public void reload() {
        Table<String, String, Integer> bigrams = this.bigrams;
        read();
        bigrams.clear();
        bigrams = null;
    }

    /**
     * 删除一个bigram项
     * 
     * @param bigram
     */
    public void removeBigram(String bigram) {
        String[] splits = StringUtils.split(bigram, SEPARATOR);
        removeBigram(splits[0], splits[1]);
    }

    /**
     * 删除一个bigram项
     * 
     * @param from
     * @param to
     */
    public void removeBigram(String from, String to) {
        bigrams.remove(from, to);
    }

    /**
     * 新增一个bigram项
     * 
     * @param bigram
     * @param freq
     */
    public void addBigram(String bigram, int freq) {
        String[] splits = StringUtils.split(bigram, SEPARATOR);
        addBigram(splits[0], splits[1], freq);
    }

    /**
     * 新增一个bigram项
     * 
     * @param from
     * @param to
     * @param freq
     */
    public void addBigram(String from, String to, int freq) {
        if (bigrams.contains(from, to)) {
            bigrams.put(from, to, bigrams.get(from, to) + freq);
        } else {
            bigrams.put(from, to, freq);
        }
    }

    public int getFreq(String text) {
        return getTransitionFreq(text, "");
    }

    /**
     * 获取两个词的转移频率
     * 
     * @param from
     * @param to
     * @return
     */
    public int getTransitionFreq(String from, String to) {
        Integer freq = bigrams.get(from, to);
        return freq == null ? 0 : freq.intValue();
    }

    @Override
    public void read(InputStream in) throws IOException {
        this.bigrams = new BigramDictionaryReader().read(in, StandardCharsets.UTF_8, "\\s+");
    }

    @Override
    public void write(OutputStream out) throws IOException {
        List<String> lines = Lists.newArrayListWithCapacity(bigrams.rowKeySet().size());
        for (Cell<String, String, Integer> cell : bigrams.cellSet()) {
            lines.add(cell.getRowKey() + "@" + cell.getColumnKey() + "\t" + cell.getValue());
        }
        IOUtils.writeLines(lines, Constant.LINE_SEPARATOR, out, StandardCharsets.UTF_8);
    }

    private class BigramDictionaryReader extends FileReader<Table<String, String, Integer>> {

        @Override
        protected Table<String, String, Integer> newResult() {
            return HashBasedTable.create();
        }

        protected void processTokens(String original, String[] tokens, Table<String, String, Integer> result) {
            int freq = NumberUtils.toInt(tokens[1], 1);
            tokens = org.apache.commons.lang3.StringUtils.split(tokens[0], SEPARATOR);
            if (tokens.length != 2) {
                return;
            }
            result.put(tokens[0], tokens[1], freq);
        }
    }

    private BigramDictionary() {
        bigrams = HashBasedTable.create();
        read();
    }
}
