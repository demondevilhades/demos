package test.nlp.newword;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import test.nlp.common.Term;
import test.nlp.segment.IWordSegmenter;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public abstract class WordSegmenterTest {

    protected IWordSegmenter wordSegmenter = newWordSegmenter();

    protected String libraryPath;

    public void testSegmentFile() throws IOException {
        File[] files = new File("E:\\自然语言处理\\语料\\小说").listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("三国演义.txt");
            }
        });
        for (File file : files) {
            String line = null;
            try (BufferedReader reader = Files.newReader(file, Charsets.UTF_8);
                    BufferedWriter writer = Files.newWriter(new File(file.getParent(), file.getName() + ".seg"),
                            Charsets.UTF_8);) {

                while ((line = reader.readLine()) != null) {
                    if (StringUtils.isBlank(line)) {
                        continue;
                    }
                    List<Term> terms = wordSegmenter.segment(line);
                    writer.write(terms.toString());
                    writer.newLine();
                }
            }
        }
    }

    protected abstract IWordSegmenter newWordSegmenter();
}
