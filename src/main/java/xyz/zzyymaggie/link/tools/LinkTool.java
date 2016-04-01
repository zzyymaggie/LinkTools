/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import xyz.zzyymaggie.link.tools.enums.LinkResultEnum;
import xyz.zzyymaggie.link.tools.utils.FileUtil;
import xyz.zzyymaggie.link.tools.utils.HttpUtil;
import xyz.zzyymaggie.link.tools.utils.LinkUtil;
import xyz.zzyymaggie.link.tools.utils.UrlUtil;

/**
 *  LinkTool is to check dead links by multi thread.
 */
public class LinkTool 
{
    public LinkTool() {
        PropertyConfigurator.configure("log4j.properties");
    }
    
    private static Logger performanceLogger = Logger.getLogger("performance");
    
    public Map<String, String> checkUrlAccess(List<String> links){
        long startTime = System.currentTimeMillis();
        Map<String, Future<LinkResultEnum>> accessResultMap = new HashMap<String, Future<LinkResultEnum>>();
        Map<String, String> deadLinkMap = new HashMap<String, String>();
        removeNotStandardUrl(links, deadLinkMap);
        LinkUtil.extractDeadLinks(links, accessResultMap);
        for(Entry<String, Future<LinkResultEnum>> entry : accessResultMap.entrySet()){
            String url = entry.getKey();
            try {
                LinkResultEnum linkResultEnum = entry.getValue().get();
                if(linkResultEnum != LinkResultEnum.SUCCESS){
                    deadLinkMap.put(url, linkResultEnum.getType());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        performanceLogger.info("checkUrlAccess run time：" + (endTime - startTime) + "ms");
        return deadLinkMap;
    }
    
    private void removeNotStandardUrl(List<String> links, Map<String, String> deadLinkMap){
        Iterator<String> iter = links.iterator();
        while (iter.hasNext()) {
            String link = iter.next();
            if(!UrlUtil.checkUrlStandard(link)){
                deadLinkMap.put(link, LinkResultEnum.NOT_STANDARD_URL.getType());
                iter.remove();
            }
        }
    }
    
    public List<String> extractUrlsFromContent(String body){
        List<String> links = UrlUtil.extractUrl(body);
        Iterator<String> iter = links.iterator();
        while (iter.hasNext()) {
            String link = iter.next();
            if(StringUtils.startsWith(link, "http://localhost")){
                iter.remove();
            }
        }
        for(int i=0;i<links.size();i++){
            links.set(i, UrlUtil.cutOffEscape(links.get(i)));
        }
        return links;
    }
    
    public void displayResult( Map<String, String> deadLinkMap){
        for(Entry<String, String> entry: deadLinkMap.entrySet()){
            System.out.println("url:" + entry.getKey() + ", result:" + entry.getValue());
        }
    }
    
    public static void testUrlAccessFromHtml(String url){
        LinkTool linkTool = new LinkTool();   
        String body = HttpUtil.get(url);
        List<String> links= linkTool.extractUrlsFromContent(body);
        Map<String, String> deadLinkMap = linkTool.checkUrlAccess(links);
        linkTool.displayResult(deadLinkMap);
    }
    
    public static void testUrlAccessFromJson(String filepath){
        LinkTool linkTool = new LinkTool();   
        String body = FileUtil.readFile(filepath);
        List<String> links= linkTool.extractUrlsFromContent(body);
        Map<String, String> deadLinkMap = linkTool.checkUrlAccess(links);
        linkTool.displayResult(deadLinkMap);
    }
    
    public static void main(String[] args) {
        System.out.println("testUrlAccessFromHtml start");
        testUrlAccessFromHtml("http://www.cnblogs.com/chenying99/p/3735282.html");
        System.out.println("testUrlAccessFromHtml end");
        System.out.println("testUrlAccessFromJson start");
        testUrlAccessFromJson("url.json");
        System.out.println("testUrlAccessFromJson end");
    }
}
