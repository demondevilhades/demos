package test.ocr.tess4j;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class Test {
    
    public void run(){
        ITesseract instance = new Tesseract();
        File imageFile = new File(this.getClass().getClassLoader().getResource("test.jpg").getPath());
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        
        // https://github.com/tesseract-ocr/tessdata
        // instance.setLanguage("eng");
        instance.setLanguage("chi_sim");
        
        try {
            System.out.println(instance.doOCR(imageFile));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
    
    public void run1(){
        ITesseract instance = new Tesseract1();
        File imageFile = new File(this.getClass().getClassLoader().getResource("test.jpg").getPath());
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        
        // https://github.com/tesseract-ocr/tessdata
        instance.setLanguage("eng");
        
        try {
            System.out.println(instance.doOCR(imageFile));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.run();
        test.run1();
    }
}
