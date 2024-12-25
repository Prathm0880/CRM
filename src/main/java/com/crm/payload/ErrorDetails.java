package com.crm.payload;

import java.util.Date;

//for used custom mssg

public class ErrorDetails {
    private Date date;
    private String mssg;

    private String request; //for using url

    public ErrorDetails(Date date, String mssg,String request) {
        this.date = date;
        this.mssg = mssg;
        this.request = request;
    }
    public Date getDate() {
        return date;
    }

    public String getMssg() {
        return mssg;
    }
    public String getRequest(){
        return request;
    }
}
