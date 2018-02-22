package com.example.ahmed.database;
import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MoviesContentProvider extends ContentProvider {


    public static final int CODE_FAVORITE_MOVIES = 100;
    public static final int CODE_FAVORITE_MOVIE_BY_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private CPDatabaseHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesCPContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MoviesCPContract.PATH_FAVORITE_MOVIES, CODE_FAVORITE_MOVIES);
        matcher.addURI(authority, MoviesCPContract.PATH_FAVORITE_MOVIES + "/#", CODE_FAVORITE_MOVIE_BY_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CPDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            case CODE_FAVORITE_MOVIES:
                retCursor = db.query(
                        MoviesCPContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CODE_FAVORITE_MOVIE_BY_ID:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        MoviesCPContract.MovieEntry.TABLE_NAME,
                        projection,
                        MoviesCPContract.MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch(sUriMatcher.match(uri)){
            case CODE_FAVORITE_MOVIES:
                long id = db.insert(MoviesCPContract.MovieEntry.TABLE_NAME, null, values);
                if(id > 0){
                    returnUri =  MoviesCPContract.MovieEntry.buildMovieUri(id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int tasksDeleted;

        switch (match) {
            case CODE_FAVORITE_MOVIE_BY_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(MoviesCPContract.MovieEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
