package com.example.square.utils;

/**
 * Created by l1maginaire on 1/25/18.
 */

import com.example.square.data.models.repomodel.Repo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GithubApi {
    @GET("users/square/repos")
    Call<List<Repo>> getSquareRepos();

//    @GET("{date}")
//    Single<MetaCurrency> statistics(@Path("date") String date, @Query("base") String base);
}
