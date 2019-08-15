package com.lzl.jackdog.http;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WebApp {
    Map<String,String> urlToName = new HashMap<String, String>();
    Map<String,String> nameToClassName = new HashMap<String, String>();
    private static final String HOME = System.getenv("JM_HOME");

    public static WebApp parseXML() throws DocumentException, MalformedURLException, URISyntaxException {
        WebApp webApp = new WebApp();
        SAXReader reader = new SAXReader();

        URL url = xmlFilename();
        // 这里能找到web.xml是因为在编译后的classes路径下，web.xml文件被放到了classes的根路径下，和Main是同级别的
        // 别的类如果有包，编译后在classes下也有包
        // 只要有ClassLoader.getResource找资源文件，相对路径是从类的包根路径开始找
        Document doc = reader.read(url);
        Element root = doc.getRootElement(); // 就是web.xml中webapp这个根路径
        for(Iterator<Element> it = root.elementIterator(); it.hasNext();){
            Element element = it.next();
            // element.getName：controller和mapping
            String s = element.getName();
            if ("controller".equals(s)) {
                String name = element.element("name").getText();
                String className = element.element("class").getText();
                webApp.nameToClassName.put(name, className);
            } else if ("mapping".equals(s)) {
                String name = element.element("url-pattern").getText();
                String urlPattern = element.element("url-pattern").getText();
                webApp.urlToName.put(urlPattern, name);
            } else {

            }
        }
        return webApp;
    }

    private static URL xmlFilename() throws MalformedURLException, URISyntaxException {
        return  new File(HOME.replace("/", File.separator) + File.separator + "webapp" +File.separator + "WEB-INF" + File.separator + "web.xml").toURI().toURL();
    }

    public Controller findController(Request request) throws IOException {
        String name = findName(request.getUrl());
        if(name == null){
            return null;
        }
        String className = findClassName(name);
        if(className == null){
            return null;
        }
        Controller controller = findInstance(className);
        return controller;
    }

    private Controller findInstance(String className) throws IOException {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
            return (Controller) cls.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    private String findName(String url) {
        return urlToName.get(url);
    }

    private String findClassName(String name){
        return nameToClassName.get(name);
    }
}
