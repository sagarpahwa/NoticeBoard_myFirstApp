package com.example.acer.admin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Teacher_For_Verify extends AppCompatActivity
{
    ServerDBClass sdc=new ServerDBClass();

    ArrayList<Teachers_Class> teacherData;

    int size;
    String strEmpID;

    ListView lv;
    Teachers_Adapter tad;
    Teachers_Class tc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_for_verify);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        lv = (ListView)findViewById(R.id.listView1);

        if(sdc.setConnection(this))
        {
            teacherData = sdc.getRegisterTeachers();
            tad = new Teachers_Adapter(this, teacherData);
            lv.setAdapter(tad);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Internet Not Found..", Toast.LENGTH_LONG).show();
        }
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

    public void VerifyTeacher(View v)
    {
        size=teacherData.size();
        for (int i=0;i<size;i++)
        {
            tc = teacherData.get(i);
            if (tc.isSelected()==true)
            {
                strEmpID=tc.getEmpID();
                if (sdc.setConnection(this))
                {
                    sdc.verifyTeacher(strEmpID);
                    Toast.makeText(getApplicationContext(),"Verify"+strEmpID+","+size,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, Teacher_For_Verify.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public void Delete(View v)
    {
        size = teacherData.size();
        for (int i=0;i<size;++i)
        {
            tc = teacherData.get(i);
            if (tc.isSelected()==true)
            {
                strEmpID = tc.getEmpID();
                if (sdc.setConnection(this))
                {
                    sdc.deleteTeacher(strEmpID);
                    Toast.makeText(getApplicationContext(),"Delete"+strEmpID+","+size,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, Teacher_For_Verify.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this, Panel_Admin.class);
        startActivity(intent);
        finish();
    }
}
