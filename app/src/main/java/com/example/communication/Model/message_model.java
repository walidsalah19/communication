package com.example.communication.Model;

public class message_model {
    String message,user_image,user_id,message_date;

    public message_model(String message, String user_image, String user_id, String message_date) {
        this.message = message;
        this.user_image = user_image;
        this.user_id = user_id;
        this.message_date = message_date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }
}
