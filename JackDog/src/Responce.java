import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

// HTTP响应：状态行+响应头+正文
// 状态行：协议版本号+状态码+状态码解释
// 响应头：Content-Type,Server,Date等等
public class Responce {
    private String partol;
    private Status status;
    private HashMap<String,String> header = new HashMap<>();

    public static Responce build(OutputStream os) {
        Responce responce = new Responce();
        responce.setServer("HTTP1.1");
        responce.setDate();
        responce.setContentType("text/html");
        responce.setStatus(Status.OK);
        responce.setPartol("HTTP/1.1");
        return  responce;
    }

    private void setServer(String version){
        setHeader("Server", version);
    }

    private void setContentType(String ct){
        setHeader("Content-Type", ct);
    }

    private void setDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        setHeader("Date", simpleDateFormat.format(new Date()));
    }

    public void setPartol(String partol) {
        this.partol = partol;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private void setHeader(String k1, String k2){
        header.put(k1, k2);
    }


    public String getPartol() {
        return partol;
    }

    public Status getStatus() {
        return status;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }
}
