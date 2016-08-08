package com.example.acer.admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by parveen on 16/05/2016.
 */
public class Send_Message_Adapter extends BaseAdapter
{
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public Send_Message_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater =(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi=convertView;

        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap = data.get(position);

        if(convertView==null)
        {
            if(MaintainSession.userType.equals("Faculty"))
                vi = inflater.inflate(R.layout.send_message_right_layout, null);
            else
                vi = inflater.inflate(R.layout.send_message_left_layout, null);
        }

        TextView msgId = (TextView)vi.findViewById(R.id.lblMsgId);
        TextView Name = (TextView)vi.findViewById(R.id.lblMsgFrom);
        TextView designation = (TextView)vi.findViewById(R.id.lblDesignation);
        TextView groupName = (TextView)vi.findViewById(R.id.lblGroupName);
        TextView Message= (TextView)vi.findViewById(R.id.txtMsg);
        TextView msgDate = (TextView)vi.findViewById(R.id.msgDate);

        msgId.setText(hashMap.get("messageId"));
        Name.setText(hashMap.get("senderName"));
        designation.setText(hashMap.get("designation"));
        groupName.setText(hashMap.get("groupName"));
        Message.setText(hashMap.get("message"));
        msgDate.setText(hashMap.get("msgDate"));

        return vi;
    }
}
