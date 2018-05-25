package test.nlp.util;

import java.io.Serializable;

import test.nlp.dict.synonym.SynonymDictionary;
import test.nlp.trie.ITrie;

public class TrieUtil {

    public static <V extends Serializable> V get(String domain, String key, ITrie<V> trie) {
        V value = trie.get(key);
        if (value != null) {
            return value;
        }

        String[] synonyms = SynonymDictionary.getInstance().getSynonymWords(domain, key);
        if (synonyms == null) {
            return null;
        }

        for (String synonym : synonyms) {
            value = trie.get(synonym);
            if (value != null) {
                return value;
            }
        }

        return null;
    }

}
