package test.fdr;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;

public interface FaceDR {

    /**
     * 重新加载
     */
    public void reload();

    /**
     * 检测人脸
     * 
     * @param mat 图片阵列
     * @return 人脸矩形框
     */
    public Rect detect(Mat mat);

    /**
     * 添加一组人脸
     * 
     * @param faceMatVector 人脸图片阵列集合
     * @return 人脸对应id
     */
    public int add(MatVector faceMatVector);

    /**
     * 移除一组人脸
     * 
     * @param id 人脸对应id
     */
    public void remove(int id);

    /**
     * 识别
     * 
     * @param faceMat 人脸图片
     * @return 人脸对应id
     */
    public int recognize(Mat faceMat);
}
