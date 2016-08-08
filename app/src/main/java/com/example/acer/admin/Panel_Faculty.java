package com.example.acer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Panel_Faculty extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_faculty);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.emp_action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.changeEmpPassword)
        {
            Intent intent = new Intent(Panel_Faculty.this, Change_Emp_Password.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.empLogout)
        {
            Intent intent = new Intent(this, Login_Register.class);
            MaintainSession.userType = "";
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void VerifyStudents(View v)
    {
        Intent intent = new Intent(Panel_Faculty.this,Student_For_Verify.class);
        startActivity(intent);
        finish();
    }

    public void ManageStudent(View v)
    {
        Intent intent=new Intent(this, Show_Verify_Student.class);
        startActivity(intent);
        finish();
    }

    public void MyGroup(View v)
    {
        Intent intent=new Intent(this, My_Group.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this, Login_Register.class);
        MaintainSession.userType = "";
        startActivity(intent);
        finish();
    }
}
