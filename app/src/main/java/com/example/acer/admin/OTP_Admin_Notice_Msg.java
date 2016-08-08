package com.example.acer.admin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OTP_Admin_Notice_Msg extends AppCompatActivity implements Runnable
{
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_admin_notice_msg);

        handler = new Handler();
        handler.postDelayed(this,3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this, Login_Register.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void run() {
        Intent intent=new Intent(this, Login_Register.class);
        startActivity(intent);
        finish();
    }
}
