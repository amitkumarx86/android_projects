package com.example.amitkumarx86.videobookmark;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by amitkumarx86 on 21/5/16.
 */
class DbHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "videoBookMark1.db";
    private static final String VIDEO_TABLE = "videoBookTable1";


    private static final String VIDEO_TOPIC = "videoTopic";
    private static final String VIDEO_TIMESTAMP = "videoTimeStamp";
    private static final String VIDEO_NAME = "videoName";

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE IF NOT EXISTS " + VIDEO_TABLE + "(" +
                VIDEO_NAME + " TEXT ," +
                VIDEO_TOPIC + " TEXT ," +
                VIDEO_TIMESTAMP  + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VIDEO_TABLE);
        onCreate(db);
    }

    public void addVideoData(VideoData videoData){
        ContentValues values = new ContentValues();

        values.put(VIDEO_NAME,videoData.getVideoName());
        values.put(VIDEO_TOPIC,videoData.getTopicName());
        values.put(VIDEO_TIMESTAMP,videoData.getTimeStamp());

        SQLiteDatabase db = getWritableDatabase();

        try {
            db.insert(VIDEO_TABLE,null, values);
        }
        catch (Exception e)
        {
            Log.d("Am", e.toString());
        }
        db.close();

    }

    public ArrayList<String> databaseToString(String videoName){


        ArrayList<String> dbString = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT  * FROM " + VIDEO_TABLE + " WHERE " +  VIDEO_NAME +" = \""+ videoName + "\";";
        //Cursor
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(VIDEO_NAME)) != null){
                //bString += c.getString(c.getColumnIndex("videoTopic"));
                dbString.add(c.getString(c.getColumnIndex(VIDEO_NAME)) + ":" + c.getString(c.getColumnIndex(VIDEO_TOPIC)) + ":" + c.getString(c.getColumnIndex(VIDEO_TIMESTAMP)));

            }
            c.moveToNext();
        }
        c.close(); // cursor close
        db.close(); //db close

        return dbString;
    }

    public int getVideoTimeStamp(String videoName, String videoTopic){

        int videoTime = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT  * FROM " + VIDEO_TABLE + " WHERE " +  VIDEO_NAME +" = \""+ videoName + "\" and " +  VIDEO_TOPIC +" = \""+ videoTopic + "\";";
        //Cursor
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(VIDEO_NAME)) != null){

                videoTime = new Integer(c.getString(c.getColumnIndex(VIDEO_TIMESTAMP)));
            }
            c.moveToNext();
        }
        c.close(); // cursor close
        db.close(); //db close

        return videoTime;
    }

    // delete from database table
    public void deleteVideoData(String _tempVideoName, String _tempVideoTopic, String _tempVideoTimeStamp){
        int videoTime = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "Delete   FROM " + VIDEO_TABLE + " WHERE " +  VIDEO_NAME +" = \""+ _tempVideoName + "\" and " + VIDEO_TOPIC +" = \""+ _tempVideoTopic + "\" and " +VIDEO_TIMESTAMP +" = \""+ _tempVideoTimeStamp + "\";";
        db.execSQL(query);
        db.close(); //db close

    }

}
