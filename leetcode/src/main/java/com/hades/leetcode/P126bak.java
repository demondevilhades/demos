package com.hades.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * wordList必须包含endWord
 * 
 * used dfs, bfs is better
 * 
 * @author HaDeS
 */
public class P126bak {

    private List<Map<Integer, Integer>> changeIndexMapList;
    private List<List<String>> resultList;
    private List<String> wordList;
    private int minSize;

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        resultList = new ArrayList<List<String>>();

        char[] beginChs = beginWord.toCharArray();
        char[] endChs = endWord.toCharArray();

        // run
        List<String> itemList = new ArrayList<String>();
        itemList.add(beginWord);
        if (getChangeIndex(beginChs, endChs) != -1) {
            itemList.add(endWord);
            resultList.add(itemList);
        } else {
            int size = wordList.size();
            List<List<Integer>> indexListList = new ArrayList<List<Integer>>(size);
            changeIndexMapList = new ArrayList<Map<Integer, Integer>>(size);
            this.wordList = wordList;
            minSize = size + 2;
            List<char[]> chsList = new ArrayList<char[]>();
            for (String word : wordList) {
                chsList.add(word.toCharArray());
                indexListList.add(new ArrayList<Integer>());
                changeIndexMapList.add(new HashMap<Integer, Integer>());
            }

            // build word index
            int changeIndex;
            List<Integer> indexList;
            Map<Integer, Integer> changeIndexMap;
            List<Integer> endWordIndexList = new ArrayList<Integer>();
            for (int i = 0; i < size; i++) {
                indexList = indexListList.get(i);
                changeIndexMap = changeIndexMapList.get(i);
                for (int j = i + 1; j < size; j++) {
                    changeIndex = getChangeIndex(chsList.get(i), chsList.get(j));
                    if (changeIndex != -1) {
                        indexList.add(j);
                        changeIndexMap.put(j, changeIndex);
                        indexListList.get(j).add(i);
                        changeIndexMapList.get(j).put(i, changeIndex);
                    }
                }
                if (endWord.equals(wordList.get(i))) {
                    endWordIndexList.add(i);
                }
            }
            if (!endWordIndexList.isEmpty()) {
                for (int i = 0; i < size; i++) {
                    changeIndex = getChangeIndex(beginChs, chsList.get(i));
                    if (changeIndex != -1) {
                        List<List<Integer>> indexListListTemp = new ArrayList<List<Integer>>(indexListList);
                        List<String> itemListTemp = new ArrayList<String>(itemList);
                        itemListTemp.add(wordList.get(i));
                        run(i, indexListListTemp, endWordIndexList, itemListTemp, changeIndex);
                    }
                }
                for (Iterator<List<String>> iterator = resultList.iterator(); iterator.hasNext();) {
                    if (iterator.next().size() > minSize) {
                        iterator.remove();
                    }
                }
            }
        }
        return resultList;
    }

    private void run(int i, List<List<Integer>> indexListListTemp, List<Integer> endWordIndexList,
            List<String> itemList, int changeIndex) {
        if (itemList.size() < minSize) {
            List<Integer> indexList = indexListListTemp.set(i, null);
            for (int j = 0, size = indexList.size(); j < size; j++) {
                int index = indexList.get(j);
                int curChangeIndex = changeIndexMapList.get(i).get(index);
                if (indexListListTemp.get(index) != null && changeIndex != curChangeIndex) {
                    List<String> itemListTemp = new ArrayList<String>(itemList);
                    itemListTemp.add(wordList.get(index));
                    if (endWordIndexList.contains(index)) {
                        minSize = itemListTemp.size();
                        resultList.add(itemListTemp);
                    } else {
                        run(index, new ArrayList<List<Integer>>(indexListListTemp), endWordIndexList, itemListTemp,
                                changeIndexMapList.get(i).get(index));
                    }
                }
            }
        }
    }

    private int getChangeIndex(char[] beginChs, char[] endChs) {
        int changeIndex = -1;
        for (int i = 0; i < endChs.length; i++) {
            if (beginChs[i] != endChs[i]) {
                if (changeIndex == -1) {
                    changeIndex = i;
                } else {
                    changeIndex = -1;
                    break;
                }
            }
        }
        return changeIndex;
    }

    public static void main(String[] args) {
        print(new P126bak().findLadders("hit", "cog",
                Arrays.asList(new String[] { "hot", "dot", "dog", "lot", "log", "cog" })));
        print(new P126bak().findLadders("hit", "cog", Arrays.asList(new String[] { "hot", "dot", "dog", "lot", "log" })));
        print(new P126bak().findLadders("a", "c", Arrays.asList(new String[] { "a", "b", "c" })));
        print(new P126bak().findLadders(
                "qa",
                "sq",
                Arrays.asList(new String[] { "si", "go", "se", "cm", "so", "ph", "mt", "db", "mb", "sb", "kr", "ln",
                        "tm", "le", "av", "sm", "ar", "ci", "ca", "br", "ti", "ba", "to", "ra", "fa", "yo", "ow", "sn",
                        "ya", "cr", "po", "fe", "ho", "ma", "re", "or", "rn", "au", "ur", "rh", "sr", "tc", "lt", "lo",
                        "as", "fr", "nb", "yb", "if", "pb", "ge", "th", "pm", "rb", "sh", "co", "ga", "li", "ha", "hz",
                        "no", "bi", "di", "hi", "qa", "pi", "os", "uh", "wm", "an", "me", "mo", "na", "la", "st", "er",
                        "sc", "ne", "mn", "mi", "am", "ex", "pt", "io", "be", "fm", "ta", "tb", "ni", "mr", "pa", "he",
                        "lr", "sq", "ye" })));
        print(new P126bak().findLadders(
                "cet",
                "ism",
                Arrays.asList(new String[] { "kid", "tag", "pup", "ail", "tun", "woo", "erg", "luz", "brr", "gay",
                        "sip", "kay", "per", "val", "mes", "ohs", "now", "boa", "cet", "pal", "bar", "die", "war",
                        "hay", "eco", "pub", "lob", "rue", "fry", "lit", "rex", "jan", "cot", "bid", "ali", "pay",
                        "col", "gum", "ger", "row", "won", "dan", "rum", "fad", "tut", "sag", "yip", "sui", "ark",
                        "has", "zip", "fez", "own", "ump", "dis", "ads", "max", "jaw", "out", "btu", "ana", "gap",
                        "cry", "led", "abe", "box", "ore", "pig", "fie", "toy", "fat", "cal", "lie", "noh", "sew",
                        "ono", "tam", "flu", "mgm", "ply", "awe", "pry", "tit", "tie", "yet", "too", "tax", "jim",
                        "san", "pan", "map", "ski", "ova", "wed", "non", "wac", "nut", "why", "bye", "lye", "oct",
                        "old", "fin", "feb", "chi", "sap", "owl", "log", "tod", "dot", "bow", "fob", "for", "joe",
                        "ivy", "fan", "age", "fax", "hip", "jib", "mel", "hus", "sob", "ifs", "tab", "ara", "dab",
                        "jag", "jar", "arm", "lot", "tom", "sax", "tex", "yum", "pei", "wen", "wry", "ire", "irk",
                        "far", "mew", "wit", "doe", "gas", "rte", "ian", "pot", "ask", "wag", "hag", "amy", "nag",
                        "ron", "soy", "gin", "don", "tug", "fay", "vic", "boo", "nam", "ave", "buy", "sop", "but",
                        "orb", "fen", "paw", "his", "sub", "bob", "yea", "oft", "inn", "rod", "yam", "pew", "web",
                        "hod", "hun", "gyp", "wei", "wis", "rob", "gad", "pie", "mon", "dog", "bib", "rub", "ere",
                        "dig", "era", "cat", "fox", "bee", "mod", "day", "apr", "vie", "nev", "jam", "pam", "new",
                        "aye", "ani", "and", "ibm", "yap", "can", "pyx", "tar", "kin", "fog", "hum", "pip", "cup",
                        "dye", "lyx", "jog", "nun", "par", "wan", "fey", "bus", "oak", "bad", "ats", "set", "qom",
                        "vat", "eat", "pus", "rev", "axe", "ion", "six", "ila", "lao", "mom", "mas", "pro", "few",
                        "opt", "poe", "art", "ash", "oar", "cap", "lop", "may", "shy", "rid", "bat", "sum", "rim",
                        "fee", "bmw", "sky", "maj", "hue", "thy", "ava", "rap", "den", "fla", "auk", "cox", "ibo",
                        "hey", "saw", "vim", "sec", "ltd", "you", "its", "tat", "dew", "eva", "tog", "ram", "let",
                        "see", "zit", "maw", "nix", "ate", "gig", "rep", "owe", "ind", "hog", "eve", "sam", "zoo",
                        "any", "dow", "cod", "bed", "vet", "ham", "sis", "hex", "via", "fir", "nod", "mao", "aug",
                        "mum", "hoe", "bah", "hal", "keg", "hew", "zed", "tow", "gog", "ass", "dem", "who", "bet",
                        "gos", "son", "ear", "spy", "kit", "boy", "due", "sen", "oaf", "mix", "hep", "fur", "ada",
                        "bin", "nil", "mia", "ewe", "hit", "fix", "sad", "rib", "eye", "hop", "haw", "wax", "mid",
                        "tad", "ken", "wad", "rye", "pap", "bog", "gut", "ito", "woe", "our", "ado", "sin", "mad",
                        "ray", "hon", "roy", "dip", "hen", "iva", "lug", "asp", "hui", "yak", "bay", "poi", "yep",
                        "bun", "try", "lad", "elm", "nat", "wyo", "gym", "dug", "toe", "dee", "wig", "sly", "rip",
                        "geo", "cog", "pas", "zen", "odd", "nan", "lay", "pod", "fit", "hem", "joy", "bum", "rio",
                        "yon", "dec", "leg", "put", "sue", "dim", "pet", "yaw", "nub", "bit", "bur", "sid", "sun",
                        "oil", "red", "doc", "moe", "caw", "eel", "dix", "cub", "end", "gem", "off", "yew", "hug",
                        "pop", "tub", "sgt", "lid", "pun", "ton", "sol", "din", "yup", "jab", "pea", "bug", "gag",
                        "mil", "jig", "hub", "low", "did", "tin", "get", "gte", "sox", "lei", "mig", "fig", "lon",
                        "use", "ban", "flo", "nov", "jut", "bag", "mir", "sty", "lap", "two", "ins", "con", "ant",
                        "net", "tux", "ode", "stu", "mug", "cad", "nap", "gun", "fop", "tot", "sow", "sal", "sic",
                        "ted", "wot", "del", "imp", "cob", "way", "ann", "tan", "mci", "job", "wet", "ism", "err",
                        "him", "all", "pad", "hah", "hie", "aim", "ike", "jed", "ego", "mac", "baa", "min", "com",
                        "ill", "was", "cab", "ago", "ina", "big", "ilk", "gal", "tap", "duh", "ola", "ran", "lab",
                        "top", "gob", "hot", "ora", "tia", "kip", "han", "met", "hut", "she", "sac", "fed", "goo",
                        "tee", "ell", "not", "act", "gil", "rut", "ala", "ape", "rig", "cid", "god", "duo", "lin",
                        "aid", "gel", "awl", "lag", "elf", "liz", "ref", "aha", "fib", "oho", "tho", "her", "nor",
                        "ace", "adz", "fun", "ned", "coo", "win", "tao", "coy", "van", "man", "pit", "guy", "foe",
                        "hid", "mai", "sup", "jay", "hob", "mow", "jot", "are", "pol", "arc", "lax", "aft", "alb",
                        "len", "air", "pug", "pox", "vow", "got", "meg", "zoe", "amp", "ale", "bud", "gee", "pin",
                        "dun", "pat", "ten", "mob" })));
    }

    private static void print(List<List<String>> listList) {
        System.out.print("[");
        for (List<String> list : listList) {
            System.out.println(Arrays.toString(list.toArray()));
        }
        System.out.println("]");
    }
}
