package com.ucsc.cmps128.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class MyDB extends SQLiteOpenHelper{

    Context ctx;
    SQLiteDatabase db;

    private static String DB_NAME = "photos.db";
    private static String TABLE_NAME = "Gallery";
    private static String COL1 = "title";
    private static String COL2 = "picture";
    private static String COL3 = "path";
    private  static int VERSION = 1;

    public MyDB(Context context) {
        super(context, DB_NAME, null, VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TABLE_NAME +
                        " (ID INTEGER PRIMARY KEY, " +
                        COL1 + " TEXT, " +
                        COL2 + " BLOB, " +
                        COL3 + " TEXT )";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        VERSION++;
        onCreate(db);
    }

    public long addData(Bitmap bm, String title){
        db = this.getWritableDatabase();
        ByteArrayOutputStream st = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 0, st);
        ContentValues cv = new ContentValues();
        cv.put(COL2, st.toByteArray());
        cv.put(COL1, title);
        return db.insert(TABLE_NAME, null, cv);
    }

    private int getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return (int)(count);
    }

    public long addDataPath(String path, String title) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL3, path);
        cv.put(COL1, title);
        return db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getAll(){
        db = getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + TABLE_NAME + ";", null );
        return cr;
    }

    public Cursor getRange(int a, int b) {
        db = getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + TABLE_NAME +
                                " where ID between "+a+" and "+b+";"
                                , null );
        return cr;
    }
    public Integer deleteKey(String s, String type){
        db = getWritableDatabase();
        if (type == "title") {
            return db.delete(TABLE_NAME, "title = ? ", new String[]{s});
        } else {
            return db.delete(TABLE_NAME, type + " = " + s, null);
        }
    }
}
