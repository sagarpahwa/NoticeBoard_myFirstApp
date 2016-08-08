package com.example.acer.admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import android.os.Handler;
import java.util.logging.LogRecord;

public class Send_Message extends AppCompatActivity implements View.OnClickListener, Runnable
{
    ArrayList<String> imagepath = new ArrayList<String>();
    ServerDBClass sdc = new ServerDBClass();
    Handler handler;
    EditText sendMessageText;
    Button sendMessageButton;

    ImageView camera,speakNow,back,sendImage;

    String strGroupId = "",strReceivers="0";
    ListView lv;

   // Handler handler;

    Send_Message_Adapter msgAdapter;
    ArrayList<HashMap<String, String>> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

       /* handler = new Handler();
        handler.postDelayed(this, 1000);*/

        lv = (ListView) findViewById(android.R.id.list);

        sendMessageText = (EditText) findViewById(R.id.sendMessageText);
        sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
        sendImage = (ImageView)findViewById(R.id.sendImage);
        back =(ImageView)findViewById(R.id.messageback);
        //camera = (ImageView)findViewById(R.id.camera);
        //photo = (ImageView)findViewById(R.id.photo);

        camera.setOnClickListener(this);
        sendMessageButton.setOnClickListener(this);

        speakNow = (ImageView) findViewById(R.id.speakNow);
        speakNow.setOnClickListener(this);

        strGroupId = getIntent().getExtras().get("gid").toString();
        Toast.makeText(Send_Message.this, strGroupId, Toast.LENGTH_LONG).show();

        if (sdc.setConnection(this))
        {
            try
            {
                strReceivers = sdc.getReceiverID(strGroupId);
                Toast.makeText(getApplicationContext(),strReceivers,Toast.LENGTH_LONG).show();

                /*if (MaintainSession.userType.equals("Student"))
                {
                    //sdc.updateStudentMsg();
                }
                else
                {
                    //sdc.updateEmpMsg();
                }*/

                //lv.setAdapter(null);
                messageList = sdc.showMessage(strGroupId);
                msgAdapter = new Send_Message_Adapter(this,messageList);
                lv.setAdapter(msgAdapter);
            }
            catch (Exception ee){}
        }
        handler = new Handler();
        handler.postDelayed(this,1);
    }

    //---------- Action Bar Menu  -----------

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
    //----------  End of Action Bar Menu  ----------------


    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.sendMessageButton)
        {
            String message = sendMessageText.getText().toString();
            sendMessageText.setText("");
            if (sdc.setConnection(this))
            {
                try
                {
                    int i=sdc.sendMessage(strGroupId,message,MaintainSession.userid,strReceivers,image);
                    if (i>0)
                    {
                        Toast.makeText(getApplicationContext(),"Message Send Ho Gaya.",Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(this,Send_Message.class);
                        //startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Try Again !!!",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ee)
                {
                    Toast.makeText(getApplicationContext(), "Internet Connection not found!!", Toast.LENGTH_LONG).show();
                }
            }
        }

        if(v.getId()==R.id.camera)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,1);
        }

        if (v.getId() == R.id.speakNow)
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode==RESULT_OK)
        {
            Bitmap bmp = (Bitmap)data.getExtras().get("data");
            String filename = "image"+ Calendar.getInstance();
            File file = new File(getApplicationContext().getFilesDir(),filename);
            FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            sendImage.setImageBitmap(bmp);
        }
        if (requestCode == 2 && resultCode == RESULT_OK)
        {
            String voice_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            Toast.makeText(getApplicationContext(),voice_text,Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent=new Intent(this, My_Group.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void run() {
        back.setMaxHeight(sendMessageText.getHeight());
        handler.postDelayed(this,100);
    }
}
