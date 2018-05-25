package test.nlp.path;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import test.nlp.trie.IHit;

/**
 * 路径树
 */
public class PathTree<V> {

    /**
     * 路径分隔符
     */
    private String separator;

    private PathNode<V> root;

    public PathTree(String separator) {
        this.separator = separator;
        this.root = new PathNode<V>();
    }

    /**
     * 添加一个路径信息
     * 
     * @param path
     * @param value
     */
    public void add(String path, V value) {
        String[] keys = StringUtils.split(path, separator);

        PathNode<V> parent = root;
        PathNode<V> node = null;
        for (int i = 0; i < keys.length; i++) {
            node = parent.getChild(keys[i]);
            if (node == null) {
                node = new PathNode<V>();
                parent.addChild(keys[i], node);
            }
            parent = node;
        }
        node.setValue(value);
        node.setEnd(true);
    }

    public PathNode<V> getRoot() {
        return root;
    }

    public PathNode<V> transition(String key, PathNode<V> root) {
        return root.getChild(key);
    }

    /**
     * 正向最大匹配数组里的路径信息
     * 
     * @param text
     * @param hit
     */
    public void fmm(String[] array, IHit<V> hit) {
        if (ArrayUtils.isEmpty(array)) {
            return;
        }

        int matchIndex = -1;
        V value = null;
        PathNode<V> node = null;

        for (int i = 0; i < array.length; i++) {
            matchIndex = -1;
            value = null;
            node = root;
            for (int j = i; j < array.length; j++) {
                node = node.getChild(array[j]);
                if (node == null) {
                    break;
                }
                if (node.isEnd()) {
                    matchIndex = j;
                    value = node.getValue();
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

    /**
     * 获取路径对应的值
     * 
     * @param path
     * @return
     */
    public V get(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }

        String[] keys = StringUtils.split(path, separator);
        PathNode<V> node = root;
        for (int i = 0; i < keys.length; i++) {
            node = node.getChild(keys[i]);
            if (node == null) {
                return null;
            }
        }
        return node.getValue();
    }

    public static void main(String[] args) {
        PathTree<String> tree = new PathTree<String>("/");
        String[] paths = new String[] { "index/doc", "index", "index/error", "search" };
        for (String path : paths) {
            tree.add(path, path);
        }

        tree.fmm(new String[] { "index", "doc", "index", "search", "index", "error", "error", "index" },
                new IHit<String>() {
                    @Override
                    public void hit(int begin, int end, String value) {
                        System.out.println(value);
                    }

                    @Override
                    public void notHit(int begin) {

                    }
                });
    }
}
