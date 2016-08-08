package com.example.acer.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Change_Student_Password extends AppCompatActivity
{
    ServerDBClass sdc=new ServerDBClass();

    EditText oldStPassword, newStPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_student_password);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        oldStPassword=(EditText)findViewById(R.id.oldStPassword);
        newStPassword=(EditText)findViewById(R.id.newStPassword);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.student_action_menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.studentLogout)
        {
            Intent intent = new Intent(this, Login_Register.class);
            MaintainSession.userType = "";
            startActivity(intent);
            finish();
        }
        return true;
    }


    public void ChangePassword(View v)
    {
        String stId = MaintainSession.userid;
        if (sdc.setConnection(this))
        {
            int i=sdc.updateStudentPassword(oldStPassword.getText().toString(), newStPassword.getText().toString(),stId);
            if (i>0)
            {
                Toast.makeText(getApplicationContext(),"Password has been Changed",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, Panel_Student.class);
        startActivity(intent);
        finish();
    }
}



