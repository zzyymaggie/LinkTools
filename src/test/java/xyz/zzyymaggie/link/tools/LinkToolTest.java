/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import xyz.zzyymaggie.link.tools.enums.LinkResultEnum;
import xyz.zzyymaggie.link.tools.utils.FileUtil;
import xyz.zzyymaggie.link.tools.utils.HttpUtil;


/**
 * Unit test for LinkTool.
 */
public class LinkToolTest
{
    private LinkTool linkTool = new LinkTool();
    
    @Test
    public void testUrlAcessByMockup() {
        List<String> links = new ArrayList<String>();
        links.add("http://www.uc.cn?{}");
        links.add("https://www.baidu.com");
        links.add("https://www.google.com.hk");
        links.add("http://a.b.c");
        links.add("a.b.c");
        Map<String, String> deadLinkMap = linkTool.checkUrlAccess(links);
        for(String key: deadLinkMap.keySet()){
            System.out.println(key + ":" + deadLinkMap.get(key));
        }
        assertNotNull(deadLinkMap);
        assertTrue(deadLinkMap.values().contains(LinkResultEnum.CONNECT_TIMEOUT.getType()));
        assertTrue(deadLinkMap.values().contains(LinkResultEnum.NOT_STANDARD_URL.getType()));
        assertTrue(deadLinkMap.values().contains(LinkResultEnum.NO_ACCESS.getType()));
    }
    
    @Test
    public void testUrlAccessFromHtml(){
        String body = HttpUtil.get("http://www.cnblogs.com/chenying99/p/3735282.html");
        List<String> links= linkTool.extractUrlsFromContent(body);
        Map<String, String> deadLinkMap = linkTool.checkUrlAccess(links);
        linkTool.displayResult(deadLinkMap);
    }
    
    @Test 
    public void testUrlAccessFromJson(){
        String body = FileUtil.readFile("url.json");
        List<String> links= linkTool.extractUrlsFromContent(body);
        Map<String, String> deadLinkMap = linkTool.checkUrlAccess(links);
        linkTool.displayResult(deadLinkMap);
        assertTrue(deadLinkMap.values().contains(LinkResultEnum.NO_ACCESS.getType()));
    }
}
