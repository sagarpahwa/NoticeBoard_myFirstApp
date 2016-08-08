package com.example.acer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DCSA_NB_Start extends AppCompatActivity //implements Runnable
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dcsa_nb_start);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.hide();
    }

    public void StartNoticeBoard(View v)
    {
        Intent intent = new Intent(this,Login_Register.class);
        startActivity(intent);
        finish();
    }
}
