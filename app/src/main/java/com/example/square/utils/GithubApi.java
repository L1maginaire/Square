package com.example.square.utils;

/**
 * Created by l1maginaire on 1/25/18.
 */

import com.example.square.data.models.repomodel.Repo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApi {
    @GET("users/square/repos")
    Flowable<List<Repo>> getSquareRepos(@Query("page") int page);

//    @GET("{date}")
//    Single<MetaCurrency> statistics(@Path("date") String date, @Query("base") String base);
}
