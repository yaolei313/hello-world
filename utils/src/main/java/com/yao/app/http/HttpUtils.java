package com.yao.app.http;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class HttpUtils {

    protected static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 以http basic抢先认证的方式获取json数据 httpclient 4.3写法
     * 
     * @param url
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public static String getContentByPreemptiveAuthentication(String url, String username, String password)
            throws Exception {
        URI uri = new URI(url);
        HttpHost target = new HttpHost(uri.getHost(), uri.getPort(), "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials(username, password));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        try {

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local
            // auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            HttpGet httpget = new HttpGet(url);

            System.out.println("Executing request " + httpget.getRequestLine() + " to target " + target);

            CloseableHttpResponse response = httpclient.execute(target, httpget, localContext);
            try {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(response.getEntity());

                return result;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
        }

    }

    public static String getContent(String url, boolean isPost, List<NameValuePair> nvpList, Cookie cookie)
            throws IOException {
        Assert.hasText(url, "url must not be empty");
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();

        CookieStore globalCookieStore = new BasicCookieStore();
        globalCookieStore.addCookie(cookie);

        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(globalCookieStore).build();
        try {

            CloseableHttpResponse response = null;

            if (isPost) {
                log.debug("post url is {}", url);

                HttpPost post = new HttpPost(url);
                if (nvpList != null && !nvpList.isEmpty()) {
                    post.setEntity(new UrlEncodedFormEntity(nvpList, Consts.UTF_8));
                }

                response = httpclient.execute(post);

            } else {
                String targetUrl = url;
                if (nvpList != null && !nvpList.isEmpty()) {
                    String strParams = EntityUtils.toString(new UrlEncodedFormEntity(nvpList, Consts.UTF_8));
                    if (url.contains("?")) {
                        targetUrl = targetUrl + "&" + strParams;
                    } else {
                        targetUrl = targetUrl + "?" + strParams;
                    }
                }

                log.debug("get url is {}", targetUrl);

                HttpGet get = new HttpGet(targetUrl);
                response = httpclient.execute(get);
            }

            try {
                HttpEntity rspEntity = response.getEntity();

                String respContent = EntityUtils.toString(rspEntity, Consts.UTF_8).trim();
                EntityUtils.consume(rspEntity);

                return respContent;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
        }
    }
}
