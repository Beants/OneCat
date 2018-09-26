package com.pilab.maxu.calculator.calu.notebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by andersen on 2018/9/17.
 */

public class NoteDBHelper extends SQLiteOpenHelper {


    private static final int DB_VERSION = 1;
    private Context mContext;

    private static final String DB_NAME = "mtools.db";
    private static final String TABLE_NAME = "Notes";

    public NoteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table Orders(Id integer primary key, CustomName text, OrderPrice integer, Country text);
        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key, time text , title text , content text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key, time text , title text , content text)";
        db.execSQL(sql);
        super.onOpen(db);
        Log.i("DBHelper", "onOpen:DBHelper 连接数据库成功！ ");
    }


}