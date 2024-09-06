package com.yao.app.baidumaps;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by Yao on 2017/8/22.
 */
public class SnTest {

    private static final String YOURSK = "dC8mz04FxwlSRjr4IRuMpMZbGxcfjE2F";

    private static final String YOURAK = "uaCUvGUxh26YGXsfOBOIrbdCOCA3Q8x0";

    public static void main(String[] args) throws Exception {
        SnTest snTest = new SnTest();
        snTest.testGet2();
        //snTest.testPost();
    }

    public void testGet2() throws Exception {
        /**
         * 以http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak为例
         * ak设置了sn校验不能直接使用必须在url最后附上sn值，get请求计算sn跟url中参数对出现顺序有关，需按序填充paramsMap，
         * post请求是按字母序填充，具体参照testPost()
         */
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("query", "汽车站");
        paramsMap.put("coord_type", "1");
        paramsMap.put("city_limit", "true");
        paramsMap.put("region", "淄博市");
        paramsMap.put("output", "json");
        paramsMap.put("ak", YOURAK);

        // 调用下面的toQueryString方法，对paramsMap内所有value作utf8编码
        String paramsStr = toQueryString(paramsMap);

        System.out.println(paramsStr);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk
        String wholeStr = new String("/place/v2/search?" + paramsStr + YOURSK);

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        System.out.println(tempStr);

        // 调用下面的MD5方法得到sn签名值
        String sn = MD5(tempStr);

        System.out.println(sn);

        String url =
            "http://api.map.baidu.com/place/v2/search?query=汽车站&coord_type=1&city_limit=true&region=淄博市" + "&output=json&ak=" + YOURAK + "&sn=" + sn;
        System.out.println(url);

        // 算得sn后发送get请求
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpget);
        InputStream is = response.getEntity().getContent();
        String result = inStream2String(is);
        // 打印响应内容
        System.out.println(result);
    }

    public void testGet() throws Exception {
        /**
         * 以http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak为例
         * ak设置了sn校验不能直接使用必须在url最后附上sn值，get请求计算sn跟url中参数对出现顺序有关，需按序填充paramsMap，
         * post请求是按字母序填充，具体参照testPost()
         */
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("address", "百度大厦");
        paramsMap.put("output", "json");
        paramsMap.put("ak", YOURAK);

        // 调用下面的toQueryString方法，对paramsMap内所有value作utf8编码
        String paramsStr = toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk
        String wholeStr = new String("/geocoder/v2/?" + paramsStr + YOURSK);

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到sn签名值
        String sn = MD5(tempStr);

        // 算得sn后发送get请求
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet("http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=" + YOURAK + "&sn=" + sn);
        CloseableHttpResponse response = client.execute(httpget);
        InputStream is = response.getEntity().getContent();
        String result = inStream2String(is);
        // 打印响应内容
        System.out.println(result);
    }

    public void testPost() throws Exception {
        /**
         * 以http://api.map.baidu.com/geodata/v3/geotable/create创建表为例
         */
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("geotype", "1");
        paramsMap.put("ak", YOURAK);
        paramsMap.put("name", "geotable80");
        paramsMap.put("is_published", "1");

        // post请求是按字母序填充，对上面的paramsMap按key的字母序排列
        Map<String, String> treeMap = new TreeMap<>(paramsMap);
        String paramsStr = toQueryString(treeMap);

        String wholeStr = new String("/geodata/v3/geotable/create?" + paramsStr + YOURSK);
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
        // 调用下面的MD5方法得到sn签名值
        String sn = MD5(tempStr);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://api.map.baidu.com/geodata/v3/geotable/create");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("geotype", "1"));
        params.add(new BasicNameValuePair("ak", YOURAK));
        params.add(new BasicNameValuePair("name", "geotable80"));
        params.add(new BasicNameValuePair("is_published", "1"));
        params.add(new BasicNameValuePair("sn", sn));
        HttpEntity formEntity = new UrlEncodedFormEntity(params);
        post.setEntity(formEntity);
        CloseableHttpResponse response = client.execute(post);
        InputStream is = response.getEntity().getContent();
        String result = inStream2String(is);
        // 打印响应内容
        System.out.println(result);
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    public String toQueryString(Map<?, ?> data) throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    // 将输入流转换成字符串
    private static String inStream2String(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray(), "UTF-8");
    }
}
