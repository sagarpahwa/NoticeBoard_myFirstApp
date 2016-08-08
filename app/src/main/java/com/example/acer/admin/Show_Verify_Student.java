package com.example.acer.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Show_Verify_Student extends AppCompatActivity implements AbsListView.MultiChoiceModeListener//AdapterView.OnItemLongClickListener
{
    ServerDBClass sdc=new ServerDBClass();

    ArrayList<Student_Normal_Class> studentData=null;
    ArrayList<String> selectedId = new ArrayList<String>();

    String strStudentId;
    ListView lv;
    Student_Normal_Class snc;
    Student_Normal_List_Adapter sad;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_verify_student);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        lv = (ListView)findViewById(android.R.id.list);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv.setMultiChoiceModeListener(this);

        //lv.setOnItemLongClickListener(this);

        /*------  Show Record from Server DATABASE  ---------*/

        if(sdc.setConnection(this))
        {
            studentData = sdc.showVerifyStudent();
            sad = new Student_Normal_List_Adapter(this, studentData);
            lv.setAdapter(sad);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Internet Not Found..", Toast.LENGTH_LONG).show();
        }

        /*-------  End of Record from Server DATABASE  ---------*/
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
    public void onBackPressed(){
        //super.onBackPressed();
        Intent intent = new Intent(this, Panel_Faculty.class);
        startActivity(intent);
        finish();
    }

   /* @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        //Student_Normal_Class sc = studentData.get(position);
        //studentId = sc.getStudentID();

        final CharSequence[] items = {"Delete"};
        Toast.makeText(getApplication(),studentId,Toast.LENGTH_LONG).show();

        AlertDialog alertDialog = new AlertDialog.Builder(this).setItems(items,null).create();
        alertDialog.show();
        return true;
    }
*/
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        studentData.get(position).setSelected(checked);
        if(checked)
            selectedId.add(position+"");
        else
            selectedId.remove(position+"");
        lv.setAdapter(sad);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.multi_item_select,menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.delete:
            {
                /*String show = "";
                for(String i:selectedId)
                {
                    show = show + i;
                    int x = Integer.parseInt(i);
                    studentData.remove(x);
                }
                sad=null;
                sad = new Student_Normal_List_Adapter(this, studentData);
                lv.setAdapter(sad);
                Toast.makeText(getApplicationContext(),show,Toast.LENGTH_LONG).show();*/

                int size = studentData.size();
                for (int i=0;i<size;++i)
                {
                    snc = studentData.get(i);
                    if (snc.isSelected()==true)
                    {
                        strStudentId = snc.getStudentID();
                        if (sdc.setConnection(this))
                        {
                            sdc.deleteVerifyStudent(strStudentId);
                            Toast.makeText(getApplicationContext(),"Student Deleted"+strStudentId,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(this,Show_Verify_Student.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
