package test.nlp.trie;

/**
 * 命中一个模式串的处理方法
 */
public interface IHit<V> {

    /**
     * 命中一个模式串
     * 
     * @param begin
     *            起始位置
     * @param end
     *            终止位置
     * @param value
     *            值
     */
    public void hit(int begin, int end, V value);

    /**
     * 无命中的情况
     * 
     * @param begin
     */
    public void notHit(int begin);
}
