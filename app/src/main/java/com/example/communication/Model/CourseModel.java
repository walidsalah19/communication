package com.example.communication.Model;

public class CourseModel {
    String course_id ,course_number, course_name,course_divison;

    public CourseModel(String course_id, String course_number, String course_name, String course_divison) {
        this.course_id = course_id;
        this.course_number = course_number;
        this.course_name = course_name;
        this.course_divison = course_divison;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_divison() {
        return course_divison;
    }

    public void setCourse_divison(String course_divison) {
        this.course_divison = course_divison;
    }
}
