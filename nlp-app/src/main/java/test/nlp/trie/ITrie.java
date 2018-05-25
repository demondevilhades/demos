package test.nlp.trie;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Trie树接口类
 */
public interface ITrie<V extends Serializable> {

    void clear();

    /**
     * 从二进制文件中读取Trie树模型
     * 
     * @param inputFile
     * @throws IOException
     * @throws ClassNotFoundException
     */
    default void read(File inputFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(inputFile)));) {
            read(in);
        }
    }

    /**
     * 读取输入流生成Trie树模型
     * 
     * @param in
     * @throws IOException
     */
    void read(ObjectInputStream in) throws IOException, ClassNotFoundException;

    /**
     * 将Trie树模型写入到二进制文件中
     * 
     * @param outputFile
     * @throws IOException
     */
    default void write(File outputFile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));) {
            write(out);
        }
    }

    /**
     * 将Trie树模型写入到输出流中
     * 
     * @param out
     * @throws IOException
     */
    void write(ObjectOutputStream out) throws IOException;

    /**
     * 新增一个节点
     * 
     * @param key
     *            关键词
     * @param value
     *            值
     * @return
     */
    default boolean add(String key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 设置关键词对应的节点值
     * 
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, V value);

    Pair<Integer, V> firstMatch(String[] array, int startIndex);

    /**
     * 删除关键词
     * 
     * @param key
     * @return
     */
    V remove(String key);

    /**
     * 判断是否包含指定的关键词
     * 
     * @param key
     * @return
     */
    boolean contains(String key);

    /**
     * 创建Trie树
     * 
     * @param items
     */
    void create(TreeMap<String, V> items);

    /**
     * 获取关键词对应的值
     * 
     * @param key
     * @return
     */
    V get(String key);

    /**
     * 获取关键词对应的节点
     * 
     * @param key
     * @return
     */
    Node<V> getNode(String key);

    /**
     * 获取关键词的最长前缀词对应的值
     * 
     * @param key
     * @return
     */
    V getLongestPrefix(String key);

    /**
     * 获取根结点
     * 
     * @return
     */
    Node<V> getRoot();

    /**
     * 状态转移
     * 
     * @param input
     * @param node
     * @return
     */
    Node<V> transition(String input, Node<V> node);

    /**
     * 状态转移
     * 
     * @param input
     * @param node
     * @return
     */
    Node<V> transition(char input, Node<V> node);

    /**
     * 解析文本
     * 
     * @param charArray
     * @param hit
     */
    void parse(char[] charArray, IHit<V> hit);

    /**
     * 正向最大匹配
     * 
     * @param charArray
     * @param hit
     */
    void fmm(char[] charArray, IHit<V> hit);

    /**
     * 最大正向匹配
     * 
     * @param array
     *            字符串数组，每一个元素必须全部匹配
     * @param hit
     */
    void fmm(String[] array, IHit<V> hit);

    /**
     * 前缀查询
     * 
     * @param key
     * @return
     */
    List<V> prefixSearch(String key);

}
