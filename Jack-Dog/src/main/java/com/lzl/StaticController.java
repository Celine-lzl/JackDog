package com.lzl;

import java.io.*;

public class StaticController implements Controller {
    @Override
    public void doGet(Request request, Response response) throws IOException {
        String url = request.getUrl();
        if (url.equals("/")) {
            url = "index.html";
        }
        String filename = "E:\\\\httpd\\webapp\\" + url;
        File file = new File(filename);

        InputStream is = new FileInputStream(file);

        byte[] buf = new byte[8192];
        int len = is.read(buf);
        response.write(buf, len);

        is.close();
    }
}
