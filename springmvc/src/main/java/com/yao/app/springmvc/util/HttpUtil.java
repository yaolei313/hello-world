package com.yao.app.springmvc.util;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
    /**
     * 以http basic抢先认证的方式获取json数据
     * 
     * @param url
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public static String getJSONContentByPreemptiveAuthentication(String url,String username,String password) throws Exception{
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            URI uri = new URI(url);
            HttpHost targetHost = new HttpHost(uri.getHost(), uri.getPort(), "http");
            
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(uri.getHost(), uri.getPort()),
                    new UsernamePasswordCredentials(username, password));

            AuthCache authCache = new BasicAuthCache();
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(targetHost, basicAuth);

            BasicHttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.AUTH_CACHE, authCache);

            HttpGet httpGet = new HttpGet(url);

            // httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");

            HttpResponse response = httpclient.execute(targetHost, httpGet, localContext);
            HttpEntity entity = response.getEntity();

            String respContent = EntityUtils.toString(entity, "UTF-8").trim();
            EntityUtils.consume(entity);
            
            return respContent;
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        
    }
}
