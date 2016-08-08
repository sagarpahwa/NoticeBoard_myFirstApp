package com.example.acer.admin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Register extends AppCompatActivity implements View.OnClickListener
{
    ServerDBClass sdc = new ServerDBClass();

    Button loginButton,regButton;

    EditText mobile, password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        mobile=(EditText)findViewById(R.id.mobile);
        password=(EditText)findViewById(R.id.password);

        loginButton = (Button)findViewById(R.id.loginButton);
        regButton = (Button)findViewById(R.id.regButton);

        loginButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==R.id.regButton)
        {
            try
            {
                if (mobile.getText().toString().length()==0)
                {
                    Toast.makeText(Login_Register.this,"Enter Mobile No. !",Toast.LENGTH_LONG).show();
                }
                else if (password.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Enter Password !",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=new Intent(this,OTPVerify.class);
                    intent.putExtra("mobile",  mobile.getText().toString());
                    intent.putExtra("password",  password.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
            catch (Exception ee)
            {
                Log.e("Register Error:", ee.toString());
            }
        }

        if(v.getId()==R.id.loginButton)
        {
            try
            {
                if (mobile.getText().toString().length()==0)
                {
                    Toast.makeText(Login_Register.this,"Enter User Name \n or \n Mobile No. !",Toast.LENGTH_LONG).show();
                    //mobile.setText("Enter User Name/Mobile No. !");
                    //mobile.setTextColor(Color.RED);
                }
                else if(password.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Enter Password !",Toast.LENGTH_LONG).show();
                    //password.setText("Enter Password !");
                    //password.setTextColor(Color.RED);
                }
                else if (sdc.setConnection(this))
                {
                    int i = sdc.checkLogin(mobile.getText().toString(),password.getText().toString());
                    if(i > 0 && MaintainSession.userType.equals("Admin"))
                    {
                        Intent intent = new Intent(this,Panel_Admin.class);
                        intent.putExtra("password",password.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                   else if(i > 0 && MaintainSession.userType.equals("Student"))
                    {
                        Intent intent = new Intent(this,Panel_Student.class);
                        intent.putExtra("password",password.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                    else if(i > 0 && MaintainSession.userType.equals("Faculty"))
                    {
                        Intent intent = new Intent(this,Panel_Faculty.class);
                        intent.putExtra("password",password.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Login_Register.this,"Invalid User Name \nor\n Mobile No.\nor\n Password !",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Internet Connection not found!!", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception ee)
            {
                Log.e("Login Error:", ee.toString());
            }
        }
    }
}
