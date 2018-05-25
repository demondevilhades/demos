package test.nlp.ltp;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import test.nlp.common.Sentence;
import test.nlp.common.Term;
import test.nlp.dict.pos.POS;
import test.nlp.segment.IWordSegmenter;
import test.nlp.segment.Segmentations;
import test.nlp.segment.Segmentations.SegType;
import test.nlp.util.SentenceUtil;

import edu.hit.ir.ltp4j.Parser;
import edu.hit.ir.ltp4j.Postagger;
import edu.hit.ir.ltp4j.Segmentor;

/**
 * 本地解析类
 */
public class LTPLocalParser extends LTPBaseParser {

    private static final Set<Character> SENTENCE_SPLIT_CHARS = newHashSet(',', '，');

    private IWordSegmenter segmenter;

    public LTPLocalParser(String domain) {
        this.segmenter = Segmentations.getSegmenter(SegType.NLP, domain).enableNameRecognize(false)
                .enablePlaceRecognize(false);
    }

    @Override
    public POS getPOS(String word) {
        List<String> words = newArrayList();
        Segmentor.segment(word, words);
        if (words.size() > 1) {
            return null;
        }
        List<String> tags = newArrayListWithCapacity(words.size());
        Postagger.postag(words, tags);
        return LTPPOS.valueOf(tags.get(0)).getPOS();
    }

    @Override
    public List<LinkedList<Term>> parse(String text) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }

        List<Sentence> sentences = SentenceUtil.split(text.trim(), SENTENCE_SPLIT_CHARS);
        List<LinkedList<Term>> result = newArrayListWithExpectedSize(sentences.size());
        for (Sentence sentence : sentences) {
            LinkedList<Term> terms = parse(segmenter.segment(sentence));
            if (!terms.isEmpty()) {
                result.add(terms);
            }
        }
        return result;
    }

    @Override
    public void parse(List<String> words, List<String> tags, List<Integer> heads, List<String> deprels) {
        Parser.parse(words, tags, heads, deprels);
    }

}
