package test.canvas;

import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;

import test.util.MatUtils;

public class CameraAndDetector {

    private final long gap = 40;
    private final boolean debug = false;

    private CanvasFrame canvas = new CanvasFrame("camera");
    private FaceDetection fd0 = new FaceDetection(false, null, 0, 0, 0, 0);
    private OpenCVFrameConverter.ToMat converter;

    public void init() throws Exception {
        canvas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        converter = new OpenCVFrameConverter.ToMat();
    }

    public void run() throws FrameGrabber.Exception, InterruptedException {
        try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);) {
            grabber.start();
            long l0 = System.currentTimeMillis();
            while (canvas.isDisplayable()) {
                Frame frame = grabber.grab();
                
                frame = detect(frame);

                canvas.showImage(frame);
                long l1 = System.currentTimeMillis();
                while (l1 < l0 || l1 - l0 < gap) {
                    Thread.yield();
                    l1 = System.currentTimeMillis();
                }
                l0 = l1;
                if (debug) {
                    System.out.println(toString(canvas));
                }
            }
            grabber.stop();
        }
    }

    private Frame detect(Frame frame) {
        Mat mat = converter.convertToMat(frame);
        Rect[] rects = fd0.detect(mat);
        for (Rect rect : rects) {
            MatUtils.write(mat, rect, Scalar.RED, 2);
        }
        return converter.convert(mat);
    }

    private String toString(CanvasFrame canvas) {
        StringBuilder sb = new StringBuilder();
        sb.append("isActive=").append(canvas.isActive()).append(", isDisplayable=").append(canvas.isDisplayable()).append(", isEnabled=")
                .append(canvas.isEnabled()).append(", isShowing=").append(canvas.isShowing()).append(", isVisible=").append(canvas.isVisible())
                .append(", isValid=").append(canvas.isValid());
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        CameraAndDetector cameraAndD = new CameraAndDetector();
        cameraAndD.init();
        cameraAndD.run();
    }
}
