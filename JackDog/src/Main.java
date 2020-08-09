import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String requestMessage = "GET /thread.html?id=18&page=9 HTTP/1.1\r\n"
                + "Host: www.bitvip.com\r\n"
                + "Accept: text/html\r\n"
                + "\r\n";
        InputStream is = new ByteArrayInputStream(requestMessage.getBytes());
        OutputStream os = new ByteArrayOutputStream();
        Responce responce = Responce.build(os);
        System.out.println(responce.getHeader());
        System.out.println(responce.getPartol());
        System.out.println(responce.getStatus());
        // 对输入的message进行解析
//        // 解析出请求行的各部分内容和请求头
//        Request request = Request.parse(is);
//        System.out.println(request.getMethod());
//        System.out.println(request.getUrl());
//        System.out.println(request.getPaotol());
//        System.out.println(request.getKeyvalue());
//        System.out.println(request.getHeader());

    }
}
