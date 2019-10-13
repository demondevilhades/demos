package com.hades.jsouptest.douban;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class People {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final String outputDir = "D:/";

    private final String peopleId = "";
    private final String peopleBaseUrl = "https://www.douban.com/people/#people#/";

    private final String itemColumnName = "name\turl\trating\tdate\ttags\tcomment";

    private final String bookBaseUrl = "https://book.douban.com";
    private final String bookWishBaseUrl = bookBaseUrl + "/people/#people#/wish";// 想读
    private final String bookCollectBaseUrl = bookBaseUrl + "/people/#people#/collect";// 读过
    private final String bookReviewsBaseUrl = bookBaseUrl + "/people/#people#/reviews";// 书评
    private final String bookAnnotationBaseUrl = bookBaseUrl + "/people/#people#/annotation/";// 笔记
    private final String bookDoBaseUrl = bookBaseUrl + "/people/#people#/do";// 在读

    private final String movieBaseUrl = "https://movie.douban.com";
    private final String movieWishBaseUrl = movieBaseUrl + "/people/#people#/wish";// 想读
    private final String movieCollectBaseUrl = movieBaseUrl + "/people/#people#/collect";// 读过
    private final String movieReviewsBaseUrl = movieBaseUrl + "/people/#people#/reviews";// 影评
    private final String movieDoBaseUrl = movieBaseUrl + "/people/#people#/do";// 在读

    public void run() throws IOException {
        String dir = outputDir + peopleId + "/";
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        String baseUrl = peopleBaseUrl.replace("#people#", peopleId);
        logger.info(baseUrl);
        Response response = Jsoup
                .connect(baseUrl)
                .userAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                .method(Method.GET).execute();
        Map<String, String> cookies = response.cookies();
//        Document dom = response.parse();

//        baseInfo(dom, dir);
        // friend(dom, cookies);

        book(cookies, dir);
        movie(cookies, dir);
    }

    /**
     * baseInfo
     * 
     * @param dom
     * @throws IOException
     */
    private void baseInfo(Document dom, String dir) throws IOException {
        logger.info("run baseInfo");
        Element usrDiv = dom.getElementById("db-usr-profile");
        String picUrl = usrDiv.select("div.pic > a > img").get(0).attr("src");// pic
        String name = usrDiv.select("div.info > h1").get(0).ownText();// name
        String display = usrDiv.getElementById("display").text();// display

        Element profileDiv = dom.getElementById("profile");
        String userfaceUrl = profileDiv.select("div.infobox > div.bd > div.basic-info > img").get(0).attr("src");// userface
        String location = profileDiv.select("div.infobox > div.bd > div.basic-info > div.user-info > a").get(0).text();// location
        String pl = profileDiv.select("div.infobox > div.bd > div.basic-info > div.user-info > div.pl").get(0).text();// info
        String intro = profileDiv.getElementById("intro_display").text();

        File file = new File(dir + "baseInfo.txt");
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.writeLine("picUrl=" + picUrl);
            fw.writeLine("name=" + name);
            fw.writeLine("display=" + display);
            fw.writeLine("userfaceUrl=" + userfaceUrl);
            fw.writeLine("location=" + location);
            fw.writeLine("pl=" + pl);
            fw.writeLine("intro=" + intro);
            FileWriter.save(picUrl, dir);
            FileWriter.save(userfaceUrl, dir);
        } catch (IOException e) {
            throw e;
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    /**
     * friend (need login
     * 
     * @param dom
     * @param cookies
     * @throws IOException
     */
    private void friend(Document dom, Map<String, String> cookies) throws IOException {
        logger.info("run friend");
        Element friendDiv = dom.getElementById("friend");
        String friendHref = friendDiv.select("h2 > span > a").get(0).attr("href");
        String revHref = friendDiv.nextElementSibling().select("a").get(0).attr("href");

        logger.info("friendHref=" + friendHref);
        logger.info("revHref=" + revHref);
    }

    private void book(Map<String, String> cookies, String dir) throws IOException {
        logger.info("run book");
        String bookWishUrl = bookWishBaseUrl.replace("#people#", peopleId);
        String bookCollectUrl = bookCollectBaseUrl.replace("#people#", peopleId);
        String bookDoUrl = bookDoBaseUrl.replace("#people#", peopleId);
        String bookReviewsUrl = bookReviewsBaseUrl.replace("#people#", peopleId);
        String bookAnnotationUrl = bookAnnotationBaseUrl.replace("#people#", peopleId);

        book2File(bookWishUrl, dir + "bookWish.txt", cookies);
        book2File(bookCollectUrl, dir + "bookCollect.txt", cookies);
        book2File(bookDoUrl, dir + "bookDoWish.txt", cookies);
        book2File(bookReviewsUrl, dir + "bookReview.txt", cookies);
        book2File(bookAnnotationUrl, dir + "bookAnnotation.txt", cookies);
    }

    private void book2File(String bookUrl, String fileName, Map<String, String> cookies) throws IOException {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.writeLine(itemColumnName);
            while (bookUrl != null) {
                logger.info(bookUrl);
                Document bookWishDom = Jsoup.connect(bookUrl).cookies(cookies).method(Method.GET).execute().parse();
                Elements infoDivs = bookWishDom.getElementById("content").select(
                        "div.grid-16-8 > div.article > ul > li > div.info");
                for (Element infoDiv : infoDivs) {
                    Element a = infoDiv.select("h2 > a").get(0);
                    Elements spans = infoDiv.select("div.short-note > div > span");
                    Elements ps = infoDiv.select("div.short-note > p");
                    StringBuilder sb = new StringBuilder();
                    sb.append(a.text()).append("\t").append(a.attr("href"));
                    String rating = "";
                    String date = "";
                    String tags = "";
                    for (Element span : spans) {
                        if (span.hasClass("rating1-t") || span.hasClass("rating2-t") || span.hasClass("rating3-t")
                                || span.hasClass("rating4-t") || span.hasClass("rating5-t")) {
                            rating = span.className().replace("rating", "").replace("-t", "");
                        } else if (span.hasClass("date")) {
                            date = span.text();
                        } else if (span.hasClass("tags")) {
                            tags = span.text();
                        }
                    }
                    sb.append("\t").append(rating).append("\t").append(date).append("\t").append(tags);
                    String comment = "";
                    if (ps.size() > 0) {
                        comment = ps.get(0).text();
                    }
                    sb.append("\t").append(comment);
                    fw.writeLine(sb.toString());
                }
                Elements pageAs = bookWishDom.select("div.grid-16-8 > div.article > div.paginator > span.next > a");
                if (pageAs.size() > 0) {
                    bookUrl = bookBaseUrl + pageAs.get(0).attr("href");
                } else {
                    bookUrl = null;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    private void movie(Map<String, String> cookies, String dir) throws IOException {
        logger.info("run movie");
        String movieWishUrl = movieWishBaseUrl.replace("#people#", peopleId);
        String movieCollectUrl = movieCollectBaseUrl.replace("#people#", peopleId);
        String movieDoUrl = movieDoBaseUrl.replace("#people#", peopleId);
        String movieReviewsUrl = movieReviewsBaseUrl.replace("#people#", peopleId);

        movie2File(movieWishUrl, dir + "movieWish.txt", cookies);
        movie2File(movieCollectUrl, dir + "movieCollect.txt", cookies);
        movie2File(movieDoUrl, dir + "movieDo.txt", cookies);
        movie2File(movieReviewsUrl, dir + "movieReviews.txt", cookies);
    }

    private void movie2File(String bookUrl, String fileName, Map<String, String> cookies) throws IOException {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.writeLine(itemColumnName);
            while (bookUrl != null) {
                logger.info(bookUrl);
                Document bookWishDom = Jsoup.connect(bookUrl).cookies(cookies).method(Method.GET).execute().parse();
                Elements infoDivs = bookWishDom.getElementById("content").select(
                        "div.grid-16-8 > div.article > div.grid-view > div.item > div.info");
                for (Element infoDiv : infoDivs) {
                    Element a = infoDiv.select("ul > li.title > a").get(0);
                    Elements spans = infoDiv.select("ul > li > span");
                    StringBuilder sb = new StringBuilder();
                    sb.append(a.text()).append("\t").append(a.attr("href"));
                    String rating = "";
                    String date = "";
                    String tags = "";
                    String comment = "";
                    for (Element span : spans) {
                        if (span.hasClass("rating1-t") || span.hasClass("rating2-t") || span.hasClass("rating3-t")
                                || span.hasClass("rating4-t") || span.hasClass("rating5-t")) {
                            rating = span.className().replace("rating", "").replace("-t", "");
                        } else if (span.hasClass("date")) {
                            date = span.text();
                        } else if (span.hasClass("tags")) {
                            tags = span.text();
                        } else if (span.hasClass("comment")) {
                            comment = span.text();
                        }
                    }
                    sb.append("\t").append(rating).append("\t").append(date).append("\t").append(tags).append("\t")
                            .append(comment);
                    fw.writeLine(sb.toString());
                }
                Elements pageAs = bookWishDom.select("div.grid-16-8 > div.article > div.paginator > span.next > a");
                if (pageAs.size() > 0) {
                    bookUrl = movieBaseUrl + pageAs.get(0).attr("href");
                } else {
                    bookUrl = null;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    @SuppressWarnings("unused")
    private void things(Element contentDiv) {
        Element thingsDiv = contentDiv.getElementById("things");
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unused")
    private void apps(Element contentDiv) {
        Element appsDiv = contentDiv.getElementById("apps");
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unused")
    private void commodity(Element contentDiv) {
        Element commodityDiv = contentDiv.getElementById("commodity");
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unused")
    private void game(Element contentDiv) {
        Element gameDiv = contentDiv.getElementById("game");
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) throws Exception {
//        System.setProperty("http.proxyHost", "proxy.***.com.cn");
//        System.setProperty("http.proxyPort", "80");
//        System.setProperty("https.proxyHost", "proxy.***.com.cn");
//        System.setProperty("https.proxyPort", "80");
        new People().run();
    }
}
