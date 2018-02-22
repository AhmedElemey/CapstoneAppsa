package com.example.ahmed.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CPDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorite_movies.db";
    private static final int DATABASE_VERSION = 1;

    public CPDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + MoviesCPContract.MovieEntry.TABLE_NAME + " (" +
                        MoviesCPContract.MovieEntry._ID                             + " INTEGER PRIMARY KEY, " +
                        MoviesCPContract.MovieEntry.COLUMN_POSTER_PATH              + " STRING, "    +
                        MoviesCPContract.MovieEntry.COLUMN_ADULT                    + " BOOLEAN,"     +
                        MoviesCPContract.MovieEntry.COLUMN_OVERVIEW                 + " STRING, "       +
                        MoviesCPContract.MovieEntry.COLUMN_RELEASE_DATE             + " STRING, "       +
                        MoviesCPContract.MovieEntry.COLUMN_ORIGINAL_TITLE           + " STRING, "       +
                        MoviesCPContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE        + " STRING, "       +
                        MoviesCPContract.MovieEntry.COLUMN_TITLE                    + " STRING, "       +
                        MoviesCPContract.MovieEntry.COLUMN_POPULARITY               + " DOUBLE, "       +
                        MoviesCPContract.MovieEntry.COLUMN_VOTE_COUNT               + " INTEGER, "       +
                        MoviesCPContract.MovieEntry.COLUMN_VIDEO                    + " BOOLEAN, "       +
                        MoviesCPContract.MovieEntry.COLUMN_VOTE_AVERAGE             + " DOUBLE, "       +
                        " UNIQUE (" + MoviesCPContract.MovieEntry._ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesCPContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
