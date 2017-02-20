package com.firebase.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AbstaractDAO implements Constants {
    public String currentTimeStamp;
    public Context context = null;


    public AbstaractDAO(Context context) {
        this.context = context;
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        calendar.setTime(myDate);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentTimeStamp = outputFmt.format(time);

    }

    public void closeDatabase() {
        try {
            DatabaseManager.getInstance(context).closeDatabase();
        } catch (Exception e) {

        }
    }

    public SQLiteDatabase getReadableDatabase() {
        return DatabaseManager.getInstance(context).openDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return DatabaseManager.getInstance(context).openDatabase();
    }

    public void delete() {
        DatabaseManager.getInstance(context).deleteDatabase();
    }
}