package com.example.communication.Model;

public class TeacherModel {
    String teacher_id , teacher_fullname,teacher_university_number,teacherEmail,teacher_sepcialist,teacher_semeter,teacher_password,student_img;

    public TeacherModel(String teacher_id, String teacher_fullname, String teacher_university_number, String teacherEmail, String teacher_sepcialist, String teacher_semeter, String teacher_password, String student_img) {
        this.teacher_id = teacher_id;
        this.teacher_fullname = teacher_fullname;
        this.teacher_university_number = teacher_university_number;
        this.teacherEmail = teacherEmail;
        this.teacher_sepcialist = teacher_sepcialist;
        this.teacher_semeter = teacher_semeter;
        this.teacher_password = teacher_password;
        this.student_img = student_img;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_fullname() {
        return teacher_fullname;
    }

    public void setTeacher_fullname(String teacher_fullname) {
        this.teacher_fullname = teacher_fullname;
    }

    public String getTeacher_university_number() {
        return teacher_university_number;
    }

    public void setTeacher_university_number(String teacher_university_number) {
        this.teacher_university_number = teacher_university_number;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacher_sepcialist() {
        return teacher_sepcialist;
    }

    public void setTeacher_sepcialist(String teacher_sepcialist) {
        this.teacher_sepcialist = teacher_sepcialist;
    }

    public String getTeacher_semeter() {
        return teacher_semeter;
    }

    public void setTeacher_semeter(String teacher_semeter) {
        this.teacher_semeter = teacher_semeter;
    }

    public String getTeacher_password() {
        return teacher_password;
    }

    public void setTeacher_password(String teacher_password) {
        this.teacher_password = teacher_password;
    }

    public String getStudent_img() {
        return student_img;
    }

    public void setStudent_img(String student_img) {
        this.student_img = student_img;
    }
}
