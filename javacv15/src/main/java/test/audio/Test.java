package test.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

import org.bytedeco.javacv.FrameRecorder;

public class Test {

    public void run() throws LineUnavailableException {
        AudioFormat audioFormat = new AudioFormat(44100.0F, 16, 2, true, false);
        System.out.println("start");
        Mixer.Info[] minfoSet = AudioSystem.getMixerInfo();
        for (Mixer.Info minfo : minfoSet) {
            System.out.println(minfo.getName());
        }
        System.out.println();
        // 通过AudioSystem获取本地音频混合器
        Mixer mixer = AudioSystem.getMixer(minfoSet[0]);
        // 通过设置好的音频编解码器获取数据线信息
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

        // 打开并开始捕获音频
        // 通过line可以获得更多控制权
        // 获取设备：TargetDataLine line
        // =(TargetDataLine)mixer.getLine(dataLineInfo);
        try (Line dataline = AudioSystem.getLine(dataLineInfo);) {

            TargetDataLine line = (TargetDataLine) dataline;
            line.open(audioFormat);

            line.start();
            System.out.println("已经开启音频！");
            // 获得当前音频采样率
            int sampleRate = (int) audioFormat.getSampleRate();
            // 获取当前音频通道数量
            int numChannels = audioFormat.getChannels();
            // 初始化音频缓冲区(size是音频采样率*通道数)
            int audioBufferSize = sampleRate * numChannels;
            byte[] audioBytes = new byte[audioBufferSize];

            new Thread() {
                ShortBuffer sBuff = null;
                int nBytesRead;
                int nSamplesRead;

                @Override
                public void run() {
                    System.out.println("读取音频数据...");
                    // 非阻塞方式读取
                    nBytesRead = line.read(audioBytes, 0, line.available());
                    // 因为我们设置的是16位音频格式,所以需要将byte[]转成short[]
                    nSamplesRead = nBytesRead / 2;
                    short[] samples = new short[nSamplesRead];
                    /**
                     * ByteBuffer.wrap(audioBytes)-将byte[]数组包装到缓冲区
                     * ByteBuffer.order(ByteOrder)-按little-endian修改字节顺序，解码器定义的
                     * ByteBuffer.asShortBuffer()-创建一个新的short[]缓冲区
                     * ShortBuffer.get(samples)-将缓冲区里short数据传输到short[]
                     */
                    ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(samples);
                    // 将short[]包装到ShortBuffer
                    sBuff = ShortBuffer.wrap(samples, 0, nSamplesRead);
                    // 按通道录制shortBuffer
//                    try {
//                        System.out.println("录制音频数据...");
//                        recorder.recordSamples(sampleRate, numChannels, sBuff);
//                    } catch (FrameRecorder.Exception e) {
//                        e.printStackTrace();
//                    }
                }

                @Override
                protected void finalize() throws Throwable {
                    sBuff.clear();
                    sBuff = null;
                    super.finalize();
                }
            }.start();
        } catch (LineUnavailableException e) {
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        new Test().run();
    }
}
