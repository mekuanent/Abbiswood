package com.bluetea.abbiswood.helper;

import com.bluetea.abbiswood.vo.CinemaVO;
import com.bluetea.abbiswood.vo.ListingVO;
import com.bluetea.abbiswood.vo.NewsVO;
import com.bluetea.abbiswood.vo.ShowtimeVO;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ApiClient {

    @GET("/listCinemas.php")
    void listCinemas(Callback<List<CinemaVO>> response);

    @GET("/listNews.php")
    void listNews(Callback<List<NewsVO>> response);

    @GET("/listShowtime.php")
    void listShowtime(Callback<List<ShowtimeVO>> response);

    @FormUrlEncoded
    @POST("/listVoted.php")
    void listVoted(@Field("op") Long op, @Field("deviceCode") String deviceCode, Callback<List<ListingVO>> response);

    @FormUrlEncoded
    @POST("/listNonVoted.php")
    void listNonVoted(@Field("op") Long op, Callback<List<ListingVO>> response);

    @FormUrlEncoded
    @POST("/vote.php")
    void vote(@Field("op") Long op,
              @Field("deviceCode") String deviceCode,
              @Field("boardId") Long boardId,
              Callback<Long> response);

//    @FormUrlEncoded
//    @POST("/accounts/authenticateUser")
//    void authenticateUser(@Field("username") String username, @Field("password") String password,
//                          Callback<AuthenticateVO> response);

}
