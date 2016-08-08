package com.example.acer.admin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Create_Group extends AppCompatActivity
{
    ServerDBClass sdc=new ServerDBClass();

    Spinner studentClassSpinner,studentSemesterSpinner;

    String gName="",gClass="";
    String[] Class,Semester;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        /*------------ Start Memory Initialization --------------*/

        studentClassSpinner = (Spinner)findViewById(R.id.studentClassSpinner);
        studentSemesterSpinner = (Spinner) findViewById(R.id.studentSemesterSpinner);

        Class = new String[]{"Select Class","MCA","M.Sc","M.Tech","BCA","B.Sc","B.Tech"};
        Semester = new String[]{"Select Semester","I Sem","II Sem","III Sem","IV Sem","V Sem","VI Sem","VII Sem","VIII Sem"};

        /*------------ End of  Memory Initialization --------------*/
        //------------ Start Spinner Adapter  --------------

        ArrayAdapter<String> strClassAdapter = new ArrayAdapter<String>(this,R.layout.my_spinner_layout, Class);
        strClassAdapter.setDropDownViewResource(R.layout.my_spinner_layout);
        studentClassSpinner.setAdapter(strClassAdapter);

        ArrayAdapter<String> strSemAdapter = new ArrayAdapter<String>(this,R.layout.my_spinner_layout, Semester);
        strSemAdapter.setDropDownViewResource(R.layout.my_spinner_layout);
        studentSemesterSpinner.setAdapter(strSemAdapter);

       //---------  End of Spinner Adapter   ------------------
       // --------  Spinner Pass Selected Value  --------------------

        studentClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (studentClassSpinner.getSelectedItemPosition()>0)
                    gName=studentClassSpinner.getSelectedItem().toString();
                //else
                    //Toast.makeText(Create_Group.this,"Select Class",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        studentSemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (studentSemesterSpinner.getSelectedItemPosition()>0)
                    gClass=studentSemesterSpinner.getSelectedItem().toString();
                //else
                    //Toast.makeText(Create_Group.this,"Select Semester",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        /*-----------  END OF SPINNER  ---------------------*/
    }
    /*--------- START DATABASE CODING -------------------*/
    public void Create_Group(View v)
    {
        try
        {
            // -----  Server DATABASE Code  ----------------------------------

                if (sdc.setConnection(this))
                {
                    if (gName.equals("") || gClass.equals(""))
                    {
                        Toast.makeText(Create_Group.this, "Select all fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int i = sdc.insertGroup(gName+" "+gClass, gName, gClass);
                        if (i > 0)
                        {
                            Intent intent = new Intent(this, Show_Group_Data.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Group already exist!!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Internet Connection not found!!", Toast.LENGTH_LONG).show();
                }
        }
        catch (Exception ee)
        {
            Log.e("Create Group Error:", ee.toString());
        }
    }
    /*------------- END OF DATABASE  -----------------------*/

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent intent = new Intent(this, Panel_Admin.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin_action_menu_1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.showGroup)
        {
            Intent intent = new Intent(this, Show_Group_Data.class);
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
}

