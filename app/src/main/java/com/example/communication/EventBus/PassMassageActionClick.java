package com.example.communication.EventBus;


public class PassMassageActionClick {

   private String msg;

    public PassMassageActionClick(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
