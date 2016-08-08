package com.example.acer.admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by parveen on 11/05/2016.
 */
public class Student_Check_Adapter extends BaseAdapter
{
    ArrayList<Student_Check_Class> studentData;
    Activity activity;

    public Student_Check_Adapter(Activity activity, ArrayList<Student_Check_Class> data)
    {
        studentData = data;
        this.activity = activity;
    }

    static class ViewHolder
    {
        protected TextView textName,textRollNo,textMobileNo,textClass,textSem;
        protected CheckBox checkbox,checkName;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();

            convertView = inflater.inflate(R.layout.student_checkbox_layout_1, null);

            if(position%2==0)
                convertView = inflater.inflate(R.layout.student_checkbox_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.checkName = (CheckBox)convertView.findViewById(R.id.checkName);
            viewHolder.textRollNo = (TextView) convertView.findViewById(R.id.studentRollNo);
            viewHolder.textMobileNo =(TextView) convertView.findViewById(R.id.studentMobileNo);
            viewHolder.textClass =(TextView) convertView.findViewById(R.id.studentClass);
            viewHolder.textSem =(TextView) convertView.findViewById(R.id.studentYear);


            viewHolder.checkName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    Student_Check_Class tc = studentData.get(getPosition);
                    tc.setSelected(buttonView.isChecked());
                    studentData.set(getPosition, tc);
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.checkName, viewHolder.checkName);
            convertView.setTag(R.id.studentRollNo, viewHolder.textRollNo);
            convertView.setTag(R.id.studentMobileNo, viewHolder.textMobileNo);
            convertView.setTag(R.id.studentClass, viewHolder.textClass);
            convertView.setTag(R.id.studentYear, viewHolder.textSem);
            /*convertView.setTag(R.id.cbox, viewHolder.checkbox);*/
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*viewHolder.checkbox.setTag(position); // This line is important.*/
        viewHolder.checkName.setTag(position); // This line is important.

        viewHolder.checkName.setText(studentData.get(position).getStudentName());
        viewHolder.textRollNo.setText(studentData.get(position).getStudentRollNo());
        viewHolder.textMobileNo.setText(studentData.get(position).getStudentMobileNo());
        viewHolder.textClass.setText(studentData.get(position).getStudentClass());
        viewHolder.textSem.setText(studentData.get(position).getStudentYear());
        /*viewHolder.checkbox.setChecked(teachersData.get(position).isSelected());*/
        viewHolder.checkName.setChecked(studentData.get(position).isSelected());

        return convertView;
    }
}
