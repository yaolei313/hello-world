package com.yao.app.http;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.auth.AuthCache;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {

    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 支持http，https(所有证书均有效),返回的client需复用
     *
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static CloseableHttpClient getHttpClient(KeyStore keyStore, List<Cookie> globalCookies)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        sslContext.getClientSessionContext().setSessionTimeout(60);

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslsf).register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .build();


        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(500);

        RequestConfig globalConfig =
                RequestConfig.custom().setConnectionRequestTimeout(500, TimeUnit.MICROSECONDS).setConnectTimeout(500, TimeUnit.MICROSECONDS)
                        .setResponseTimeout(1000, TimeUnit.MICROSECONDS).setRedirectsEnabled(false)
                        .build();

        CookieStore globalCookieStore = new BasicCookieStore();
        if (globalCookies != null) {
            for (Cookie cookie : globalCookies)
                globalCookieStore.addCookie(cookie);
        }

        CloseableHttpClient httpclient =
                HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(globalConfig).setDefaultCookieStore(globalCookieStore).build();

        return httpclient;
    }

    public static String getContentByPreemptiveAuthentication(CloseableHttpClient httpClient, String url, String username, String password,
                                                              List<NameValuePair> nvpList, List<Cookie> cookies, boolean isPost) throws IOException, ParseException {
        HttpClientContext localContext = HttpClientContext.create();

        // 添加cookies
        if (cookies != null && !cookies.isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Cookie cookie : cookies)
                cookieStore.addCookie(cookie);
            localContext.setCookieStore(cookieStore);
        }

        // http抢先认证
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        URL turl = new URL(url);
        HttpHost target = new HttpHost(turl.getProtocol(), turl.getHost(), turl.getPort( ));

        credentialsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()), new UsernamePasswordCredentials(username, password.toCharArray()));
        localContext.setCredentialsProvider(credentialsProvider);
        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(target, basicAuth);

        // Add AuthCache to the execution context
        localContext.setAuthCache(authCache);

        return getHttpResonse(httpClient, url, nvpList, isPost, localContext);
    }

    public static String getContent(CloseableHttpClient httpClient, String url, List<NameValuePair> nvpList, List<Cookie> cookies, boolean isPost)
            throws IOException, ParseException {
        HttpClientContext localContext = HttpClientContext.create();

        // 添加cookies
        if (cookies != null && !cookies.isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Cookie cookie : cookies)
                cookieStore.addCookie(cookie);
            localContext.setCookieStore(cookieStore);
        }

        return getHttpResonse(httpClient, url, nvpList, isPost, localContext);
    }

    private static String getHttpResonse(CloseableHttpClient httpClient, String url, List<NameValuePair> nvpList, boolean isPost,
            HttpClientContext localContext) throws IOException, ParseException {
        CloseableHttpResponse response = null;
        if (isPost) {
            log.debug("post url is {}", url);

            HttpPost post = new HttpPost(url);
            if (nvpList != null && !nvpList.isEmpty()) {
                post.setEntity(new UrlEncodedFormEntity(nvpList, StandardCharsets.UTF_8));
            }

            response = httpClient.execute(post, localContext);

        } else {
            String targetUrl = url;
            if (nvpList != null && !nvpList.isEmpty()) {
                String strParams = EntityUtils.toString(new UrlEncodedFormEntity(nvpList, StandardCharsets.UTF_8));
                if (url.contains("?")) {
                    targetUrl = targetUrl + "&" + strParams;
                } else {
                    targetUrl = targetUrl + "?" + strParams;
                }
            }

            log.debug("get url is {}", targetUrl);

            HttpGet get = new HttpGet(targetUrl);
            response = httpClient.execute(get, localContext);
        }

        try {
            HttpEntity rspEntity = response.getEntity();

            String respContent = EntityUtils.toString(rspEntity, StandardCharsets.UTF_8).trim();
            EntityUtils.consume(rspEntity);

            return respContent;
        } finally {
            response.close();
        }
    }

    public static void destroyHttpClient(CloseableHttpClient httpClient) {
        try {
            httpClient.close();
        } catch (IOException e) {
        }
    }
}
