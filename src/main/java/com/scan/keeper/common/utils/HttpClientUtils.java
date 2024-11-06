package com.scan.keeper.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: amber
 * Date: 2018-05-15
 * Time: 17:40
 */
public class HttpClientUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); // 

    private static RequestConfig requestConfig = null;

    private static CloseableHttpClient httpClient;

    static {
        try {
            SSLContext sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 
        requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
    }

    /**
     * postjson
     *
     * @param url  url
     * @param
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        // post
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 
        httpPost.setConfig(requestConfig);
        try {
            if (null != jsonParam) {
                // 
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // ，
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // json
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // jsonjson
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * postString ：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url      url
     * @param strParam 
     * @return
     */
    public static JSONObject httpPost(String url, String strParam) {
        // post
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (null != strParam) {
                // 
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // ，
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // json
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // jsonjson
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    public static String httpPostGetStr(String url, String strParam) {
        // post
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (null != strParam) {
                // 
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // ，
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // json
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    return str;
                } catch (Exception e) {
                    logger.error("post:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * get
     *
     * @param url 
     * @return
     */
    public static JSONObject httpGet(String url) {
        // get
        JSONObject jsonResult = null;
        // get
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpClient.execute(request);

            // ，
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // json
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // jsonjson
                jsonResult = JSONObject.parseObject(strResult);
            } else {
                logger.error("get:" + url);
            }
        } catch (IOException e) {
            logger.error("get:" + url, e);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }

    public static JSONArray httpGetArray(String url) {
        // get
        JSONArray jsonResult = null;
        // get
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpClient.execute(request);

            // ，
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // json
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // jsonjson
                jsonResult = JSONArray.parseArray(strResult);
            } else {
                logger.error("get:" + url);
            }
        } catch (IOException e) {
            logger.error("get:" + url, e);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }


    public static String httpGetStr(String url) {
        // get
        // get
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpClient.execute(request);

            // ，
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // json
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // jsonjson
                return strResult;
            } else {
                logger.error("get:" + url);
            }
        } catch (IOException e) {
            logger.error("get:" + url, e);
        } finally {
            request.releaseConnection();
        }
        return null;
    }

}
