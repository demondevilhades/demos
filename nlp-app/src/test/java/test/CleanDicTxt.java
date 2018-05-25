package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class CleanDicTxt {
    
    private Set<String> set = new HashSet<String>();
    
    public void init() throws IOException{
//        File file = new File("D:/region.txt");//39
//      File file = new File("library/med/checking.txt");
//      File file = new File("library/med/disease.txt");
//      File file = new File("library/med/instruments.txt");
//      File file = new File("library/med/medicine.txt");
//      File file = new File("library/med/surgery.txt");
//      File file = new File("library/med/symptom.txt");

//      File file = new File("library/med/zy_yaocai.txt");//11157
//      File file = new File("library/med/zy_fangji.txt");//12728
//      File file = new File("library/med/zy_xuewei.txt");//712
//    File file = new File("library/med/body.txt");//500+
        File file = new File("D:/inn.txt");//5085
        
//    File file = new File("library/core.txt");
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            String line = br.readLine();
            while (line != null) {
                set.add(line);
//                set.add(line.split("\t")[0]);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public void run() throws IOException {
        init();
        File file = new File("library/med/other.txt");
        File newFile = new File("library/med/other1.txt");
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            fos = new FileOutputStream(newFile);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);

            String line = br.readLine();
            while (line != null) {
                fix(line, bw);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (bw != null) {
                    bw.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private void fix(String line, BufferedWriter bw) throws IOException {
        if(set.contains(line)){
            System.out.println(line);
        } else {
            bw.write(line);
            bw.newLine();
        }
    }

    public static void main(String[] args) throws Exception {
        new CleanDicTxt().run();
    }
}
