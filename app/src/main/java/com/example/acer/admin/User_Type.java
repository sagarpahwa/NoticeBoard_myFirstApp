package com.example.acer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class User_Type extends AppCompatActivity implements View.OnClickListener
{
    Spinner userTypeSpinner;
    Button next;

    String[] strLoginType;
    String strMobile, strPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type);

        strMobile=getIntent().getExtras().get("mobile").toString();
        strPass=getIntent().getExtras().get("password").toString();

        userTypeSpinner=(Spinner)findViewById(R.id.userTypeSpinner);

        next=(Button)findViewById(R.id.next);
        next.setOnClickListener(this);

        strLoginType = new String[]{"Select User Type","Student","Faculty"};

        /*------------ START SPINNER  ---------------*/

        ArrayAdapter<String> strArrayAdapter = new ArrayAdapter<String>(this,R.layout.my_spinner_layout, strLoginType);
        strArrayAdapter.setDropDownViewResource(R.layout.my_spinner_layout);
        userTypeSpinner.setAdapter(strArrayAdapter);

        //----------- End of Spinner  --------------------
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==R.id.next)
        {
            try
            {
                Toast.makeText(User_Type.this, userTypeSpinner.getSelectedItemPosition()+"", Toast.LENGTH_SHORT).show();
                if(userTypeSpinner.getSelectedItemPosition()==0)
                {
                    Toast.makeText(User_Type.this,"Select User Type !!!",Toast.LENGTH_LONG).show();
                }
                if(userTypeSpinner.getSelectedItemPosition()==1)
                {
                    Intent intent = new Intent(this, AddStudent.class);
                    intent.putExtra("mobile",strMobile);
                    intent.putExtra("password",strPass);
                    startActivity(intent);
                }
                if(userTypeSpinner.getSelectedItemPosition()==2)
                {
                    Intent intent=new Intent(this, AddEmp.class);
                    intent.putExtra("mobile", strMobile);
                    intent.putExtra("password",strPass);
                    startActivity(intent);
                }
            }
            catch (Exception ee)
            {}
        }
    }
}
