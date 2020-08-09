package com.lzl.jackdog.http;

import org.dom4j.DocumentException;
import java.io.File;
import java.io.IOException;
import java.net.*;

public class Servier {
    private static final String HOME = System.getenv("JM_HOME");
    private final Controller staticController = new StaticController();
    private final WebApp webApp = WebApp.parseXML();

    public Servier() throws DocumentException, MalformedURLException, URISyntaxException {
    }

    public void run(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Request request = Request.parse(socket.getInputStream());
                Response response = Response.build(socket.getOutputStream());
                // 如果url对应的静态文件存在，就当成静态文件处理，否则当成动态文件处理
                String filename = getFileName(request.getUrl());
                File file = new File(filename);
                Controller controller = null;
                if (file.exists()) {
                    // 当成静态文件处理
                    controller = staticController;
                } else {
                    // 当成动态controller处理
                    controller = webApp.findController(request);
                }

                System.out.println(request.getUrl());
                System.out.println(controller);

                if (controller == null) {
                    response.setStatus(Status.NotFound);
                    response.println(Status.NotFound.getStatus());
                } else {
                    if ("GET".equals(request.getMethod())) {
                        controller.doGet(request, response);

                    } else if ("POST".equals(request.getMethod())) {
                        controller.doPost(request, response);

                    } else {
                        response.setStatus(Status.MethodNotAllowed);
                        response.println(Status.NotFound.getStatus());
                    }
                }
                response.flush();
                socket.close();
            }catch (SocketException e){
                e.printStackTrace();
            }
        }
    }

    private String getFileName(String url) {
        if(url == null){
             url = "/index.html";
        }
        return HOME + File.separator + "webapp" + File.separator + url.replace("/", File.separator);
    }

    public static void main(String[] args) throws DocumentException, IOException, URISyntaxException {
        new Servier().run(8088);
    }
}

