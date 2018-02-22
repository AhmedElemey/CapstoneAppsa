package com.example.ahmed.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ahmed.R;
import com.example.ahmed.ReviewAdapter;
import com.example.ahmed.TrailerAdapter;
import com.example.ahmed.Widget.FavouritMovieWidgetProvider;
import com.example.ahmed.activities.DetailsActivity;
import com.example.ahmed.database.MoviesCPContract;
import com.example.ahmed.retrofit.Api;
import com.example.ahmed.retrofit.MovieReview;
import com.example.ahmed.retrofit.MovieTrailer;
import com.example.ahmed.retrofit.Result;
import com.example.ahmed.retrofit.TrailerResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailFragment extends Fragment {
    String TAG = DetailsActivity.class.getSimpleName();
    @BindView(R.id.iv_detail_poster)
    ImageView poster;
    @BindView(R.id.iv_detail_poster1)
    ImageView poster1;
    @BindView(R.id.btn_fav)
    ImageView btnFav;
    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.tv_date)
    TextView date;
    @BindView(R.id.tv_vote)
    TextView vote;
    @BindView(R.id.tv_over)
    TextView over;

    @BindView(R.id.radio_group)
    RadioGroup rdGroup;

    @BindView(R.id.trailer_list)
    RecyclerView trailerList;

    @BindView(R.id.people_list)
    RecyclerView peopleList;
    ReviewAdapter reviewAdapter;
    TrailerAdapter trailerAdapter;
    Result movie;
    String imageURL;
    Api api;
    List<TrailerResult> trailerResultslist;
    List<MovieReview.ResultsEntity> reviewsResultslist;
    Context context;
    int id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        ButterKnife.bind(this,view);
        trailerResultslist = new ArrayList<>();
        reviewsResultslist = new ArrayList<>();
        movie = (Result) getArguments().getSerializable("result");
        if (isFavorite(movie)) {
            btnFav.setImageResource(R.drawable.ic_favourite);
        } else {
            btnFav.setImageResource(R.drawable.ic_unfavourite);
        }

        title.setText(movie.getTitle());
        date.setText(movie.getReleaseDate());
        over.setText(movie.getOverview());
        vote.setText(""+movie.getVoteAverage());
        id = movie.getId();
        Log.wtf("dasd", id + "");
        imageURL = "http://image.tmdb.org/t/p/w185/" + movie.getPosterPath();
        Picasso.with(getActivity()).load(imageURL).into(poster);
        Picasso.with(getActivity()).load(imageURL).into(poster1);


        trailerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        trailerAdapter = new TrailerAdapter(trailerResultslist,getActivity());
        trailerList.setAdapter(trailerAdapter);

        reviewAdapter = new ReviewAdapter(reviewsResultslist);
        peopleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        peopleList.setAdapter(reviewAdapter);



        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite(movie)){

                    removeMovieFromContentProvider(movie);
                } else {
                    addMovieToContentProvider(movie);
                }
                Intent intent = new Intent(getActivity(), FavouritMovieWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                int[] ids = AppWidgetManager.getInstance(getActivity()).getAppWidgetIds(new ComponentName(getActivity(), FavouritMovieWidgetProvider.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                getActivity().sendBroadcast(intent);}
        });

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.reviews_radio){
                    //show review
                    peopleList.setVisibility(View.VISIBLE);
                    trailerList.setVisibility(View.GONE);
                }
                else {
                    //show trailers
                    peopleList.setVisibility(View.GONE);
                    trailerList.setVisibility(View.VISIBLE);

                }
            }
        });

        review();
        trailers();
        return view;

    }

    private void trailers() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        final Api api = retrofit.create(Api.class);
        api.trailer(""+id).enqueue(new Callback<MovieTrailer>() {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response) {
                Log.i(TAG,"onResponse");
                Log.i(TAG,"onResponse response.body()");
                MovieTrailer movieTrailer = response.body();
                trailerResultslist.addAll(movieTrailer.getResults());
                trailerAdapter.notifyDataSetChanged();

            }
            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t) {
                Log.i(TAG," trailer onFailure "+t.getMessage());
            }
        });
    }

    private void review() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        final Api api = retrofit.create(Api.class);
        api.reviews(id + "", 1).enqueue(new Callback<MovieReview>() {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {
                Log.i(TAG, "url "+response.raw().toString());
                MovieReview movieReview = response.body();
                reviewsResultslist.addAll(movieReview.getResults());
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieReview> call, Throwable t) {


            }


        });
    }
    private void addMovieToContentProvider(Result movieModel) {

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoviesCPContract.MovieEntry._ID, movieModel.getId());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, movieModel.getOriginalLanguage());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_TITLE, movieModel.getTitle());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movieModel.getOriginalTitle());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_ADULT, movieModel.getAdult());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_POPULARITY, movieModel.getPopularity());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_POSTER_PATH, movieModel.getPosterPath());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_OVERVIEW, movieModel.getOverview());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_RELEASE_DATE, movieModel.getReleaseDate());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_VOTE_AVERAGE, movieModel.getVoteAverage());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_VOTE_COUNT, movieModel.getVoteCount());
            contentValues.put(MoviesCPContract.MovieEntry.COLUMN_VIDEO, movieModel.getVideo());
            Uri uri = getActivity().getContentResolver().insert(MoviesCPContract.MovieEntry.CONTENT_URI, contentValues);

            btnFav.setImageResource(R.drawable.ic_favourite);

            Log.w("URI: ", uri.toString());
        }catch (UnsupportedOperationException e){
            e.printStackTrace();
        }
    }

    private void removeMovieFromContentProvider(Result movieModel) {
        try {
            int id = movieModel.getId();
            String stringId = Integer.toString(id);
            Uri uri = MoviesCPContract.MovieEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(stringId).build();
            getActivity().getContentResolver().delete(uri, null, null);
            btnFav.setImageResource(R.drawable.ic_unfavourite);
        }
        catch (UnsupportedOperationException e){
            e.printStackTrace();
        }
    }

    private boolean isFavorite(Result movieModel) {
        Uri singleUri = ContentUris.withAppendedId(MoviesCPContract.MovieEntry.CONTENT_URI,movieModel.getId());
        String[] titleColumn = {MoviesCPContract.MovieEntry.COLUMN_TITLE};
        Cursor coverCursor = getActivity().getContentResolver().query(singleUri,titleColumn , null, null, null);

        boolean isFavorite = coverCursor.getCount() > 0 ? true : false;
        coverCursor.close();
        return isFavorite;
    }

}

