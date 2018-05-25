package test.nlp.io;

import java.util.Map;

import com.google.common.collect.Maps;

public class MapReader extends FileReader<Map<String, String>> {

    @Override
    protected Map<String, String> newResult() {
        return Maps.newHashMap();
    }

    @Override
    protected void processTokens(String original, String[] tokens, Map<String, String> result) {
        if (tokens.length < 2) {
            return;
        }
        result.put(tokens[0], tokens[1]);
    }
}
