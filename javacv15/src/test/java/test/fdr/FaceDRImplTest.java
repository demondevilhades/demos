package test.fdr;

import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

import java.io.File;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.junit.Before;
import org.junit.Test;

public class FaceDRImplTest {
    private final String rootPath = "D:/temp/faceTestImg/med/";
    private final String trainPath = rootPath + "train/";
    private final String facePath = rootPath + "faces/";
    private final String testPath = rootPath + "test/";

    FaceDR faceDR;

    @Before
    public void before() throws Exception {
        faceDR = FaceDRImpl.getFromProp("app");
        faceDR.reload();
    }

    @Test
    public void testAll() {
        // rename(trainPath);
        // detect(testPath);
        // train(trainPath);
         test(testPath);
    }

    private void rename(String inputPath) {
        File[] ppFiles = new File(inputPath).listFiles();
        for (File ppFile : ppFiles) {
            if (ppFile.isDirectory()) {
                File[] imgFiles = ppFile.listFiles();
                int i = 1;
                for (File imgFile : imgFiles) {
                    File dFile = new File(ppFile, ppFile.getName() + "-" + i + ".jpg");
                    imgFile.renameTo(dFile);
                    i++;
                }
            }
        }
    }

    private void detect(String inputPath) {
        File[] ppFiles = new File(inputPath).listFiles();
        int sum = 0;
        for (File ppFile : ppFiles) {
            if (ppFile.isDirectory()) {
                String pfPath = facePath + ppFile.getName() + "/";
                File pfFile = new File(pfPath);
                pfFile.delete();
                pfFile.mkdirs();
                File[] imgFiles = ppFile.listFiles();
                for (File imgFile : imgFiles) {
                    Mat inputMat = imread(imgFile.getPath(), IMREAD_COLOR);
                    Rect rect0 = faceDR.detect(inputMat);
                    if (rect0 != null) {
                        imwrite(facePath + ppFile.getName() + "/" + imgFile.getName(), new Mat(inputMat, rect0));
                    } else {
                        System.err.println("input=" + imgFile.getPath());
                    }
                }
                System.out.println(imgFiles.length);
                sum += imgFiles.length;
            }
        }
        System.out.println(sum);
    }

    private void train(String inputPath) {
        File[] ppFiles = new File(inputPath).listFiles();
        for (File ppFile : ppFiles) {
            if (ppFile.isDirectory()) {
                File[] imgFiles = ppFile.listFiles();
                MatVector images = new MatVector();
                for (File imgFile : imgFiles) {
                    Mat inputMat = imread(imgFile.getPath(), IMREAD_COLOR);
                    Rect rect0 = faceDR.detect(inputMat);
                    if (rect0 != null) {
                        images.push_back(new Mat(inputMat, rect0));
                    } else {
                        System.err.println("input=" + imgFile.getPath());
                    }
                }
                faceDR.add(images);
            }
        }
    }

    private void test(String inputPath) {
        File[] ppFiles = new File(inputPath).listFiles();
        for (File ppFile : ppFiles) {
            if (ppFile.isDirectory()) {
                File[] imgFiles = ppFile.listFiles();
                for (File imgFile : imgFiles) {
                    Mat inputMat = imread(imgFile.getPath(), IMREAD_COLOR);
                    Rect rect0 = faceDR.detect(inputMat);
                    if (rect0 != null) {
                        int id = faceDR.recognize(new Mat(inputMat, rect0));
                        String r = String.valueOf((char)('A' - 1 + id));
                        if(ppFile.getName().equals(r)) {
                            System.out.println(imgFile.getPath() + "\t" + (char)('A' - 1 + id));
                        } else {
                            System.err.println(imgFile.getPath() + "\t" + (char)('A' - 1 + id));
                        }
                    } else {
                        System.err.println("input=" + imgFile.getPath());
                    }
                }
            }
        }
    }
}
