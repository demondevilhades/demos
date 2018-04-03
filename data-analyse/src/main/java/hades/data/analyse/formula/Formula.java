package hades.data.analyse.formula;

import hades.data.analyse.data.DataArrayList;
import hades.data.analyse.util.Logable;

public interface Formula extends Logable {

    /**
     * 
     * @param data
     * @return
     */
    public DataArrayList calc(DataArrayList data);
}
