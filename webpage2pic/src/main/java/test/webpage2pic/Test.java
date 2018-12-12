package test.webpage2pic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicEditorPaneUI;

import org.apache.commons.io.IOUtils;

import test.webpage2pic.util.BufferedFileReader;

public class Test {
    private static final Color DEF_COLOR = new Color(0xffffff);
    private static final EmptyBorder DEF_EB = new EmptyBorder(0, 50, 0, 50);

    public static boolean paintPage(Graphics g, int hPage, int pageIndex, JTextPane panel) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = ((BasicEditorPaneUI) panel.getUI()).getPreferredSize(panel);
        double panelHeight = d.height;
        double pageHeight = hPage;
        int totalNumPages = (int) Math.ceil(panelHeight / pageHeight);
        g2.translate(0f, -(pageIndex - 1) * pageHeight);
        panel.paint(g2);
        return pageIndex < totalNumPages;
    }

    public static void html2jpeg(String htmlFileStr, String imgFileStr, int width, int height, Color bgColor, EmptyBorder eb) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedFileReader bfr = new BufferedFileReader(htmlFileStr)) {
            String line = bfr.readLine();
            while (line != null) {
                sb.append(line);
                line = bfr.readLine();
            }
        }
        String html = sb.toString();

        JTextPane tp = new JTextPane();
        tp.setContentType("text/html");
        tp.setText(html);
        if (eb == null) {
            eb = DEF_EB;
        }
        tp.setBorder(eb);
        if (bgColor != null) {
            tp.setBackground(bgColor);
        } else {
            tp.setBackground(DEF_COLOR);
        }
        if (width <= 0) {
            width = (int) Math.ceil(tp.getPreferredSize().getWidth());
        }
        if (height <= 0) {
            height = (int) Math.ceil(tp.getPreferredSize().getHeight());
        }
        tp.setSize(width, height);

        int pageIndex = 1;
        boolean bcontinue = true;
        byte[] bytes = null;
        while (bcontinue) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setClip(0, 0, width, height);
            bcontinue = paintPage(g, height, pageIndex, tp);
            g.dispose();
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    FileOutputStream fos = new FileOutputStream(imgFileStr.replace("@pageIndex@", String.valueOf(pageIndex)));) {
                ImageIO.write(image, "jpg", baos);
                baos.flush();
                bytes = baos.toByteArray();
                IOUtils.write(bytes, fos);
            }
            pageIndex++;
        }
    }
}
