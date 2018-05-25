package test.nlp.trie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 树节点
 */
public abstract class Node<V extends Serializable> {

    /**
     * 当前节点的深度
     */
    protected int depth;

    /**
     * 节点对应的内容
     */
    protected V value;

    /**
     * 父节点
     */
    protected Node<V> parent;

    public Node() {

    }

    public Node(Node<V> parent) {
        this.parent = parent;
        this.depth = parent.depth + 1;
    }

    public Node<V> getParent() {
        return parent;
    }

    public void setParent(Node<V> parent) {
        this.parent = parent;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V content) {
        this.value = content;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public abstract boolean isWord();

    public abstract void setWord(boolean isWord);

    public abstract boolean isLeaf();

    public abstract void read(ObjectInputStream in) throws IOException, ClassNotFoundException;

    public abstract void write(ObjectOutputStream out) throws IOException;
}
