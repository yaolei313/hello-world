package com.yao.app.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    protected static final Logger log = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        String loginUrl = "https://passport.csdn.net/account/login";
        String contentUrl = "";
        String username = "yaolei313@gmail.com";
        String password = "1";

        // pwd:U3VtbWVyITIz
        // log.debug("after base64 password is {}",
        // Base64.encode(password.getBytes()));

        // ssoid=1808698c93a744dfbc852d229c2d329b
        Cookie cookie = new BasicClientCookie("ssoid", "b678753346ab418b9b0b27179ea65618");

        //
        try {
            RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();

            CookieStore globalCookieStore = new BasicCookieStore();

            CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                    .setDefaultCookieStore(globalCookieStore).build();
            try {
                HttpGet get = new HttpGet(loginUrl);
                CloseableHttpResponse response = httpclient.execute(get);

                String respContent = null;
                try {
                    HttpEntity rspEntity = response.getEntity();

                    respContent = EntityUtils.toString(rspEntity, Consts.UTF_8).trim();
                    EntityUtils.consume(rspEntity);
                } finally {
                    response.close();
                }

                if (StringUtils.isBlank(respContent)) {
                    return;
                }

                String lt = null;
                Pattern p = Pattern.compile("name=\"lt\"\\s+value=\"(\\w+-\\d+-\\w+)\"");
                Matcher m = p.matcher(respContent);
                if (m.find()) {
                    lt = m.group(1);
                }

                if (StringUtils.isBlank(lt)) {
                    return;
                }

                // username:yaolei313@gmail.com
                // password:1
                // lt:LT-5712-tOexBm9NpJdearVNvNDWAcjgCBmwTJ
                // execution:e3s1
                // _eventId:submit

                List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
                nvpList.add(new BasicNameValuePair("username", username));
                nvpList.add(new BasicNameValuePair("password", password));
                nvpList.add(new BasicNameValuePair("lt", lt));
                nvpList.add(new BasicNameValuePair("execution", "e3s1"));
                nvpList.add(new BasicNameValuePair("_eventId", "submit"));

                HttpPost post = new HttpPost(loginUrl);
                post.setEntity(new UrlEncodedFormEntity(nvpList));
                CloseableHttpResponse loginResponse = httpclient.execute(post);
                
                try {
                    System.out.println(loginResponse.getStatusLine());
                    
                    Header[] headers = loginResponse.getAllHeaders();
                    for(Header h :headers){
                    System.out.println(h.getName()+":"+h.getValue());
                    }
                    HttpEntity contentRspEntity = loginResponse.getEntity();

                    respContent = EntityUtils.toString(contentRspEntity, Consts.UTF_8).trim();
                    EntityUtils.consume(contentRspEntity);

                    System.out.println(respContent);
                } finally {
                    response.close();
                }

            } catch (Exception e) {
                throw e;
            } finally {
                httpclient.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
