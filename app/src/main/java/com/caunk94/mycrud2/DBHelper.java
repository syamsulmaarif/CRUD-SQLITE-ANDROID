package com.caunk94.mycrud2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by caunk94 on 4/25/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Procurement_db";
    public static final String TABLE_NAME = "Payment";
    public static final int DB_VERSION = 1;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IDPAYMENT = "id_payment";
    public static final String COLUMN_TANGGALPAYMENT = "tanggalpayment";
    public static final String COLUMN_SUPPLIER = "supplier";
    public static final String COLUMN_TOTALBAYAR = "total_bayar";
    public static final String CREATE_SQL = "CREATE TABLE "+ TABLE_NAME + "("
            + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT, "+ COLUMN_IDPAYMENT +
            " varchar(50), "+ COLUMN_TANGGALPAYMENT+ " DATETIME, " + COLUMN_SUPPLIER +
            " varchar(50), "+ COLUMN_TOTALBAYAR + " DECIMAL)";
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE"+TABLE_NAME);
        onCreate(db);
    }
}
