package com.example.acer.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class OTP_Teacher_Notice_Msg extends AppCompatActivity implements Runnable
{
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_teacher_notice_msg);

        handler = new Handler();
        handler.postDelayed(this, 3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this, Login_Register.class);
        startActivity(intent);

    }

    @Override
    public void run() {
        Intent intent=new Intent(this, Login_Register.class);
        startActivity(intent);
        finish();
    }
}
