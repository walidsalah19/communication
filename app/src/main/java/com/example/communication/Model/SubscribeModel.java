package com.example.communication.Model;

public class SubscribeModel {
    String subscribe_id ,subscriber_number, user_type,course_number;

    public SubscribeModel(String subscribe_id, String subscriber_number, String user_type, String course_number) {
        this.subscribe_id = subscribe_id;
        this.subscriber_number = subscriber_number;
        this.user_type = user_type;
        this.course_number = course_number;
    }

    public String getSubscribe_id() {
        return subscribe_id;
    }

    public void setSubscribe_id(String subscribe_id) {
        this.subscribe_id = subscribe_id;
    }

    public String getSubscriber_number() {
        return subscriber_number;
    }

    public void setSubscriber_number(String subscriber_number) {
        this.subscriber_number = subscriber_number;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }
}
