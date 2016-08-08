package com.example.acer.admin;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Panel_Admin extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_admin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.adminLogout)
        {
            Intent intent = new Intent(this, Login_Register.class);
            MaintainSession.userType = "";
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.changeAdminPassword)
        {
            Intent intent = new Intent(this, Change_Admin_Password.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void CreateGroup(View v)
    {
        Intent intent = new Intent(this,Create_Group.class);
        startActivity(intent);
        finish();
    }

    //------  Manage Group.. by Assign a Group To Teacher ------

    public void ManageGroup(View v)
    {
        Intent intent = new Intent(this, Show_Group_Data.class);
        startActivity(intent);
        finish();
    }

    //------- Manage Employee for Verify and Delete  -----------

    public void VerifyEmployee(View v)
    {
        Intent intent = new Intent(this,Teacher_For_Verify.class);
        startActivity(intent);
        finish();
    }

    public void ManageEmployee(View v)
    {
        Intent intent = new Intent(this,Show_Verify_Emp.class);
        startActivity(intent);
        finish();
    }

    public void ChangeAdminPassword(View v)
    {
        Intent intent=new Intent(this,Change_Admin_Password.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Login_Register.class);
        MaintainSession.userType = "";
        startActivity(intent);
        finish();
    }
}
