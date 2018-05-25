package test.nlp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLUtil {
    public static String delHTMLTag(String htmlStr) {
        if (org.apache.commons.lang3.StringUtils.isBlank(htmlStr)) {
            return "";
        }
        String lineSeparator = System.getProperty("line.separator");

        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_p_tag = "<\\s*[/]{0,1}p[^>]*>";
        String regEx_br_tag = "<\\s*br\\s*/{0,1}\\s*>";
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        // 过滤p标签
        Pattern p_pTag = Pattern.compile(regEx_p_tag, Pattern.CASE_INSENSITIVE);
        Matcher m_pTag = p_pTag.matcher(htmlStr);
        htmlStr = m_pTag.replaceAll(lineSeparator);

        // 过滤br
        Pattern p_brTag = Pattern.compile(regEx_br_tag, Pattern.CASE_INSENSITIVE);
        Matcher m_brTag = p_brTag.matcher(htmlStr);
        htmlStr = m_brTag.replaceAll(lineSeparator);

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        return htmlStr.trim(); // 返回文本字符串
    }

    public static String cutString(String str, int len) {
        String ret = str;
        if (str.length() > len) {
            ret = str.substring(0, len) + "...";
        }
        return ret;
    }

    public static String delHTMLPStyleTag(String htmlStr) {
        if (org.apache.commons.lang3.StringUtils.isBlank(htmlStr)) {
            return org.apache.commons.lang3.StringUtils.trimToEmpty(htmlStr);
        }
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<p[^>]*>"; // 定义p标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤p标签

        return htmlStr.trim();
    }

    public static void main(String[] args) {
        System.out.println(delHTMLPStyleTag("<html><p>asdfasdf</p><br /></html>"));
    }

}