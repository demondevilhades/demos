package test.canvas;

import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Camera {

    private final long gap = 40;
    private final boolean debug = false;

    public void run() throws FrameGrabber.Exception, InterruptedException {
        try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);) {
            // try (VideoInputFrameGrabber grabber =
            // VideoInputFrameGrabber.createDefault(0);) {
            grabber.start();
            CanvasFrame canvas = new CanvasFrame("camera");
            canvas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            canvas.setAlwaysOnTop(true);
            long l0 = System.currentTimeMillis();
            while (canvas.isDisplayable()) {
                canvas.showImage(grabber.grab());
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

    private String toString(CanvasFrame canvas) {
        StringBuilder sb = new StringBuilder();
        sb.append("isActive=").append(canvas.isActive()).append(", isDisplayable=").append(canvas.isDisplayable()).append(", isEnabled=")
                .append(canvas.isEnabled()).append(", isShowing=").append(canvas.isShowing()).append(", isVisible=").append(canvas.isVisible())
                .append(", isValid=").append(canvas.isValid());
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        new Camera().run();
    }
}
