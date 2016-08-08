package com.example.acer.admin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddStudent extends AppCompatActivity
{
    EditText studentName, studentRollNo,studentMobileNo,studentPassword;
    Spinner studentClassSpinner,studentSemesterSpinner;

    String strMobile, strPass;
    String strSemester="",strClass="";
    String[] Class,Semester;

    ServerDBClass sdc=new ServerDBClass();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        /*------------ Start Memory Initialization --------------*/

        strMobile=getIntent().getExtras().get("mobile").toString();
        strPass=getIntent().getExtras().get("password").toString();

        studentName = (EditText) findViewById(R.id.studentName);
        studentRollNo = (EditText) findViewById(R.id.studentRollNo);
        studentMobileNo = (EditText) findViewById(R.id.studentMobileNo);
        studentPassword = (EditText) findViewById(R.id.studentPassword);
        studentClassSpinner = (Spinner)findViewById(R.id.studentClassSpinner);
        studentSemesterSpinner = (Spinner) findViewById(R.id.studentSemesterSpinner);

        studentMobileNo.setText(strMobile);
        studentPassword.setText(strPass);

        /*------------ End of  Memory Initialization --------------*/

        Class = new String[]{"Select Class","MCA","M.Sc","M.Tech","BCA","B.Sc","B.Tech"};
        Semester = new String[]{"Select Semester","I Sem","II Sem","III Sem","IV Sem","V Sem","VI Sem","VII Sem","VIII Sem"};

        /*------------ Start SPINNER Adapter  ---------------*/

        ArrayAdapter<String> strClassAdapter = new ArrayAdapter<String>(this,R.layout.my_spinner_layout, Class);
        strClassAdapter.setDropDownViewResource(R.layout.my_spinner_layout);
        studentClassSpinner.setAdapter(strClassAdapter);

        ArrayAdapter<String> strSemAdapter = new ArrayAdapter<String>(this,R.layout.my_spinner_layout, Semester);
        strSemAdapter.setDropDownViewResource(R.layout.my_spinner_layout);
        studentSemesterSpinner.setAdapter(strSemAdapter);

        //------------ Select Spinner Value  ----------------

        studentClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (studentClassSpinner.getSelectedItemPosition() > 0)
                {
                    strClass = studentClassSpinner.getSelectedItem().toString();
                }
                else
                {
                    Toast.makeText(AddStudent.this, "Select Class", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        studentSemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (studentSemesterSpinner.getSelectedItemPosition()>0)
                {
                    strSemester = studentSemesterSpinner.getSelectedItem().toString();
                }
                else
                {
                    Toast.makeText(AddStudent.this, "Select Semester", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        /*--------------  END OF SPINNER  ---------------------*/
    }
    /*--------- START DATABASE CODING -------------------*/

    public void SaveStudentData(View v)
    {
        try
        {
            // ----  Local DATABASE Code for Save Record in Mobile ---------
/*
            MyDBHelper obj = new MyDBHelper(this);
            SQLiteDatabase db = obj.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("studentName",studentName.getText().toString());
            cv.put("studentRollNo",studentRollNo.getText().toString());
            cv.put("studentMobileNo",strMobile);
            cv.put("studentPassword",strPass);
            cv.put("studentClassSpinner",strClass);
            cv.put("studentSemSpinner",strSemester);

            long l = db.insert("Students","",cv);

            if(l>0 && i >0){Intent intent = new Intent(this, Show_Verify_Student.class);
                startActivity(intent);}
*/

            // ----- Server DATABASE Code  ------------

            if(sdc.setConnection(this))
            {
                String strGroupID=sdc.getGroupsID(strClass,strSemester);
                if(strGroupID.equals("0"))
                {
                    Toast.makeText(AddStudent.this, "Your Group not available!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (studentName.getText().toString().length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "Enter Name !", Toast.LENGTH_LONG).show();
                        //studentName.setText("Enter Your Name !");
                        //studentName.setTextColor(Color.RED);
                    }
                    else if (studentRollNo.getText().toString().length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "Enter Roll No. !", Toast.LENGTH_LONG).show();
                        //studentRollNo.setText("Enter Your Name !");
                        //studentRollNo.setTextColor(Color.RED);
                    }
                    else
                    {
                        int i = sdc.insertStudent(studentName.getText().toString(), studentRollNo.getText().toString(), strMobile, strPass, strClass, strSemester, strGroupID);
                        if (i > 0)
                        {
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(this, OTP_Admin_Notice_Msg.class);
                            startActivity(intent);
                        }
                    }
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Internet Connection not found!!", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ee)
        {
            Log.e("Add Student Error:",ee.toString());
        }
    }
    /*------------- END OF DATABASE  -----------------------*/
    @Override
    public void onBackPressed(){ super.onBackPressed();}
}

