package com.mark.common.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 *
 * @author 木可
 */
public class HttpClientUtil {


    /**
     * 发送get请求，不带参数
     *
     * @param url 请求地址
     * @return String
     */
    public static String doGet(String url) {
        return doGetParams(url, null);
    }

    /**
     * 发送get请求,并携带参数
     *
     * @param url   请求地址
     * @param param 参数
     * @return String
     */
    public static String doGetParams(String url, Map<String, String> param) {
        return doGetParamsHeaders(url, param, null);
    }

    /**
     * 发送get请求,并携带请求头
     *
     * @param url   请求地址
     * @param headers 请求头
     * @return String
     */
    public static String doGetHeaders(String url, Map<String, String> headers) {
        return doGetParamsHeaders(url, null, headers);
    }

    /**
     * 发送get请求，并携带参数，请求头
     *
     * @param url     请求地址
     * @param param   请求参数
     * @param headers 请求头
     * @return String
     */
    public static String doGetParamsHeaders(String url, Map<String, String> param, Map<String, String> headers) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 请求头非空
            if (headers != null) {
                // 遍历
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpGet.setHeader(header.getKey(), header.getValue());
                }
            }
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), getDefaultCharSet());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("GET请求异常：" + e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 发送POST请求，不带参数
     *
     * @param url 请求地址
     * @return String
     */
    public static String doPost(String url) {
        return doPostJson(url, null);
    }

    /**
     * 发送POST请求，携带map格式的参数
     * 如：name="jok",age="10"
     *
     * @param url   请求地址
     * @param param Map格式的参数
     * @return String
     */
    public static String doPostMap(String url, Map<String, String> param) {
        return doPostBodyMapHeader(url, param, null);
    }

    /**
     * 发送post请求，携带json类型数据
     * 如：{"name":"jok","age":"10"}
     *
     * @param url  请求地址
     * @param json json格式参数
     * @return String
     */
    public static String doPostJson(String url, String json) {
        return doPostBodyJsonHeader(url, json, null);
    }

    /**
     * 发送post请求，携带请求头
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return String
     */
    public static String doPostHeader(String url, Map<String, String> headers) {
        return doPostBodyMapHeader(url, null, headers);
    }

    /**
     * 发送post请求，携带json类型数据
     * 如：{"name":"jok","age":"10"}
     *
     * @param url  请求地址
     * @param json json格式参数
     * @param headers 请求头
     * @return String
     */
    public static String doPostBodyJsonHeader(String url, String json, Map<String, String> headers) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 判断请求头是否为空
            if (headers != null) {
                // 遍历map
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpPost.setHeader(header.getKey(), header.getValue());
                }
            }
            // 判断json非空
            if (json != null) {
                // 创建请求内容
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                httpPost.setEntity(entity);
            }

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), getDefaultCharSet());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("POST请求异常：" + e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 发送POST请求，携带map格式的参数
     * 如：name="jok",age="10"
     *
     * @param url   请求地址
     * @param param Map格式的参数
     * @param headers 请求头
     * @return String
     */
    public static String doPostBodyMapHeader(String url, Map<String, String> param, Map<String, String> headers) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, getDefaultCharSet());
                httpPost.setEntity(entity);
            }
            // 判断请求头是否为空
            if (headers != null) {
                // 遍历map
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpPost.setHeader(header.getKey(), header.getValue());
                }
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), getDefaultCharSet());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("POST请求异常：" + e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 设置HTTP请求头
     * @param httpPost http
     * @param headers 请求头
     */
    private static void setHttpHeaders(HttpPost httpPost, Map<String, String> headers) {

    }

    /**
     * 设置编码格式utf-8,防止乱码
     *
     * @return utf-8
     */
    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        return writer.getEncoding();
    }
}