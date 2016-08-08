package com.example.acer.admin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEmp extends AppCompatActivity
{
    ServerDBClass sdc=new ServerDBClass();

    EditText empName, empDesignation, empCode, empMobileNo, empPassword;

    String strMobile, strPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_emp);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        /*------------ Start MEMORY INITIALIZATION  ------------------*/

        strMobile=getIntent().getExtras().get("mobile").toString();
        strPass=getIntent().getExtras().get("password").toString();

        empName = (EditText)findViewById(R.id.empName);
        empDesignation = (EditText)findViewById(R.id.empDesignation);
        empCode = (EditText)findViewById(R.id.empCode);
        empMobileNo = (EditText)findViewById(R.id.empMobileNo);
        empPassword = (EditText)findViewById(R.id.empPassword);

        empMobileNo.setText(strMobile);
        empPassword.setText(strPass);

        /*------------- END OF MEMORY INITIALIZATION ------------*/
    }

    /*--------------   Start DATABASE --------------------*/

    public void SaveEmpData(View v)
    {
        try
        {
            /*--------  Local DATABASE  ---------------*/
/*
            MyDBHelper obj = new MyDBHelper(this);
            SQLiteDatabase db = obj.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("empName", empName.getText().toString());
            cv.put("empDesignation",empDesignation.getText().toString());
            cv.put("empCode",empCode.getText().toString());
            cv.put("empMobileNo",strMobile);
            cv.put("empPassword", strPass);

            long l = db.insert("Employees", "", cv);
            if(l>0  && i>0 )
                {
                    Intent intent = new Intent(this,OTP_Admin_Notice_Msg.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_LONG).show();
                }
            */

            /*---------- Server DATABASE  ------------------------*/

            if (sdc.setConnection(this))
            {
                if (empName.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Enter Name !",Toast.LENGTH_LONG).show();
                    //empName.setText("Enter Your Name !");
                    //empName.setTextColor(Color.RED);
                }
                else if (empDesignation.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Enter Designation !",Toast.LENGTH_LONG).show();
                    //empDesignation.setText("Enter Your Name !");
                    //empDesignation.setTextColor(Color.RED);
                }
                else if (empCode.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Enter Employee Code !",Toast.LENGTH_LONG).show();
                    //empCode.setText("Enter Your Name !");
                    //empDesignation.setTextColor(Color.RED);
                }
                else
                {
                    int i=sdc.insertEmployee(empName.getText().toString(),empDesignation.getText().toString(),empCode.getText().toString(),strMobile,strPass);
                    if(i>0 )
                    {
                        Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this,OTP_Admin_Notice_Msg.class);
                        startActivity(intent);
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
            Log.e("Add Employee Error:",ee.toString());
        }
    }
    /*------------- END OF DATABASE  -----------------------*/

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
