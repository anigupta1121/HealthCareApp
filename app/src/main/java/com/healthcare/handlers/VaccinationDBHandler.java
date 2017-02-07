package com.healthcare.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.healthcare.Fragments.module_vaccination.ChildDetails;
import com.healthcare.Fragments.module_vaccination.VaccineDetails;

import java.util.ArrayList;

/**
 * Created by guptaanirudh100 on 2/5/2017.
 */

public class VaccinationDBHandler extends SQLiteOpenHelper {

    private static int DATABASE_VER=1;
    private static String DATABASE_NAME="vaccine.db";

    private static String TABLE_NAME="Child_details";
    private static String COLUMN_ID="id_child";
    private static String COLUMN_NAME="child_name";
    private static String COLUMN_DOB="child_dob";
    private static String COLUMN_GENDER="child_gender";

    private static String TABLE_NAME1="Vaccine_details";
    private static String COLUMN_ID1="vaccine_id";
    private static String COLUMN_NAME1="vaccine_name";
    private static String COLUMN_VACCINATION_MONTH ="vaccination_month";

    public VaccinationDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String q="Create Table "+TABLE_NAME+"( "
                +COLUMN_ID+" integer PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_NAME+" varchar(20), "
                +COLUMN_DOB+" date,"
                +COLUMN_GENDER+" varchar(20))";
        db.execSQL(q);

        String q1="Create Table "+TABLE_NAME1+"( "
                +COLUMN_ID1+" integer PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_NAME1+" varchar(20), "
                + COLUMN_VACCINATION_MONTH +" date,foreign key("+COLUMN_ID+") references "+TABLE_NAME+"("+COLUMN_ID+"))";

        db.execSQL(q1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+TABLE_NAME);
        db.execSQL("Drop table if exists "+TABLE_NAME1);
        onCreate(db);
    }

    public void addChild(ChildDetails childDetails){
        ArrayList<VaccineDetails> vaccineDetails=childDetails.vaccineDetails;
      ContentValues contentValues=new ContentValues();
        ContentValues contentValues1=new ContentValues();
        SQLiteDatabase db=getWritableDatabase();

        contentValues.put(COLUMN_NAME,childDetails.name);
        contentValues.put(COLUMN_DOB,childDetails.dob);
        contentValues.put(COLUMN_GENDER,childDetails.gender);

        db.insert(TABLE_NAME,null,contentValues);

        for (int i=0;i<vaccineDetails.size();i++){
            contentValues1.put(COLUMN_NAME1,vaccineDetails.get(i).vaccine_name);
            contentValues1.put(COLUMN_VACCINATION_MONTH,vaccineDetails.get(i).date);
            contentValues1.put(COLUMN_GENDER,childDetails.gender);

            db.insert(TABLE_NAME1,null,contentValues1);
        }
        db.close();
    }

    public void delChild(ChildDetails childDetails){

    }
}
