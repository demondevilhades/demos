package test.nlp.util;

import java.util.Comparator;
import java.util.TreeSet;

@SuppressWarnings("serial")
public class TopNTreeSet<T> extends TreeSet<T> {

    private int limit;

    public TopNTreeSet(int limit) {
        this.limit = limit;
    }

    public TopNTreeSet(int limit, Comparator<T> comparator) {
        super(comparator);
        this.limit = limit;
    }

    @Override
    public boolean add(T e) {
        if (size() < limit) {
            return super.add(e);
        }
        if (contains(e)) {
            return false;
        }
        super.add(e);
        super.pollLast();
        return true;
    }
}
