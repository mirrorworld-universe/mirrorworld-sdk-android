package com.mirror.sdk.response;

public class CommonResponse<E> {
    public long code = 1;
    public E data;
    public String error;
    public String status;
    public String message;
    public long http_status_code;
}
