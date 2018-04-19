package com.yao.app.baidumaps;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.yao.app.baidumaps.PathEnum.GEO_CODING_API;

/**
 * Created by yaolei02 on 2017/5/9.
 */
public class BaiduMapsUtil {

    private static final String API_HOST = "http://api.map.baidu.com";

    private static final String AK = "uaCUvGUxh26YGXsfOBOIrbdCOCA3Q8x0";

    private static final String SK = "dC8mz04FxwlSRjr4IRuMpMZbGxcfjE2F";

    public static String getGeocoding(String address) {
        try {
            List<NameValuePair> params = new ArrayList<>(3);
            params.add(new BasicNameValuePair("address", address));
            // params.add(new BasicNameValuePair("city", "quanguo"));
            params.add(new BasicNameValuePair("output", "json"));
            params.add(new BasicNameValuePair("ak", AK));

            String requestUrl = generateRequestUrl(GEO_CODING_API, params);

            return requestUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateRequestUrl(PathEnum pathEnum, List<NameValuePair> params) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuilder requestUriBuilder = new StringBuilder(pathEnum.getRelativePath());
        requestUriBuilder.append("?").append(toQueryString(params));

        String requestUri = requestUriBuilder.toString();

        String wholeStr = new String(requestUri + SK);
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
        String sign = md5(tempStr);

        StringBuilder requestUrlBuilder = new StringBuilder(API_HOST);
        requestUrlBuilder.append(requestUri);
        requestUrlBuilder.append("&sn=").append(sign);

        return requestUrlBuilder.toString();
    }


    // 对Map内所有value作utf8编码，拼接返回结果
    public static String toQueryString(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (NameValuePair pair : params) {
            queryString.append(pair.getName() + "=");
            queryString.append(URLEncoder.encode(pair.getValue(), "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public static String md5(String md5) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(md5.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

}
