package com.healthcare.Fragments.module_doctor_chat;

public class Chat {

    private String message;
    private String author;
    boolean isImage;

    public Chat(){}

    public Chat(String message, String author,boolean isImage) {
        this.message = message;
        this.author = author;
        this.isImage=isImage;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }




}
