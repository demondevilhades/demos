package test.nlp.trie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.collect.ComparisonChain;

public class TrieNode<V extends Serializable> extends Node<V> implements Cloneable, Comparable<TrieNode<V>> {

    /**
     * 当前节点对应的字符
     */
    private char ch;

    private boolean isWord;

    /**
     * 子节点
     */
    private Map<Character, TrieNode<V>> children = new TreeMap<Character, TrieNode<V>>();

    public TrieNode(char ch, Node<V> parent) {
        super(parent);
        this.ch = ch;
        ((TrieNode<V>) parent).addChild(ch, this);
    }

    public TrieNode() {
        super();
    }

    public char getCh() {
        return ch;
    }

    TrieNode<V> getChild(char ch) {
        return children.get(ch);
    }

    public void removeChild(char ch) {
        children.remove(ch);
    }

    public void addChild(char ch, TrieNode<V> child) {
        children.put(ch, child);
    }

    public int getChildCount() {
        return children.size();
    }

    public Map<Character, TrieNode<V>> getChildren() {
        return children;
    }

    @Override
    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean isWord) {
        this.isWord = isWord;
    }

    @Override
    public boolean isLeaf() {
        return children.size() == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TrieNode) {
            TrieNode<V> other = (TrieNode<V>) obj;
            return this.ch == other.ch;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ch).hashCode();
    }

    @Override
    public int compareTo(TrieNode<V> o) {
        if (this.equals(o)) {
            return 0;
        }
        return ComparisonChain.start().compare(children.size(), o.children.size()).compare(this.ch, o.ch).result();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        int childCount = in.readInt();
        for (int i = 0; i < childCount; i++) {
            char childKey = in.readChar();
            TrieNode<V> child = new TrieNode<V>(childKey, this);
            child.setWord(in.readBoolean());
            child.setValue((V) in.readObject());
            child.read(in);
            children.put(childKey, child);
        }
    }

    @Override
    public void write(ObjectOutputStream out) throws IOException {
        out.writeInt(children.size());
        for (Map.Entry<Character, TrieNode<V>> entry : children.entrySet()) {
            out.writeChar(entry.getKey());
            out.writeBoolean(entry.getValue().isWord());
            out.writeObject(entry.getValue().getValue());
            entry.getValue().write(out);
        }
    }

    public void clear() {
        for (TrieNode<V> node : children.values()) {
            node.clear();
        }
        this.children.clear();
    }
}
