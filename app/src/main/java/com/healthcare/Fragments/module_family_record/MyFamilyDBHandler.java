package com.healthcare.Fragments.module_family_record;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ashish on 28-Jan-17.
 */
public class MyFamilyDBHandler extends SQLiteOpenHelper
{
    private static int Database_version=1;
    private static String Database_Name="Family_DB.db";
    private static String Table_name1="T_family";
    private static String Table_name2="T_history";
    private static String Column_id="_id";
    private static String Column_fmember="_fmember";
    private static String Column_date="_date";
    private static String Column_doctor="_doctor";
    private static String Column_place="_place";
    private static String Column_prescription="_prescription";
    private static String Column_disease="_disease";
    private static String Column_tests="_tests";
    Context c;

    public MyFamilyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Database_Name, factory, Database_version);
        c=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            String q = "Create Table " + Table_name1 + "( " + Column_id + " integer PRIMARY KEY AUTOINCREMENT, " + Column_fmember + " varchar(20))";
            db.execSQL(q);
            q = "Create Table " + Table_name2 + "( " + Column_id + " integer PRIMARY KEY AUTOINCREMENT, " + Column_fmember + " varchar(20), " + Column_date + " date , "  + Column_doctor + " varchar(30) , "  + Column_place + " varchar(30) , "  + Column_disease + " varchar(30) , "+ Column_tests + " varchar(30) , " + Column_prescription + " varchar(50) )";
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
        db.execSQL("Drop table "+Table_name1);
        db.execSQL("Drop table "+Table_name2);
        onCreate(db);
    }

    public void addRow1(String s)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(Column_fmember,s);
        SQLiteDatabase db=getWritableDatabase();
        db.insert(Table_name1,null,contentValues);
        db.close();
    }

   public void addRow2(Element element)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(Column_fmember,element._fmember);
        contentValues.put(Column_date,element._date);
        contentValues.put(Column_doctor,element._doctor);
        contentValues.put(Column_place,element._place);
        contentValues.put(Column_disease,element._disease);
        contentValues.put(Column_tests,element._tests);
        contentValues.put(Column_prescription,element._prescription);
        SQLiteDatabase db=getWritableDatabase();
        db.insert(Table_name2,null,contentValues);

            System.out.println("Tu neend mein hai");

        db.close();
    }
    public void deleteRow1(String n)
    {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from " + Table_name1 + " where " + Column_fmember + "= '" + n + "'");
            db.execSQL("Delete from " + Table_name2 + " where " + Column_fmember + "= '" + n + "'");
            db.close();
        }
        catch (Exception e)
        {
            Toast.makeText(c,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteRow2(String n1,String n2,String n3)
    {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from " + Table_name2 + " where " + Column_doctor + "= '" + n1 + "' and "+Column_disease+" = '"+n2+"' and "+Column_fmember+" = '"+n3+"'");

            db.close();
        }
        catch (Exception e)
        {
            Toast.makeText(c,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Element displaydesc(String n1,String n2,String n3)
    {
        String n="", d="", h="", m="",l="",des="";
        SQLiteDatabase db = getWritableDatabase();
        String q = "Select * from " + Table_name2 + " where " + Column_doctor + "= '" + n1 + "' and "+Column_disease+" = '"+n2+"' and "+Column_fmember+" = '"+n3+"'";


        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
                d = cursor.getString(cursor.getColumnIndex(Column_date));

                n = cursor.getString(cursor.getColumnIndex(Column_doctor));

                h = cursor.getString(cursor.getColumnIndex(Column_disease));
                m = cursor.getString(cursor.getColumnIndex(Column_place));
                l=cursor.getString(cursor.getColumnIndex(Column_tests));
                des=cursor.getString(cursor.getColumnIndex(Column_prescription));

        }
        Element element = new Element();
        element.set_date(d);
        element.set_doctor(n);
        element.set_disease(h);
        element.set_place(m);
        element.set_tests(l);
        element.set_prescription(des);
        cursor.close();
        db.close();
        return element;
    }

    public ArrayList<String> displayf()
    {

            String n;
            SQLiteDatabase db = getWritableDatabase();
            String q = "Select " + Column_fmember + " from " + Table_name1;
            ArrayList<String> arrayList = new ArrayList<String>();

            Cursor cursor = db.rawQuery(q, null);
            cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            do {


                n = cursor.getString(cursor.getColumnIndex(Column_fmember));

                arrayList.add(n);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;

    }
    public ArrayList<MemberInfo> displayt(String na)
    {

        String n1,n2;
        System.out.println("na =  =  "+na);
        SQLiteDatabase db = getWritableDatabase();
        String q = "Select " + Column_disease+" , "+Column_doctor + " from " + Table_name2 +" where "+Column_fmember+" = '"+na+"'";
        ArrayList<MemberInfo> arrayList = new ArrayList<MemberInfo>();

        Cursor cursor = db.rawQuery(q, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            do {


                n1 = cursor.getString(cursor.getColumnIndex(Column_disease));
                n2 = cursor.getString(cursor.getColumnIndex(Column_doctor));

                arrayList.add(new MemberInfo(n1,n2));

            } while (cursor.moveToNext());
        }
        System.out.println("Yo   dfs");
        for(int i=0;i<arrayList.size();i++)
        {
            System.out.println(arrayList.get(i).doc+"\ndfs");
        }
        System.out.println("End   dfs");
        cursor.close();
        db.close();
        return arrayList;

    }

}
