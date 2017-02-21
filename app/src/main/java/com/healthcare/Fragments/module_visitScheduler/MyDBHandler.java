package com.healthcare.Fragments.module_visitScheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ashish on 28-Jan-17.
 */
public class MyDBHandler extends SQLiteOpenHelper
{
    private static int Database_version=1;
    private static String Database_Name="Doctor_DB.db";
    private static String Table_name="T_Visit";
    private static String Column_id="_id";
    private static String Column_name="_name";
    private static String Column_date="_date";
    private static String Column_hour="_hour";
    private static String Column_minute="_minute";
    private static String Column_location="_location";
    private static String Column_description="_description";
    Context c;

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Database_Name, factory, Database_version);
        c=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            String q = "Create Table " + Table_name + "( " + Column_id + " integer PRIMARY KEY AUTOINCREMENT, " + Column_name + " varchar(20), " + Column_date + " date , " + Column_hour + " int , " + Column_minute + " int , " + Column_location + " varchar(30) , " + Column_description + " varchar(50) )";
            db.execSQL(q);
        }
        catch (Exception e)
        {
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("Drop table "+Table_name);
        onCreate(db);
    }

    public void addRow(Element element)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(Column_name,element._name);
        contentValues.put(Column_date,element._date);
        contentValues.put(Column_hour,element._hour);
        contentValues.put(Column_minute,element._minute);
        contentValues.put(Column_location,element._location);
        contentValues.put(Column_description,element._description);
        SQLiteDatabase db=getWritableDatabase();
        db.insert(Table_name,null,contentValues);
        db.close();
    }
    public void deleteRow(String n)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("Delete from "+Table_name+" where "+Column_name+"= '"+n+"'");
        db.close();
    }

    public Element displayone(String na)
    {
        String n="", d="", h="", m="",l="",des="";
        SQLiteDatabase db = getWritableDatabase();
        String q = "Select * from " + Table_name+" where "+Column_name+"='"+na+"'";


        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
                d = cursor.getString(cursor.getColumnIndex(Column_date));

                n = cursor.getString(cursor.getColumnIndex(Column_name));

                h = cursor.getString(cursor.getColumnIndex(Column_hour));
                m = cursor.getString(cursor.getColumnIndex(Column_minute));
                l=cursor.getString(cursor.getColumnIndex(Column_location));
                des=cursor.getString(cursor.getColumnIndex(Column_description));

        }
        Element element = new Element(n,d,h,m,l,des);
        cursor.close();
        db.close();
        return element;
    }

    public ArrayList<VisitInfo> display()
    {

            String n, d, h, m, t;
            SQLiteDatabase db = getWritableDatabase();
            String q = "Select " + Column_name + " , " + Column_date + " , " + Column_hour + " , " + Column_minute + " from " + Table_name;
            ArrayList<VisitInfo> arrayList = new ArrayList<VisitInfo>();

            Cursor cursor = db.rawQuery(q, null);
            cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            do {
                d = cursor.getString(cursor.getColumnIndex(Column_date));

                n = cursor.getString(cursor.getColumnIndex(Column_name));

                h = cursor.getString(cursor.getColumnIndex(Column_hour));
                m = cursor.getString(cursor.getColumnIndex(Column_hour));
                t = h + " : " + m;
                arrayList.add(new VisitInfo(n, d, t));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;

    }

}
