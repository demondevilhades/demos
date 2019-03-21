package test.icepdf;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

public class Test {
    private final float scale = 1.0f; // 缩放比例
    private final float rotation = 0f; // 旋转角度

    public void run() {
        String filePath = "D:/test.pdf";
        Document document = new Document();
        try {
            document.setFile(filePath);
            BufferedImage image = (BufferedImage) document.getPageImage(0, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, scale);
            File file = new File("D:/test.jpg");
            ImageIO.write(image, "png", file);
            image.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.dispose();
        }
    }

    public static void main(String[] args) {
        new Test().run();
    }
}
