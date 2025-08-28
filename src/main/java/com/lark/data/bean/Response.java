package com.lark.data.bean;

public class Response<T> extends ResponseEntity {
    private T data;

    public Response() {
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
