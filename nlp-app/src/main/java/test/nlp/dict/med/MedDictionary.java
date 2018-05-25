package test.nlp.dict.med;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import test.nlp.common.Attribute;
import test.nlp.common.Vertex;
import test.nlp.dict.Dictionary;
import test.nlp.dict.pos.POS;
import test.nlp.trie.DoubleArrayTrie;
import test.nlp.trie.IHit;
import test.nlp.trie.ITrie;
import test.nlp.util.IOUtil;
import test.nlp.util.SegmentUtil;

/**
 * med
 * 
 * @author zs
 */
public class MedDictionary extends Dictionary {

    private static final String PATH_MED = "med";

    private static MedDictionary instance;

    public static MedDictionary getInstance() {
        if (instance == null) {
            synchronized (MedDictionary.class) {
                if (instance == null) {
                    instance = new MedDictionary();
                }
            }
        }
        return instance;
    }

    private ITrie<Attribute<POS>> iTrie;

    private MedDictionary() {
        try {
            read(new File(libraryPath + PATH_MED, "med.dic"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Load med dictionary error.", e);
        }
    }

    @Override
    public void create(File source, File desti) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(File path) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void write(OutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void read(InputStream in) throws IOException, ClassNotFoundException {
        iTrie = new DoubleArrayTrie<Attribute<POS>>();
        try (ObjectInputStream ois = IOUtil.getObjectInputStream(in);) {
            iTrie.read(ois);
        }
    }

    public void fmm(Vertex[] vertexes, String domain) {
        SegmentUtil.fmm(iTrie, vertexes, new AttributeHit(vertexes));
    }

    private static class AttributeHit implements IHit<Attribute<POS>> {

        private Vertex[] vertexes;

        public AttributeHit(Vertex[] vertexes) {
            this.vertexes = vertexes;
        }

        @Override
        public void hit(int begin, int end, Attribute<POS> value) {
            vertexes[begin].setPosAttr(value);
            vertexes[begin].setPos(value.getPossibleAttr());
            if (end > begin) {
                StringBuilder merge = new StringBuilder(vertexes[begin].getWord());
                for (int i = begin + 1; i <= end; i++) {
                    merge.append(vertexes[i].getWord());
                    vertexes[i] = null;
                }
                vertexes[begin].setWord(merge.toString());
            }
        }

        @Override
        public void notHit(int begin) {
        }
    }
}
