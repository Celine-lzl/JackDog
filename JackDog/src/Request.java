import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String url;
    private String method;
    private String paotol;
    private static Map<String,Integer> keyvalue = new HashMap<>();
    private static Map<String,String> header = new HashMap<>();



    public static Request parse(InputStream is) throws IOException {
//        String requestMessage = "GET /thread.html?id=18&page=9 HTTP/1.1\r\n"
//                + "Host: www.bitvip.com\r\n"
//                + "Accept: text/html\r\n"
//                + "\r\n";
        Request request = new Request();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // 解析请求行
        parseRequestLine(reader,request);
        // 解析请求头
        setRequestHeader(reader,request);
        return request;
    }

    private static void parseRequestLine(BufferedReader reader, Request request) throws IOException {
        String line = reader.readLine();
        if (line == null) {
            throw new IOException("不该读到结尾");
        }
        String[] segments = line.split(" ");
        if (segments.length != 3) {
            throw new IOException("输入格式不对");
        }
        request.setMethod(segments[0]);
        request.setUrl(segments[1]);
        request.setPaotol(segments[2]);
    }

    private void setMethod(String method){
        this.method = method;
        if(method.toUpperCase().equals("POST") || method.equals("GET")){
            return;
        }
        throw new RuntimeException("只支持Get和Post方法");
    }

    //        String requestMessage = "GET /thread.html?id=18&page=9 HTTP/1.1\r\n"
//                + "Host: www.bitvip.com\r\n"
//                + "Accept: text/html\r\n"
//                + "\r\n";
    private void setUrl(String url) throws UnsupportedEncodingException {
        String[] uu = url.split("\\?");
        if(uu == null){
            throw new RuntimeException("不应该读到结尾");
        }
        this.url= URLDecoder.decode(uu[0], "UTF-8");
        setKeyvalue(uu[1]);
    }

    private void setKeyvalue(String topart){
        String[] part = topart.split("&");
            for (String temp : part) {
                String[] kv = temp.split("=");
                String key = kv[0];
                String val = null;
                if (kv.length > 1){
                    val = kv[1];
                }
                keyvalue.put(key, Integer.valueOf(val));
            }
    }

    private void setPaotol(String paotol){
        if(paotol.toUpperCase().startsWith("HTTP")){
            this.paotol = paotol;
            return;
        }else{
            throw new RuntimeException("不支持的协议");
        }

    }

    private static void setRequestHeader(BufferedReader reader, Request request) throws IOException {
        String line;
        while((line = reader.readLine()) != null & line.length() > 1){
            if(line.length() > 1){
                String key = line.split(":")[0];
                String val = line.split(":")[1];
                header.put(key, val);
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getPaotol() {
        return paotol;
    }

    public Map<String, Integer> getKeyvalue() {
        return keyvalue;
    }

    public static Map<String, String> getHeader() {
        return header;
    }
}
