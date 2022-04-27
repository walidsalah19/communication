package com.example.communication.Model;

public class StudentModel {
    String student_id , student_fullname,student_university_number,studentEmail,student_level,student_sepcialist,student_semeter,student_password,student_img;

    public StudentModel(String student_id, String student_fullname, String student_university_number, String studentEmail, String student_level, String student_sepcialist, String student_semeter, String student_password, String student_img) {
        this.student_id = student_id;
        this.student_fullname = student_fullname;
        this.student_university_number = student_university_number;
        this.studentEmail = studentEmail;
        this.student_level = student_level;
        this.student_sepcialist = student_sepcialist;
        this.student_semeter = student_semeter;
        this.student_password = student_password;
        this.student_img = student_img;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_fullname() {
        return student_fullname;
    }

    public void setStudent_fullname(String student_fullname) {
        this.student_fullname = student_fullname;
    }

    public String getStudent_university_number() {
        return student_university_number;
    }

    public void setStudent_university_number(String student_university_number) {
        this.student_university_number = student_university_number;
    }

    public String getStudent_level() {
        return student_level;
    }

    public void setStudent_level(String student_level) {
        this.student_level = student_level;
    }

    public String getStudent_sepcialist() {
        return student_sepcialist;
    }

    public void setStudent_sepcialist(String student_sepcialist) {
        this.student_sepcialist = student_sepcialist;
    }

    public String getStudent_semeter() {
        return student_semeter;
    }

    public void setStudent_semeter(String student_semeter) {
        this.student_semeter = student_semeter;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }

    public String getStudent_img() {
        return student_img;
    }

    public void setStudent_img(String student_img) {
        this.student_img = student_img;
    }
}
