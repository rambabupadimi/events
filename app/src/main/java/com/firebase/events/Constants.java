package com.firebase.events;


public interface Constants {

    public final String DATABASE_NAME ="events_db";
    public final int DATABASE_VERSION = 1;


    public final String TABLE_EVENTS ="events";
    public final String COLUMN_ID   =   "id";
    public final  String COLUMN_EVENTS_ID = "events_id";
    public final String COLUMN_EVENT_NAME  = "name";
    public final String COLUMN_EVENT_CATEGORY = "category";
    public final String COLUMN_EVENT_DESCRIPTION ="description";
    public final String COLUMN_EVENT_IMAGE = "image";
    public final String COLUMN_EVENT_EXPERIENCE   =   "experience";
    public final String COLUMN_IS_FAVORITE  =   "favorite";
}
