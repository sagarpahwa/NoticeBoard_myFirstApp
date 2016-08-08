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

public class Student_For_Verify extends AppCompatActivity
{
    ServerDBClass sdc=new ServerDBClass();

    ArrayList<Student_Check_Class> studentVerifyData;

    int size;
    String strStudentID;

    ListView lv;
    Student_Check_Adapter sad;
    Student_Check_Class svc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_for_verify);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        lv = (ListView)findViewById(R.id.listView1);

        if(sdc.setConnection(this))
        {
            studentVerifyData = sdc.getRegisterStudent();
            sad = new Student_Check_Adapter(this,studentVerifyData);
            lv.setAdapter(sad);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Internet Not Found..", Toast.LENGTH_LONG).show();
        }
    }

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

    public void VerifyStudent(View v)
    {
        size = studentVerifyData.size();
        for (int i=0;i<size;i++)
        {
            svc = studentVerifyData.get(i);
            if (svc.isSelected()==true)
            {
                strStudentID=svc.getStudentID();
                if (sdc.setConnection(this))
                {
                    sdc.verifyStudent(strStudentID);
                    Toast.makeText(getApplicationContext(),"Student Verified",Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),"Verify"+strStudentID+","+size,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, Student_For_Verify.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public void Delete(View v)
    {
        size=studentVerifyData.size();
        for (int i=0;i<size;++i)
        {
            svc = studentVerifyData.get(i);
            if (svc.isSelected()==true)
            {
                strStudentID = svc.getStudentID();
                if (sdc.setConnection(this))
                {
                    sdc.deleteStudent(strStudentID);
                    Toast.makeText(getApplicationContext(),"Student Deleted",Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),"Delete"+strStudentID+","+size,Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(this, Student_For_Verify.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this, Panel_Faculty.class);
        startActivity(intent);
        finish();
    }

}
