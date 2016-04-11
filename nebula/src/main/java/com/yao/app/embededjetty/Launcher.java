package com.yao.app.embededjetty;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Properties;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

public class Launcher {

	public static final int PORT = 8080;

	public static final String CONTEXT = "/";

	private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";

	public void startJetty() throws Exception {
		// 系统的classpath
		System.out.println(System.getProperty("sun.boot.class.path"));
		// 扩展的classpath
		System.out.println(System.getProperty("java.ext.dirs"));
		// 用户自己的classpath
		System.out.println(System.getProperty("java.class.path"));

		Server server = new Server();
		// 设置在JVM退出时关闭Jetty的钩子。
		server.setStopAtShutdown(true);

		// 这是http的连接器
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(PORT);
		connector.setReuseAddress(false);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webapp = new WebAppContext(DEFAULT_WEBAPP_PATH, "/");
		webapp.setDescriptor("src/main/webapp/WEB-INF/web.xml");
		webapp.setResourceBase(DEFAULT_WEBAPP_PATH);
		webapp.setParentLoaderPriority(true);
		// webapp.setClassLoader(Thread.currentThread().getContextClassLoader());

		System.out.println(Thread.currentThread().getContextClassLoader());

		// This webapp will use jsps and jstl. We need to enable the
		// AnnotationConfiguration in order to correctly set up the jsp
		// container
		Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
		classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
				"org.eclipse.jetty.annotations.AnnotationConfiguration");

		// Set the ContainerIncludeJarPattern so that jetty examines these
		// container-path jars for tlds, web-fragments etc.
		// If you omit the jar that contains the jstl .tlds, the jsp engine will
		// scan for them instead.
		webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");

		server.setHandler(webapp);
		
		// Setup JMX
		MBeanContainer mbContainer=new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
		server.addEventListener(mbContainer);
		server.addBean(mbContainer);
		 
		// Add loggers MBean to server (will be picked up by MBeanContainer above)
		server.addBean(Log.getLog());

		server.start();
		server.join();
		
		Runtime.getRuntime();
		Map<String,String> envs = System.getenv();
		Properties p = System.getProperties();

	}

	public static void main(String[] args) {
		System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
		System.setProperty("org.eclipse.jetty.LEVEL", "INFO");
		// System.setProperty("-javaagent:/Users/yaolei/opt/spring-instrument-4.2.1.RELEASE.jar",
		// "");
		try {
			new Launcher().startJetty();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
