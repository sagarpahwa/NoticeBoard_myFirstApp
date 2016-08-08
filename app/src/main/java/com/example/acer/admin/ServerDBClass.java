package com.example.acer.admin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerDBClass
{
    Connection con;
    ResultSet rs;
    PreparedStatement ps;
    public boolean setConnection(Context context)
    {
        boolean checkNetwork=false;
        try
        {
            ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni=cm.getActiveNetworkInfo();
            if(ni==null)
            {
                checkNetwork=false;
            }
            else if(!ni.isAvailable())
            {
                checkNetwork=false;
            }
            else if(!ni.isConnected())
            {

                checkNetwork=false;
            }
            else {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=sagarDB; user=sagarDBUser; password=Sagar@db!2#;", "SagarDBUser", "Sagar@db!2#");
                checkNetwork=true;
            }
        }
        catch (Exception ee)
        {}
        return checkNetwork;
    }

    public int checkLogin(String strUser, String strPass)
    {
        int i=0;
            try {
                ps = con.prepareStatement("select vUserName from notice_admin where vUserName=? and vPassword=?");
                ps.setString(1, strUser);
                ps.setString(2, strPass);
                rs = ps.executeQuery();
                if(rs.next()) {
                    MaintainSession.name=rs.getString("vUserName");
                    MaintainSession.username=MaintainSession.name;
                    MaintainSession.userType="Admin";
                    MaintainSession.groupid="0";
                    MaintainSession.groupName="null";
                    MaintainSession.mobileNo="null";
                    i=1;
                }
            } catch (Exception ee) {
                Log.e("Error in Admin Login : ", ee.toString());
            }
            try {
                ps = con.prepareStatement("select StudentID, class, year, name, mobile, rollno, groupid from notice_students where mobile=? and password=? and isApproved=1");
                ps.setString(1, strUser);
                ps.setString(2, strPass);
                rs = ps.executeQuery();
                if(rs.next()) {
                    MaintainSession.name=rs.getString("name");
                    MaintainSession.userid=rs.getString("StudentID");
                    MaintainSession.username=rs.getString("mobile");
                    MaintainSession.userType="Student";
                    MaintainSession.groupid=rs.getString("groupid");
                    MaintainSession.groupName="null";
                    MaintainSession.mobileNo=strUser;
                    i=1;
                }
            } catch (Exception ee) {
                Log.e("Error in Admin Login : ", ee.toString());
            }

            try {
                ps = con.prepareStatement("select empId, vName, vDesignation, vCode, vMobile, isApproved, groupid from notice_Employees where vMobile=? and vPassword=? and isApproved=1");
                ps.setString(1, strUser);
                ps.setString(2, strPass);
                rs = ps.executeQuery();
                if(rs.next()) {

                    MaintainSession.userid=rs.getString("empId");
                    MaintainSession.name=rs.getString("vName");
                    MaintainSession.username=rs.getString("vMobile");
                    MaintainSession.userType="Faculty";
                    MaintainSession.groupid="0";
                    MaintainSession.groupName="null";
                    MaintainSession.mobileNo=strUser;
                    i=1;
                }
            } catch (Exception ee) {
                Log.e("Error in Admin Login : ", ee.toString());
            }
        return i;
    }

    // ------- register student used in AddStudent class --------

    public int insertStudent(String name, String roll, String mobile, String pass, String cls, String year, String groupID)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "insert into notice_students(name, rollno, mobile, password, class, year, groupid, isApproved, notify) values(?,?,?,?,?,?,0,0,0)");
            ps.setString(1, name);
            ps.setString(2, roll);
            ps.setString(3, mobile);
            ps.setString(4, pass);
            ps.setString(5, cls);
            ps.setString(6, year);
            ps.setString(7, groupID);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Insert Student Error:", ee.toString());
        }
        return i;
    }

    //------- used in Student_For_Verify class -----------

    public int verifyStudent(String studentID)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "update notice_students set isApproved=1 where StudentID=?");
            ps.setString(1, studentID);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Verify Student Error:", ee.toString());
        }
        return i;
    }

    //--------- used in StudentVeify class  -----------

    public int deleteStudent(String studentID)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "delete from notice_students where isApproved = 0 and StudentID=?");
            ps.setString(1, studentID);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Delete Student Error:", ee.toString());
        }
        return i;
    }

    public int deleteVerifyStudent(String studentID)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "delete from notice_students where isApproved = 1 and StudentID=?");
            ps.setString(1, studentID);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Delete Student Error:", ee.toString());
        }
        return i;
    }

    // --------  used in Student_For_Verify class ----------

    public ArrayList<Student_Check_Class> getRegisterStudent()
    {
        ArrayList<Student_Check_Class> stList=new ArrayList<Student_Check_Class>();

        try
        {
            ps=con.prepareStatement("select * from notice_students where isApproved = 0 order by name");
            rs=ps.executeQuery();
            while(rs.next())
            {
                Student_Check_Class stClass=new Student_Check_Class();

                stClass.setStudentID(rs.getString(1));
                stClass.setStudentClass(rs.getString(2));
                stClass.setStudentYear(rs.getString(3));
                stClass.setStudentName(rs.getString(4));
                stClass.setStudentMobileNo(rs.getString(5));
                stClass.setStudentRollNo(rs.getString(6));

                stList.add(stClass);
            }
        }
        catch (Exception ee)
        {
            Log.e("Get Reg.Student !!! : ", ee.toString());
        }
        return stList;
    }

    // ------- used in Show_Verify_Student class --------

    public ArrayList<Student_Normal_Class> showVerifyStudent()
    {
        ArrayList<Student_Normal_Class> stList=new ArrayList<Student_Normal_Class>();

        try
        {
            ps=con.prepareStatement("select * from notice_students where isApproved = 1 order by name");
            rs=ps.executeQuery();
            while(rs.next())
            {
                Student_Normal_Class stClass=new Student_Normal_Class();

                stClass.setStudentID(rs.getString(1));
                stClass.setStudentClass(rs.getString(2));
                stClass.setStudentYear(rs.getString(3));
                stClass.setStudentName(rs.getString(4));
                stClass.setStudentMobileNo(rs.getString(5));
                stClass.setStudentRollNo(rs.getString(6));
                stClass.setSelected(false);
                stList.add(stClass);

            }
        }
        catch (Exception ee)
        {
            Log.e("Verify Student Error : ", ee.toString());
        }

        return stList;

    }

    // -------- register employee  -----
    // ----------  used in Add Emp Class -------------

    public int insertEmployee(String name, String designation, String code, String mobile, String pass)
    {
        int i=0;

        try
        {
            ps=con.prepareStatement("insert into notice_Employees(vName, vDesignation, vCode, vMobile, vPassword) values(?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, designation);
            ps.setString(3, code);
            ps.setString(4, mobile);
            ps.setString(5, pass);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Insert Employee Error:", ee.toString());
        }
        return i;
    }

    // -------- show all registered employees ----------
    // -------  used in Teacher_For_Verify java class ------------

    public ArrayList<Teachers_Class> getRegisterTeachers()
    {
        ArrayList<Teachers_Class> thList=new ArrayList<Teachers_Class>();

        try
        {
            ps=con.prepareStatement("select * from notice_Employees where isApproved = 0 order by vName");
            rs=ps.executeQuery();
            while(rs.next())
            {
                Teachers_Class thrClass = new Teachers_Class();

                thrClass.setEmpID(rs.getString(1));
                thrClass.setEmpName(rs.getString(2));
                thrClass.setEmpDesignation(rs.getString(3));
                thrClass.setEmpCode(rs.getString(4));
                thrClass.setEmpMobileNo(rs.getString(5));

                thList.add(thrClass);
            }
        }
        catch (Exception ee)
        {
            Log.e("Teacher Verify Error : ", ee.toString());
        }

        return thList;

    }

    //-----------  verified by admin -------------------------
    //---------- used in Teacher_For_Verify java class  --------------

    public int verifyTeacher(String empID)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "update notice_Employees set isApproved=1 where empID=?");
            ps.setString(1, empID);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Verify Teacher Error:", ee.toString());
        }
        return i;
    }

    // check -------  delete by admin --------
    //---- used in Teacher_For_Verify Class ----------

    public int deleteTeacher(String empID)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "delete from notice_Employees where isApproved = 0 and empID=?");
            ps.setString(1, empID);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Delete Teacher Error:", ee.toString());
        }
        return i;
    }

    /*public ArrayList<Emp_Class> getEmployees()
    {
        ArrayList<Emp_Class> empList=new ArrayList<Emp_Class>();

        try
        {
            ps=con.prepareStatement("select * from notice_Employees order by vName");
            rs=ps.executeQuery();
            while(rs.next())
            {
                Emp_Class empClass=new Emp_Class();

                empClass.setEmpID(rs.getString(1));
                empClass.setEmpName(rs.getString(2));
                empClass.setEmpDesignation(rs.getString(3));
                empClass.setEmpCode(rs.getString(4));
                empClass.setEmpMobileNo(rs.getString(5));

                empList.add(empClass);

            }
        }
        catch (Exception ee)
        {
            Log.e("Employees Error : ", ee.toString());
        }

        return empList;

    }*/

    //----------- show all teachers..., verified by admin...
    // ---------  used in Teacher Show java class ----------

    public ArrayList<Teachers_Class> getVerifyTeachers()
    {
        ArrayList<Teachers_Class> tchList=new ArrayList<Teachers_Class>();

        try
        {
            ps=con.prepareStatement("select * from notice_Employees where isApproved=1  order by vName");
            rs=ps.executeQuery();
            while(rs.next())
            {
                Teachers_Class tchClass=new Teachers_Class();

                tchClass.setEmpID(rs.getString(1));
                tchClass.setEmpName(rs.getString(2));
                tchClass.setEmpDesignation(rs.getString(3));
                tchClass.setEmpCode(rs.getString(4));
                tchClass.setEmpMobileNo(rs.getString(5));

                tchList.add(tchClass);

            }
        }
        catch (Exception ee)
        {
            Log.e("Teacher Error : ", ee.toString());
        }

        return tchList;

    }

    // Create new Group by Admin  ----------

    public int insertGroup(String name, String cls, String sem)
    {
        int i=0;

        try
        {
            ps=con.prepareStatement( "if exists(select * from notice_groups where vGroupName=?) begin select 'a'=0 end else begin select 'a'=1 insert into notice_Groups(vGroupName,vClass, vYear) values(?,?,?) end");
            ps.setString(1, name);
            ps.setString(2, name);
            ps.setString(3, cls);
            ps.setString(4, sem);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                i=rs.getInt(1);
            }
        }
        catch (Exception ee)
        {
            Log.e("Insert Group Error:", ee.toString());
        }
        return i;
    }

    // check ---- show groups create by admin used in showGroupData java class ----------

    public ArrayList<Group_Class> getGroups()
    {
        ArrayList<Group_Class> groupList=new ArrayList<Group_Class>();

        try
        {
            ps=con.prepareStatement("select * from notice_Groups order by vGroupName");
            rs=ps.executeQuery();
            while(rs.next())
            {
                Group_Class groupClass=new Group_Class();

                groupClass.setGroupID(rs.getString(1));
                groupClass.setGroupName(rs.getString(2));
                groupClass.setGroupClass(rs.getString(3));
                groupClass.setGroupYear(rs.getString(4));

                groupList.add(groupClass);

            }
        }
        catch (Exception ee)
        {
            Log.e("Get Group Error : ", ee.toString());
        }

        return groupList;

    }

    public ArrayList<Group_Class> getMyGroups()
    {
        ArrayList<Group_Class> groupList=new ArrayList<Group_Class>();

        try
        {
            ps=con.prepareStatement("select * from notice_Groups where groupid in(select isnull(iGroupID, 0) from notice_AssignGroupToTeacher where iEmpId=?) order by vGroupName");
            ps.setString(1, MaintainSession.userid);
            rs=ps.executeQuery();
            while(rs.next())
            {
                Group_Class groupClass=new Group_Class();

                groupClass.setGroupID(rs.getString(1));
                groupClass.setGroupName(rs.getString(2));
                groupClass.setGroupClass(rs.getString(3));
                groupClass.setGroupYear(rs.getString(4));

                groupList.add(groupClass);

            }
        }
        catch (Exception ee)
        {
            Log.e("Get Group Error : ", ee.toString());
        }

        return groupList;

    }

    // ------- used in add student java class-------

    public String getGroupsID(String ClassName, String Sem)
    {
        String strGroupID="0";
       try
        {
            ps=con.prepareStatement("select * from notice_Groups where vClass=? and vYear=?");
            ps.setString(1, ClassName);
            ps.setString(2, Sem);
            rs=ps.executeQuery();
            if(rs.next())
            {
                strGroupID=rs.getString(1);
            }
        }
        catch (Exception ee)
        {
            Log.e("Get Group Error : ", ee.toString());
        }
        return strGroupID;
    }

    // check  --------- assign group by admin -------

    public int assignGroupToTeacher(String groupId, String empId)
    {
        int i=0;

        try
        {
            ps=con.prepareStatement( "if exists(select * from notice_AssignGroupToTeacher where iEmpId=? and iGroupId=?) begin select 'a'=0 end else begin select 'a'=1 insert into notice_AssignGroupToTeacher(iGroupId,iEmpId) values(?,?) end");
            ps.setString(1, empId);
            ps.setString(2, groupId);
            ps.setString(3, groupId);
            ps.setString(4, empId);
            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("AssignTeacher Error:", ee.toString());
        }
        return i;
    }

    public int assignGroupToStudent()
    {
        int i=0;

        try
        {

        }
        catch (Exception ee)
        {}
        return i;
    }



    public int sendMessage(String groupID, String message, String sender, String receiver, String image)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement("insert into notice_message(vGroupID, vMessage, vSender, vReceiver, msgDate, vViewer, image) values(?,?,?,?,getdate(),'0',?)");
            ps.setString(1, groupID);
            ps.setString(2, message);
            ps.setString(3, sender);
            ps.setString(4, receiver);
            ps.setString(5,image);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        { Log.e("Send Message Error!",ee.toString());}
        return i;
    }
    public ArrayList<String> getMessages()
    {
        ArrayList<String> messageList=new ArrayList<String>();

        try
        {

        }
        catch (Exception ee)
        {}

        return messageList;
    }

    public int updateStudentMsg(String studentID)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "update notice_students set notify=1 where StudentID=?");
            ps.setString(1, studentID);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Update St_Msg Error:", ee.toString());
        }
        return i;
    }

    public int updateEmpMsg(String empID)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "update notice_Employees set notify=1 where empID=?");
            ps.setString(1, empID);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Update Emp_Msg Error:", ee.toString());
        }
        return i;
    }

    public ArrayList<HashMap<String, String>> showMessage(String groupId)
    {
        ArrayList<HashMap<String, String>> messageList = new ArrayList<HashMap<String, String>>();
        try
        {
            ps=con.prepareStatement("select * from notice_message join notice_groups on notice_message.vGroupID=notice_groups.groupID join notice_employees on notice_message.vSender=notice_employees.empID  where notice_groups.groupid=?");
            ps.setString(1, groupId);

            rs = ps.executeQuery();
            while (rs.next()) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("messageId",rs.getString("iMessageID"));
                map.put("message",rs.getString("vMessage"));
                //map.put("sender",rs.getString("vSender"));
                map.put("msgDate",rs.getString("msgDate"));
                map.put("groupName",rs.getString("vGroupName"));
                map.put("senderName",rs.getString("vName"));
                map.put("designation",rs.getString("vDesignation"));

                messageList.add(map);
            }
        }
        catch (Exception ee)
        {
            Log.e("show msg Error : ", ee.toString());
        }
        return messageList;
    }

    /*public String showMessage(String groupID)
    {
        String strGroupId="0", strMessage, strName, strAddress;
        try
        {
            ps=con.prepareStatement("select * from notice_message where vGroupID=?");
            ps.setString(1, groupID);

            rs=ps.executeQuery();
            if(rs.next())
            {
                strGroupId=rs.getString(2);
                strMessage=rs.getString(3);
                strName=rs.getString(4);
                strAddress=rs.getString(5);
            }
        }
        catch (Exception ee)
        {
            Log.e("Get Group Error : ", ee.toString());
        }
        return strGroupId;
    }*/

    public String getReceiverID(String studentID)
    {
        String strReceivers="0";
        try
        {
            ps=con.prepareStatement("select studentID from notice_students where groupid=? and isApproved=1");
            ps.setString(1, studentID);

            rs=ps.executeQuery();
            while (rs.next())
            {
                strReceivers=strReceivers+","+rs.getString("studentID");
            }
        }
        catch (Exception ee)
        {
            Log.e("Get St.Id Error : ", ee.toString());
        }
        return strReceivers;
    }

    public int updateAdminPassword(String oldpass, String newpass)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "update notice_admin set vPassword=? where vPassword=?");
            ps.setString(1, newpass);
            ps.setString(2, oldpass);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("Update Admin Error:", ee.toString());
        }
        return i;
    }

    public int updateEmpPassword(String oldpass, String newpass, String empId)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "update notice_Employees set vPassword=? where vPassword=? and empID=?");
            ps.setString(1, newpass);
            ps.setString(2, oldpass);
            ps.setString(3, empId);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("update emp Error:", ee.toString());
        }
        return i;
    }

    public int updateStudentPassword(String oldpass, String newpass, String stId)
    {
        int i=0;
        try
        {
            ps=con.prepareStatement( "update notice_students set password=? where password=? and StudentId=?");
            ps.setString(1, newpass);
            ps.setString(2, oldpass);
            ps.setString(3, stId);

            i=ps.executeUpdate();
        }
        catch (Exception ee)
        {
            Log.e("update Student Error:", ee.toString());
        }
        return i;
    }

}
