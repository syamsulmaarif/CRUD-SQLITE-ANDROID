package com.caunk94.mycrud2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by caunk94 on 4/25/2016.
 */
public class DBManager {
    private DBHelper dbHelper =null;
    private SQLiteDatabase db=null;
    Cursor cursor;
    private String []columns = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_IDPAYMENT,
            DBHelper.COLUMN_TANGGALPAYMENT,
            DBHelper.COLUMN_SUPPLIER,
            DBHelper.COLUMN_TOTALBAYAR
    };

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
        //Open();
    }

    public void Open() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {

        }
    }
    public void close(){
        try {
            dbHelper.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void insert(Payment pay){
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_IDPAYMENT, pay.getIdpayment());
            values.put(DBHelper.COLUMN_TANGGALPAYMENT, pay.getTanggalpayment());
            values.put(DBHelper.COLUMN_SUPPLIER, pay.getSupplier());
            values.put(DBHelper.COLUMN_TOTALBAYAR, pay.getTotalbayar());

            db.insert(DBHelper.TABLE_NAME, null, values);
        } catch (Exception e) {

        }
    }

    public Cursor getListSiswaAsCursor(){
        Cursor c = db.query(
                DBHelper.TABLE_NAME,
                columns,
                null,
                null,
                null, null, null);
        return c;

    }

    private Payment cursorToSiswa(Cursor c){
        Payment s= null;
        if (c !=null) {
            s = new Payment();
            s.set_id(c.getLong(0));
            s.setIdpayment(c.getString(1));
            s.setTanggalpayment(c.getString(2));
            s.setSupplier(c.getString(3));
            s.setTotalbayar(Double.valueOf(c.getString(4)));
        }
        return s;
    }

    public Payment getDataByID(long id){
        Payment pay =null;
        Cursor c = db.query(
                DBHelper.TABLE_NAME,
                columns,
                DBHelper.COLUMN_ID+" = ?",
                new String[]{id+""},
                null, null, null);
        if (c.moveToFirst()) {
            pay = cursorToSiswa(c);
        }

        return pay;
    }
    public void delete(long id){
        try {
            db.delete(DBHelper.TABLE_NAME,
                    DBHelper.COLUMN_ID+" =?",
                    new String[]{id+""});
        } catch (Exception e) {

        }
    }

    public void update(Payment pay){
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_IDPAYMENT, pay.getIdpayment());
            values.put(DBHelper.COLUMN_TANGGALPAYMENT, pay.getTanggalpayment());
            values.put(DBHelper.COLUMN_SUPPLIER, pay.getSupplier());
            values.put(DBHelper.COLUMN_TOTALBAYAR, pay.getTotalbayar());

            db.update(DBHelper.TABLE_NAME,
                    values,
                    DBHelper.COLUMN_ID + " =?",
                    new String[]{pay.get_id() + ""});
        } catch (Exception e) {

        }
    }

    public Cursor fetchCountriesByIdPayment(String inputText) throws SQLException {
        Log.w(dbHelper.TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = db.query(DBHelper.TABLE_NAME, columns,
                    null, null, null, null, null);

        }
        else {
            mCursor = db.query(true, DBHelper.TABLE_NAME,columns,
                    DBHelper.COLUMN_IDPAYMENT + " like '%" + inputText + "%'",
                    null, null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor  getStudentListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();





        if (cursor == null || search.length() == 0) {
            cursor =  db.query(dbHelper.TABLE_NAME, columns,
                    null, null, null, null, null);

        }else{
            String selectQuery =  "SELECT  rowid as " +
                    DBHelper.COLUMN_ID + "," +
                    DBHelper.COLUMN_IDPAYMENT + "," +
                    DBHelper.COLUMN_TANGGALPAYMENT + "," +
                    DBHelper.COLUMN_SUPPLIER + "," +
                    DBHelper.COLUMN_TOTALBAYAR +
                    " FROM " + DBHelper.TABLE_NAME +
                    " WHERE " +  DBHelper.COLUMN_IDPAYMENT + "  LIKE  '%" +search + "%' ";
            cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
        }

        if (cursor != null) {
            cursor.moveToFirst();
            //cursor.close();
            //return null;
        }
        return cursor;


    }
}
