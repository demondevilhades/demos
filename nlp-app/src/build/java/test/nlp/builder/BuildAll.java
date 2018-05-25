package test.nlp.builder;

import static com.zte.nlp.corpus.CommonBuilderExt.INPUT_LIBRARY_PATH;
import static com.zte.nlp.corpus.CommonBuilderExt.OUTPUT_LIBRARY_PATH;

import java.io.File;

import com.zte.nlp.corpus.BigramDictionaryBuilderExt;
import com.zte.nlp.corpus.CommonBuilderExt;
import com.zte.nlp.corpus.CoreDictionaryBuilderExt;
import com.zte.nlp.corpus.CustomDictionaryBuilderExt;
import com.zte.nlp.corpus.PersonDictionaryBuilderExt;
import com.zte.nlp.corpus.PlaceDictionaryBuilderExt;

/**
 * -Xmx1000m -Xmx1000m
 */
public class BuildAll {

    public void run() throws Exception {
        System.out.println("build nr");
        new PersonDictionaryBuilderExt().readAndWrite(INPUT_LIBRARY_PATH + "nr/", OUTPUT_LIBRARY_PATH + "nr/");
        System.out.println("build ns");
        new PlaceDictionaryBuilderExt().readAndWrite(INPUT_LIBRARY_PATH + "ns/", OUTPUT_LIBRARY_PATH + "ns/");
        System.out.println("build core");
        new CoreDictionaryBuilderExt().readAndWrite(INPUT_LIBRARY_PATH, OUTPUT_LIBRARY_PATH);
        System.out.println("build custom");
        new CustomDictionaryBuilderExt().readAndWrite(INPUT_LIBRARY_PATH + "custom/", OUTPUT_LIBRARY_PATH + "custom/");
        System.out.println("build bigram");
        new BigramDictionaryBuilderExt().readAndWrite(INPUT_LIBRARY_PATH, OUTPUT_LIBRARY_PATH);
        System.out.println("build med");
        new MedDictionaryBuilder().buildFromCorpus(new File(CommonBuilderExt.INPUT_LIBRARY_PATH, "med/"), new File(
                CommonBuilderExt.OUTPUT_LIBRARY_PATH, "med/"));
    }

    public static void main(String[] args) throws Exception {
        new BuildAll().run();
    }
}
