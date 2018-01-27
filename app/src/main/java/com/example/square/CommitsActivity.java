package com.example.square;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.square.data.models.commitmodel.Commit;
import com.example.square.data.models.commitmodel.CommitData;
import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.CommitAdapter;
import com.example.square.utils.EndlessScrollImplementation;
import com.example.square.utils.GithubApi;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CommitsActivity extends AppCompatActivity {
    public static final String REPO_NAME = "repo_name";
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LinearLayoutManager mLinearLayoutManager;
    private CommitAdapter mCommitAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<CommitData> commitList;
    private GithubApi mGithubApi;
    private int pageNumber = 1;

    @Override
    protected void onStop() {//todo: onpause?
        super.onStop();
        mCompositeDisposable.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);

        String s = getIntent().getStringExtra(REPO_NAME);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.commitsRecycler);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessScrollImplementation(mLinearLayoutManager) {
            @Override
            public void onLoadMore() {
                subscribeForData(s);
            }
        });
        commitList = new ArrayList<>();
        setupAdapter();
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        mGithubApi = component.getGithubService();
        subscribeForData(s);
    }

    private void setupAdapter() {
        mCommitAdapter = new CommitAdapter(this, commitList);
        mRecyclerView.setAdapter(mCommitAdapter);
    }

    private void subscribeForData(String repoName) {
        mCompositeDisposable.add(mGithubApi.getCommits(/*repoName, */pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
//                        .map(data -> ())
                        .subscribe(data -> {
                            for (Commit commit : data) {
                                CommitData commitData  = new CommitData();
                                commitData.setAuthor(commit.getCommit().getAuthor().getName()); //todo to map
                                commitData.setCommitter(commit.getCommit().getCommitter().getName());
                                commitData.setDate(commit.getCommit().getAuthor().getDate());
                                commitData.setMessage(commit.getCommit().getMessage());
                                commitData.setSha(commit.getSha());
                                commitData.setUrl(commit.getCommit().getUrl());
                                commitList.add(commitData);
                            }
                            mCommitAdapter.notifyItemRangeInserted(30/*30ли?*/ * pageNumber++, commitList.size());
                        })
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);//todo
    }
}
