public enum Status {
    OK(200,"成功"),
    BADREQUEST(400,"错误的请求"),
    NNOTFOUND(404,"错误的请求"),
    MRTHODNOT(405,"不支持的方法"),
    INTERNALSERVERERROR(500,"服务器内部错误");
    private int code;
    private String status;

    Status(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
