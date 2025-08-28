package com.lark.data.pojo;

public class Node {
    private String token;
    private String parentToken;

    public Node(String token, String parentToken) {
        this.token = token;
        this.parentToken = parentToken;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getParentToken() {
        return this.parentToken;
    }

    public void setParentToken(String parentToken) {
        this.parentToken = parentToken;
    }
}
