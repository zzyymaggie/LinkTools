/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package zzyymaggie.xyz.link.tools.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import jregex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import zzyymaggie.xyz.link.tools.enums.LinkResultEnum;


/**
 * 类 LinkUtil.java 的实现描述 
 * 
 * @author zhangyu
 * 
 * @date 2014年5月19日 下午2:16:41
 */
public class LinkUtil {
	private static Logger urlcheckerLogger = Logger.getLogger("urlchecker");
	
	private static int threadCount = 80;
	private static ThreadPoolExecutor executor;
	
	static {
		initExecutor();
	}

	public static void initExecutor() {
		executor = new ThreadPoolExecutor(1, threadCount, 10, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/**
	 * @author zhangyu
	 * 
	 * @date 2014年5月15日 下午5:57:38
	 */
	public static void extractDeadLinks(List<String> links, Map<String, Future<LinkResultEnum>> accessResultMap) {
		if (links == null || links.size() == 0){
			return;
		}
		for (String link : links) {
			if (!accessResultMap.containsKey(link)) {
				Callable<LinkResultEnum> task = new UrlCheckTask(link);
				Future<LinkResultEnum> future = executor.submit(task);
				accessResultMap.put(link, future);
			}
		}
	}
	
	
	//<meta   http-equiv="refresh"   content="0;URL=http://nfdnserror1.wo.com.cn:8080">
	public static final Pattern TELECOM_CONST_PATTERN = new Pattern("(.*)url=http:\\/\\/(.+)dnserror\\d?\\.wo\\.com\\.cn:8080(.*)", "i");
	/**
	 * 检查URL是否可以访问 http请求需设置自动跳转，要检查的是目标地址是否可以访问 访问超时时间为7+8s
	 * 
	 * @author zhangyu
	 * 
	 * @date 2014年5月19日 下午3:23:41
	 */
	private static LinkResultEnum checkUrlAccess(String url) {
		LinkResultEnum result = LinkResultEnum.NO_ACCESS;
		try {
			HttpClient httpClient = new HttpClient();
			HttpGet getQueueCountGet = new HttpGet(url);
			getQueueCountGet.setConfig(HttpUtil.defaultRequestConfig);
			HttpResponse response = httpClient.request(getQueueCountGet);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
			    String responseString = EntityUtils.toString(response.getEntity());
			    if(!RegexUtil.find(TELECOM_CONST_PATTERN, responseString)){
			        result = LinkResultEnum.SUCCESS;
			    }
			}
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				urlcheckerLogger.error("url:" + url, e);
			}
		} catch (Exception e) {
			urlcheckerLogger.error("url:" + url, e);
			Throwable aa = e;
			if(RuntimeException.class.isInstance(e)){
				if(e.getCause()!=null){
					aa = e.getCause();
				}
			}
			result = getResultByException(aa);
		}
		return result;
	}
	
	private static class UrlCheckTask implements Callable<LinkResultEnum>{
		private String targetUrl;
		
		private UrlCheckTask(String url){
			targetUrl = StringUtils.trim(url);
		}

		public LinkResultEnum call() throws Exception {
			return checkUrlAccess(targetUrl);
		}
		
	}

	private static LinkResultEnum getResultByException(Throwable aa){
		if(aa == null){
			return LinkResultEnum.SUCCESS;
		}
		if(ConnectTimeoutException.class.isInstance(aa)||SocketTimeoutException.class.isInstance(aa)){
			return LinkResultEnum.CONNECT_TIMEOUT;
		}
		return LinkResultEnum.NO_ACCESS;
	}
}
