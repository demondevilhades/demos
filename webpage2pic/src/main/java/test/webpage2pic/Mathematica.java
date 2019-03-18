package test.webpage2pic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Mathematica {

    public Mathematica() {
    }

    public void mosaic() throws IOException {
        String picPath = "D:/temp/answer_detail.png";
        BufferedImage image = ImageIO.read(new File(picPath));
        BufferedImage imageNew = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int blockSize = 10;
        int[][] blockArr = new int[image.getWidth() / blockSize + 1][image.getHeight() / blockSize + 1];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int a = x - (x % blockSize);
                int b = y - (y % blockSize);
                int blockArrX = x / blockSize;
                int blockArrY = y / blockSize;
                if (a == x && b == y) {
                    blockArr[blockArrX][blockArrY] = 0;
                    for (int i = a, w = a + blockSize; i < w; i++) {
                        for (int j = b, h = b + blockSize; j < h; j++) {
                            blockArr[blockArrX][blockArrY] += image.getRGB(x, y);
                        }
                    }
                    blockArr[blockArrX][blockArrY] /= (blockSize * blockSize);
                }
                imageNew.setRGB(x, y, blockArr[blockArrX][blockArrY]);
            }
        }
        ImageIO.write(imageNew, "png", new File("D:/temp/answer_detail_new.png"));
    }

    /**
     * TODO there is a bug
     * 
     * @throws IOException
     */
    public void test1() throws IOException {
        int int_f = Integer.MAX_VALUE;
        String picPath = "D:/temp/answer_detail.png";
        BufferedImage image = ImageIO.read(new File(picPath));

        int[][] rgb00 = new int[0][];
        int[][] rgb01 = new int[0][];
        int[][] rgb10 = new int[0][];
        int[][] rgb11 = new int[0][];

        {
            int x_;
            int y_;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    Coordinate c = change(new Coordinate(x, y), 1, 1.5);
                    if (c.x >= 0) {
                        if (c.y >= 0) {
                            x_ = (int) Math.abs(c.x);
                            y_ = (int) Math.abs(c.y);
                            if (rgb00.length <= x_) {
                                rgb00 = Arrays.copyOf(rgb00, x_ + 1);
                            }
                            if (rgb00[x_] == null) {
                                rgb00[x_] = new int[y_ + 1];
                                Arrays.fill(rgb00[x_], int_f);
                            }
                            if (rgb00[x_].length < y_ + 1) {
                                int a = rgb00[x_].length;
                                rgb00[x_] = Arrays.copyOf(rgb00[x_], y_ + 1);
                                Arrays.fill(rgb00[x_], a, y_ + 1, int_f);
                            }
                            rgb00[x_][y_] = image.getRGB(x, y);
                        } else {
                            x_ = (int) Math.abs(c.x);
                            y_ = (int) Math.abs(c.y);
                            if (rgb01.length <= x_) {
                                rgb01 = Arrays.copyOf(rgb01, x_ + 1);
                            }
                            if (rgb01[x_] == null) {
                                rgb01[x_] = new int[y_ + 1];
                                Arrays.fill(rgb01[x_], int_f);
                            }
                            if (rgb01[x_].length < y_ + 1) {
                                int a = rgb01[x_].length;
                                rgb01[x_] = Arrays.copyOf(rgb01[x_], y_ + 1);
                                Arrays.fill(rgb01[x_], a, y_ + 1, int_f);
                            }
                            rgb01[x_][y_] = image.getRGB(x, y);
                        }
                    } else {
                        if (c.y >= 0) {
                            x_ = (int) Math.abs(c.x);
                            y_ = (int) Math.abs(c.y);
                            if (rgb10.length <= x_) {
                                rgb10 = Arrays.copyOf(rgb10, x_ + 1);
                            }
                            if (rgb10[x_] == null) {
                                rgb10[x_] = new int[y_ + 1];
                                Arrays.fill(rgb10[x_], int_f);
                            }
                            if (rgb10[x_].length < y_ + 1) {
                                int a = rgb10[x_].length;
                                rgb10[x_] = Arrays.copyOf(rgb10[x_], y_ + 1);
                                Arrays.fill(rgb10[x_], a, y_ + 1, int_f);
                            }
                            rgb10[x_][y_] = image.getRGB(x, y);
                        } else {
                            x_ = (int) Math.abs(c.x);
                            y_ = (int) Math.abs(c.y);
                            if (rgb11.length <= x_) {
                                rgb11 = Arrays.copyOf(rgb11, x_ + 1);
                            }
                            if (rgb11[x_] == null) {
                                rgb11[x_] = new int[y_ + 1];
                                Arrays.fill(rgb11[x_], int_f);
                            }
                            if (rgb11[x_].length < y_ + 1) {
                                int a = rgb11[x_].length;
                                rgb11[x_] = Arrays.copyOf(rgb11[x_], y_ + 1);
                                Arrays.fill(rgb11[x_], a, y_ + 1, int_f);
                            }
                            rgb11[x_][y_] = image.getRGB(x, y);
                        }
                    }
                }
            }
        }
        int rgb00_h = 0;
        int rgb01_h = 0;
        int rgb10_h = 0;
        int rgb11_h = 0;
        for (int[] is : rgb00) {
            if (is != null) {
                rgb00_h = Math.max(rgb00_h, is.length);
            }
        }
        System.out.println("====================================================");
        for (int[] is : rgb01) {
            if (is != null) {
                rgb01_h = Math.max(rgb01_h, is.length);
            }
        }
        System.out.println("====================================================");
        for (int[] is : rgb10) {
            if (is != null) {
                rgb10_h = Math.max(rgb10_h, is.length);
            }
        }
        System.out.println("====================================================");
        for (int[] is : rgb11) {
            if (is != null) {
                rgb11_h = Math.max(rgb11_h, is.length);
            }
        }
        System.out.println("====================================================");

        int rgb00_w = rgb00.length;
        int rgb01_w = rgb01.length;
        int rgb10_w = rgb10.length;
        int rgb11_w = rgb11.length;

        for (int i = 0; i < rgb11.length; i++) {
            int[] arr_new = new int[rgb11_h];
            if (rgb11[i] != null) {
                for (int j = 0; j < rgb11[i].length; j++) {
                    int rgb = rgb11[i][j];
                    if (rgb == int_f) {
                        rgb = 0;
                        int count = 0;
                        if (i > 0) {
                            rgb += rgb11[i - 1][j];
                            count++;
                        }
                        if (j > 0) {
                            rgb += rgb11[i][j - 1];
                            count++;
                        }
                        if (count > 1) {
                            rgb /= count;
                        }
                    }
                    arr_new[j] = rgb;
                }
            }
            rgb11[i] = arr_new;
        }
        for (int i = 0; i < rgb01.length; i++) {
            int[] arr_new = new int[rgb01_h];
            if (rgb01[i] != null) {
                for (int j = 0; j < rgb01[i].length; j++) {
                    int rgb = rgb01[i][j];
                    if (rgb == int_f) {
                        rgb = 0;
                        int count = 0;
                        if (i > 0) {
                            rgb += rgb01[i - 1][j];
                            count++;
                        }
                        if (j > 0) {
                            rgb += rgb01[i][j - 1];
                            count++;
                        }
                        if (count > 1) {
                            rgb /= count;
                        }
                    }
                    arr_new[j] = rgb;
                }
            }
            rgb01[i] = arr_new;
        }
        for (int i = 0; i < rgb10.length; i++) {
            int[] arr_new = new int[rgb10_h];
            if (rgb10[i] != null) {
                for (int j = 0; j < rgb10[i].length; j++) {
                    int rgb = rgb10[i][j];
                    if (rgb == int_f) {
                        rgb = 0;
                        int count = 0;
                        if (i > 0) {
                            rgb += rgb10[i - 1][j];
                            count++;
                        }
                        if (j > 0) {
                            rgb += rgb10[i][j - 1];
                            count++;
                        }
                        if (count > 1) {
                            rgb /= count;
                        }
                    }
                    arr_new[j] = rgb;
                }
            }
            rgb10[i] = arr_new;
        }
        for (int i = 0; i < rgb00.length; i++) {
            int[] arr_new = new int[rgb00_h];
            if (rgb00[i] != null) {
                for (int j = 0; j < rgb00[i].length; j++) {
                    int rgb = rgb00[i][j];
                    if (rgb == int_f) {
                        rgb = 0;
                        int count = 0;
                        if (i > 0) {
                            rgb += rgb00[i - 1][j];
                            count++;
                        }
                        if (j > 0) {
                            rgb += rgb00[i][j - 1];
                            count++;
                        }
                        if (count > 1) {
                            rgb /= count;
                        }
                    }
                    arr_new[j] = rgb;
                }
            }
            rgb00[i] = arr_new;
        }

        int max0X_w = Math.max(rgb00_w, rgb01_w);
        int max1X_w = Math.max(rgb10_w, rgb11_w);
        int maxX0_h = Math.max(rgb00_h, rgb10_h);
        int maxX1_h = Math.max(rgb01_h, rgb11_h);
        System.out.println(max0X_w);
        System.out.println(max1X_w);
        System.out.println(maxX0_h);
        System.out.println(maxX1_h);
        System.out.println("====================================================");

        BufferedImage imageNew = new BufferedImage(max0X_w + max1X_w, maxX0_h + maxX1_h, image.getType());
        System.out.println(imageNew.getWidth());
        System.out.println(imageNew.getHeight());
        System.out.println("====================================================");
        for (int i = 0; i < rgb11.length; i++) {
            for (int j = 0; j < rgb11[i].length; j++) {
                imageNew.setRGB(max1X_w - i, maxX1_h - j, rgb11[i][j]);
            }
        }
        System.out.println(rgb01.length);
        for (int i = 0; i < rgb01.length; i++) {
            for (int j = 0; j < rgb01[i].length; j++) {
                imageNew.setRGB(max1X_w + i, maxX1_h - j, rgb01[i][j]);
            }
        }
        System.out.println(rgb10.length);
        for (int i = 0; i < rgb10.length; i++) {
            for (int j = 0; j < rgb10[i].length; j++) {
                imageNew.setRGB(max1X_w - i, maxX1_h + j, rgb10[i][j]);
            }
        }
        for (int i = 0; i < rgb00.length; i++) {
            for (int j = 0; j < rgb00[i].length; j++) {
                imageNew.setRGB(max1X_w + i, maxX1_h + j, rgb00[i][j]);
            }
        }
        ImageIO.write(imageNew, "png", new File("D:/temp/answer_detail_new.png"));
    }

    private Coordinate change(Coordinate c, double r_m, double f_m) {
        Coordinate c_new = new Coordinate(0, 0);
        double r = Math.sqrt(c.x * c.x + c.y * c.y) * r_m;
        double f = Math.atan2(c.y, c.x) * f_m;
        c_new.x = r * Math.cos(f);
        c_new.y = r * Math.sin(f);
        return c_new;
    }

    static class Coordinate {
        double x;
        double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {
        new Mathematica().test1();
    }
}
