package test.nlp.path;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

/**
 * 路径节点
 */
public class PathNode<V> {

    /**
     * 是否是一个结束位置
     */
    private boolean end;

    /**
     * 子节点信息
     */
    private Map<String, PathNode<V>> children;

    private V value;

    public PathNode() {
        this.children = newHashMap();
    }

    public PathNode(V value) {
        this.end = true;
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public Map<String, PathNode<V>> getChildren() {
        return children;
    }

    public PathNode<V> getChild(String key) {
        return children.get(key);
    }

    public void addChild(String key, PathNode<V> child) {
        this.children.put(key, child);
    }
}
