package com.example.ahmed.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ahmed.R;
import com.example.ahmed.database.MoviesCPContract;

import java.util.ArrayList;


public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList listItemList = new ArrayList();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }


    private void populateListItem() {

                    Cursor cursor = context.getContentResolver().query(MoviesCPContract.MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    MoviesCPContract.MovieEntry.COLUMN_TITLE);

        while(cursor.moveToNext()) {
            int titleIndex = cursor.getColumnIndex(MoviesCPContract.MovieEntry.COLUMN_TITLE);
            String title = cursor.getString(titleIndex);

            listItemList.add(title);
        }
    }

    @Override
    public void onCreate() {
        
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_favourit_list);

        remoteView.setTextViewText(R.id.fav_movie_name, (CharSequence) listItemList.get(i));

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
