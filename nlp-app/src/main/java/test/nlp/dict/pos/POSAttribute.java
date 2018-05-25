package test.nlp.dict.pos;

import test.nlp.common.Attribute;

/**
 * 词性属性
 */
public class POSAttribute extends Attribute<POS> {

    private static final long serialVersionUID = 1L;

    /**
     * 是否包含数词
     */
    private transient boolean containsNumber;

    /**
     * 是否包含量词
     */
    private transient boolean containsQuantifier;

    public POSAttribute(POS pos, int freq) {
        super(pos, freq);
    }

    public POSAttribute(POS[] allPOS, int[] freqs, int totalFreq) {
        super(allPOS, freqs, totalFreq);
    }

    public POSAttribute(int size) {
        super(size);
    }

    public boolean isContainsNumber() {
        return containsNumber;
    }

    public boolean isContainsQuantifier() {
        return containsQuantifier;
    }

    public void setAttribute() {
        for (int i = 0; i < items.length; i++) {
            if (POS.m == items[i]) {
                this.containsNumber = true;
            } else if (POS.q == items[i] || POS.qt == items[i] || POS.qv == items[i]) {
                this.containsQuantifier = true;
            }
        }
    }
}
