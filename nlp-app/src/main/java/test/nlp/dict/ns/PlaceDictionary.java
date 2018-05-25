package test.nlp.dict.ns;

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
 * 地名词典
 */
public class PlaceDictionary extends Dictionary {

    public static final String PATH_NS = "ns";

    private static volatile PlaceDictionary instance;

    /**
     * 地名角色词典
     */
    private ITrie<Attribute<NS>> nsRoleDictionary;

    /**
     * 地名角色转移词典
     */
    private TransformMatrixDictionary<NS> nsTransformDictionary;

    /**
     * 地名模式词典
     */
    private ITrie<NSPattern> patternDictionary;

    public static PlaceDictionary getInstance() {
        if (instance == null) {
            synchronized (PlaceDictionary.class) {
                if (instance == null) {
                    instance = new PlaceDictionary();
                }
            }
        }
        return instance;
    }

    public Attribute<NS> getNSAttr(String word) {
        return nsRoleDictionary.get(word);
    }

    public TransformMatrixDictionary<NS> getNsTransformDictionary() {
        return nsTransformDictionary;
    }

    public ITrie<NSPattern> getPattern() {
        return patternDictionary;
    }

    @Override
    public void read(File path) throws IOException, ClassNotFoundException {
        readNRRoleDictionary(new File(path, "ns.dic"));
        readNRTransformMatrixDictionary(new File(path, "transform_matrix.dic"));

        patternDictionary = new DoubleArrayTrie<NSPattern>();
        TreeMap<String, NSPattern> patterns = Maps.newTreeMap();
        for (NSPattern nsPattern : NSPattern.values()) {
            patterns.put(nsPattern.toString(), nsPattern);
        }
        patternDictionary.create(patterns);

    }

    @Override
    public void write(File path) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void read(InputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void write(OutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }

    private PlaceDictionary() {
        try {
            read(new File(libraryPath, PATH_NS));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Load PlaceDictionary error.", e);
        }

    }

    private void readNRRoleDictionary(File nrRoleFile) throws IOException, ClassNotFoundException {
        nsRoleDictionary = new DoubleArrayTrie<Attribute<NS>>();
        try (ObjectInputStream in = IOUtil.getObjectInputStream(nrRoleFile);) {
            nsRoleDictionary.read(in);
        }
    }

    private void readNRTransformMatrixDictionary(File nrTransformMaxtrixFile) throws IOException {
        nsTransformDictionary = new TransformMatrixDictionary<NS>();
        try (InputStream in = new FileInputStream(nrTransformMaxtrixFile);) {
            nsTransformDictionary.read(in);
        }
    }
}
