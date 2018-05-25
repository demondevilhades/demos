package test.nlp.ltp;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import test.nlp.common.Term;

public class DPTerms {
    /**
     * 修饰词，如定语，状语等。
     */
    private LinkedList<Term> modTerms;

    /**
     * 中心词
     */
    private Term centerTerm;

    public DPTerms(Term centerTerm, LinkedList<Term> modTerms) {
        this.centerTerm = centerTerm;
        this.modTerms = modTerms;
    }

    public int size() {
        return modTerms.size() + 1;
    }

    public Term getCenterTerm() {
        return centerTerm;
    }

    public void setCenterTerm(Term centerTerm) {
        this.centerTerm = centerTerm;
    }

    public LinkedList<Term> getModTerms() {
        return modTerms;
    }

    public void setModTerms(LinkedList<Term> modTerms) {
        this.modTerms = modTerms;
    }

    public LinkedList<Term> toTerms() {
        if (centerTerm == null) {
            return modTerms;
        }

        LinkedList<Term> terms = newLinkedList(modTerms);
        terms.add(centerTerm);
        return terms;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DPTerms)) {
            return false;

        }

        DPTerms other = (DPTerms) obj;
        if (!this.centerTerm.getWord().equals(other.centerTerm.getWord())) {
            return false;
        }

        if (this.modTerms.size() != other.modTerms.size()) {
            return false;
        }

        Iterator<Term> iterator1 = this.modTerms.iterator();
        Iterator<Term> iterator2 = other.modTerms.iterator();

        while (iterator1.hasNext()) {
            if (!(iterator1.next().getWord().equals(iterator2.next().getWord()))) {
                return false;
            }
        }
        return true;

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.centerTerm.getWord());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}