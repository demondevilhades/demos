package test.jcseg;

import org.lionsoul.jcseg.test.JcsegTest;

import test.Test;

/**
 * https://github.com/lionsoul2014/jcseg
 */
public class App {

    public static void main(String[] args) throws Exception {
        for (String str : Test.TEST0) {
            JcsegTest jcsegTest = new JcsegTest();
            jcsegTest.keyphrase(str);
            jcsegTest.keywords(str);
            jcsegTest.sentence(str);
            jcsegTest.summary(str);
            jcsegTest.tokenize(str);
        }
    }
}
