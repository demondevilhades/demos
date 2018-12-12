package test.webpage2pic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import gui.ava.html.Html2Image;
import test.webpage2pic.util.BufferedFileReader;

public class Test2 {

    public static void html2png(String htmlFileStr, String imgFileStr, String bgPng) throws Exception {
        try (BufferedFileReader bfr = new BufferedFileReader(htmlFileStr)) {
            StringBuilder sb = new StringBuilder();
            sb.append("<body style='background-image:url(");
            sb.append(bgPng);
            sb.append(");'>");
            String line = bfr.readLine();
            while (line != null) {
                sb.append(line);
                line = bfr.readLine();
            }
            sb.append("</body>");
            Html2Image.fromHtml(sb.toString()).getImageRenderer().saveImage(imgFileStr);
        }
    }

    public static void html2jpg(String htmlFileStr, String imgFileStr, String bgJpg) throws Exception {
        try (BufferedFileReader bfr = new BufferedFileReader(htmlFileStr)) {
            StringBuilder sb = new StringBuilder();
            sb.append("<body style='background-image:url(");
            sb.append(bgJpg);
            sb.append(");'>");
            String line = bfr.readLine();
            while (line != null) {
                sb.append(line);
                line = bfr.readLine();
            }
            sb.append("</body>");
            BufferedImage bufferedImage = Html2Image.fromHtml(sb.toString()).getImageRenderer().getBufferedImage(BufferedImage.TYPE_INT_RGB);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); FileOutputStream fos = new FileOutputStream(imgFileStr);) {
                ImageIO.write(bufferedImage, "jpg", baos);
                baos.flush();
                byte[] bytes = baos.toByteArray();
                IOUtils.write(bytes, fos);
            }
        }
    }
}
