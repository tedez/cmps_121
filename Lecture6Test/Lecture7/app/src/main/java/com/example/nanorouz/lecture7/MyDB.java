package com.example.nanorouz.lecture7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyDB extends SQLiteOpenHelper{

    Context ctx;
    SQLiteDatabase db;
    private static String DB_NAME = "names";
    private static String TABLE_NAME = "name_table";
    private  static int VERSION = 1;

    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(id integer primary key, " +
                "first_name String, last_name String);");
        Toast.makeText(ctx, "DB is created", Toast.LENGTH_LONG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        VERSION++;
        onCreate(db);
    }

    public long insert(String s1, String s2){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("first_name", s1);
        cv.put("last_name", s2);
        return db.insert(TABLE_NAME, null, cv);
    }

    public void getAll(){
        db = getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + TABLE_NAME + ";", null );
        StringBuilder sr = new StringBuilder();
        while(cr.moveToNext()){
            sr.append(cr.getString(1) + "    " + cr.getString(2) + "\n ");
        }
        Toast.makeText(ctx, sr.toString(), Toast.LENGTH_LONG).show();
    }
    public void delete(String s){
        db = getWritableDatabase();
        db.delete(TABLE_NAME, "first_name = ?", new String[]{s});
    }

    public void update(String s1, String s2){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("first_name", s1);
        cv.put("last_name", s2);
        db.update(TABLE_NAME, cv,  "first_name = ?", new String[]{s1});
    }
}
