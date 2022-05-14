package com.example.app_news;

public class Model {
    String header,text;

    public Model(String header, String text) {
        this.header = header;
        this.text = text;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getHeader() {
        return header;
    }
}
