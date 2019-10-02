package test.audio;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

public class Convert {
    public void convert(String inputFile, String outputFile, int audioCodec, int sampleRate, int audioBitrate, int audioChannels)
            throws FrameGrabber.Exception, FrameRecorder.Exception {
        Frame frame = null;
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);) {
            grabber.start();
            try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, audioChannels);) {
                frame = grabber.grab();
                if (frame != null) {
                    recorder.setAudioOption("crf", "0");
                    recorder.setAudioCodec(audioCodec);
                    recorder.setAudioBitrate(audioBitrate);
                    recorder.setAudioChannels(frame.audioChannels);
                    recorder.setSampleRate(sampleRate);
                    recorder.setAudioQuality(0);
                    recorder.setAudioOption("aq", "10");
                    recorder.start();

                    recorder.setTimestamp(frame.timestamp);
                    recorder.record(frame);

                    while ((frame = grabber.grab()) != null) {
                        recorder.setTimestamp(frame.timestamp);
                        recorder.record(frame);
                    }
                }
                recorder.stop();
                recorder.release();
            }
            grabber.flush();
            grabber.stop();
        }
    }

    public static void main(String[] args) throws Exception {
        new Convert().convert("D:/temp/test.wma", "D:/temp/test.mp3", avcodec.AV_CODEC_ID_MP3, 8000, 16, 1);
    }
}
