package test.util;

import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;

public class MatUtils {

    public static final void pushInt(Mat mat, int i) {
        mat.push_back(new Mat(new int[] { i }));
    }
    
    public static final Mat read(String imgPath) {
        return imread(imgPath, IMREAD_COLOR);
    }

    public static final void write(String imgPath, Mat mat) {
        imwrite(imgPath, mat);
    }

    public static final void write(Mat mat, Rect rect, Scalar scalar, int thickness) {
        rectangle(mat, rect, scalar, thickness, 1, 0);
    }
}
