package com.example.acer.admin;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by parveen on 02/05/2016.
 */
public class Student_Normal_List_Adapter extends BaseAdapter
{
    ArrayList<Student_Normal_Class> studentData;
    Activity activity;

    public Student_Normal_List_Adapter(Activity activity, ArrayList<Student_Normal_Class> data)
    {
        studentData=data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return studentData.size();
    }

    @Override
    public Object getItem(int position) {
        return studentData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Student_Normal_Class sc=studentData.get(position);
        //convertView = null;

            convertView = activity.getLayoutInflater().inflate(R.layout.student_normal_layout_1, null);
        if (position % 2 == 0)
            convertView = activity.getLayoutInflater().inflate(R.layout.student_normal_layout, null);

        if(sc.isSelected())
        {
                convertView=activity.getLayoutInflater().inflate(R.layout.student_normal_layout_1_selected, null);
            if(position%2==0)
                convertView=activity.getLayoutInflater().inflate(R.layout.student_normal_layout_selected, null);
        }

        TextView tvName=(TextView)convertView.findViewById(R.id.studentName);
        TextView tvRollNo=(TextView)convertView.findViewById(R.id.studentRollNo);
        TextView tvMobileNo=(TextView)convertView.findViewById(R.id.studentMobileNo);
        TextView tvClass=(TextView)convertView.findViewById(R.id.studentClass);
        TextView tvYear=(TextView)convertView.findViewById(R.id.studentYear);
        TextView tvID=(TextView)convertView.findViewById(R.id.studentID);

        tvID.setText(sc.getStudentID());
        tvName.setText(sc.getStudentName());
        tvRollNo.setText(sc.getStudentRollNo());
        tvMobileNo.setText(sc.getStudentMobileNo());
        tvClass.setText(sc.getStudentClass());
        tvYear.setText(sc.getStudentYear());

        return convertView;
    }
}
