package com.firebase.events;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AppPreferences {


        SharedPreferences pref;
        SharedPreferences.Editor editor;
        Context context;

        //
        int PRIVATE_MODE=0;
        private static final String PREF_NAME="app_flags";

        private static String MAP_DATA= "map_data";
    private static String MAP_DATA_STAR= "map_data_star";
        private String TAG = "App pref";

    private static final String APP_INSERT_DB ="app_insert_db";

        // Constructor
        public AppPreferences(Context context)
        {
            Log.i("coming ", "shared calss constructor");
            this.context = context;
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }




        public void clear()
        {
            editor.clear();
        }


    public void setAppInsertDb(String value)
    {
        String val=value.toString().trim();
        editor.putString(APP_INSERT_DB,val);
        editor.commit();

    }
    public String getAppInsertDb()
    {
        String flag = pref.getString(APP_INSERT_DB, "false");
        return flag;

    }


    public  void saveMap( Map inputMap){
        if (pref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pref.edit();
            editor.remove(MAP_DATA).commit();
            editor.putString(MAP_DATA, jsonString);
            editor.commit();
        }
    }

    public Map loadMap(){
        Map outputMap = new HashMap();
        try{
            if (pref != null){
                String jsonString = pref.getString(MAP_DATA, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String k = keysItr.next();
                    Log.i(TAG,"App pref"+k);
                    Log.i(TAG,"val"+jsonObject.get(k));
                    int v = (int) jsonObject.get(k);
                    outputMap.put(k,v);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }


    public  void saveMapStarData( Map inputMap){
        if (pref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pref.edit();
            editor.remove(MAP_DATA_STAR).commit();
            editor.putString(MAP_DATA_STAR, jsonString);
            editor.commit();
        }
    }

    public Map loadMapStarData(){
        Map outputMap = new HashMap();
        try{
            if (pref != null){
                String jsonString = pref.getString(MAP_DATA_STAR, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String k = keysItr.next();
                    Log.i(TAG,"App pref"+k);
                    Log.i(TAG,"val"+jsonObject.get(k));
                    int v = (int) jsonObject.get(k);
                    outputMap.put(k,v);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }



}
