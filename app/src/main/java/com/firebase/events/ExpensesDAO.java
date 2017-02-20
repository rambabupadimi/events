package com.firebase.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExpensesDAO extends AbstaractDAO implements Constants {
    public ExpensesDAO(Context context) {
        super(context);
    }

    private String TAG = "ExpensesDAO.java";

    public long insert(WebsiteModel expensesModel) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_EVENTS_ID, expensesModel.getId());
            values.put(COLUMN_EVENT_NAME, expensesModel.getName());
            values.put(COLUMN_EVENT_CATEGORY, expensesModel.getCategory());
            values.put(COLUMN_EVENT_EXPERIENCE, expensesModel.getExperience());
            values.put(COLUMN_EVENT_IMAGE, expensesModel.getImgUrl());
            values.put(COLUMN_EVENT_DESCRIPTION,expensesModel.getDescription());
            values.put(COLUMN_IS_FAVORITE,"false");
            Log.i(TAG,"values insert"+values);
            long id = db.insert(TABLE_EVENTS, null, values);
            closeDatabase();
            return id;
        } catch (Exception e) {

            closeDatabase();
            return 0;
        }
    }


    public List<WebsiteModel> getData()
    {
        try
        {
            String sql = "select * from " + TABLE_EVENTS;
            Log.i(TAG,"sql"+sql);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,null);
            Log.i(TAG,"cursor size"+cursor.getCount());
            List<WebsiteModel> mapList =new ArrayList<>();
            if (cursor.moveToFirst()) {

                do {

                    WebsiteModel websiteModel = new WebsiteModel();
                    websiteModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_ID)));
                    websiteModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME)));
                    websiteModel.setImgUrl(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_IMAGE)));
                    websiteModel.setExperience(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_EXPERIENCE)));
                    websiteModel.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DESCRIPTION)));
                    websiteModel.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_CATEGORY)));

                    Log.i(TAG,"isfav check"+cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAVORITE)));
                    websiteModel.setIsfavorite(cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAVORITE)));
                    mapList.add(websiteModel);
                }while (cursor.moveToNext());
            }
            cursor.close();
            closeDatabase();
            return mapList;
        }catch (Exception e) {

            closeDatabase();
            return null;
        }

    }

    public int updateIsFavoriteTrue(int id,String fav)
    {
        String sql = "update " + TABLE_EVENTS + " SET "+COLUMN_IS_FAVORITE+"= '"+fav+"' where "+COLUMN_EVENTS_ID+" = "+id;
        Log.i(TAG,"sql"+sql);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        Log.i(TAG,"cursor size"+cursor.getCount());
        if(cursor.moveToFirst())
        {
            return 1;
        }
        return 0;

    }

    public String getCurrentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        String result = dateFormat.format(date);
        return result;
    }


    public List<WebsiteModel> getDataFavouriteList()
    {
        try
        {
            String sql = "select * from " + TABLE_EVENTS + " where " +COLUMN_IS_FAVORITE+"= 'true'";
            Log.i(TAG,"sql"+sql);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,null);
            Log.i(TAG,"cursor size"+cursor.getCount());
            List<WebsiteModel> mapList =new ArrayList<>();
            if (cursor.moveToFirst()) {

                do {

                    WebsiteModel websiteModel = new WebsiteModel();
                    websiteModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_ID)));
                    websiteModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME)));
                    websiteModel.setImgUrl(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_IMAGE)));
                    websiteModel.setExperience(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_EXPERIENCE)));
                    websiteModel.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DESCRIPTION)));
                    websiteModel.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_CATEGORY)));

                    Log.i(TAG,"isfav check"+cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAVORITE)));
                    websiteModel.setIsfavorite(cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAVORITE)));
                    mapList.add(websiteModel);
                }while (cursor.moveToNext());
            }
            cursor.close();
            closeDatabase();
            return mapList;
        }catch (Exception e) {

            closeDatabase();
            return null;
        }

    }

    public List<WebsiteModel> getDataSortByFav() {
        try {
            String sql = "select * from " + TABLE_EVENTS + " order by " + COLUMN_IS_FAVORITE + " DESC ";
            Log.i(TAG, "sql" + sql);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            Log.i(TAG, "cursor size" + cursor.getCount());
            List<WebsiteModel> mapList = new ArrayList<>();
            if (cursor.moveToFirst()) {

                do {

                    WebsiteModel websiteModel = new WebsiteModel();
                    websiteModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_ID)));
                    websiteModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME)));
                    websiteModel.setImgUrl(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_IMAGE)));
                    websiteModel.setExperience(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_EXPERIENCE)));
                    websiteModel.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DESCRIPTION)));
                    websiteModel.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_CATEGORY)));

                    Log.i(TAG, "isfav check" + cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAVORITE)));
                    websiteModel.setIsfavorite(cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAVORITE)));
                    mapList.add(websiteModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            closeDatabase();
            return mapList;
        } catch (Exception e) {

            closeDatabase();
            return null;
        }
    }

        public List<WebsiteModel> getDataSortByCat()
        {
            try
            {
                String sql = "select * from " + TABLE_EVENTS + " order by "+COLUMN_EVENT_CATEGORY+ " ASC ";
                Log.i(TAG,"sql"+sql);
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery(sql,null);
                Log.i(TAG,"cursor size"+cursor.getCount());
                List<WebsiteModel> mapList =new ArrayList<>();
                if (cursor.moveToFirst()) {

                    do {

                        WebsiteModel websiteModel = new WebsiteModel();
                        websiteModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTS_ID)));
                        websiteModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME)));
                        websiteModel.setImgUrl(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_IMAGE)));
                        websiteModel.setExperience(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_EXPERIENCE)));
                        websiteModel.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DESCRIPTION)));
                        websiteModel.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_CATEGORY)));

                        Log.i(TAG,"isfav check"+cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAVORITE)));
                        websiteModel.setIsfavorite(cursor.getString(cursor.getColumnIndex(COLUMN_IS_FAVORITE)));
                        mapList.add(websiteModel);
                    }while (cursor.moveToNext());
                }
                cursor.close();
                closeDatabase();
                return mapList;
            }catch (Exception e) {

                closeDatabase();
                return null;
            }


        }


}