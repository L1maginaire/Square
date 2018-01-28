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
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LinearLayoutManager mLinearLayoutManager;
    private ContributorsAdapter mContributorsAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<ContributorsData> contributorsList;
    private GithubApi mGithubApi;
    private int pageNumber = 1;

    @Override
    protected void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);

        String repoName = getIntent().getStringExtra(REPO_NAME);
        getSupportActionBar().setSubtitle(repoName);


        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.contributorsRecycler);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessScrollImplementation(mLinearLayoutManager) {
            @Override
            public void onLoadMore() {
                fetchData(repoName);
            }
        });
        contributorsList = new ArrayList<>();
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        mGithubApi = component.getGithubService();
        setupAdapter(component.getPicasso());
        fetchData(repoName);
    }

    private void setupAdapter(Picasso picasso) {
        mContributorsAdapter = new ContributorsAdapter(this, contributorsList, picasso);
        mRecyclerView.setAdapter(mContributorsAdapter);
    }

    private void fetchData(String repoName) {
        mCompositeDisposable.add(mGithubApi.getContributors(repoName, pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .map(data -> ())
                        .subscribe(data -> {
                            dataProcessing(data);
                            mContributorsAdapter.notifyItemRangeInserted(30 * pageNumber++, contributorsList.size());
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
