package com.scan.keeper.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;

public class SMSUtil {
    private static final Logger logger = LoggerFactory.getLogger(SMSUtil.class);

    // 
    public static String sendVerifyCodeSMSBy253s(String account, String password, String mobile, String content) {
        URL url = null;
        try {
            String sendUrl = "http://intapi.253.com/send";
            Map<String, String> map = new HashMap<String, String>();
            
            map.put("account",account);//API
            map.put("password",password);//API
            map.put("msg",content);//
            map.put("mobile",mobile);//
            
            JSONObject js = (JSONObject) JSONObject.toJSON(map);
            
            url = new URL(sendUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(js.toString().getBytes("UTF-8"));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //253
    public static String sendVerifyCodeSMSBy253(String account, String password, String mobile, String content) {
        URL url = null;
        try {
            String sendUrl = "https://intapi.253.com/send/json";
            Map<String, String> map = new HashMap<String, String>();
            
            map.put("account",account);//API
            map.put("password",password);//API
            map.put("msg",content);//
            map.put("mobile",mobile);//
            
            JSONObject js = (JSONObject) JSONObject.toJSON(map);
            
            url = new URL(sendUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(js.toString().getBytes("UTF-8"));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 
    public static String sendVerifyCodeByZthy(String username, String password, String mobile, String content) {
        String url = "http://api.zthysms.com/sendSms.do";
        String tkey = SMSTimeUtil.getNowTime("yyyyMMddHHmmss");
        String xh = "";
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("sendVerifyCodeSMS", e);
        }
        String param = "url=" + url + "&username=" + username + "&password=" + SMSMD5Gen.getMD5(SMSMD5Gen.getMD5(password) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile + "&content=" + content + "&xh" + xh;
        logger.info("sendVerifyCodeSMS-->param:" + param);
        String ret = sendPost(url, param);
        logger.info("sendVerifyCodeSMS-->result:" + ret);
        return ret;
    }
    
    /**
     *  URL POST
     *
     * @param url    URL
     * @param param ， name1=value1&name2=value2 。
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
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // POST
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //
            conn.setConnectTimeout(30000);
            //
            conn.setReadTimeout(30000);
            // URLConnection
            out = new PrintWriter(conn.getOutputStream());
            // 
            out.print(param);
            // flush
            out.flush();
            // BufferedReaderURL
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("sendPost", e);
            return " POST ！";
        }
        //finally、
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
