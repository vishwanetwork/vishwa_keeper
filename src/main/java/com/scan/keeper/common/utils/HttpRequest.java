package com.scan.keeper.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
	/**
	 * URLGET
	 * 
	 * @param url
	 *            URL
	 * @param
	 *            ， name1=value1&name2=value2 。
	 * @return URL 
	 */
	public static String sendGet(String url) {
		System.out.println("url:" + url);
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// URL
			URLConnection connection = realUrl.openConnection();
			// 
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 
			connection.connect();
			// 
			Map<String, List<String>> map = connection.getHeaderFields();
			// 
			for (String key : map.keySet()) {
				//System.out.println("Console:" + key + "--->" + map.get(key));
			}
			//  BufferedReaderURL
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("GET！" + e);
			e.printStackTrace();
		}
		// finally
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 *  URL POST
	 * 
	 * @param url
	 *             URL
	 * @param param
	 *            ， name1=value1&name2=value2 。
	 * @return 
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// URL
			URLConnection conn = realUrl.openConnection();
			// 
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8; ");
			// POST
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// URLConnection
			out = new PrintWriter(conn.getOutputStream());
			// 
			out.print(param);
			// flush
			out.flush();
			// BufferedReaderURL
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println(" POST ！" + e);
			e.printStackTrace();
		}
		// finally、
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPostForJson(String url, Map<String, Object> map) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// URL
			URLConnection conn = realUrl.openConnection();
			// 
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8; ");
			// POST
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// URLConnection
			out = new PrintWriter(conn.getOutputStream());
			// 
			out.print(JSONObject.toJSONString(map));
			// flush
			out.flush();
			// BufferedReaderURL
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println(" POST ！" + e);
			e.printStackTrace();
		}
		// finally、
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
