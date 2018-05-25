package test.nlp.trie;

public class Item<T> {

    private String key;

    private T content;

    public Item(String key) {
        this.key = key;
    }

    public Item(String key, T content) {
        this.key = key;
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
