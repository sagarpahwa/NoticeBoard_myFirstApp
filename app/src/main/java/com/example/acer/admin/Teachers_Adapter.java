package com.example.acer.admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.ArrayList;

public class Teachers_Adapter extends BaseAdapter
{
    ArrayList<Teachers_Class> teachersData;
    Activity activity;

    public Teachers_Adapter(Activity activity, ArrayList<Teachers_Class> data)
    {
        teachersData = data;
        this.activity = activity;
    }

    static class ViewHolder
    {
        protected TextView textCode,textDesignation,textMobileNo;
        protected CheckBox checkName;
    }

    @Override
    public int getCount() {
        return teachersData.size();
    }

    @Override
    public Object getItem(int position) {
        return teachersData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();

            convertView = inflater.inflate(R.layout.teachers_checkbox_layout_1, null);

            if(position%2==0)
            convertView = inflater.inflate(R.layout.teachers_checkbox_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.checkName = (CheckBox)convertView.findViewById(R.id.checkName);
            /*viewHolder.textName = (TextView) convertView.findViewById(R.id.empName);*/
            viewHolder.textMobileNo =(TextView) convertView.findViewById(R.id.empMobileNo);
            viewHolder.textCode =(TextView) convertView.findViewById(R.id.empCode);
            viewHolder.textDesignation =(TextView) convertView.findViewById(R.id.empDesignation);

            /*viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.cbox);*/
            viewHolder.checkName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            /*viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()*/ {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    Teachers_Class tc = teachersData.get(getPosition);
                    tc.setSelected(buttonView.isChecked());
                    teachersData.set(getPosition, tc);

                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.checkName, viewHolder.checkName);
            /*convertView.setTag(R.id.empName, viewHolder.checkName);*/
            /*convertView.setTag(R.id.empName, viewHolder.textName);*/
            convertView.setTag(R.id.empMobileNo, viewHolder.textMobileNo);
            convertView.setTag(R.id.empCode, viewHolder.textCode);
            convertView.setTag(R.id.empDesignation, viewHolder.textDesignation);
            /*convertView.setTag(R.id.cbox, viewHolder.checkbox);*/

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*viewHolder.checkbox.setTag(position); // This line is important.*/
        viewHolder.checkName.setTag(position); // This line is important.

        viewHolder.checkName.setText(teachersData.get(position).getEmpName());
        /*viewHolder.textName.setText(teachersData.get(position).getEmpName());*/
        viewHolder.textMobileNo.setText(teachersData.get(position).getEmpMobileNo());
        viewHolder.textCode.setText(teachersData.get(position).getEmpCode());
        viewHolder.textDesignation.setText(teachersData.get(position).getEmpDesignation());
        /*viewHolder.checkbox.setChecked(teachersData.get(position).isSelected());*/
        viewHolder.checkName.setChecked(teachersData.get(position).isSelected());

        return convertView;
    }
}
