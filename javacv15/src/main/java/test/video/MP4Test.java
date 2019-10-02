package test.video;

import java.util.Map;

import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

public class MP4Test {
    private CanvasFrame canvas = new CanvasFrame("video");

    public void init() throws Exception {
        canvas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
    }

    public void run() throws FrameGrabber.Exception {
        try (FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault("D:/temp/test.mp4");) {
            grabber.start();
            System.out.println(toString(grabber));
            if (canvas.isDisplayable()) {
                Frame frame = grabber.grab();
                grabber.setImageWidth(frame.imageWidth);
                grabber.setImageHeight(frame.imageHeight);
                canvas.showImage(frame);
            }
            while (canvas.isDisplayable()) {
                Frame frame = grabber.grab();
                canvas.showImage(frame);
            }
            grabber.stop();
            canvas.dispose();
        }
        System.exit(0);
    }
    
    private String toString(FFmpegFrameGrabber grabber) {
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = grabber.getMetadata();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        sb.append("\n");
        map = grabber.getOptions();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        sb.append("\n");
        map = grabber.getAudioMetadata();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        sb.append("\n");
        map = grabber.getAudioOptions();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        sb.append("\n");
        map = grabber.getVideoMetadata();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        sb.append("\n");
        map = grabber.getVideoOptions();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        sb.append("\n");
//        sb.append("AspectRatio=").append(grabber.getAspectRatio()).append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioChannels=").append(grabber.getAudioChannels())
//        .append(", AudioCodec=").append(grabber.getAudioCodec())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate())
//        .append(", AudioBitrate=").append(grabber.getAudioBitrate());
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        new MP4Test().run();
    }
}
