package com.example.acer.admin;

/**
 * Created by parveen on 04/05/2016.
 */
public class Student_Check_Class
{
    private String studentID;
    private String studentName;
    private String studentRollNo;
    private String studentMobileNo;
    private String studentClass;
    private String studentYear;
    boolean selected = false;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public String getStudentMobileNo() {
        return studentMobileNo;
    }

    public void setStudentMobileNo(String studentMobileNo) {
        this.studentMobileNo = studentMobileNo;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(String studentYear) {
        this.studentYear = studentYear;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
