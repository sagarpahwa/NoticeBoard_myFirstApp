package com.example.acer.admin;

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

public class Show_Verify_Emp extends AppCompatActivity
{
    ServerDBClass sdc = new ServerDBClass();

    /*ArrayList<String> aa;*/
    ListView lv;
    Teachers_Adapter tad;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_verify_emp);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        lv = (ListView)findViewById(android.R.id.list);

        /*------  Show Record from Server DATABASE  ---------*/

        if(sdc.setConnection(this))
        {
            ArrayList<Teachers_Class> tchData = sdc.getVerifyTeachers();
            tad = new Teachers_Adapter(this, tchData);
            lv.setAdapter(tad);
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
        getMenuInflater().inflate(R.menu.admin_action_menu3,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
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
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent intent = new Intent(this, Panel_Admin.class);
        startActivity(intent);
        finish();
    }
}