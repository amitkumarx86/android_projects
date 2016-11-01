package com.example.amitkumarx86.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amitkumarx86 on 28/5/16.
 */
public class dbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME =  "video.db";
    private static final String DATABASE_TABLE = "videoInfo";
    private static final String VIDEO_NAME = "videoName";
    private static final String VIDEO_TOPIC = "videoTopic";
    private static final String VIDEO_TIMESTAMP = "videoTimeStamp";

    public dbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table " + DATABASE_TABLE + " (" +
                  VIDEO_NAME + " text ," +
                  VIDEO_TOPIC + " text ," +
                  VIDEO_TIMESTAMP + " text );" ;
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_TABLE);
        onCreate(db);
    }

    public void  addVideo(String videoName, String videoTopic, String videoTimeStamp)
    {
        ContentValues values = new ContentValues();
        values.put(VIDEO_NAME,videoName);
        values.put(VIDEO_TOPIC,videoTopic);
        values.put(VIDEO_TIMESTAMP,videoTimeStamp);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(DATABASE_TABLE,null,values);
        db.close();
    }

    public String tableSelect(String videoName){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + DATABASE_TABLE + " where " + VIDEO_NAME + "=\"" + videoName + "\";";

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("videoName"))!= null){
                dbString += c.getString(c.getColumnIndex("videoName"));
                dbString += c.getString(c.getColumnIndex("videoTopic"));
                dbString += c.getString(c.getColumnIndex("videoTimeStamp"));
                dbString += "\n";
            }
            c.moveToNext();
        }

        db.close();
        return dbString;
    }
}
