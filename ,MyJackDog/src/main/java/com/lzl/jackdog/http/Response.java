package com.lzl.jackdog.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Response {

    private OutputStream os;
    private Status status;
    private Map<String,String> headers = new HashMap<String, String>();
    private byte[] buf = new byte[8192];
    private int offset = 0;

    public Response(OutputStream os) {
        this.os = os;
    }

    public static Response build(OutputStream os){
        Response response = new Response(os);
        response.setStatus(Status.OK);
        response.setContentType("text/html");
        response.setServer();
        response.setDate();
        return response;
    }

    private void setDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        setHeader("Data", sdf.format(new Date()));
    }

    private void setServer() {
        setHeader("Server", "jack dog/1.0");
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setContentType(String s) {
        setHeader("Content-Type",s + "; charset = UTF-8");
    }

    // 标准HTTP可以是1个key多个value，这是我们只是1个k一个V
    private void setHeader(String s, String s1) {
        headers.put(s, s1);
    }


    public void print(Object o) throws UnsupportedEncodingException {
        // 暂时不考虑buf溢出的问题
        byte[] src = o.toString().getBytes("UTF-8");
        System.arraycopy(src, 0, buf, offset, src.length);
        offset += src.length;
    }

    public void println(Object o)throws IOException{
        print(String.format("%s%n",o));
    }

    public void flush() throws IOException {
        setHeader("Content-Length", String.valueOf(offset));
        System.out.println(offset);
        sendResponseLine();
        sendResponseHanders();
        sendResponseBody();
    }

    public void printf(String format,Object...o) throws FileNotFoundException, UnsupportedEncodingException {
        print(new Formatter().format(format,o));
    }

    private void sendResponseLine() throws IOException {
        String responseLine = String.format("HTTP/1.0 %d %s",status.getCode(),status.getStatus());
        os.write(responseLine.getBytes("UTF-8"));
    }

    private void sendResponseHanders() throws IOException {
        for(Map.Entry<String,String> map: headers.entrySet()){
            String key = map.getKey();
            String val = map.getValue();
            String headerLine = String.format("%s: %s\r\n",key,val);
            os.write(headerLine.getBytes("UTF-8"));
        }
        os.write('\r');
        os.write('\n');
    }

    private void sendResponseBody() throws IOException {
        os.write(buf,0,offset);
    }

    public void write(byte[] b, int off, int len) {
        System.arraycopy(b, off, buf, offset, len);
    }
}





