/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.java.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xyz.zzyymaggie.link.tools.utils.HttpClient;
import xyz.zzyymaggie.link.tools.utils.HttpUtil;
import xyz.zzyymaggie.link.tools.utils.OpenCSV;

/**
 *  HtmlParserDemo is to extract TKD for https://www.titanslockerroom.com/ and its child sites
 *  and save into a csv file
 */
public class HtmlParserDemo {
    public HtmlParserDemo() {
        PropertyConfigurator.configure("log4j.properties");
    }
    private static final Logger logger = Logger.getLogger(HtmlParserDemo.class);
    public List<Record> getLinkList(String url, String host) {
        List<Record> urlList = new ArrayList<>();
        String html = this.getHtmlBody(url);
        Document doc = Jsoup.parse(html);
        Elements dropdownList = doc.select("li[class*=dropdown]");
        for (Element element : dropdownList) {
            Elements hrefList = element.select("li > a");
            if (hrefList != null) {
                for (Element element2 : hrefList) {
                    String siteUrl = element2.attr("href");
                    String name = element2.text();
                    if (siteUrl.startsWith("/collections")) {
                        Record record = new Record(name, host + siteUrl);
                        urlList.add(record);
                    }
                }
            }
        }
        return urlList;
    }
    
    public void extractTKD(List<Record> records) {
        for(Record record: records) {
            extractTKD(record);
        }
    }
    
    private void extractTKD(Record record) {
        String url = record.getUrl();
        String body = this.getHtmlBody(url);
        Document doc = Jsoup.parse(body);
        record.setTitle(doc.select("title").get(0).text());
        record.setDescription(doc.select("meta[name=description]").get(0).attr("content").replace("\r\n", " "));
    }
    
    private String getHtmlBody(String url) {
        String responseString = null;
        try {
            HttpClient httpClient = new HttpClient();
            HttpGet getQueueCountGet = new HttpGet(url);
            getQueueCountGet.setConfig(HttpUtil.defaultRequestConfig);
            responseString = httpClient.requestString(getQueueCountGet);
            logger.info(responseString);
        } catch (Exception e) {
            Throwable aa = e;
            if(RuntimeException.class.isInstance(e)){
                if(e.getCause()!=null){
                    aa = e.getCause();
                }
            }
            logger.error(aa);
        }
        return responseString;
    }
    
    public static void main(String[] args) throws IOException {
        String url = "https://www.titanslockerroom.com/";
        String host = "https://www.titanslockerroom.com";
        HtmlParserDemo demo = new HtmlParserDemo();
        List<Record> urlList = demo.getLinkList(url, host);
        Record record = new Record("mainPage", url);
        urlList.add(0, record);
        demo.extractTKD(urlList);
        
        List<String[]> allLines = new ArrayList<>();
        for(Record item: urlList) {
            allLines.add(item.getCSVContent());
        }
        try {
            String[] entries = "name#url#title#description#keywords".split("#");
            OpenCSV.CSVWrite(entries, allLines, "titanslockerroom.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
