package com.example.acer.admin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Change_Admin_Password extends AppCompatActivity
{
    ServerDBClass sdc=new ServerDBClass();

    EditText oldPassword, newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_admin_password);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        oldPassword=(EditText)findViewById(R.id.oldPassword);
        newPassword=(EditText)findViewById(R.id.newPassword);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin_action_menu3, menu);
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
        return true;
    }


    public void ChangePassword(View v)
    {
        if (sdc.setConnection(this))
        {
            int i=sdc.updateAdminPassword(oldPassword.getText().toString(), newPassword.getText().toString());
            if (i>0)
            {
                Toast.makeText(getApplicationContext(),"password has been Changed",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this,Panel_Admin.class);
        startActivity(intent);
        finish();
    }
}



