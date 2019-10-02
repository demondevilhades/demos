package test.fdr;

import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.equalizeHist;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.FisherFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.util.MatUtils;
import test.util.ResourcesUtils;

public class FaceDRImpl implements FaceDR {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static FaceDRImpl faceDRImpl = null;

    private final Detector fd;
    private final Recognizer fr;

    private FaceDRImpl(boolean debug, String ccFilename, int minWidth, int minHeight, int maxWidth, int maxHeight, double scaleFactor, int minNeighbors,
            String rootPath, int imageNums, double threshold, int maxSize, int width, int height, FRType frType, boolean useEqualizeHist) {
        fd = new Detector(debug, ccFilename, minWidth, minHeight, maxWidth, maxHeight, scaleFactor, minNeighbors);
        fr = new Recognizer(rootPath, imageNums, threshold, maxSize, width, height, frType, useEqualizeHist);
    }

    @Override
    public Rect detect(Mat mat) {
        return fd.detect(mat);
    }

    @Override
    public int add(MatVector faceMatVector) {
        return fr.addImages(faceMatVector);
    }

    @Override
    public void remove(int id) {
        fr.removeImages(id);
    }

    @Override
    public int recognize(Mat faceMat) {
        return fr.predict(faceMat);
    }

    @Override
    public void reload() {
        fr.reloadImages();
    }

    public static FaceDR getFromProp(String propName) throws Exception {
        Properties prop = ResourcesUtils.load(propName);
        boolean debug = Boolean.valueOf(prop.getProperty("debug", "false"));
        String ccFilename = prop.getProperty("ccFilename");
        int minWidth = Integer.parseInt(prop.getProperty("minWidth", "0"));
        int minHeight = Integer.parseInt(prop.getProperty("minHeight", "0"));
        int maxWidth = Integer.parseInt(prop.getProperty("maxWidth", "0"));
        int maxHeight = Integer.parseInt(prop.getProperty("maxHeight", "0"));
        double scaleFactor = Double.parseDouble(prop.getProperty("scaleFactor", "1.1"));
        int minNeighbors = Integer.parseInt(prop.getProperty("minNeighbors", "3"));

        String rootPath = prop.getProperty("rootPath");
        int imageNums = Integer.parseInt(prop.getProperty("imageNums", "3"));
        double threshold = Double.parseDouble(prop.getProperty("threshold", "1.1"));
        int maxSize = Integer.parseInt(prop.getProperty("maxSize", "0"));
        int width = Integer.parseInt(prop.getProperty("width", "0"));
        int height = Integer.parseInt(prop.getProperty("height", "0"));
        FRType frType = FRType.valueOf(prop.getProperty("frType", "EigenFace"));
        boolean useEqualizeHist = Boolean.valueOf(prop.getProperty("useEqualizeHist", "false"));

        return get(debug, ccFilename, minWidth, minHeight, maxWidth, maxHeight, scaleFactor, minNeighbors, rootPath, imageNums, threshold, maxSize, width,
                height, frType, useEqualizeHist);
    }

    /**
     * 
     * @param debug           调试信息
     * @param ccFilename      检测数据文件
     * @param minWidth        检测最小宽度
     * @param minHeight       检测最小高度
     * @param maxWidth        检测最大宽度
     * @param maxHeight       检测最大高度
     * @param scaleFactor
     * @param minNeighbors
     * @param rootPath        图片存储根目录
     * @param imageNums       识别每组图片数
     * @param threshold       识别阈值
     * @param maxSize         识别最大分组
     * @param width           平均脸宽度
     * @param height          平均脸高度
     * @param frType          识别算法
     * @param useEqualizeHist 使用均衡直方
     * @return
     */
    public static FaceDR get(boolean debug, String ccFilename, int minWidth, int minHeight, int maxWidth, int maxHeight, double scaleFactor, int minNeighbors,
            String rootPath, int imageNums, double threshold, int maxSize, int width, int height, FRType frType, boolean useEqualizeHist) {
        if (faceDRImpl == null) {
            synchronized (FaceDRImpl.class) {
                if (faceDRImpl == null) {
                    faceDRImpl = new FaceDRImpl(debug, ccFilename, minWidth, minHeight, maxWidth, maxHeight, scaleFactor, minNeighbors, rootPath, imageNums,
                            threshold, maxSize, width, height, frType, useEqualizeHist);
                }
            }
        }
        return faceDRImpl;
    }

    private class Detector {
        private final CascadeClassifier cascade;
        private final Size minSize;
        private final Size maxSize;

        private final double scaleFactor;
        private final int minNeighbors;
        private final int flags = 0;

        Detector(boolean debug, String ccFilename, int minWidth, int minHeight, int maxWidth, int maxHeight, double scaleFactor, int minNeighbors) {
            System.setProperty("org.bytedeco.javacpp.logger.debug", String.valueOf(debug));
            if (ccFilename == null || "".equals(ccFilename)) {
                ccFilename = "haarcascade_frontalface_default.xml";
            }
            File ccFile = new File(ResourcesUtils.getResourceFile(ccFilename));
            if (!ccFile.exists()) {
                throw new RuntimeException("ccFile not found : " + ccFile.getPath());
            }
            this.cascade = new CascadeClassifier(ccFile.getPath());
            this.minSize = (minWidth > 0 && minHeight > 0) ? new Size(minWidth, minHeight) : null;
            this.maxSize = (maxWidth > 0 && maxHeight > 0) ? new Size(maxWidth, maxHeight) : null;
            this.scaleFactor = scaleFactor;
            this.minNeighbors = minNeighbors;
        }

        Rect detect(Mat inputMat) {
            Mat mat = new Mat();
            cvtColor(inputMat, mat, COLOR_BGRA2GRAY);// 灰度化
            equalizeHist(mat, mat);// 均衡化直方图
            RectVector faces = new RectVector();
            cascade.detectMultiScale(mat, faces, scaleFactor, minNeighbors, flags, minSize, maxSize);

            Rect rect0 = null;
            Rect[] rects = faces.get();
            for (Rect rect : rects) {
                if (rect0 == null || rect.width() * rect.height() > rect0.width() * rect0.height()) {
                    rect0 = rect;
                }
            }
            return rect0;
        }
    }

    private class Recognizer {
        private final double threshold;
        @SuppressWarnings("unused")
        private final String rootPath;
        private final String facePath;

        private final Size reSize;
        private final boolean useEqualizeHist;

        private FaceRecognizer jcvfr;
        private final Map<Integer, Integer> indexIdMap = new HashMap<>();
        private final Map<Integer, String> indexPathMap = new HashMap<>();
        private final Set<Integer> idSet = new HashSet<>();

        private final int imageNums;
        private final int maxSize;
        private final FRType frType;

        private int index = 1;
        private boolean trained = false;

        private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final ReadLock rLock = rwLock.readLock();
        private final WriteLock wLock = rwLock.writeLock();

        Recognizer(String rootPath, int imageNums, double threshold, int maxSize, int width, int height, FRType frType, boolean useEqualizeHist) {
            this.rootPath = rootPath;
            this.facePath = rootPath + "faces/";
            File faceFile = new File(facePath);
            if (!faceFile.exists()) {
                faceFile.mkdirs();
            }

            this.imageNums = imageNums;
            this.threshold = threshold;
            this.maxSize = maxSize;
            this.useEqualizeHist = useEqualizeHist;
            this.reSize = new Size(width, height);
            this.frType = frType;
        }

        int addImages(MatVector matVector) {
            if (matVector.size() != imageNums) {
                throw new FaceDRException("imageNums = " + imageNums + ", matVector.size = " + matVector.size());
            }
            int id = 1;
            try {
                wLock.lock();
                while (idSet.contains(id) && id < maxSize) {
                    id++;
                }
                if (id >= maxSize) {
                    throw new FaceDRException("too many id : " + id);
                }
                String pFileStr = facePath + id + "/";
                File pFile = new File(pFileStr);
                pFile.mkdirs();
                for (int i = 0; i < imageNums; i++) {
                    String imagePath = pFileStr + id + "-" + i + ".jpg";
                    Mat faceMat = matVector.get(i);
                    imwrite(imagePath, faceMat);
                }
                reloadImages();
                return id;
            } catch (Throwable e) {
                if (id > 0 && id < maxSize) {
                    removeImages(id);
                }
                throw e;
            } finally {
                wLock.unlock();
            }
        }

        void reloadImages() {
            try {
                wLock.lock();
                File[] pFiles = new File(facePath).listFiles();
                MatVector images = new MatVector();
                Mat labels = new Mat();

                indexIdMap.clear();
                indexPathMap.clear();
                idSet.clear();

                index = 1;
                for (File pFile : pFiles) {
                    if (pFile.isDirectory()) {
                        int id = Integer.parseInt(pFile.getName());
                        File[] imgFiles = pFile.listFiles();
                        for (File imgFile : imgFiles) {
                            String imagePath = imgFile.getPath();
                            Mat faceMat = imread(imagePath, IMREAD_COLOR);
                            loadFaceMat(faceMat, index, id, imagePath, images, labels);
                            if (logger.isDebugEnabled()) {
                                logger.debug(imagePath + ",\tindex = " + index + ",\tid = " + id);
                            }
                            index++;
                        }
                        idSet.add(id);
                    }
                }
                if (images.size() > 0) {
                    if (jcvfr != null) {
                        jcvfr.clear();
                        jcvfr.deallocate();
                        jcvfr.close();
                    }
                    if (frType == FRType.FisherFace) {
                        this.jcvfr = FisherFaceRecognizer.create();
                    } else {
                        this.jcvfr = EigenFaceRecognizer.create();
                    }
                    if (threshold > 0) {
                        this.jcvfr.setThreshold(threshold);
                    }
                    jcvfr.train(images, labels);
                    trained = true;
                }
            } catch (Throwable e) {
                throw e;
            } finally {
                wLock.unlock();
            }
        }

        void removeImages(int id) {
            try {
                wLock.lock();
                if (idSet.contains(id)) {
                    new File(facePath + id + "/").delete();
                    reloadImages();
                }
            } catch (Throwable e) {
                throw e;
            } finally {
                wLock.unlock();
            }
        }

        private void loadFaceMat(Mat faceMat, int tIndex, int id, String imagePath, MatVector images, Mat labels) {
            Mat mat = align(faceMat);
            images.push_back(mat);
            indexIdMap.put(tIndex, id);
            indexPathMap.put(tIndex, imagePath);
            MatUtils.pushInt(labels, tIndex);
        }

        private Mat align(Mat faceMat) {
            Mat mat = new Mat();
            cvtColor(faceMat, mat, COLOR_BGRA2GRAY);// 灰度化
            if (useEqualizeHist) {
                equalizeHist(mat, mat);
            }
            resize(mat, mat, reSize);
            return mat;
        }

        int predict(Mat faceMat) {
            if (trained != true) {
                return -2;
            }
            try {
                rLock.lock();
                Mat mat = align(faceMat);
                int label = jcvfr.predict_label(mat);
                Integer integer = indexIdMap.get(label);
                if (logger.isDebugEnabled()) {
                    double[] confidence = new double[1];
                    int[] labelArr = new int[1];
                    jcvfr.predict(mat, labelArr, confidence);
                    logger.debug("index = " + labelArr[0] + ",\tid = " + String.valueOf(integer) + ",\tconfidence = " + confidence[0]);
                }
                return integer == null ? -1 : integer;
            } finally {
                rLock.unlock();
            }
        }
    }

    public enum FRType {
        EigenFace, FisherFace
    }
}
