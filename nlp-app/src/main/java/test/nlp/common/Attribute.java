package test.nlp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 属性信息
 */
public class Attribute<E extends Enum<E>> implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性值集
     */
    protected Object[] items;

    /**
     * 属性对应的频率
     */
    protected int[] freqs;

    /**
     * 总频率
     */
    protected int totalFreq;

    /**
     * 最有可能的属性值
     */
    protected E possibleAttr;

    public Attribute(Object[] items, int[] freqs, int totalFreq) {
        this.items = items;
        this.freqs = freqs;
        this.totalFreq = totalFreq;
    }

    public Attribute(E item, int freq) {
        this(new Object[] { item }, new int[] { freq }, freq);
    }

    public Attribute(int size) {
        this.items = new Object[size];
        this.freqs = new int[size];
    }

    @SuppressWarnings("unchecked")
    public Attribute(E... items) {
        this.items = new Object[items.length];
        this.freqs = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            this.items[i] = items[i];
            this.freqs[i] = 1;
        }
    }

    public boolean containsItem(E item) {
        return ArrayUtils.contains(items, item);
    }

    @SuppressWarnings("unchecked")
    public E getPossibleAttr() {

        if (possibleAttr == null) {
            if (items.length == 1) {
                possibleAttr = (E) items[0];
            } else if (items.length > 1) {
                int index = 0;
                int maxFreq = -1;
                for (int i = 0; i < items.length; i++) {
                    if (maxFreq < freqs[i]) {
                        index = i;
                        maxFreq = freqs[i];
                    }
                }
                this.possibleAttr = (E) items[index];
            }
        }
        return possibleAttr;
    }

    public void addItem(int index, E item, int freq) {
        this.items[index] = item;
        this.freqs[index] = freq;
        this.totalFreq += freq;
    }

    public int getTotalFreq() {
        return totalFreq;
    }

    public int getFreq(int index) {
        return freqs[index];
    }

    public int size() {
        return items.length;
    }

    @SuppressWarnings("unchecked")
    public E getItem(int index) {
        return (E) items[index];
    }

    public Attribute<E> copyOf(int newSize) {
        Attribute<E> attribute = new Attribute<E>(newSize);
        attribute.items = Arrays.copyOf(this.items, newSize);
        attribute.freqs = Arrays.copyOf(this.freqs, newSize);
        attribute.totalFreq = this.totalFreq;
        return attribute;
    }

    @SuppressWarnings("unchecked")
    public Attribute<E> clone(E[] items) {
        Attribute<E> clone = (Attribute<E>) clone();
        clone.items = items;
        return clone;
    }

    public void setAttribute() {

    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        setAttribute();
    }
}
