package hades.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import hades.itext.util.BufferedFileWriter;
import hades.itext.util.Config;
import hades.itext.util.RichTextRenderListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public void run() {
        String src = Config.get("src");
        File pdfFile = new File(src);
        if (pdfFile.exists()) {
            File parentFile = pdfFile.getParentFile();
            String pdfFullName = pdfFile.getName();
            String pdfName = pdfFullName.substring(0, pdfFullName.lastIndexOf("."));

            try {
                PdfReader pdfReader = new PdfReader(pdfFile.getPath());
                PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
                for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                    RichTextRenderListener richTextRenderListener = new RichTextRenderListener();
                    parser.processContent(i, richTextRenderListener);

                    String txtName = pdfName + "_" + i + ".txt";
                    File txtFile = new File(parentFile, txtName);
                    List<TextRenderInfo> textRenderInfoList = richTextRenderListener.getTextRenderInfoList();
                    try (BufferedFileWriter bfr = new BufferedFileWriter(txtFile, StandardCharsets.UTF_8, false)) {
                        for (int j = 0; j < textRenderInfoList.size(); j++) {
                            TextRenderInfo textRenderInfo = textRenderInfoList.get(j);
                            bfr.writeLine(textRenderInfo.getText());
                        }
                    }

                    List<ImageRenderInfo> imageRenderInfoList = richTextRenderListener.getImageRenderInfoList();
                    for (int j = 0; j < imageRenderInfoList.size(); j++) {
                        PdfImageObject pdfImageObject = imageRenderInfoList.get(j).getImage();
                        StringBuilder imgNameSb = new StringBuilder().append(pdfName).append("_").append(i).append("_")
                                .append(j).append(".").append(pdfImageObject.getImageBytesType().getFileExtension());
                        try (FileOutputStream fos = new FileOutputStream(new File(parentFile, imgNameSb.toString()));) {
                            fos.write(pdfImageObject.getImageAsBytes());
                        }
                    }
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Config.init();
        new App().run();
    }
}
