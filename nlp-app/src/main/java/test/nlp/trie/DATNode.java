package test.nlp.trie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 双数组trie树节点
 */
public class DATNode<V extends Serializable> extends Node<V> {

    /**
     * 当前节点的索引下标
     */
    private int index;

    private int base;

    private int check;

    public DATNode(int index, Node<V> parent) {
        super(parent);
        this.index = index;
    }

    public DATNode(int index) {
        super();
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    @Override
    public boolean isWord() {
        return this.base < 0;
    }

    @Override
    public void setWord(boolean isWord) {
        this.base = Math.abs(this.base);
    }

    @Override
    public boolean isLeaf() {
        return this.base == -index;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.base = in.readInt();
        this.check = in.readInt();
        this.depth = in.readInt();
        this.value = (V) in.readObject();
    }

    @Override
    public void write(ObjectOutputStream out) throws IOException {
        out.writeInt(this.base);
        out.writeInt(this.check);
        out.writeInt(this.depth);
        out.writeObject(this.value);
    }
}
