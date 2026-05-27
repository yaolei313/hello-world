package com.yao.app.embedded;

import jakarta.servlet.ServletContainerInitializer;
import org.apache.catalina.*;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.catalina.webresources.JarResourceSet;
import org.apache.catalina.webresources.DirResourceSet; // 引入本地目录资源集
import org.apache.coyote.http2.Http2Protocol;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.util.ClassUtils;

public class ServerMain {

    public static final int PORT = 8080;

    public void startTomcatWebServer() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PORT);

        // 创建 Tomcat 工作基准目录
        File baseDir = createTempDir(PORT);
        tomcat.setBaseDir(baseDir.getAbsolutePath());

        Connector connector = tomcat.getConnector();
        connector.setThrowOnFailure(true);
        connector.setProperty("server", "ServerHeader()");
        connector.setURIEncoding(StandardCharsets.UTF_8.name());
        connector.addUpgradeProtocol(new Http2Protocol()); // 开启 HTTP/2 支持

        tomcat.getHost().setAppBase(".");
        tomcat.getHost().setAutoDeploy(false);

        // 修复：不要用 "." 暴露整个项目根目录，改为指向前端静态资源目录或临时目录
        File webappDir = new File("src/main/webapp");
        String docBase = webappDir.exists() ? webappDir.getAbsolutePath() : baseDir.getAbsolutePath();
        Context context = tomcat.addWebapp("/", docBase);

        // addDefaultServlet(context);
        // addJspServlet(context);
        // addJasperInitializer(context);

        WebResourceRoot resources = new StandardRoot(context);

        // 假设这里初始化你的三方依赖 Jar 列表，如果没有，可以保持空
        List<URL> spiritUrls = new ArrayList<>();
        addResourceJars(resources, spiritUrls);

        // 修复：智能识别是 JAR 运行还是本地 IDEA 运行
        URL mainClassUrl = ServerMain.class.getResource(ServerMain.class.getSimpleName() + ".class");
        if (mainClassUrl != null) {
            String urlStr = mainClassUrl.toString();

            if (urlStr.startsWith("jar:")) {
                // ======= 场景 A：打成 JAR 包后运行 =======
                String jarPath = getJarPath(urlStr);
                System.out.println("Running from JAR: " + jarPath);

                // 从 JAR 根目录将生成的类文件挂载到 Web 环境的类加载路径下
                resources.addPreResources(new JarResourceSet(resources, "/WEB-INF/classes", jarPath, "/"));
            } else {
                // ======= 场景 B：在 IDE (IntelliJ IDEA) 中直接运行 =======
                System.out.println("Running from IDE (Target classes)...");

                // 1. 动态找到本地的 target/classes 目录
                File classDir = new File(ServerMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                if (classDir.exists()) {
                    resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", classDir.getAbsolutePath(), "/"));
                }

                // 2. 如果存在本地源码 webapp 目录，显式挂载
                if (webappDir.exists()) {
                    resources.addPreResources(new DirResourceSet(resources, "/", webappDir.getAbsolutePath(), "/"));
                }
            }
        }

        // --- 加上这段代码，彻底屏蔽 Maven 依赖树带来的 Manifest 扫描噪音 ---
        if (context.getJarScanner() instanceof StandardJarScanner jarScanner) {
            StandardJarScanFilter filter = new StandardJarScanFilter();
            // 1. 忽略包含以下关键字的全部第三方 jar 包的 TLD 和 Web 碎片扫描
            filter.setTldSkip("*jaxb*,*activation*,*angus*,*jackson*,*spring*");
            // 2. 核心关键：禁止 Tomcat 顺着 JAR 包里的 Class-Path 属性往深处瞎找
            jarScanner.setScanClassPath(false);

            context.setJarScanner(jarScanner);
        }

        context.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }

    /**
     * 修复后的安全提取 JAR 路径方法
     */
    private String getJarPath(String urlStr) throws Exception {
        int bangIdx = urlStr.indexOf("!/");
        if (bangIdx != -1) {
            String jarUrl = urlStr.substring(4, bangIdx); // 剥离 "jar:" 前缀和 "!/" 后缀
            return new URI(jarUrl).getPath();
        }
        return new File(".").getAbsolutePath();
    }

    private File createTempDir(int port) {
        try {
            File tempDir = Files.createTempDirectory("tomcat." + port + ".").toFile();
            tempDir.deleteOnExit();
            return tempDir;
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"), ex);
        }
    }

    private void addDefaultServlet(Context context) {
        Wrapper defaultServlet = context.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.setLoadOnStartup(1);
        defaultServlet.setOverridable(true);
        context.addChild(defaultServlet);
        context.addServletMappingDecoded("/", "default");
    }

    private void addJspServlet(Context context) {
        Wrapper jspServlet = context.createWrapper();
        jspServlet.setName("jsp");
        jspServlet.setServletClass("org.apache.jasper.servlet.JspServlet");
        jspServlet.addInitParameter("fork", "false");
        jspServlet.addInitParameter("development", "true"); // 开发环境建议设为 true，支持 JSP 热加载
        jspServlet.setLoadOnStartup(3);
        context.addChild(jspServlet);
        context.addServletMappingDecoded("*.jsp", "jsp");
        context.addServletMappingDecoded("*.jspx", "jsp");
    }

    private void addJasperInitializer(Context context) {
        try {
            ServletContainerInitializer initializer = (ServletContainerInitializer) ClassUtils
                    .forName("org.apache.jasper.servlet.JasperInitializer", null)
                    .getDeclaredConstructor()
                    .newInstance();
            context.addServletContainerInitializer(initializer, null);
        } catch (Exception ex) {
            // Ignored
        }
    }

    private void addResourceJars(WebResourceRoot resources, List<URL> resourceJarUrls) {
        for (URL url : resourceJarUrls) {
            String path = url.getPath();
            if (path.endsWith(".jar") || path.endsWith(".jar!/")) {
                String jar = url.toString();
                if (!jar.startsWith("jar:")) {
                    jar = "jar:" + jar + "!/";
                }
                addResourceSet(resources, jar);
            } else {
                addResourceSet(resources, url.toString());
            }
        }
    }

    private static final String WEB_APP_MOUNT = "/";
    // 修复：确保这是一个相对虚拟路径，不要在 IDEA 的文件复制拦截中产生歧义
    private static final String INTERNAL_PATH = "META-INF/resources";

    private void addResourceSet(WebResourceRoot resources, String resource) {
        try {
            if (isInsideClassicNestedJar(resource)) {
                addClassicNestedResourceSet(resources, resource);
                return;
            }
            URL url = new URL(resource);
            if (isInsideNestedJar(resource)) {
                // 如果存在自定义的 NestedJarResourceSet 扩展则进入此处
                // resources.addJarResources(new NestedJarResourceSet(url, resources, WEB_APP_MOUNT, INTERNAL_PATH));
            } else {
                resources.createWebResourceSet(WebResourceRoot.ResourceSetType.RESOURCE_JAR, WEB_APP_MOUNT, url, "/" + INTERNAL_PATH);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }

    private void addClassicNestedResourceSet(WebResourceRoot resources, String resource) throws Exception {
        URL url = new URL(resource.substring(0, resource.length() - 2));
        resources.createWebResourceSet(WebResourceRoot.ResourceSetType.RESOURCE_JAR, WEB_APP_MOUNT, url, "/" + INTERNAL_PATH);
    }

    private boolean isInsideClassicNestedJar(String resource) {
        return !isInsideNestedJar(resource) && resource.indexOf("!/") < resource.lastIndexOf("!/");
    }

    private boolean isInsideNestedJar(String resource) {
        return resource.startsWith("jar:nested:");
    }

    public static void main(String[] args) {
        try {
            new ServerMain().startTomcatWebServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}