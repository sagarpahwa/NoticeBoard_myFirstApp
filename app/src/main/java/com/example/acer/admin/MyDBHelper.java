package com.example.acer.admin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by parveen on 22/04/2016.
 */
public class MyDBHelper extends SQLiteOpenHelper
{
    public MyDBHelper(Context context)
    {
        super(context, "MyDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table Students(studentID integer primary key autoincrement, studentName text, studentRollNo text, studentMobileNo text, studentPassword text, studentClassSpinner text, studentYearSpinner text)");
        db.execSQL("create table Employees(empID integer primary key autoincrement, empName text, empDesignation text, empCode text, empMobileNo text, empPassword text)");
        db.execSQL("create table Admin( adminName text, adminPassword text)");
        db.execSQL("create table Create_Group(groupId integer primary key autoincrement, groupName text, groupClass text, groupSemester text)");
        db.execSQL("create table AssignGroupToTeacher(assignTeacherID integer primary key autoincrement,teacherId  text, groupId text)");
        db.execSQL("create table AssignGroupToStudent(assignStudentID integer primary key autoincrement,studentId  text, groupId text)");
        db.execSQL("create table Message(msgID integer primary key autoincrement,message  text,teacherId text, groupId text)");

        db.execSQL("insert into Admin values('admin','pass')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
