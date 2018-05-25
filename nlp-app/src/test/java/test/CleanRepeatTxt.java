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

public class CleanRepeatTxt {
    
    private Set<String> set = new HashSet<String>();
    
    public void run() throws IOException {
        File file = new File("library/med/body.txt");
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
                fix2(line, bw);
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

    @SuppressWarnings("unused")
    private void fix(String line, BufferedWriter bw) throws IOException {
        if(set.contains(line)){
            System.out.println(line);
        } else {
            set.add(line);
            bw.write(line);
            bw.newLine();
        }
    }

    private void fix2(String line, BufferedWriter bw) throws IOException {
        if(line.length() == 1){
            System.out.println(line);
        } else {
            set.add(line);
            bw.write(line);
            bw.newLine();
        }
    }

    public static void main(String[] args) throws Exception {
        new CleanRepeatTxt().run();
    }
}
