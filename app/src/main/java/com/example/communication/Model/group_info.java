package com.example.communication.Model;

public class group_info {
    static String course_name,course_number,section_number,group_id,chat_type;

    public static String getChat_type() {
        return chat_type;
    }

    public static void setChat_type(String chat_type) {
        group_info.chat_type = chat_type;
    }

    public static String getGroup_id() {
        return group_id;
    }

    public static void setGroup_id(String group_id) {
        group_info.group_id = group_id;
    }

    public static String getCourse_name() {
        return course_name;
    }

    public static void setCourse_name(String course_name) {
        group_info.course_name = course_name;
    }

    public static String getCourse_number() {
        return course_number;
    }

    public static void setCourse_number(String course_number) {
        group_info.course_number = course_number;
    }

    public static String getSection_number() {
        return section_number;
    }

    public static void setSection_number(String section_number) {
        group_info.section_number = section_number;
    }
}
