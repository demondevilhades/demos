package test.nlp.trie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class DoubleArrayTrie<V extends Serializable> extends AbstractTrie<V> {

    // 冲突处理
    private static final int MAX_RETRY = 300;

    private static final int STEP_BASE = 3;

    private DATNode<V>[] datNodes;

    @Override
    public void clear() {
        // for(DATNode<V> node : datNodes) {
        // node = null;
        // }
        datNodes = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(ObjectInputStream in) throws IOException, ClassNotFoundException {

        this.datNodes = new DATNode[in.readInt()];
        for (int i = 0; i < this.datNodes.length; i++) {
            this.datNodes[i] = new DATNode<V>(i);
            this.datNodes[i].read(in);

        }

        // read parent
        for (int i = 0; i < this.datNodes.length; i++) {
            if (in.readBoolean()) {
                this.datNodes[i].setParent(this.datNodes[in.readInt()]);
            }
        }
    }

    @Override
    public void write(ObjectOutputStream out) throws IOException {
        out.writeInt(this.datNodes.length);
        for (int i = 0; i < this.datNodes.length; i++) {
            this.datNodes[i].write(out);
        }

        // write parent
        for (int i = 0; i < this.datNodes.length; i++) {
            if (this.datNodes[i].getParent() != null) {
                out.writeBoolean(true);
                out.writeInt(((DATNode<V>) this.datNodes[i].getParent()).getIndex());
            } else {
                out.writeBoolean(false);
            }
        }

    }

    @Override
    public V remove(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(TreeMap<String, V> items) {
        // 先创建普通Trie树
        Trie<V> trie = new Trie<V>();

        trie.create(items);

        resize(Character.MAX_VALUE << 5);

        PriorityQueue<State<V>> stateQueue = new PriorityQueue<State<V>>();

        int newBase = 1;
        State<V> state = new State<V>();
        int total = 1;
        do {
            TreeMap<Character, TrieNode<V>> childNodes = (TreeMap<Character, TrieNode<V>>) trie.getChildren(state.node);
            if (childNodes.isEmpty()) {
                break;
            }

            // 找出base值
            int tmpBase = findBase(childNodes, newBase);
            this.datNodes[state.offset].setBase(tmpBase);
            // 如果是单词的结束位置，则设置base值为负base
            if (state.isWord()) {
                this.datNodes[state.offset].setBase(-tmpBase);
            }

            if (tmpBase - newBase >= MAX_RETRY) {
                newBase += STEP_BASE;
            }

            // 设置所有子结点的check值为父结点的base下标
            int check = state.offset;
            for (Map.Entry<Character, TrieNode<V>> entry : childNodes.entrySet()) {
                TrieNode<V> childNode = entry.getValue();
                int idx = entry.getKey() + tmpBase;
                this.datNodes[idx].setBase(-idx);
                this.datNodes[idx].setCheck(check);
                this.datNodes[idx].value = childNode.getValue();
                this.datNodes[idx].depth = childNode.depth;
                this.datNodes[idx].parent = this.datNodes[state.offset];

                // 该结点不是叶子结点，则入队列
                if (!childNode.isLeaf()) {
                    State<V> subState = new State<V>(childNode);
                    subState.offset = idx;
                    stateQueue.offer(subState);
                }
            }
            total = Math.max(childNodes.lastKey() + tmpBase + 1, total);
        } while ((state = stateQueue.poll()) != null);
        trimToSize(total);
    }

    @Override
    public Node<V> getRoot() {
        return datNodes[0];
    }

    @Override
    public Node<V> transition(char input, Node<V> node) {
        int base = Math.abs(((DATNode<V>) node).getBase());
        int nextOffset = base + input;
        if (nextOffset > 0 && nextOffset < this.datNodes.length
                && this.datNodes[nextOffset].getCheck() == ((DATNode<V>) node).getIndex()) {
            return datNodes[nextOffset];
        }
        return null;
    }

    @Override
    public List<V> prefixSearch(String key) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        if (this.datNodes == null) {
            this.datNodes = new DATNode[newSize];
            for (int i = 0; i < this.datNodes.length; i++) {
                this.datNodes[i] = new DATNode<V>(i);
            }
        } else {
            int oldSize = this.datNodes.length;
            this.datNodes = Arrays.copyOf(this.datNodes, newSize);
            for (int i = oldSize; i < newSize; i++) {
                this.datNodes[i] = new DATNode<V>(i);
            }
        }
    }

    /**
     * 找出当前结点base下标值
     * 
     * @param item
     * @param tmpBase
     * @return
     */
    private int findBase(TreeMap<Character, TrieNode<V>> nodes, int tmpBase) {
        boolean loop;
        do {
            loop = false;
            for (Map.Entry<Character, TrieNode<V>> entry : nodes.entrySet()) {
                int tmpIndex = entry.getKey() + tmpBase;
                if (tmpIndex >= this.datNodes.length) {
                    resize(tmpBase + nodes.lastKey());
                }
                if (this.datNodes[tmpIndex].getBase() != 0) {
                    ++tmpBase;
                    loop = true;
                    break;
                }
            }
        } while (loop);
        return tmpBase;
    }

    private void trimToSize(int size) {
        this.datNodes = Arrays.copyOf(this.datNodes, size);
    }

    private static class State<V extends Serializable> implements Comparable<State<V>> {

        /**
         * 偏移量
         */
        private int offset;

        private TrieNode<V> node;

        public State() {

        }

        public State(TrieNode<V> node) {
            this.node = node;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof State) {
                return this.node.equals(((State<V>) obj).node);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return node.hashCode();
        }

        @Override
        public int compareTo(State<V> o) {
            return node.compareTo(o.node);
        }

        public boolean isWord() {
            return this.node != null && this.node.isWord();
        }

    }

}
