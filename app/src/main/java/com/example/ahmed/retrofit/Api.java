package com.example.ahmed.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("3/movie/popular?api_key=ad7b6dbe2cd8c3b637d59a7ac1677960&language=en-US")
    Call<Movies> getJson(@Query("page")int page);

    @GET("/3/movie/popular?api_key=ad7b6dbe2cd8c3b637d59a7ac1677960&language=en-US")
    Call<Movies> sortByPopularity (@Query("page") int page, @Query("sort_by") String sort_by);
    @GET("/3/movie/top_rated?api_key=ad7b6dbe2cd8c3b637d59a7ac1677960&language=en-US")
    Call<Movies> sortByRate (@Query("page") int page, @Query("sort_by") String sort_type);

    @GET("/3/movie/upcoming?api_key=ad7b6dbe2cd8c3b637d59a7ac1677960&language=en-US")
    Call<Movies> sortByUpComing (@Query("page") int page, @Query("sort_by") String sort_type);

    @GET("/3/movie/now_playing?api_key=ad7b6dbe2cd8c3b637d59a7ac1677960&language=en-US")
    Call<Movies> sortByPlaying (@Query("page") int page, @Query("sort_by") String sort_type);

    @GET("3/movie/{id}/reviews?api_key=ad7b6dbe2cd8c3b637d59a7ac1677960&language=en-US")
    Call <MovieReview> reviews (@Path("id") String idd, @Query("page")int page);


    @GET("3/movie/{id}/videos?api_key=ad7b6dbe2cd8c3b637d59a7ac1677960&language=en-US")
    Call <MovieTrailer> trailer (@Path("id") String idd);


}
