/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools.utils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClient {
	private static final Logger logger = Logger.getLogger(HttpClient.class);

	private static int POOL_SIZE = 200;
	private static int CONNECT_TIMEOUT = 30000;
	private static int SOCKET_TIMEOUT = 30000;

	private static CloseableHttpClient HTTP;
	
	private String referrer;

	static {
		initHttpsClient();
	}
	
	public HttpResponse request(HttpUriRequest request) {
		return request(request, null);
	}

	public HttpResponse request(HttpUriRequest request, HttpHost host) {

		if (!StringUtils.isEmpty(this.referrer) && !request.containsHeader(HttpHeaders.REFERER)) {
			request.setHeader(HttpHeaders.REFERER, this.referrer);
		}

		HttpResponse response;
		try {
			if (host == null) {
				host = URIUtils.extractHost(request.getURI());
			}

			response = HTTP.execute(host, request);

		} catch (IOException e) {
			request.abort();
			throw new RuntimeException(e);
		}

		return response;
	}

	/**
	 * @return Response Body as String
	 */
	public String requestString(HttpUriRequest request) {
		return requestString(request, null);
	}

	/**
	 * @return Response Body as String
	 */
	public String requestString(HttpUriRequest request, HttpHost host) {
		HttpResponse httpResponse = request(request, host);
		String responseBody;
		try {
			responseBody = EntityUtils.toString(httpResponse.getEntity());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return responseBody;
	}
	
	public static Header referer(String uri) {
		return new BasicHeader("Referer", uri);
	}

	public static void initHttpsClient() {
		initHttpsClient(POOL_SIZE, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
	}

	/**
	 * 
	 * @param poolSize 请求线程池大小
	 * @param connectTimeout 连接超时时间
	 * @param socketTimeout 指定的出口ip
	 * @return
	 */
	public static void initHttpsClient(int poolSize, int connectTimeout, int socketTimeout) {
		POOL_SIZE = poolSize;
		CONNECT_TIMEOUT = connectTimeout;
		SOCKET_TIMEOUT = connectTimeout;

		X509TrustManager x509TrustManager = new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
		};

		SSLContext sslContext;

		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] {x509TrustManager}, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		Registry<ConnectionSocketFactory> sslRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslSocketFactory).register("http", PlainConnectionSocketFactory.INSTANCE).build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(sslRegistry);
		connectionManager.setMaxTotal(poolSize);
		connectionManager.setDefaultMaxPerRoute(poolSize/2);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setCookieSpec(
				CookieSpecs.BROWSER_COMPATIBILITY);

		RequestConfig requestConfig = requestConfigBuilder.setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).build();
		CloseableHttpClient oldHttp = HTTP;
		HTTP = HttpClientBuilder
				.create()
				.setConnectionManager(connectionManager)
				.setUserAgent("Mozilla/5.0 (Windows NT 5.1; rv:16.0) Gecko/20100101 Firefox/16.0")
				.setDefaultHeaders(
						Arrays.asList(new BasicHeader("Accept", "*/*"), new BasicHeader("Accept-Language",
								"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"), new BasicHeader("Connection", "keep-alive")))
				.setRedirectStrategy(new LaxRedirectStrategy()).setDefaultRequestConfig(requestConfig).build();

		if (oldHttp != null) {
			try {
				oldHttp.close();
			} catch (IOException e) {
				logger.error("close old HTTP error", e);
			}
		}
	}

	public static int getPOOL_SIZE() {
		return POOL_SIZE;
	}

	public static void setPOOL_SIZE(int pOOL_SIZE) {
		POOL_SIZE = pOOL_SIZE;
	}

	public static int getCONNECT_TIMEOUT() {
		return CONNECT_TIMEOUT;
	}

	public static void setCONNECT_TIMEOUT(int cONNECT_TIMEOUT) {
		CONNECT_TIMEOUT = cONNECT_TIMEOUT;
	}

	public static int getSOCKET_TIMEOUT() {
		return SOCKET_TIMEOUT;
	}

	public static void setSOCKET_TIMEOUT(int sOCKET_TIMEOUT) {
		SOCKET_TIMEOUT = sOCKET_TIMEOUT;
	}

}
