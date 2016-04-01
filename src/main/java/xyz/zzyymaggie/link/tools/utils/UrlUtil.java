/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import jregex.Pattern;

public class UrlUtil {

    private static Logger urlcheckerLogger = Logger.getLogger("urlchecker");
    
    public static final Pattern pattern = new Pattern("^https?:\\/\\/.*?");
    
    /**
     * 提取URL的正则表达式
     */
    public static final Pattern urlExtractPattern = new Pattern("(['\"])([^'\"]*?)(https?:\\/\\/.*?)\\1");
    
    /**
     * 检测URL是否符合URL标准，主要是判断是否包含特殊字符
     * @author zhangyu
     * 
     * @date 2014年5月21日 下午7:32:16
     */
    public static boolean checkUrlStandard(String url){
        if(!RegexUtil.match(pattern, url)){
            return false;
        }
        try {
            new URI(url);
        } catch (URISyntaxException e) {
            urlcheckerLogger.error("url:" + url, e);
            return false;
        }
        return true;
    }
    
    public static List<String> extractUrl(String content){
        List<String> links = new ArrayList<String>();
        if(StringUtils.isEmpty(content)){
            return links;
        }
        List<List<String>> groups = RegexUtil.extractGroups(UrlUtil.urlExtractPattern, content);
        if(groups == null){
            return links;
        }
        for(List<String> group : groups){
            if(group.size() >= 4) {
                links.add(group.get(3));
            }
        }
        return links;
    }
    
    public static String cutOffEscape(String url) {
        if(StringUtils.endsWith(url, "\\")){
            url = StringUtils.substring(url, 0, url.length() - 1);
        }
        return url;
    }
}
