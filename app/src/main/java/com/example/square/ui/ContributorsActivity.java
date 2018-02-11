package com.example.square.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.square.R;
import com.example.square.data.models.ContributorsData;
import com.example.square.data.models.contributormodel.Contributor;
import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.ContributorsAdapter;
import com.example.square.utils.EndlessScrollImplementation;
import com.example.square.interfaces.GithubApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContributorsActivity extends AppCompatActivity {
    public static final String REPO_NAME = "repo_name";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinearLayoutManager linearLayoutManager;
    private ContributorsAdapter contributorsAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ContributorsData> contributorsList;
    private GithubApi githubApi;
    private int pageNumber = 1;

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);

        String repoName = getIntent().getStringExtra(REPO_NAME);
        getSupportActionBar().setSubtitle(repoName);


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) findViewById(R.id.contributorsRecycler);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new EndlessScrollImplementation(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                subscribeForData(repoName);
            }
        });
        contributorsList = new ArrayList<>();
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        githubApi = component.getGithubService();
        setupAdapter(component.getPicasso());
        subscribeForData(repoName);
    }

    private void setupAdapter(Picasso picasso) {
        contributorsAdapter = new ContributorsAdapter(this, contributorsList, picasso);
        recyclerView.setAdapter(contributorsAdapter);
    }

    private void subscribeForData(String repoName) {
        compositeDisposable.add(githubApi.getContributors(repoName, pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            dataProcessing(data);
                            contributorsAdapter.notifyItemRangeInserted(30 * pageNumber++, contributorsList.size());
                        })
        );
    }

    private void dataProcessing(List<Contributor> contributors) {
        for (Contributor contributor : contributors) {
            ContributorsData contributorsData = new ContributorsData();
            contributorsData.setAvatarUrl(contributor.getAvatarUrl());
            contributorsData.setContributions(contributor.getContributions());
            contributorsData.setLogin(contributor.getLogin());
            contributorsData.setProfileUrl(contributor.getHtmlUrl());
            contributorsList.add(contributorsData);
        }
    }
}
