package test.nlp.io;

import java.util.TreeMap;

import com.google.common.collect.Maps;
import test.nlp.common.Attribute;

public class DictionaryReader<V extends Enum<V>> extends FileReader<TreeMap<String, Attribute<V>>> {

    private Class<V> enumType;

    public DictionaryReader(Class<V> enumType) {
        this.enumType = enumType;
    }

    @Override
    protected TreeMap<String, Attribute<V>> newResult() {
        return Maps.newTreeMap();
    }

    @Override
    protected void processTokens(String original, String[] tokens, TreeMap<String, Attribute<V>> result) {
        Attribute<V> attribute = newAttribute((tokens.length - 1) / 2);
        int index = 0;
        for (int i = 1; i < tokens.length; i += 2) {
            attribute.addItem(index++, Enum.valueOf(enumType, tokens[i]), Integer.parseInt(tokens[i + 1]));
        }
        attribute.setAttribute();
        result.put(tokens[0], attribute);
    }

    protected Attribute<V> newAttribute(int size) {
        return new Attribute<V>(size);

    }
}
