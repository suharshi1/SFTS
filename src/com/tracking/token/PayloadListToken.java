package com.tracking.token;

import java.util.List;

public class PayloadListToken implements Token {

    private int code;
    private String message;
    private List<Object> payload;

    public PayloadListToken() {
    }

    public PayloadListToken(List<? extends Object> payload) {
        this.payload = (List<Object>) payload;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    public List<? extends Object> getPayload() {
        return payload;
    }

    public void setPayload(List<? extends Object> payload) {
        this.payload = (List<Object>) payload;
    }

}
