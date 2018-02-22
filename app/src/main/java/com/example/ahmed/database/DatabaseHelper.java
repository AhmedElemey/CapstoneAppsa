package com.example.ahmed.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.ahmed.retrofit.MovieReview;
import com.example.ahmed.retrofit.Result;
import com.example.ahmed.retrofit.TrailerResult;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "PopularMovies.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String getDatabaseName(String token) {
        return token + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource source) {
        createTables(source);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource source, int oldVersion, int newVersion) {
        //dropTables(source, oldVersion, newVersion);
        createNewTables(source);
    }

    @Override
    public void close() {
        super.close();
    }

    private void createNewTables(ConnectionSource source) {
        try {
            TableUtils.createTableIfNotExists(source, Result.class);
            TableUtils.createTableIfNotExists(source, MovieReview.ResultsEntity.class);
            TableUtils.createTableIfNotExists(source, TrailerResult.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables(ConnectionSource source) {
        try {
            TableUtils.createTable(source, Result.class);
            TableUtils.createTable(source, MovieReview.ResultsEntity.class);
            TableUtils.createTable(source, TrailerResult.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTables(ConnectionSource source, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(source, Result.class, true);
            TableUtils.dropTable(source, MovieReview.ResultsEntity.class, true);
            TableUtils.dropTable(source, TrailerResult.class, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


