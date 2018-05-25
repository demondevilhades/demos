package test.nlp.io;

import java.lang.reflect.Array;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public abstract class MultiValueMapReader<V> extends FileReader<Map<String, V[]>> {

    private char split;

    private Class<V> clazz;

    public MultiValueMapReader(Class<V> clazz, char split) {
        this.clazz = clazz;
        this.split = split;
    }

    @Override
    protected Map<String, V[]> newResult() {
        return Maps.newHashMap();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void processTokens(String original, String[] tokens, Map<String, V[]> result) {
        if (tokens.length < 2) {
            return;
        }
        String[] splits = StringUtils.split(tokens[1], split);
        V[] values = (V[]) Array.newInstance(clazz, splits.length);
        result.put(tokens[0], createValues(splits, values));
    }

    protected abstract V[] createValues(String[] splits, V[] values);
}
