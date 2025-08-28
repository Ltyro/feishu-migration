package com.lark.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class ResponseEntity {
    private Integer code;
    private String msg;

    public ResponseEntity(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseEntity() {
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return "Response{code=" + this.code + ", msg='" + this.msg + '\'' + '}';
    }
}
