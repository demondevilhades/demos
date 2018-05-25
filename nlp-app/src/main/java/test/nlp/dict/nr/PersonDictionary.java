package test.nlp.dict.nr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.TreeMap;

import test.nlp.common.Attribute;
import test.nlp.dict.Dictionary;
import test.nlp.dict.TransformMatrixDictionary;
import test.nlp.trie.DoubleArrayTrie;
import test.nlp.trie.ITrie;
import test.nlp.util.IOUtil;

import com.google.common.collect.Maps;

/**
 * 人名标注词典
 */
public class PersonDictionary extends Dictionary {

    private static final String PATH_NR = "nr";

    private static volatile PersonDictionary instance;

    /**
     * 人名属性词典
     */
    private ITrie<Attribute<NR>> nrDictionary;

    /**
     * 人名模式词典
     */
    private ITrie<NRPattern> patternDictionary;

    /**
     * 人名属性转移词典
     */
    private TransformMatrixDictionary<NR> nrTransformMaxtrixDictionary;

    public static PersonDictionary getInstance() {
        if (instance == null) {
            synchronized (PersonDictionary.class) {
                if (instance == null) {
                    instance = new PersonDictionary();
                }
            }
        }
        return instance;
    }

    @Override
    public void read(File path) throws IOException, ClassNotFoundException {
        readNRRoleDictionary(new File(path, "nr.dic"));
        readNRTransformMaxtrixDictionary(new File(path, "transform_matrix.dic"));

        patternDictionary = new DoubleArrayTrie<NRPattern>();
        TreeMap<String, NRPattern> patternItems = Maps.newTreeMap();
        for (NRPattern nrPattern : NRPattern.values()) {
            patternItems.put(nrPattern.toString(), nrPattern);
        }
        patternDictionary.create(patternItems);
    }

    @Override
    public void write(File path) throws IOException {
        throw new UnsupportedOperationException();
    }

    public Attribute<NR> getNRAttr(String word) {
        return nrDictionary.get(word);
    }

    public TransformMatrixDictionary<NR> getTransformMatrixDictionary() {
        return nrTransformMaxtrixDictionary;
    }

    public ITrie<NRPattern> getPatternDictionary() {
        return patternDictionary;
    }

    @Override
    protected void read(InputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void write(OutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }

    private PersonDictionary() {
        try {
            read(new File(libraryPath, PATH_NR));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Load person dictionary error.", e);
        }
    }

    private void readNRTransformMaxtrixDictionary(File nrTransformMatrixFile) throws IOException {
        nrTransformMaxtrixDictionary = new TransformMatrixDictionary<NR>();
        try (InputStream in = new FileInputStream(nrTransformMatrixFile);) {
            nrTransformMaxtrixDictionary.read(in);
        }
    }

    private void readNRRoleDictionary(File nrRoleFile) throws IOException, ClassNotFoundException {
        nrDictionary = new DoubleArrayTrie<Attribute<NR>>();
        try (ObjectInputStream in = IOUtil.getObjectInputStream(nrRoleFile);) {
            nrDictionary.read(in);
        }
    }
}
