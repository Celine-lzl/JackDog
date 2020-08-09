package com.lzl.jackdog.http;

import java.io.*;

public class JMClassLoader extends ClassLoader{
    private static final String HOME = System.getenv("JM_HOME");
    @Override
    // ListController 在HOME/webapp/WEB-INF/classes
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 1, 根据类名称，找到name对应的*.class文件
        File classFile = getClassFile(name);
        // 2, 读取该文件的内容
        byte[] buf;
        try {
             buf = readClassBytes(classFile);
        } catch (IOException e) {
            throw new ClassNotFoundException(name,e);
        }
        // 3, 调用defineClass，转为class
        return defineClass(name,buf ,0 ,buf.length);
    }

    private byte[] readClassBytes(File classFile) throws IOException {
        int len = (int)classFile.length(); // 这里默认文件大小不会超过我们缓冲区的大小，默认比较小
        byte[] buf = new byte[len];
        InputStream is = new FileInputStream(classFile);
        is.read(buf,0,len);
        return buf;
    }

    private File getClassFile(String name){
        String filename = HOME + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator
                + "classes" + File.separator + name + ".class";
        return new File(filename);
    }
}
