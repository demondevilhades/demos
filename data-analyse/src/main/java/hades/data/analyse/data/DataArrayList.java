package hades.data.analyse.data;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class DataArrayList extends ArrayList<Data> {

    public DataArrayList() {
        super();
    }

    public DataArrayList(Collection<Data> c) {
        super(c);
    }

    public DataArrayList(int initialCapacity) {
        super(initialCapacity);
    }
}
