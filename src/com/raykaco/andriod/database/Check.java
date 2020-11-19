package com.raykaco.andriod.database;

import java.util.ArrayList;
import android.database.Cursor;
import android.util.Log;
import com.raykaco.andriod.G;


public class Check extends DataAccess {

    private String        shahr;
    private Integer       ostan;
    private int           id;
    ArrayList<StrucOstan> Data = new ArrayList<StrucOstan>();


    public void setOstan(Integer integer) {
        ostan = integer;
    }


    public Integer getOstan() {
        return ostan;
    }


    public void setShahr(String shahrestan) {
        shahr = shahrestan;
    }


    public String getShahr() {
        return shahr;
    }


    public int getLog() {
        Log.i("LOG", "#" + G.DB_PATH + G.DB_NAME);
        openDatabase();
        Log.i("LOG", "##" + G.DB_PATH + G.DB_NAME);
        String sql = "SELECT che FROM che";
        Log.i("LOG", "###" + G.DB_PATH + G.DB_NAME);
        Cursor cursor = newDb.rawQuery(sql, null);
        Log.i("LOG", "####" + G.DB_PATH + G.DB_NAME);
        int s = 3;
        while (cursor.moveToNext()) {

            s = cursor.getInt(cursor.getColumnIndex("che"));

        }

        cursor.close();
        newDb.close();
        return s;

    }


    public void updateLog() {
        openDatabase();
        String sql = "update che set che=1";
        newDb.execSQL(sql);
        newDb.close();

    }


    public int getWelcom() {
        Log.i("LOG", "#" + G.DB_PATH + G.DB_NAME);
        openDatabase();
        Log.i("LOG", "##" + G.DB_PATH + G.DB_NAME);
        String sql = "SELECT wel FROM che";
        Log.i("LOG", "###" + G.DB_PATH + G.DB_NAME);
        Cursor cursor = newDb.rawQuery(sql, null);
        Log.i("LOG", "####" + G.DB_PATH + G.DB_NAME);
        int s = 3;
        while (cursor.moveToNext()) {

            s = cursor.getInt(cursor.getColumnIndex("wel"));

        }

        cursor.close();
        newDb.close();
        return s;

    }


    public void updateWelcom() {
        openDatabase();
        String sql = "update che set wel=1";
        newDb.execSQL(sql);
        newDb.close();

    }


    public int getSur() {
        Log.i("LOG", "#" + G.DB_PATH + G.DB_NAME);
        openDatabase();
        Log.i("LOG", "##" + G.DB_PATH + G.DB_NAME);
        String sql = "SELECT sur FROM che";
        Log.i("LOG", "###" + G.DB_PATH + G.DB_NAME);
        Cursor cursor = newDb.rawQuery(sql, null);
        Log.i("LOG", "####" + G.DB_PATH + G.DB_NAME);
        int s = 3;
        while (cursor.moveToNext()) {

            s = cursor.getInt(cursor.getColumnIndex("sur"));

        }

        cursor.close();
        newDb.close();
        return s;

    }


    public void updateSur() {
        openDatabase();
        String sql = "update che set sur=1";
        newDb.execSQL(sql);
        newDb.close();

    }


    public void updateWel0() {
        openDatabase();
        String sql = "update che set wel=0";
        newDb.execSQL(sql);
        newDb.close();

    }


    public String getCity() {
        Log.i("LOG", "#" + G.DB_PATH + G.DB_NAME);
        openDatabase();
        Log.i("LOG", "##" + G.DB_PATH + G.DB_NAME);
        String sql = "SELECT city FROM che";
        Log.i("LOG", "###" + G.DB_PATH + G.DB_NAME);
        Cursor cursor = newDb.rawQuery(sql, null);
        Log.i("LOG", "####" + G.DB_PATH + G.DB_NAME);
        String s = "";
        while (cursor.moveToNext()) {

            s = cursor.getString(cursor.getColumnIndex("city"));

        }

        cursor.close();
        newDb.close();
        return s;

    }


    public void updateCity() {
        openDatabase();
        String sql = "update che set city='" + getShahr() + "'";
        newDb.execSQL(sql);
        newDb.close();

    }
}
