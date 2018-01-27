package com.example.square.utils;

/**
 * Created by l1maginaire on 1/25/18.
 */

import com.example.square.data.models.commitmodel.Commit;
import com.example.square.data.models.contributormodel.Contributor;
import com.example.square.data.models.repomodel.Repo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {
    @GET("users/square/repos")
    Single<List<Repo>> getSquareRepos(@Query("page") int page);

    @GET("repos/square/{repo}/commits")
    Single<List<Commit>> getCommits(@Path("repo")String repoName, @Query("page") int page);

    @GET("repos/square/{repo}/contributors")
    Single<List<Contributor>> getContributors(@Path("repo")String repoName, @Query("page") int page);


    //https://api.github.com/
//    @GET("{date}")
//    Single<MetaCurrency> statistics(@Path("date") String date, @Query("base") String base);
}
