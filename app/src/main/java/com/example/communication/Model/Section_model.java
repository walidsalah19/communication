package com.example.communication.Model;

public class Section_model {
    String SectioName,SectionNumber,Course_name,Course_num;

    public Section_model(String sectioName, String sectionNumber, String course_name, String course_num) {
        SectioName = sectioName;
        SectionNumber = sectionNumber;
        Course_name = course_name;
        Course_num = course_num;
    }

    public String getSectioName() {
        return SectioName;
    }

    public String getSectionNumber() {
        return SectionNumber;
    }

    public String getCourse_name() {
        return Course_name;
    }

    public String getCourse_num() {
        return Course_num;
    }
}
