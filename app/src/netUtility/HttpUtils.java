package netUtility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HttpUtils {

	public static final String TAG = "HttpUtils";

	/**
	 * 创建HttpEntiy
	 * 
	 * @param paramMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static HttpEntity createEntity(Map<String, String> paramMap)
			throws UnsupportedEncodingException {
		ArrayList localArrayList = new ArrayList();
		Iterator localIterator = paramMap.keySet().iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			localArrayList.add(new BasicNameValuePair(str, String
					.valueOf(paramMap.get(str))));
		}
		return new UrlEncodedFormEntity(localArrayList, HTTP.UTF_8);
	}

	/**
	 * httppost数据
	 * 
	 * @param url
	 * @param paramMap
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public static String postData(String url, Map<String, String> paramMap,
			String sessionId) {
		Log.i("HttpUtils", "上传数据 url=" + url + "  data=" + paramMap);
		HttpClient localHttpClient = GlobalContext.getInstance()
				.getHttpClient();
		try {
			HttpPost localHttpPost = new HttpPost(new URI(url));
			localHttpPost.setHeader("Cookie", "JSESSIONID=" + sessionId);
			localHttpPost.setEntity(createEntity(paramMap));
			HttpResponse response = localHttpClient.execute(localHttpPost);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String out = EntityUtils.toString(entity);
				return out;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println("httpPost:" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.println("httpPost:" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("httpPost:" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("httpPost:" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return "服务器接口异常！请联系系统管理员！";
	}

	/**
	 * httppost数据
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String postData(String url){
		HttpClient localHttpClient = GlobalContext.getInstance()
				.getHttpClient();
		try {
			HttpPost localHttpPost = new HttpPost(new URI(url));
			//localHttpPost.setEntity(createEntity(paramMap));
			HttpResponse response = localHttpClient.execute(localHttpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String out = EntityUtils.toString(entity);
				return out;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
//			throw new RuntimeException(e.getMessage());
			return null;
		}
		return "";
	}	
	
	/**
	 * httppost数据
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String postData(String url, Map<String, String> paramMap){
		Log.i("HttpUtils", "上传数据 url=" + url + "  data=" + paramMap);

		HttpClient localHttpClient = GlobalContext.getInstance()
				.getHttpClient();
		try {
			HttpPost localHttpPost = new HttpPost(new URI(url));
			localHttpPost.setEntity(createEntity(paramMap));
			HttpResponse response = localHttpClient.execute(localHttpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String out = EntityUtils.toString(entity);
				return out;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
//			throw new RuntimeException(e.getMessage());
			return null;
		}
		return "";
	}
	public static byte[] postDataByte(String url, Map<String, String> paramMap){
		Log.i("HttpUtils", "上传数据 url=" + url + "  data=" + paramMap);

		HttpClient localHttpClient = GlobalContext.getInstance()
				.getHttpClient();
		try {
			HttpPost localHttpPost = new HttpPost(new URI(url));
			localHttpPost.setEntity(createEntity(paramMap));
			HttpResponse response = localHttpClient.execute(localHttpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				byte[] out = EntityUtils.toByteArray(entity);
				return out;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
}
