package test.nlp.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 句子
 */
public class Sentence implements Iterable<Atom> {

    /**
     * 该句子中所有的原子对象
     */
    private List<Atom> atoms;

    /**
     * 该句子总词长
     */
    private int length;

    /**
     * 句子在原文本中的起始位置
     */
    private int offset;

    public Sentence(int offset) {
        this.atoms = new ArrayList<Atom>();
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void addAtom(Atom atom) {
        atoms.add(atom);
        this.length += atom.getWord().length();
    }

    public boolean isEmpty() {
        return atoms.isEmpty();
    }

    public int length() {
        return length;
    }

    public int size() {
        return atoms.size();
    }

    public Atom getAtom(int index) {
        return atoms.get(index);
    }

    @Override
    public Iterator<Atom> iterator() {
        return atoms.iterator();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
