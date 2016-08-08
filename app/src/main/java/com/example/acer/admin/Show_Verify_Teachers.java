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

public class Show_Verify_Teachers extends AppCompatActivity// implements CompoundButton.OnCheckedChangeListener
{
    ServerDBClass sdc=new ServerDBClass();

    ArrayList<Teachers_Class> teacherData;
    Teachers_Class tc;
    Teachers_Adapter tad;

    String strGroupId="", strEmpID;
    int size;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_verify_teachers);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        strGroupId=getIntent().getExtras().get("gid").toString();

        Toast.makeText(Show_Verify_Teachers.this,strGroupId,Toast.LENGTH_LONG).show();

        lv = (ListView)findViewById(R.id.listView1);

        /*------  Show Record from Server DATABASE  ---------*/

        if(sdc.setConnection(this))
        {
            teacherData = sdc.getVerifyTeachers();
            tad = new Teachers_Adapter(this,teacherData);
            lv.setAdapter(tad);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Internet Not Found..", Toast.LENGTH_LONG).show();
        }
        /*-------  End of Record from Server DATABASE  ---------*/

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                CheckBox checkBox = (CheckBox)view;
                if (checkBox.isChecked())
                {
                    tc = (Teachers_Class)parent.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),"Clicked on Row: " + tc.getEmpName(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplication(),"uncheck"+tc.getEmpName(),Toast.LENGTH_LONG).show();
                }

                  }
        });*/
    }

    //-------- Start Action Bar Menu  -----

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin_action_menu,menu);
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

    //--------- End of Action Bar Menu  -----------

    public void AssignToTeacher(View view)
    {
        size = teacherData.size();
        for (int i=0;i<size;i++)
        {
            tc=teacherData.get(i);
            if (tc.isSelected()==true)
            {
                strEmpID=tc.getEmpID();
                if(sdc.setConnection(this))
                {
                    sdc.assignGroupToTeacher(strGroupId, strEmpID);
                    Toast.makeText(getApplicationContext(),strGroupId+","+"Assign To "+ strEmpID+","+size,Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void Cancel(View view)
    {
        Intent intent = new Intent(this, Show_Group_Data.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Show_Group_Data.class);
        startActivity(intent);
        finish();
    }

    /*@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = lv.getPositionForView(buttonView);
        System.out.print("pos["+pos+"]");
        if (pos != ListView.INVALID_POSITION)
        {
        }
    }*/
}
