package com.lzl.jackdog.http;

public enum Status {

    OK(200,"成功"),
    BadRequest(400,"错误的请求"),
    NotFound(404,"没找到URL对应的资源"),
    InternalServerError(500,"服务器内部错误"),
    MethodNotAllowed(405,"不支持的方法");

    // 状态码
    private int code;
    // 原因
    private String reason;

    Status(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode(){
        return code;
    }

    public String getStatus() {
        return reason;
    }
}

