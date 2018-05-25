package test.nlp.dict.stopword;

import static com.google.common.collect.Sets.newHashSet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import test.nlp.common.Term;
import test.nlp.dict.Dictionary;
import test.nlp.dict.pos.POS;

/**
 * 停用词典
 */
public class StopwordDictionary extends Dictionary {

    public static final String PATH_STOPWORD = "stopword";

    public static final Set<POS> FILTERED_POSES = Sets.newHashSet(POS.w, POS.x, POS.e, POS.o, POS.y, POS.u);

    public static final StopwordDictionary NULL = new StopwordDictionary(new HashSet<String>()) {
        public void filter(List<Term> terms) {
            return;
        }
    };

    /**
     * 停用词集合
     */
    private Set<String> stopwords;

    /**
     * 需要过滤的词性
     */
    private Set<POS> filteredPOSes;

    public StopwordDictionary() {
        this.stopwords = newHashSet();
        this.filteredPOSes = newHashSet(FILTERED_POSES);
    }

    public StopwordDictionary(Set<String> stopwords) {
        this.stopwords = stopwords;
        this.filteredPOSes = newHashSet(FILTERED_POSES);
    }

    public StopwordDictionary(Set<String> stopwords, Set<POS> filteredPOSes) {
        this(stopwords);
        this.filteredPOSes.addAll(filteredPOSes);
    }

    public void add(String stopword) {
        this.stopwords.add(stopword);
    }

    public void filter(List<Term> terms) {
        Iterator<Term> iterator = terms.iterator();
        while (iterator.hasNext()) {
            if (isStopword(iterator.next())) {
                iterator.remove();
            }
        }
    }

    public boolean isStopword(Term term) {
        if (this.stopwords.contains(term.getWord())) {
            return true;
        }

        POS pos = term.getPos();
        if (pos == null || pos.isBelong(filteredPOSes)) {
            return true;
        }

        return false;
    }

    public void remove(String stopword) {
        this.stopwords.remove(stopword);
    }

    @Override
    public void read(File stopwordDirectory) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(File path) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void read(InputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void write(OutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }

}
