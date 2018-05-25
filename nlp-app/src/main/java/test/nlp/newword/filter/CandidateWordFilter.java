package test.nlp.newword.filter;

import test.nlp.newword.CandidateWord;

/**
 * 候选词过滤接品
 */
public interface CandidateWordFilter {

    boolean isAccept(CandidateWord candidateWord);
}
