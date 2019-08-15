package com.lzl.jackdog.http;


import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String url;
    private String protocol; // 版本号
    private Map<String,String> requestParams = new HashMap<String, String>();
    private Map<String,String> headers = new HashMap<String, String>();

    //  String requestMessage = "GET /thread.html?id=18&page=9 HTTP/1.1\r\n"
    //                + "Host: www.bitvip.com\r\n"
    //                + "Accept: text/html\r\n"
    //                + "\r\n";
    public static Request parse(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Request request = new Request();
        // 解析请求行
        parseRequestLine(reader,request);
        // 解析请求头
        parseRequestHeaders(reader, request);
        return request;
    }

    private static void parseRequestLine(BufferedReader reader, Request request) throws IOException {
        String line = reader.readLine(); // readline不会读\r\n，只会把前面内容读到
        if(line == null){
            throw  new IOException("读到结尾");
        }
        String[] segment = line.split(" ");
        if(segment.length != 3){
            throw new IOException("错误的请求行");
        }
        request.setMethod(segment[0]);
        request.setUrl(segment[1]);
        request.setProtocol(segment[2]);
    }

    // 解析方法
    private void setMethod(String method) throws IOException {
        this.method = method.toUpperCase();
        if(this.method.equals("POST") || this.method.equals("GET")){
            return;
        }
        throw  new IOException("不支持的方法");
    }

    // 解析版本号
    private void setProtocol(String protocol) throws IOException {
        if(!protocol.toUpperCase().startsWith("HTTP")){
            throw new IOException("错误的HTTP版本");
        }
        this.protocol = protocol.toUpperCase();
    }

    // 解析url
    private void setUrl(String url) throws UnsupportedEncodingException {
        String[] segments = url.split("\\?");
        this.url = URLDecoder.decode(segments[0], "UTF-8");
        if(segments.length > 1){
            setRequestParams(segments[1]);
        }
    }

    private void setRequestParams(String queryString) throws UnsupportedEncodingException {
        for(String kv : queryString.split("&")) {
            String[] segments = kv.split("=");
            String key = segments[0];
            String val = "";
            // value值可以为空
            if (segments.length > 1) {
                val = URLDecoder.decode(segments[1], "UTF-8");
            }
            requestParams.put(key, val);
        }
    }



    private  static void parseRequestHeaders(BufferedReader reader,Request request) throws IOException {
        String l ;
        // trim：去掉头尾的空格
        while ((l  = reader.readLine())!= null && l.trim().length() != 0) {
            String[] arr = l.split(":");
            String key = arr[0].trim();
            String val = arr[1].trim();
            request.setHeader(key,val);
        }
    }


    public String getMethod() {
        return this.method;
    }

    public String getUrl() {
        return  url;
    }

    public String getProtol() {
        return protocol;
    }

    private void setHeader(String key, String val) {
        headers.put(key, val);
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }


    public Map<String,String> getHeaders() {
        return headers;
    }
}

