package test.canvas;

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.equalizeHist;

import java.io.File;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import test.util.ResourcesUtils;

public class FaceDetection {
    private final CascadeClassifier cascade;
    private final Size minSize;
    private final Size maxSize;

    private final double scaleFactor = 1.1;
    private final int minNeighbors = 3;
    private final int flags = 0;

    public FaceDetection(boolean debug, String ccFilename, int minWidth, int minHeight, int maxWidth, int maxHeight) {
        System.setProperty("org.bytedeco.javacpp.logger.debug", String.valueOf(debug));
        if (ccFilename == null) {
            // https://github.com/opencv/opencv/tree/master/data/haarcascades
            ccFilename = "haarcascade_frontalface_default.xml";
        }
        File ccFile = new File(ResourcesUtils.getResourceFile(ccFilename));
        if (!ccFile.exists()) {
            throw new RuntimeException("ccFile not found : " + ccFile.getPath());
        }
        cascade = new CascadeClassifier(ccFile.getPath());
        minSize = (minWidth > 0 && minHeight > 0) ? new Size(minWidth, minHeight) : null;
        maxSize = (maxWidth > 0 && maxHeight > 0) ? new Size(maxWidth, maxHeight) : null;
    }

    public Rect[] detect(Mat inputMat) {
        Mat grayMat = new Mat();
        cvtColor(inputMat, grayMat, COLOR_BGRA2GRAY);// 灰度化
        equalizeHist(grayMat, grayMat);// 均衡化直方图
        RectVector faces = new RectVector();
        cascade.detectMultiScale(grayMat, faces, scaleFactor, minNeighbors, flags, minSize, maxSize);
        return faces.get();
    }
}
