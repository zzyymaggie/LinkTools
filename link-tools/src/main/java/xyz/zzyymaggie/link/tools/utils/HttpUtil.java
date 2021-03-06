/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools.utils;

import java.net.URI;

import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;

public class HttpUtil {
	/**
	 * 常用的请求超时时间设置
	 */
	public static RequestConfig defaultRequestConfig;
	
	static {
		defaultRequestConfig = RequestConfig.custom().setSocketTimeout(7000)
				  .setConnectTimeout(8000)
				  .setConnectionRequestTimeout(8000)
				  .build();
	}
	
	/**
	 * 死链检查的请求接口，超时时间为15s
	 * @author zhangyu
	 * 
	 * @date 2015年3月27日 下午8:37:10
	 */
	public static String getByDeadLinkHttpClient(String url) {
		HttpClient httpClient = new HttpClient();
		HttpGet getQueueCountGet = new HttpGet(url);
		getQueueCountGet.setConfig(defaultRequestConfig);
		return httpClient.requestString(getQueueCountGet);
	}
	
	/**
	 * 获取一个超时时间config
	 * @author zhangyu
	 * 
	 * @date 2015年3月27日 下午8:54:55
	 */
	public static RequestConfig makeRequestConfig(int connectTimeout, int socketTimeout) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
		  .setConnectTimeout(connectTimeout)
		  .setConnectionRequestTimeout(connectTimeout)
		  .build();
		return requestConfig;
	}
	
	public static String get(String url) {
        return get(URI.create(url));
    }

    public static String get(URI uri) {
        return get(getHttpClient(), uri);
    }

    public static String get(HttpClient http, URI uri) {
        return get(http, uri, null);
    }

    public static String get(HttpClient http, URI uri, String referrer) {
        HttpGet getQueueCountGet = new HttpGet(uri);
        if (referrer != null) {
            getQueueCountGet.setHeader(HttpHeaders.REFERER, referrer);
        }
        return http.requestString(getQueueCountGet);
    }
    
    public static HttpClient getHttpClient() {
        return new HttpClient();
    }
}
