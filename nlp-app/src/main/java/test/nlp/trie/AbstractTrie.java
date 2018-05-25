package test.nlp.trie;

import java.io.Serializable;

import org.apache.commons.lang3.tuple.Pair;

public abstract class AbstractTrie<V extends Serializable> implements ITrie<V> {

    @Override
    public boolean set(String key, V value) {
        Node<V> node = getNode(key);
        if (node != null && node.isWord()) {
            node.setValue(value);
            return true;
        }
        return false;
    }

    @Override
    public V get(String key) {
        Node<V> node = getNode(key);
        return node == null ? null : node.getValue();
    }

    @Override
    public boolean contains(String key) {
        Node<V> node = getNode(key);
        return (node != null && node.isWord());
    }

    public Node<V> getNode(String key) {
        int length = key.length();
        Node<V> node = getLongestPrefixNode(key, length);
        if (node.depth == length) {
            return node;
        }
        return null;
    }

    @Override
    public V getLongestPrefix(String key) {
        return getLongestPrefix(key.toCharArray());
    }

    @Override
    public Node<V> transition(String input, Node<V> node) {
        for (int i = 0, length = input.length(); i < length; i++) {
            node = transition(input.charAt(i), node);
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    @Override
    public void parse(char[] charArray, IHit<V> hit) {
        Node<V> node = null;
        int matchIndex = -1;
        for (int i = 0; i < charArray.length; i++) {
            node = getRoot();
            matchIndex = -1;
            for (int j = i; j < charArray.length; j++) {
                node = transition(charArray[j], node);
                if (node == null) {
                    break;
                } else {
                    if (node.isWord()) {
                        matchIndex = j;
                        hit.hit(i, j, node.getValue());
                    }
                    if (node.isLeaf()) {
                        break;
                    }
                }
            }

            if (matchIndex != -1) {
                i = matchIndex;
            }
        }
    }

    public Pair<Integer, V> firstMatch(String[] array, int startIndex) {
        Pair<Integer, V> result = null;
        Node<V> node = getRoot();
        int matchIndex = -1;
        V value = null;
        for (int i = startIndex; i < array.length; i++) {

            node = transition(array[i], node);
            if (node == null) {
                break;
            } else {
                if (node.isWord()) {
                    matchIndex = i;
                    value = node.getValue();
                }
                if (node.isLeaf()) {
                    break;
                }
            }

        }

        if (matchIndex != -1) {
            result = Pair.of(matchIndex, value);
        } else {
            result = Pair.of(matchIndex, null);
        }
        return result;
    }

    @Override
    public void fmm(char[] charArray, IHit<V> hit) {
        Node<V> node = null;
        int matchIndex = -1;
        V value = null;

        for (int i = 0; i < charArray.length; i++) {
            node = getRoot();
            matchIndex = -1;

            for (int j = i; j < charArray.length; j++) {
                node = transition(charArray[j], node);
                if (node == null) {
                    break;
                } else {
                    if (node.isWord()) {
                        matchIndex = j;
                        value = node.getValue();
                    }
                    if (node.isLeaf()) {
                        break;
                    }
                }
            }

            if (matchIndex != -1) {
                hit.hit(i, matchIndex, value);
                i = matchIndex;
            } else {
                hit.notHit(i);
            }
        }
    }

    @Override
    public void fmm(String[] array, IHit<V> hit) {
        Node<V> node = null;
        int matchIndex = -1;
        V value = null;

        for (int i = 0; i < array.length; i++) {
            node = getRoot();
            matchIndex = -1;

            for (int j = i; j < array.length; j++) {
                node = transition(array[j], node);
                if (node == null) {
                    break;
                } else {
                    if (node.isWord()) {
                        matchIndex = j;
                        value = node.getValue();
                    }
                    if (node.isLeaf()) {
                        break;
                    }
                }
            }

            if (matchIndex != -1) {
                hit.hit(i, matchIndex, value);
                i = matchIndex;
            } else {
                hit.notHit(i);

            }
        }
    }

    protected Node<V> getLongestPrefixNode(String key, int length) {
        Node<V> node = getRoot();
        Node<V> prevNode = node;
        for (int i = 0; i < length; i++) {
            node = transition(key.charAt(i), node);
            if (node == null) {
                break;
            }
            prevNode = node;
        }
        return prevNode;
    }

    protected V getLongestPrefix(char[] charArray) {
        V result = null;
        Node<V> node = getRoot();
        for (int i = 0; i < charArray.length; i++) {
            node = transition(charArray[i], node);
            if (node == null) {
                break;
            }
            if (node.isWord()) {
                result = node.getValue();
            }
        }
        return result;
    }

}
