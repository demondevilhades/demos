package test.nlp.newword.filter;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import test.nlp.newword.CandidateWord;

public class CandidateWordFilters implements CandidateWordFilter {

    private List<CandidateWordFilter> filters;

    public CandidateWordFilters() {
        this.filters = newArrayList();
        this.filters.add(new PrefixSuffixFilter());
        this.filters.add(new DisableWordsFilter());
        this.filters.add(new POSCombineFilter());
    }

    public CandidateWordFilters(List<CandidateWordFilter> filters) {
        this.filters = filters;
    }

    public void addFilter(CandidateWordFilter filter) {
        this.filters.add(filter);
    }

    @Override
    public boolean isAccept(CandidateWord candidateWord) {
        for (CandidateWordFilter filter : filters) {
            if (!filter.isAccept(candidateWord)) {
                return false;
            }
        }
        return true;
    }
}
