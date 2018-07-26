package test.hanlp;

import java.util.List;

import test.Test;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

/**
 * https://github.com/hankcs/HanLP
 */
public class App {

    public static void main(String[] args) {
        for (String str : Test.TEST0) {
            {
                List<Term> termList = StandardTokenizer.segment(str);
                System.out.println(termList);
            }
//            {
//                List<Term> termList = NLPTokenizer.segment(str);
//                System.out.println(termList);
//            }
            {
                List<Term> termList = IndexTokenizer.segment(str);
                System.out.println(termList);
            }
            {
                List<Term> termList = IndexTokenizer.segment(str);
                System.out.println(termList);
            }
        }
    }
}
