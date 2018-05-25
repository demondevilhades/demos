package test.nlp.dict;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import test.nlp.dict.pos.POS;

/**
 * 词性转移矩阵词典
 */
public class POSTransformMatrixDictionary extends TransformMatrixDictionary<POS> {

    public static final String FILE_TR_DIC = "transform_matrix.dic";

    private static volatile POSTransformMatrixDictionary instance;

    public static POSTransformMatrixDictionary getInstance() {
        if (instance == null) {
            synchronized (POSTransformMatrixDictionary.class) {
                if (instance == null) {
                    instance = new POSTransformMatrixDictionary();
                }
            }
        }
        return instance;
    }

    private POSTransformMatrixDictionary() {
        try (InputStream in = new FileInputStream(new File(libraryPath, FILE_TR_DIC));) {
            read(in);
        } catch (Exception e) {
            throw new RuntimeException("Load transform_matrix.dic error.", e);
        }
    }
}
