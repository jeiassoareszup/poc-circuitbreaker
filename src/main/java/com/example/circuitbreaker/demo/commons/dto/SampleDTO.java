package com.example.circuitbreaker.demo.commons.dto;

public class SampleDTO {

    private String msg;
    private long time;

    public SampleDTO(String msg) {
        this.msg = msg;
    }

    public SampleDTO(String msg, long time) {
        this.msg = msg;
        this.time = time;
    }

    public SampleDTO() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
