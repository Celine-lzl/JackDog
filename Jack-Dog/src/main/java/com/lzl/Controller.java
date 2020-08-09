package com.lzl;

import java.io.IOException;

public interface Controller {
    default void doGet(Request request, Response response) throws IOException {
        response.stauts = "405 Method Not Allowed";
        response.println("Method Error");
    }

    default void doPost(Request request, Response response) throws IOException {
        response.stauts = "405 Method Not Allowed";
        response.println("Method Error");
    }
}
