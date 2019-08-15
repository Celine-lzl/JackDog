package com.lzl.jackdog.http;


import java.io.IOException;

public class Controller {
    public void doGet(Request request,Response response) throws IOException {
        response.setStatus(Status.MethodNotAllowed);
        response.println(Status.MethodNotAllowed.getStatus());
    }
    public void doPost(Request request,Response response) throws IOException {
        response.setStatus(Status.MethodNotAllowed);
        response.println(Status.MethodNotAllowed.getStatus());
    }
}
