package com.example.acer.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class Show_Group_Data extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    ServerDBClass sdc = new ServerDBClass();

    ArrayList<Group_Class> groupData=null;

    ListView lv;
    Group_List_Adapter gad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_group_data);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        lv = (ListView) findViewById(android.R.id.list);

        /*------  Show Record from Server DATABASE  ---------*/

        if (sdc.setConnection(this))
        {
            groupData= sdc.getGroups();
            gad = new Group_List_Adapter(this, groupData);
            lv.setAdapter(gad);
            lv.setOnItemClickListener(this);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Internet Not Found..", Toast.LENGTH_LONG).show();
        }

        /*-------  End of Record from Server DATABASE  ---------*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin_action_menu_2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.CreateGroup)
        {
            Intent intent = new Intent(this, Create_Group.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.adminLogout)
        {
            Intent intent = new Intent(this, Login_Register.class);
            MaintainSession.userType = "";
            startActivity(intent);
            finish();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Panel_Admin.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Group_Class gc=groupData.get(position);

        Intent intent = new Intent(this, Show_Verify_Teachers.class);
        intent.putExtra("gid",gc.getGroupID());
        startActivity(intent);
        finish();
    }
}

