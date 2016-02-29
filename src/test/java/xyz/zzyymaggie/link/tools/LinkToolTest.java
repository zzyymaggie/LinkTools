/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import xyz.zzyymaggie.link.tools.enums.LinkResultEnum;


/**
 * Unit test for LinkTool.
 */
public class LinkToolTest
{
    static{
        PropertyConfigurator.configure("log4j.properties");
    }
    
    @Test
    public void testUrlAcess() {
        List<String> links = new ArrayList<String>();
        links.add("http://www.uc.cn?{}");
        links.add("https://www.baidu.com");
        links.add("https://www.google.com.hk");
        links.add("http://a.b.c");
        links.add("a.b.c");
        Map<String, String> deadLinkMap = LinkTool.checkUrlAccess(links);
        for(String key: deadLinkMap.keySet()){
            System.out.println(key + ":" + deadLinkMap.get(key));
        }
        assertNotNull(deadLinkMap);
        assertTrue(deadLinkMap.values().contains(LinkResultEnum.CONNECT_TIMEOUT.getType()));
        assertTrue(deadLinkMap.values().contains(LinkResultEnum.NOT_STANDARD_URL.getType()));
        assertTrue(deadLinkMap.values().contains(LinkResultEnum.NO_ACCESS.getType()));
    }
    
}
