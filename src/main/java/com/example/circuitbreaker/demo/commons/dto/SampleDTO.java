package com.example.circuitbreaker.demo.commons.dto;

public class SampleDTO {

    private String msg;

    public SampleDTO(String msg) {
        this.msg = msg;
    }

    public SampleDTO() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
