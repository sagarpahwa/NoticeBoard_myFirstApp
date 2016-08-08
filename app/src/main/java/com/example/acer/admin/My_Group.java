package com.example.acer.admin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class My_Group extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    ServerDBClass sdc = new ServerDBClass();

    ArrayList<Group_Class> gData;
    Group_List_Adapter gAdapter;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_group);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        lv = (ListView)findViewById(android.R.id.list);

        if (sdc.setConnection(this))
        {
            gData = sdc.getMyGroups();
            gAdapter = new Group_List_Adapter(this,gData);
            lv.setAdapter(gAdapter);
            lv.setOnItemClickListener(this);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Internet Not Found..", Toast.LENGTH_LONG).show();
        }
    }

    //---------- Action Bar Menu  -----------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.emp_action_menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.empLogout)
        {
            Intent intent = new Intent(this, Login_Register.class);
            MaintainSession.userType = "";
            startActivity(intent);
            finish();
        }
        return true;
    }
    //----------  End of Action Bar Menu  ----------------


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(this, Panel_Faculty.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Group_Class gc=gData.get(position);
        Intent intent=new Intent(this,Send_Message.class);
        intent.putExtra("gid",gc.getGroupID());
        startActivity(intent);
        finish();
    }
}
