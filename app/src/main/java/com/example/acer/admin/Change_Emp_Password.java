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

public class Change_Emp_Password extends AppCompatActivity
{
    ServerDBClass sdc=new ServerDBClass();

    EditText oldPass,newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_emp_password);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        oldPass=(EditText)findViewById(R.id.oldEmpPassword);
        newPass=(EditText)findViewById(R.id.newEmpPassword);

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


    public void ChangePassword(View v)
    {
        String empId = MaintainSession.userid;

        if (sdc.setConnection(this))
        {
            int i=sdc.updateEmpPassword(oldPass.getText().toString(), newPass.getText().toString(),empId);
            if (i>0)
            {
                Toast.makeText(getApplicationContext(),"password has been Changed",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, Panel_Faculty.class);
        startActivity(intent);
        finish();
    }
}



