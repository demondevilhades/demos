package test.nlp.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import test.nlp.common.Attribute;
import test.nlp.corpus.Token;
import test.nlp.dict.pos.POS;
import test.nlp.trie.DoubleArrayTrie;
import test.nlp.trie.ITrie;
import test.nlp.util.IOUtil;

import com.google.common.io.Files;
import com.zte.nlp.corpus.CommonBuilderExt;

/**
 * -Xmx1000m -Xmx1000m
 * 
 * @author zs
 */
public class MedDictionaryBuilder {
    protected TreeMap<String, Attribute<POS>> attributeMap = new TreeMap<>();
    protected String binFileName = "med.dic";

    private final int defFreq = 12;
    @SuppressWarnings("serial")
    private final Map<String, POS> fileMap = new HashMap<String, POS>() {
        {
            put("disease", POS.nhd);
            put("medicine", POS.nhm);
            put("checking", POS.nhc);
            put("instruments", POS.nhi);
            put("other", POS.nh);
            put("surgery", POS.nhsu);
            put("symptom", POS.nhsy);
            put("zy_fangji", POS.nhzyfj);
            put("zy_xuewei", POS.nhzyxw);
            put("zy_yaocai", POS.nhzyyc);
            put("body", POS.nhb);
            put("inn", POS.nhinn);
        }
    };

    public MedDictionaryBuilder() {
    }

    protected void stat(File file) throws IOException {
        String filename = file.getName();
        if (filename.endsWith(".txt")) {
            System.out.println("load " + filename);
            LinkedList<Token<POS>> list;
            for (Map.Entry<String, POS> entry : fileMap.entrySet()) {
                String key = entry.getKey();
                if (filename.startsWith(key)) {
                    list = readFile(file, entry.getValue());
                    for (Token<POS> token : list) {
                        Attribute<POS> attribute = new Attribute<POS>(1);
                        attribute.addItem(0, token.getLabel(), defFreq);
                        attributeMap.put(token.getWord(), attribute);
                    }
                    System.out.println("size : " + list.size());
                    break;
                }
            }
        }
    }

    protected void save(File savePath) throws IOException {
        File binFile = new File(savePath, binFileName);
        System.out.println("save file : " + binFile.getAbsolutePath());
        ITrie<Attribute<POS>> trie = new DoubleArrayTrie<Attribute<POS>>();
        trie.create(attributeMap);
        try (ObjectOutputStream out = IOUtil.getObjectOutputStream(binFile);) {
            trie.write(out);
        }
    }

    private LinkedList<Token<POS>> readFile(File file, POS med) throws IOException {
        LinkedList<Token<POS>> lineList = new LinkedList<Token<POS>>();
        try (BufferedReader reader = Files.newReader(file, StandardCharsets.UTF_8);) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                lineList.add(new Token<POS>(line.trim(), POS.nh, med));
            }
            return lineList;
        }
    }

    // ---

    public void buildFromCorpus(File corpusDir, File saveDir) throws Exception {
        statDirectory(corpusDir);
        save(saveDir);
    }

    protected void statDirectory(File corpusDir) throws IOException {
        File[] corpusFiles = corpusDir.listFiles();
        for (File corpusFile : corpusFiles) {
            if (corpusFile.isDirectory()) {
                statDirectory(corpusFile);
            } else {
                stat(corpusFile);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        MedDictionaryBuilder builder = new MedDictionaryBuilder();
        builder.buildFromCorpus(new File(CommonBuilderExt.INPUT_LIBRARY_PATH, "med/"), new File(
                CommonBuilderExt.OUTPUT_LIBRARY_PATH, "med/"));
    }
}
