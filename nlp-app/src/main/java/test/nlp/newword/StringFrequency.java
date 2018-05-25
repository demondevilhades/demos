package test.nlp.newword;

import java.util.List;

/**
 * 重复串信息
 */
public class StringFrequency {

    /**
     * array[position]为重复串在source中的开始位置，lcp[position]为重复串的长度
     */
    private int position;

    private List<Integer> positions;

    public StringFrequency(int position, List<Integer> positions) {
        this.position = position;
        this.positions = positions;

    }

    public int getPosition() {
        return position;
    }

    public int getFreq() {
        return positions.size();
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
