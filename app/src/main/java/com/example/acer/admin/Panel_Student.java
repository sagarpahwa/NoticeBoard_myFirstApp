package com.example.acer.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Panel_Student extends AppCompatActivity
{
    ServerDBClass sdc = new ServerDBClass();

    ListView lv;

    String strGroupId;

    Send_Message_Adapter msgAdapter;
    ArrayList<HashMap<String, String>> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_student);

        lv = (ListView)findViewById(android.R.id.list);

        if (sdc.setConnection(this))
        {
            strGroupId = MaintainSession.groupid;
            Toast.makeText(getApplicationContext(),strGroupId,Toast.LENGTH_LONG).show();
            try
            {
                messageList = sdc.showMessage(strGroupId);
                msgAdapter = new Send_Message_Adapter(this,messageList);
                lv.setAdapter(msgAdapter);
            }
            catch (Exception ee)
            {
                Log.e("Internet Not Found !",ee.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.student_action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.changeStudentPassword)
        {
            Intent intent = new Intent(this, Change_Student_Password.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.studentLogout)
        {
            Intent intent = new Intent(this, Login_Register.class);
            MaintainSession.userType = "";
            startActivity(intent);
            finish();
        }
        return true;
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
