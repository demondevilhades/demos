package test.nlp.dict;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import test.nlp.common.Atom;
import test.nlp.common.Attribute;
import test.nlp.common.Sentence;
import test.nlp.common.Vertex;
import test.nlp.dict.pos.POS;
import test.nlp.dict.pos.POSAttribute;
import test.nlp.io.DictionaryReader;
import test.nlp.segment.Graph;
import test.nlp.trie.ITrie;
import test.nlp.trie.Node;
import test.nlp.util.IOUtil;

/**
 * 分词词典基类
 */
public abstract class SegmentDictionary extends Dictionary {

    protected ITrie<Attribute<POS>> dict;

    public SegmentDictionary(ITrie<Attribute<POS>> dict) {
        this.dict = dict;
    }

    public int getFreq(String word) {
        Attribute<POS> attribute = dict.get(word);
        return attribute == null ? 0 : attribute.getTotalFreq();
    }

    @Override
    public void read(InputStream in) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = IOUtil.getObjectInputStream(in);) {
            dict.read(ois);
        }
    }

    @Override
    public void write(OutputStream out) throws IOException {
        try (ObjectOutputStream oos = IOUtil.getObjectOutputStream(out);) {
            this.dict.write(oos);
        }
    }

    /**
     * 判断是否包含某个key
     * 
     * @param key
     * @return
     */
    public boolean contains(String word) {
        return dict.contains(word);
    }

    /**
     * 获取词对应的词性
     * 
     * @param word
     * @return
     */
    public Attribute<POS> get(String word) {
        return dict.get(word);
    }

    /**
     * 对句子进行全切分
     * 
     * @param sentence
     * @param graph
     */
    public void segment(Sentence sentence, Graph graph) {
        segment(sentence, graph, dict);
    }

    /**
     * 对句子进行全切分
     * 
     * @param setence
     * @param graph
     * @param dict
     */
    protected void segment(Sentence sentence, Graph graph, ITrie<Attribute<POS>> dict) {

        StringBuilder word = new StringBuilder();
        for (int i = 0, size = sentence.size(); i < size; i++) {
            Node<Attribute<POS>> node = dict.getRoot();
            word.setLength(0);
            for (int j = i; j < size; j++) {
                Atom atom = sentence.getAtom(j);
                node = dict.transition(atom.getWord(), node);
                if (node == null) {
                    break;
                }
                word.append(atom.getWord());
                if (node.isWord()) {
                    graph.addVertex(new Vertex(sentence.getAtom(i).getOffset(), word.toString(), node.getValue()));
                }
                if (node.isLeaf()) {
                    break;
                }
            }
        }
    }

    protected DictionaryReader<POS> newDictionaryReader() {
        return new DictionaryReader<POS>(POS.class) {

            @Override
            protected Attribute<POS> newAttribute(int size) {
                return new POSAttribute(size);
            }
        };
    }
}
