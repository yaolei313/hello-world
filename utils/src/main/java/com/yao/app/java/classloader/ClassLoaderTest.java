package com.yao.app.java.classloader;

public class ClassLoaderTest {

	public static void main(String[] args) throws Exception {
		System.out.println(ClassLoaderTest.class.getName());
		
		ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		ClassLoader newClassLoader = new CustomClassLoader();
		
		System.out.println(appClassLoader);
		System.out.println(appClassLoader);
		System.out.println(newClassLoader);
		
		Class<?> class1 = Class.forName("org.mariadb.jdbc.Driver",false,sysClassLoader);
		Class<?> class2 = Class.forName("net.sf.log4jdbc.DriverSpy",false,newClassLoader);
		
		Student s = new Student();

		System.out.println(class1.getClassLoader());
		System.out.println(class2.getClassLoader());
		System.out.println(s.getClass().getClassLoader());
		
	}

}
