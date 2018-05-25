package test.nlp.ltp;

import java.io.File;

import edu.hit.ir.ltp4j.NER;
import edu.hit.ir.ltp4j.Parser;
import edu.hit.ir.ltp4j.Postagger;
import edu.hit.ir.ltp4j.SRL;
import edu.hit.ir.ltp4j.Segmentor;

public class LTPUtil {
    private static boolean isSegmentInit;

    private static boolean isPOSTaggerInit;

    private static boolean isParserInit;

    private static boolean isNERInit;

    private static boolean isSRLInit;

    public static void init(boolean useParser, boolean useSRL, String ltpDataPath) {
        init(false, useParser, useSRL, ltpDataPath);
    }

    public static void init(boolean useSegmentor, boolean useParser, boolean useSRL, String ltpDataPath) {
        if (useSegmentor) {
            initSegmentor(ltpDataPath);
            initPOSTagger(ltpDataPath);
        }

        if (useParser) {
            initParser(ltpDataPath);
        }
        if (useSRL) {
            initNER(ltpDataPath);
            initSRL(ltpDataPath);
        }
    }

    public static void release() {
        releaseSRL();
        releaseNER();
        releaseParser();
        releasePOSTagger();
        releaseSegmentor();
    }

    private static void initSegmentor(String ltpDataPath) {
        if (Segmentor.create(ltpDataPath + File.separator + "cws.model") < 0) {
            throw new RuntimeException("Load cws.model error.");
        }
        isSegmentInit = true;
    }

    private static void releaseSegmentor() {
        if (isSegmentInit) {
            Segmentor.release();
        }
    }

    private static void initPOSTagger(String ltpDataPath) {
        if (Postagger.create(ltpDataPath + File.separator + "pos.model") < 0) {
            throw new RuntimeException("Load pos.model error.");
        }
        isPOSTaggerInit = true;
    }

    private static void releasePOSTagger() {
        if (isPOSTaggerInit) {
            Postagger.release();
        }
    }

    private static void initParser(String ltpDataPath) {
        if (Parser.create(ltpDataPath + File.separator + "parser.model") < 0) {
            throw new RuntimeException("Load parser.model error.");
        }
        isParserInit = true;
    }

    private static void releaseParser() {
        if (isParserInit) {
            try {
                Parser.release();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    private static void initNER(String ltpDataPath) {
        if (NER.create(ltpDataPath + File.separator + "ner.model") < 0) {
            throw new RuntimeException("Load ner.model error.");
        }
        isNERInit = true;
    }

    private static void releaseNER() {
        if (isNERInit) {
            try {
                NER.release();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    private static void initSRL(String ltpDataPath) {
        if (SRL.create(ltpDataPath + File.separator + "srl") < 0) {
            throw new RuntimeException("Load srl error.");
        }
        isSRLInit = true;
    }

    private static void releaseSRL() {
        if (isSRLInit) {
            try {
                SRL.release();
            } catch (Exception e) {
                // ignore
            }
        }

    }

}
