package test.nlp.trie;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

/**
 * 后缀trie实现
 */
public class SuffixTrie<T extends Serializable> implements ITrie<T> {
    private ITrie<T> suffix;

    public SuffixTrie() {
        suffix = new DoubleArrayTrie<T>();
    }

    public SuffixTrie(ITrie<T> suffix) {
        this.suffix = suffix;
    }

    @Override
    public void clear() {
        this.suffix.clear();
    }

    @Override
    public void create(TreeMap<String, T> items) {
        TreeMap<String, T> reverseItems = Maps.newTreeMap();
        for (Entry<String, T> entry : items.entrySet()) {
            reverseItems.put(StringUtils.reverse(entry.getKey()), entry.getValue());
        }
        suffix.create(reverseItems);
    }

    @Override
    public boolean add(String key, T value) {
        return suffix.add(StringUtils.reverse(key), value);
    }

    @Override
    public boolean contains(String key) {
        return suffix.contains(StringUtils.reverse(key));
    }

    @Override
    public void fmm(char[] charArray, IHit<T> hit) {
        ArrayUtils.reverse(charArray);
        suffix.fmm(charArray, hit);
    }

    @Override
    public void fmm(String[] array, IHit<T> hit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pair<Integer, T> firstMatch(String[] array, int startIndex) {
        return null;
    }

    @Override
    public T get(String key) {
        return suffix.get(StringUtils.reverse(key));
    }

    @Override
    public T getLongestPrefix(String key) {
        return suffix.getLongestPrefix(StringUtils.reverse(key));
    }

    @Override
    public Node<T> getRoot() {
        return suffix.getRoot();
    }

    @Override
    public void parse(char[] charArray, IHit<T> hit) {
        ArrayUtils.reverse(charArray);
        suffix.parse(charArray, hit);
    }

    @Override
    public List<T> prefixSearch(String key) {
        return suffix.prefixSearch(StringUtils.reverse(key));
    }

    @Override
    public void read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        suffix.read(in);
    }

    @Override
    public T remove(String key) {
        return suffix.remove(StringUtils.reverse(key));
    }

    @Override
    public void write(ObjectOutputStream out) throws IOException {
        suffix.write(out);
    }

    @Override
    public Node<T> transition(char input, Node<T> node) {
        return suffix.transition(input, node);
    }

    @Override
    public Node<T> transition(String input, Node<T> node) {
        return suffix.transition(StringUtils.reverse(input), node);
    }

    @Override
    public boolean set(String key, T value) {
        return suffix.set(StringUtils.reverse(key), value);
    }

    @Override
    public void read(File inputFile) throws IOException, ClassNotFoundException {
        suffix.read(inputFile);
    }

    @Override
    public void write(File outputFile) throws IOException {
        suffix.write(outputFile);
    }

    @Override
    public Node<T> getNode(String key) {
        return suffix.getNode(StringUtils.reverse(key));
    }

}
