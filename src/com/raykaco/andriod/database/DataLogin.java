package com.raykaco.andriod.database;

import java.util.ArrayList;
import android.database.Cursor;
import com.raykaco.andriod.G;


public class DataLogin extends DataAccess {

    private String        accessToken;
    private String        tokenType;
    private String        Bearer;
    private String        expiresIn;
    private String        userName;
    private String        issUed;
    private String        Expires;

    private String        shahr;
    private Integer       ostan;
    private int           id;
    ArrayList<StrucOstan> Data = new ArrayList<StrucOstan>();


    public void setAccessToken(String accesstoken) {
        accessToken = accesstoken;
    }


    public void setTokenType(String tokentype) {
        tokenType = tokentype;
    }


    public void setBearer(String bearer) {
        Bearer = bearer;
    }


    public void setExpiresIn(String expiresin) {
        expiresIn = expiresin;
    }


    public void setUserName(String username) {
        userName = username;
    }


    public void setIssUed(String issued) {
        issUed = issued;
    }


    public void setExpires(String expires) {
        Expires = expires;
    }


    public String getIssUed() {
        return issUed;
    }


    public String getExpiress() {
        return Expires;
    }


    public String getUserName() {
        return userName;
    }


    public String getExpiresIn() {
        return expiresIn;
    }


    public String getBearer() {
        return Bearer;
    }


    public String getTokenType() {
        return tokenType;
    }


    public String getAccessToken() {
        return accessToken;
    }


    public ArrayList<StrucOstan> getAllOfOstan() {
        Data.clear();
        openDatabase();
        String sql = "SELECT name,id FROM Tbl_Ostan";
        Cursor cursor = newDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            StrucOstan data = new StrucOstan();

            data.name = cursor.getString(cursor.getColumnIndex("name"));
            data.id = cursor.getInt(cursor.getColumnIndex("id"));

            Data.add(data);
        }

        cursor.close();
        newDb.close();
        return Data;

    }


    /* public ArrayList<StrucOstan> getAllOfShahr() {
         Data.clear();
         //Log.i("LOG", "|||" + getIdOstan());
         openDatabase();
         String sql = "SELECT name,id FROM Tbl_Shahrestan WHERE pk_ostan = '" + getOstan() + "'";

         Cursor cursor = newDb.rawQuery(sql, null);
         while (cursor.moveToNext()) {
             StrucOstan data = new StrucOstan();

             data.name = cursor.getString(cursor.getColumnIndex("name"));
             data.id = cursor.getInt(cursor.getColumnIndex("id"));

             Data.add(data);
         }
         cursor.close();
         newDb.close();
         return Data;

     }*/

    public void getDateLogin() {

        openDatabase();
        String sql = "SELECT access_token,token_type,userName,issued,expires,on_off FROM Data_Login";
        Cursor cursor = newDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            G.DATALOGIN[0] = cursor.getString(cursor.getColumnIndex("access_token"));
            G.DATALOGIN[1] = cursor.getString(cursor.getColumnIndex("token_type"));
            G.DATALOGIN[2] = cursor.getString(cursor.getColumnIndex("userName"));
            G.DATALOGIN[3] = cursor.getString(cursor.getColumnIndex("issued"));
            G.DATALOGIN[4] = cursor.getString(cursor.getColumnIndex("expires"));
            G.DATALOGIN[5] = cursor.getString(cursor.getColumnIndex("on_off"));
        }
        cursor.close();
        newDb.close();

    }


    public void updateDateLogin() {

        openDatabase();
        String str = "1";
        String sql = "update Data_Login set access_token='" + getAccessToken() + "',token_type='" + getTokenType() + "',expires_in='" + getExpiresIn() + "',userName='" + getUserName() + "',issued='" + getIssUed() + "',expires='" + getExpiress() + "',on_off='" + str + "'";
        newDb.execSQL(sql);
        newDb.close();

    }


    public void updateExitLogin() {

        openDatabase();
        String sql = "update Data_Login set on_off='0'";
        newDb.execSQL(sql);
        newDb.close();

    }
}
