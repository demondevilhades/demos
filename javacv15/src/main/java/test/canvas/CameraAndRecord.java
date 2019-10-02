package test.canvas;

import javax.swing.WindowConstants;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class CameraAndRecord {

    private final long gap = 40;
    private final boolean debug = false;

    private CanvasFrame canvas = new CanvasFrame("camera");

    public void init() throws Exception {
        canvas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
    }

    public void run() throws FrameGrabber.Exception, InterruptedException, FrameRecorder.Exception {
        try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);) {
            grabber.start();
            if (canvas.isDisplayable()) {
                Frame frame = grabber.grab();
                try (FrameRecorder recorder = FrameRecorder.createDefault("D:/temp/testRecord.flv", frame.imageWidth, frame.imageHeight);) {
                    recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
                    recorder.setFormat("flv");// rtmp
                    recorder.setFrameRate(1000 / gap);
                    recorder.start();
                    record(recorder, 0, frame);
                    long l0 = System.currentTimeMillis();
                    long starttime = l0;

                    long l1 = System.currentTimeMillis();
                    while (l1 < l0 || l1 - l0 < gap) {
                        Thread.yield();
                        l1 = System.currentTimeMillis();
                    }
                    l0 = l1;
                    while (canvas.isDisplayable()) {
                        frame = grabber.grab();

                        record(recorder, (l0 - starttime) * 1000, frame);

                        canvas.showImage(frame);
                        l1 = System.currentTimeMillis();
                        while (l1 < l0 || l1 - l0 < gap) {
                            Thread.yield();
                            l1 = System.currentTimeMillis();
                        }
                        l0 = l1;
                        if (debug) {
                            System.out.println(toString(canvas));
                        }
                    }
                    recorder.stop();
                    recorder.release();
                }
            }
            grabber.stop();
        }
    }

    private void record(FrameRecorder recorder, long time, Frame frame) throws FrameRecorder.Exception {
        recorder.setTimestamp(time);
        recorder.record(frame);
    }

    private String toString(CanvasFrame canvas) {
        StringBuilder sb = new StringBuilder();
        sb.append("isActive=").append(canvas.isActive()).append(", isDisplayable=").append(canvas.isDisplayable()).append(", isEnabled=")
                .append(canvas.isEnabled()).append(", isShowing=").append(canvas.isShowing()).append(", isVisible=").append(canvas.isVisible())
                .append(", isValid=").append(canvas.isValid());
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        CameraAndRecord cameraAndD = new CameraAndRecord();
        cameraAndD.init();
        cameraAndD.run();
    }
}
