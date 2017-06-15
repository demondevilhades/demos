package com.hades.jsouptest;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class HttpUnitTest {

    public void run() throws IOException, SAXException {
        HttpUnitOptions.setScriptingEnabled(false);
        WebConversation wc = new WebConversation();
        WebResponse wr = wc.getResponse("https://www.baidu.com/");
        System.out.println(wr.getText());
    }

    public void run1() throws IOException, SAXException {
        WebConversation wc = new WebConversation();
        WebRequest req = new GetMethodWebRequest("https://www.oschina.net/question/tag/java");
        req.setParameter("show", "time");
        WebResponse wr = wc.getResponse(req);
        System.out.println(wr.getText());
    }

    public void run2() throws IOException, SAXException {
        WebConversation wc = new WebConversation();
        WebRequest req = new GetMethodWebRequest("https://www.oschina.net/question/tag/java");
        req.setParameter("show", "time");
        WebResponse wr = wc.getResponse(req);
        WebLink link = wr.getLinkWith("尚未回答");
        link.click();
        wr = wc.getCurrentPage();
        System.out.println(wr.getText());
    }

    public static void main(String[] args) throws Exception {
        System.getProperties().setProperty("http.proxyHost", "proxy.***.com.cn");
        System.getProperties().setProperty("http.proxyPort", "80");
        System.getProperties().setProperty("https.proxyHost", "proxy.***.com.cn");
        System.getProperties().setProperty("https.proxyPort", "80");
        System.getProperties().setProperty("proxySet", "true");
        new HttpUnitTest().run1();
    }
}
