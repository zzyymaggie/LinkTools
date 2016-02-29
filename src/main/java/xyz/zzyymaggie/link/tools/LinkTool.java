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

import xyz.zzyymaggie.link.tools.enums.LinkResultEnum;
import xyz.zzyymaggie.link.tools.utils.LinkUtil;
import xyz.zzyymaggie.link.tools.utils.UrlUtil;

/**
 *  LinkTool is to check dead links by multi thread.
 */
public class LinkTool 
{
    public static Map<String, String> checkUrlAccess(List<String> links){
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
        return deadLinkMap;
    }
    
    private static void removeNotStandardUrl(List<String> links, Map<String, String> deadLinkMap){
        Iterator<String> iter = links.iterator();
        while (iter.hasNext()) {
            String link = iter.next();
            if(!UrlUtil.checkUrlStandard(link)){
                deadLinkMap.put(link, LinkResultEnum.NOT_STANDARD_URL.getType());
                iter.remove();
            }
        }
    }
}
