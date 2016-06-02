package com.agrantsem.tools.csript;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class GetProcess {

    public static void main(String[] args) throws Exception {
        URL resource = GetProcess.class.getResource("");
        String path = resource.getPath();
        if (path.startsWith("/")) {
            path = path.substring(1, path.length());
        }
        InputStream is = Runtime.getRuntime().exec("CScript " + path + "test.vbe").getInputStream();
        InputStreamReader isr = new InputStreamReader(is, Charset.forName("GBK"));
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
    }
}
