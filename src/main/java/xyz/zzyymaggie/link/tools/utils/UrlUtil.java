/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package zzyymaggie.xyz.link.tools.utils;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import jregex.Pattern;

public class UrlUtil {

    private static Logger urlcheckerLogger = Logger.getLogger("urlchecker");
    
    public static final Pattern pattern = new Pattern("^https?:\\/\\/.*?");
    
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
}
