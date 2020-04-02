package com.yao.app.http;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLContext;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
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
                RequestConfig.custom().setConnectionRequestTimeout(500).setConnectTimeout(500).setSocketTimeout(1000).setRedirectsEnabled(false)
                        .setCookieSpec(CookieSpecs.DEFAULT).build();

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
            List<NameValuePair> nvpList, List<Cookie> cookies, boolean isPost) throws ParseException, IOException {
        HttpClientContext localContext = HttpClientContext.create();

        // 添加cookies
        if (cookies != null && !cookies.isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Cookie cookie : cookies)
                cookieStore.addCookie(cookie);
            localContext.setCookieStore(cookieStore);
        }

        // http抢先认证
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        URL turl = new URL(url);
        HttpHost target = new HttpHost(turl.getHost(), turl.getPort(), turl.getProtocol());

        credentialsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()), new UsernamePasswordCredentials(username, password));
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
            throws ClientProtocolException, IOException {
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
            HttpClientContext localContext) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        if (isPost) {
            log.debug("post url is {}", url);

            HttpPost post = new HttpPost(url);
            if (nvpList != null && !nvpList.isEmpty()) {
                post.setEntity(new UrlEncodedFormEntity(nvpList, Consts.UTF_8));
            }

            response = httpClient.execute(post, localContext);

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
            response = httpClient.execute(get, localContext);
        }

        try {
            HttpEntity rspEntity = response.getEntity();

            String respContent = EntityUtils.toString(rspEntity, Consts.UTF_8).trim();
            EntityUtils.consume(rspEntity);

            return respContent;
        } finally {
            response.close();
        }
    }

    public static void destoryHttpClient(CloseableHttpClient httpClient) {
        try {
            httpClient.close();
        } catch (IOException e) {
        }
    }
}
