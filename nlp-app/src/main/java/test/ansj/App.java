package test.ansj;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;

import test.Test;

/**
 * https://github.com/NLPchina/ansj_seg
 */
public class App {

    public static void main(String[] args) {
        for (String str : Test.TEST0) {
            Result result = ToAnalysis.parse(str);
            System.out.println(result.getTerms());
        }
    }
}
