package com.yao.app.java.classloader;

public class CustomClassLoader extends ClassLoader {

	private ClassLoader parent;

	public CustomClassLoader() {
		super();
		
	}

	public CustomClassLoader(ClassLoader parent) {
		super(parent);
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "CustomClassLoader [toString()=" + super.toString() + "]";
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if(name.equals("com.yao.app.thread.Student")){
			return loadClass(name, false);
		}
		return parent.loadClass(name);
        
    }
	
//	@Override
//	protected Class<?> findClass(final String name) throws ClassNotFoundException {
//		byte[] b = loadClassData(name);
//		return defineClass(name, b, 0, b.length);
//	}
//
//	private byte[] loadClassData(String name) {
//        //URL url = new URL("file:");
//        return null;
//    }
}
