package com.example.acer.admin;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class Group_List_Adapter extends BaseAdapter
{
    ArrayList<Group_Class> groupData;
    Activity activity;

    public Group_List_Adapter(Activity activity, ArrayList<Group_Class> data)
    {
        groupData=data;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return groupData.size();
    }

    @Override
    public Object getItem(int position) {
        return groupData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view=activity.getLayoutInflater().inflate(R.layout.group_list_layout_1, null);

        if(position%2==0)

            view=activity.getLayoutInflater().inflate(R.layout.group_list_layout, null);

        TextView tvName=(TextView)view.findViewById(R.id.groupName);
        TextView tvClass=(TextView)view.findViewById(R.id.gClass);
        TextView tvYear=(TextView)view.findViewById(R.id.gYear);
        TextView tvID=(TextView)view.findViewById(R.id.groupID);

        Group_Class gc=groupData.get(position);
        tvID.setText(gc.getGroupID());
        tvName.setText(gc.getGroupName());
        tvClass.setText(gc.getGroupClass());
        tvYear.setText(gc.getGroupYear());

        return view;
    }
}
