package com.example.square.interfaces;

/**
 * Created by l1maginaire on 1/25/18.
 */

import com.example.square.mvp.model.commits.Commit;
import com.example.square.mvp.model.contributors.Contributor;
import com.example.square.mvp.model.repos.Repo;

import java.util.List;

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
}
