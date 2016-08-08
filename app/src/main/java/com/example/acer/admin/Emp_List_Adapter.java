package com.example.acer.admin;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by parveen on 03/05/2016.
 */
public class Emp_List_Adapter extends BaseAdapter
{
    ArrayList<Emp_Class> empData;
    Activity activity;

    public Emp_List_Adapter(Activity activity, ArrayList<Emp_Class> data)
    {
        empData = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return empData.size();
    }

    @Override
    public Object getItem(int position) {
        return empData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view=activity.getLayoutInflater().inflate(R.layout.emp_list_row1, null);

        if(position%2==0)

            view=activity.getLayoutInflater().inflate(R.layout.emp_list_row, null);

        TextView tvName=(TextView)view.findViewById(R.id.empName);
        TextView tvDesignation=(TextView)view.findViewById(R.id.empDesignation);
        TextView tvCode=(TextView)view.findViewById(R.id.empCode);
        TextView tvMobileNo=(TextView)view.findViewById(R.id.empMobileNo);
        TextView tvID=(TextView)view.findViewById(R.id.empID);

        Emp_Class ec=empData.get(position);
        tvID.setText(ec.getEmpID());
        tvName.setText(ec.getEmpName());
        tvDesignation.setText(ec.getEmpDesignation());
        tvCode.setText(ec.getEmpCode());
        tvMobileNo.setText(ec.getEmpMobileNo());

        return view;
    }
}
