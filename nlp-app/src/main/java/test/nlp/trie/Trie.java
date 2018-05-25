package test.nlp.trie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Lists;

public class Trie<V extends Serializable> extends AbstractTrie<V> {

    private TrieNode<V> root = new TrieNode<V>();

    @Override
    public void read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        root.read(in);
    }

    @Override
    public void write(ObjectOutputStream out) throws IOException {
        root.write(out);
    }

    @Override
    public boolean add(String key, V value) {
        int length = key.length();
        TrieNode<V> node = (TrieNode<V>) getLongestPrefixNode(key, length);
        if (node.depth != length) {
            TrieNode<V> parent = node;
            for (int i = node.depth; i < length; i++) {
                node = new TrieNode<V>(key.charAt(i), parent);
                parent = node;
            }
        }
        node.setValue(value);
        node.setWord(true);
        return true;
    }

    public void clear() {
        root.clear();
        root = null;
    }

    @Override
    public V remove(String key) {
        Node<V> node = getNode(key);

        // 关键词不存在
        if (node == null || !node.isWord()) {
            return null;
        }

        V removeValue = node.getValue();

        // 要删除的节点不是叶子节点
        if (!node.isLeaf()) {
            node.setWord(false);
            node.setValue(null);
        } else {
            do {
                ((TrieNode<V>) node.getParent()).removeChild(((TrieNode<V>) node).getCh());
                node = node.getParent();
            } while (node.isLeaf() && !node.isWord());
        }

        return removeValue;
    }

    @Override
    public void create(TreeMap<String, V> items) {
        for (Map.Entry<String, V> item : items.entrySet()) {
            add(item.getKey(), item.getValue());
        }
    }

    public Map<Character, TrieNode<V>> getChildren(TrieNode<V> node) {
        if (node == null) {
            return root.getChildren();
        }
        return node.getChildren();
    }

    @Override
    public Node<V> getRoot() {
        return root;
    }

    @Override
    public Node<V> transition(char ch, Node<V> node) {
        return ((TrieNode<V>) node).getChild(ch);
    }

    @Override
    public List<V> prefixSearch(String key) {

        int length = key.length();
        TrieNode<V> node = (TrieNode<V>) getLongestPrefixNode(key, length);
        if (node.depth != length) {
            return Collections.emptyList();
        }

        List<V> result = Lists.newArrayList();
        prefixSearch(node, result);
        return result;
    }

    private void prefixSearch(TrieNode<V> node, List<V> result) {
        if (node.isWord()) {
            result.add(node.getValue());
            if (node.isLeaf()) {
                return;
            }
        } else {
            Map<Character, TrieNode<V>> children = node.getChildren();
            for (TrieNode<V> child : children.values()) {
                prefixSearch(child, result);
            }
        }
    }

    @SuppressWarnings("unused")
    private void reverse(List<V> result, TrieNode<V> node) {
        if (node == null) {
            return;
        }
        if (node.isWord()) {
            result.add(node.getValue());
        }
        for (Map.Entry<Character, TrieNode<V>> childNode : node.getChildren().entrySet()) {
            reverse(result, childNode.getValue());
        }
    }

}