package com.lzl.jackdog.http;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StaticController extends Controller{
    //getenv:读取环境变量(将配置的环境变量读出来)
    private static final String JM_Home = System.getenv("JM_HOME");
    public void doGet(Request request,Response response) throws IOException {
        // 根据url找到文件路径
        String filename = getFileName(request.getUrl());

        // 根据文件名的后缀决定content-type
        String suffix = getSuffix(filename);
        String contentTpye = getConttent(suffix);
        response.setContentType(contentTpye);


        // 把文件的所有内容作为response发送
        InputStream in = new FileInputStream(filename);
        byte[] buf = new byte[1024];
        int len;
        while((len = in.read(buf)) != -1){
            response.write(buf,0,len);
        }

    }

    private final Map<String,String> CONTENT_TYPE = new HashMap<String, String>(){
        {
            put("html", "text/html");
            put("css", "text/css");
            put("js", "application/js");
        }
    };
    private String getConttent(String suffix) {
        String contentType = CONTENT_TYPE.get(suffix);
        if(contentType == null){
            contentType = "text/html";
        }
        return contentType;
    }

    // 获取文件名后缀
    private String getSuffix(String filename) {
        int index = filename.lastIndexOf(".");
        if(index == -1){
            return null;
        }
        return filename.substring(index+1);
    }

    private String getFileName(String url) {
        if(url.equals("/")) {
            url = "/index.html";
        }
        String filename = JM_Home +  File.separator + "webapp" + File.separator + url.replace("/", File.separator);
        return filename;
    }
}