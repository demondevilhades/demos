package test.nlp.dict.nr;

import org.junit.Test;

public class PersonDictionaryTest {

    @Test
    public void test() {
        System.out.println(PersonDictionary.getInstance().getTransformMatrixDictionary().getTransitionFreq(NR.C, NR.C));
        System.out.println(PersonDictionary.getInstance().getTransformMatrixDictionary().getTransitionFreq(NR.K, NR.B));
    }
}
