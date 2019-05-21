package com.kd.openplatform.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.kd.openplatform.exception.HlhtException;

/**
 * 工具类
 *
 */
public class HttpRequestUtil {
	private static Log logger = LogFactory.getLog(HttpRequestUtil.class);
	public static final String HTTP_GET = "GET";

	public static final String HTTP_POST = "POST";
	/** 连接超时 */
	private static int CONNECT_TIME_OUT = 5000;

	/** 读取数据超时 */
	private static int READ_TIME_OUT = 10000;

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;


	/**
	 * POST请求，字符串形式数据
	 *
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求数据
	 * @param charset
	 *            编码方式
	 * @throws HlhtException
	 */
	public static String sendPostTokenRequestUrl(String url, String param, String charset, String tokenRequest)
			throws HlhtException {

		PrintWriter out = null;
		BufferedReader in = null;
		InputStreamReader stream = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性 消息头
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setConnectTimeout(CONNECT_TIME_OUT);
			conn.setReadTimeout(READ_TIME_OUT);
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("Authorization", tokenRequest);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			stream = new InputStreamReader(conn.getInputStream(), charset);
			in = new BufferedReader(stream);
			String line;
			while ((line = in.readLine()) != null) {
				// result += line;
				result.append(line);
			}
		} catch (Exception e) {
			logger.error("[" + HttpRequestUtil.class.getName() + "]打开连接失败");
			throw new HlhtException("请求第三方打开连接失败", 90001);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (stream != null) {
					stream.close();
				}
			} catch (IOException ex) {
				logger.error("[" + HttpRequestUtil.class.getName() + "]关闭流失败", ex);
			}
		}
		return result.toString();
	}

	/***************************************** HTTPS请求操作*************************************************/

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 *
	 * @param apiUrl
	 *            API接口URL
	 * @param Params
	 *            JSON对象
	 * @param token 携带的token
	 * @return
	 */
	public static String doPostSSL(String apiUrl, String Params,String token) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			httpPost.setHeader("Authorization", token);
			StringEntity stringEntity = new StringEntity(Params, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);

			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			logger.error("Exception"+e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (IOException e) {
					logger.error("Exception"+e);
				}
			} else {
				if (httpClient != null) {
					try {
						httpClient.close();
					} catch (IOException e) {
						logger.error("Exception"+e);
					}
				}
			}
		}
		return httpStr;
	}
	/**
	 * 创建SSL安全连接
	 *
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			logger.error("Exception"+e);
		}
		return sslsf;
	}
}
