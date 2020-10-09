package hades.itext.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.PdfImageObject.ImageBytesType;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class RenderInfoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RenderInfoUtils.class);

    public static void parse(TextRenderInfo textRenderInfo) {
        PdfString pdfString = textRenderInfo.getPdfString();
        try {
            LOGGER.info("text0 = {}", new String(pdfString.getBytes(), pdfString.getEncoding()));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("", e);
        }
        
        String text = textRenderInfo.getText();
        LOGGER.info("text = {}", text);

        Rectangle rectangle = textRenderInfo.getBaseline().getBoundingRectange().getBounds();
        LOGGER.info("x = {}, y = {}, width = {}, height = {}, ", rectangle.x, rectangle.y, rectangle.width,
                rectangle.height);
    }

    public static void parse(ImageRenderInfo imageRenderInfo) {
        try {
            PdfImageObject pdfImageObject = imageRenderInfo.getImage();
            
            ImageBytesType imageBytesType = pdfImageObject.getImageBytesType();
            LOGGER.info("ImageBytesType = {}", imageBytesType);
            
            byte[] bs = pdfImageObject.getImageAsBytes();
            LOGGER.info("size = {}", bs.length);
            
//            BufferedImage bufferedImage = pdfImageObject.getBufferedImage();
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }
}
