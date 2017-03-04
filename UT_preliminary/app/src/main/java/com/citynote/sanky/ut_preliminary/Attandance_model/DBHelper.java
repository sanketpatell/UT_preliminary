package com.citynote.sanky.ut_preliminary.Attandance_model;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    Myhelper m;
    SQLiteDatabase s;

    public DBHelper (Context c){
        m = new Myhelper(c,"attandance.db",null,1);
    }
    public SQLiteDatabase open(){
        s =m.getWritableDatabase();
        return null;
    }

    public void inserIntoattandance(Integer id,String emp_id,String date,String status)
    {
        Log.d("****", "====" + id + " " + emp_id + " " + date + " " + status);

        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("emp_id", emp_id);
        cv.put("date", date);
        cv.put("status", status);
        s.insert("attend", null, cv);
        s.update("attend", cv, "id="+id, null);
    }



    //present data storing in to calender
    public List<String> getpresent_date( )
    {

        SQLiteDatabase Db1=m.getReadableDatabase();
        List<String> listt=new ArrayList<>();
        String empid="UTI_5873";
        String stts="present";
        String query="select * from attend where emp_id = '" + empid + "' and  status='" + stts + "'";
        Cursor  cursor=Db1.rawQuery(query,null);

        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                do {
                    String date=cursor.getString(2);
                    Log.e("DATEEE", date);
                    listt.add(date);

                }while (cursor.moveToNext());
            }

        }
        return listt;
    }
//absent data storing in to calender
    public List<String> getabsent_date( )
    {

        SQLiteDatabase Db1=m.getReadableDatabase();
        List<String> listt=new ArrayList<>();
        String empid="UTI_5873";
        String stts="absent";
        String query="select * from attend where emp_id = '" + empid + "' and  status='" + stts + "'";
        Cursor  cursor=Db1.rawQuery(query,null);

        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                do {
                    String date=cursor.getString(2);
                    Log.e("DATEEE", date);
                    listt.add(date);

                }while (cursor.moveToNext());
            }

        }
        return listt;
    }




    class Myhelper extends SQLiteOpenHelper {

        public Myhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("create table attend(id INTEGER ,emp_id TEXT,date TEXT,status TEXT);");



        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

   /* // Getting All Categories
    public List<Attandance> getAllpresentdate() {
        List<Attandance> attandanceList = new ArrayList<Attandance>();
        // Select All Query
        String selectQuery = "SELECT emp_id from attend table where status = ";

        SQLiteDatabase s = this.open();
        Cursor cursor = s.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    Attandance attandance = new Attandance();
                    attandance.setEmp_id(cursor.getInt(0));
                    attandance.setDate(cursor.getString(1));
                    attandance.setStatus(cursor.getString(2));
                    attandanceList.add(attandance);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            s.close();
        }

        return attandanceList;

    }*/

}