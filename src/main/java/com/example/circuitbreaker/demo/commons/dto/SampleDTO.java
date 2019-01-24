package com.example.circuitbreaker.demo.commons.dto;

public class SampleDTO {

    private String msg;
    private long requestTime;

    public SampleDTO(String msg) {
        this.msg = msg;
    }

    public SampleDTO(String msg, long requestTime) {
        this.msg = msg;
        this.requestTime = requestTime;
    }

    public SampleDTO() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }
}
