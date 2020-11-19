package com.raykaco.andriod.database;

import java.util.ArrayList;
import android.database.Cursor;
import android.util.Log;
import com.raykaco.android.customcontrol.StrucTopic;


public class BasketProductDB extends DataAccess {

    private int    id;
    private int    count;
    private String price;
    private String name;
    private String teacheName;


    public void setText(String value) {
        name = value;
    }


    public void setPrice(String value) {
        price = value;
    }


    public void setTeacherName(String value) {
        teacheName = value;
    }


    public void setId(int value) {
        id = value;
    }


    public void setCount(int value) {
        count = value;
    }


    public int selectId() {
        openDatabase();
        String sql = "select count from basket_product where id_product=" + id + "";
        Cursor cursor = newDb.rawQuery(sql, null);
        int count = 0;
        Log.i("SQL", sql);
        while (cursor.moveToNext()) {

            count = cursor.getInt(cursor.getColumnIndex("count"));
        }
        Log.i("SQL", "" + count);
        cursor.close();
        newDb.close();
        return count;

    }


    public int selectAllCount() {
        openDatabase();
        String sql = "select count from basket_product ";
        Cursor cursor = newDb.rawQuery(sql, null);
        int count = 0;
        Log.i("SQL", sql);
        while (cursor.moveToNext()) {

            count = count + cursor.getInt(cursor.getColumnIndex("count"));
        }
        Log.i("SQL", "" + count);
        cursor.close();
        newDb.close();
        return count;

    }


    public ArrayList<StrucTopic> selectAllItem() {
        openDatabase();
        String sql = "select * from basket_product";
        Cursor cursor = newDb.rawQuery(sql, null);
        int count = 0;
        Log.i("SQL", sql);
        ArrayList<StrucTopic> allProduct = new ArrayList<StrucTopic>();
        while (cursor.moveToNext()) {

            StrucTopic st = new StrucTopic();
            st.name = cursor.getString(cursor.getColumnIndex("name"));
            st.price = cursor.getString(cursor.getColumnIndex("price"));
            st.nameTeacher = cursor.getString(cursor.getColumnIndex("teacher"));
            st.id = cursor.getInt(cursor.getColumnIndex("id_product"));
            allProduct.add(st);
        }
        Log.i("SQL", "" + count);
        cursor.close();
        newDb.close();
        return allProduct;

    }


    public Long selectAllPrice() {
        openDatabase();
        String sql = "select price from basket_product";
        Cursor cursor = newDb.rawQuery(sql, null);
        int count = 0;
        Log.i("SQL", sql);
        long price = 0;
        while (cursor.moveToNext()) {

            String pri = cursor.getString(cursor.getColumnIndex("price"));
            String pri2 = pri.replaceAll("﷼", "");
            pri2 = pri2.replace(",", "").trim();
            long str = Long.parseLong(pri2);
            price = price + str;
        }
        cursor.close();
        newDb.close();
        return price;

    }


    public void insertProduct() {
        openDatabase();
        String sql = "INSERT INTO basket_product VALUES (null,'" + name + "','" + price + "','" + teacheName + "'," + id + "," + count + " )";
        Log.i("NOT", sql);
        newDb.execSQL(sql);
        newDb.close();

    }


    public void updateProduct() {

        Log.i("SQL", count + "  " + id);
        openDatabase();
        String sql = "update basket_product set count=" + count + " WHERE id_product=" + id + "";
        newDb.execSQL(sql);
        newDb.close();

    }


    public void deleteProduct() {

        Log.i("SQL", count + "  " + id);
        openDatabase();
        String sql = "delete from basket_product  WHERE id_product=" + id + "";
        newDb.execSQL(sql);
        newDb.close();

    }

    /* public ArrayList<StrucNotification> getNotific() {
         ArrayList<StrucNotification> Data = new ArrayList<StrucNotification>();
         openDatabase();
         String sql = "SELECT id,title,text,date,view,image,logic_delete FROM notific ORDER BY id desc";
         Cursor cursor = newDb.rawQuery(sql, null);
         while (cursor.moveToNext()) {
             if (cursor.getInt(cursor.getColumnIndex("logic_delete")) == 0) {
                 StrucNotification notific = new StrucNotification();
                 notific.id = cursor.getInt(cursor.getColumnIndex("id"));
                 notific.title = cursor.getString(cursor.getColumnIndex("title"));
                 notific.text = cursor.getString(cursor.getColumnIndex("text"));
                 notific.date = cursor.getString(cursor.getColumnIndex("date"));
                 notific.view = cursor.getInt(cursor.getColumnIndex("view"));
                 notific.image = cursor.getString(cursor.getColumnIndex("image"));
                 Data.add(notific);
             }

         }

         cursor.close();
         newDb.close();
         return Data;

     }





     public void deleteOne() {

         openDatabase();
         String sql = "update notific set logic_delete=1 WHERE id='" + _id + "'";
         newDb.execSQL(sql);
         newDb.close();

     }*/

    /*  public ArrayList<StrucNotification> selectOne() {
          ArrayList<StrucNotification> Data = new ArrayList<StrucNotification>();
          openDatabase();
          String sql = "select date,title,text,image from notific where id='" + _id + "'";
          Cursor cursor = newDb.rawQuery(sql, null);
          int id = 0;
          while (cursor.moveToNext()) {
              StrucNotification notific = new StrucNotification();
              notific.date = cursor.getString(cursor.getColumnIndex("date"));
              notific.title = cursor.getString(cursor.getColumnIndex("title"));
              notific.text = cursor.getString(cursor.getColumnIndex("text"));
              notific.image = cursor.getString(cursor.getColumnIndex("image"));
              Data.add(notific);
          }

          cursor.close();
          newDb.close();
          return Data;

      }


      public void updateView() {
          openDatabase();
          String sql = "update notific set view=1 where id=" + _id;
          Log.i("KOK", _id + "    " + sql);
          newDb.execSQL(sql);
          newDb.close();

      }*/

}
