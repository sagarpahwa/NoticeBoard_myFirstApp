package com.example.acer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

@SuppressWarnings("SpellCheckingInspection")
public class OTPVerify extends AppCompatActivity implements View.OnClickListener
{
    ServerDBClass sdc = new ServerDBClass();

    EditText otp;
    Button verifyButton;

    String strMobile = "", strPass = "";
    int ranNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification);

        strMobile = getIntent().getExtras().get("mobile").toString();
        strPass = getIntent().getExtras().get("password").toString();

        verifyButton = (Button)findViewById(R.id.verifyButton);
        verifyButton.setOnClickListener(this);

        otp = (EditText)findViewById(R.id.otpCode);

        Random random = new Random();

        ranNumber = random.nextInt(100000);

        if (sdc.setConnection(this))
        {
            try
            {
                SmsManager smsmanager = SmsManager.getDefault();
                smsmanager.sendTextMessage(strMobile, "","DCSA - KUK: Your OTP Verification code is : "+ranNumber,null, null);
            }
            catch (Exception ee)
            {
                Log.e("Interner Not Found !",ee.toString());
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==R.id.verifyButton)
        {
            if(Integer.parseInt(otp.getText().toString()) == ranNumber)
            {
                Intent intent = new Intent(this, User_Type.class);
                intent.putExtra("mobile",strMobile);
                intent.putExtra("password",strPass);
                startActivity(intent);
                finish();
            }
            else if (otp.getText().toString().equals(""))
            {
                Toast.makeText(OTPVerify.this, "Enter OTP Code !", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(OTPVerify.this, "Invalid OTP Number!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
