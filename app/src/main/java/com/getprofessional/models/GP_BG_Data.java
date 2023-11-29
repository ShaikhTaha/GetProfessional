package com.getprofessional.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.getprofessional.BackGrounds.UserDetails;

import java.util.Random;

public class GP_BG_Data extends SQLiteOpenHelper {
    public static final String database_name = "bg_data";
    public static final String ALLDATA = "alldata";
    public static final String ORDERSDATA = "ordersData";
    public static final String UPDATEDORDERS = "updatedOrders";

    public static final String[] ad_col = {"ID", "username", "password", "custID", "custNAME",
            "custEMAIL", "empID", "empNAME", "empIMAGE", "servicePOSITION",
            "serviceNAME", "orderAMT", "orderDATE"};

    public static final String[] od_col = {"odrID", "order_id", "cust_id", "emp_image", "emp_name", "service_name",
            "order_date", "order_amt"};

    public static final String[] uo_col = {"ID", "emp_id", "cust_id", "emp_image", "emp_name", "service_name",
            "order_date", "order_amt"};
    private Context context;

    public GP_BG_Data(Context context) {
        super(context, database_name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ALLDATA + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , username TEXT, password TEXT, custID INTEGER, custNAME TEXT, custEMAIL TEXT, empID INTEGER, empNAME TEXT, empIMAGE TEXT, servicePOSITION INTEGER, serviceNAME TEXT, orderAMT INTEGER, orderDATE TEXT) ");
        db.execSQL("create table " + ORDERSDATA + " (odrID INTEGER PRIMARY KEY AUTOINCREMENT , order_id INTEGER, cust_id INTEGER, emp_image TEXT, emp_name TEXT, service_name TEXT, order_date TEXT, order_amt DOUBLE) ");
        db.execSQL("create table " + UPDATEDORDERS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , emp_id INTEGER, cust_id INTEGER, emp_image TEXT, emp_name TEXT, service_name TEXT, order_date TEXT, order_amt DOUBLE) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + ALLDATA);
        db.execSQL(" DROP TABLE IF EXISTS " + ORDERSDATA);
        db.execSQL(" DROP TABLE IF EXISTS " + UPDATEDORDERS);
        onCreate(db);
    }

    public void insertUser(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ad_col[1], username);
        contentValues.put(ad_col[2], password);
        long result = db.insert(ALLDATA, null, contentValues);

    }

    public String[] name_email() {
        String[] UnP = {"", ""};
        SQLiteDatabase db = this.getWritableDatabase();

        //Getting The Positoin Of last index
        String qry = " SELECT * FROM alldata ORDER BY ID DESC LIMIT 1 ";
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            UnP[0] = cursor.getString(4);
            UnP[1] = cursor.getString(5);
            cursor.close();
        }
        return UnP;
    }

    public void insertUserDetals(int custID, String custNAME, String custEMAIL) {

        SQLiteDatabase db = this.getWritableDatabase();

        //Getting The Positoin Of last index
        String qry = " SELECT * FROM alldata ORDER BY ID DESC LIMIT 1 ";
        Cursor cursor = db.rawQuery(qry, null);
        String str = "";
        if (cursor.moveToFirst())
            str = cursor.getString(cursor.getColumnIndex("ID"));
        cursor.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ad_col[3], custID);
        contentValues.put(ad_col[4], custNAME);
        contentValues.put(ad_col[5], custEMAIL);
        db.update(ALLDATA, contentValues, "ID=" + str, null);
    }

    public String getCustID() {
        String CustID = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = " SELECT * FROM alldata ORDER BY ID DESC LIMIT 1 ";
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst())
            CustID = cursor.getString(cursor.getColumnIndex("custID"));
        cursor.close();

        return CustID;
    }

    public void insertOrders(int order_id, int cust_id, String emp_image, String emp_name, String service_name, String order_date, double order_amt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(od_col[1], order_id);
        contentValues.put(od_col[2], cust_id);
        contentValues.put(od_col[3], emp_image);
        contentValues.put(od_col[4], emp_name);
        contentValues.put(od_col[5], service_name);
        contentValues.put(od_col[6], order_date);
        contentValues.put(od_col[7], order_amt);
        db.insert(ORDERSDATA, null, contentValues);
    }

    public void deleteOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM ordersData");
    }

    public Cursor getOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = " SELECT * FROM ordersData ";
        return db.rawQuery(qry, null);
    }



}
