package com.example.square.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.square.R;
import com.example.square.data.models.commitmodel.Commit;
import com.example.square.data.models.CommitData;
import com.example.square.data.models.commitmodel.CommitDetails;
import com.example.square.di.components.DaggerSquareComponent;
import com.example.square.di.components.SquareComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.CommitAdapter;
import com.example.square.utils.EndlessScrollImplementation;
import com.example.square.interfaces.GithubApi;
import com.example.square.utils.StringProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CommitsActivity extends AppCompatActivity {
    public static final String REPO_NAME = "repo_name";
    private StringProcessor processor = new StringProcessor();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LinearLayoutManager mLinearLayoutManager;
    private CommitAdapter mCommitAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<CommitData> commitList;
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
        setContentView(R.layout.activity_commits);

        String repoName = getIntent().getStringExtra(REPO_NAME);

        getSupportActionBar().setSubtitle(repoName);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.commitsRecycler);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessScrollImplementation(mLinearLayoutManager) {
            @Override
            public void onLoadMore() {
                fetchData(repoName);
            }
        });
        commitList = new ArrayList<>();
        setupAdapter();
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        mGithubApi = component.getGithubService();
        fetchData(repoName);
    }

    private void setupAdapter() {
        mCommitAdapter = new CommitAdapter(this, commitList);
        mRecyclerView.setAdapter(mCommitAdapter);
    }

    private void fetchData(String repoName) {
        mCompositeDisposable.add(mGithubApi.getCommits(repoName, pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                dataProcessing(data);
                            mCommitAdapter.notifyItemRangeInserted(30 * pageNumber++, commitList.size());
                        })
        );
    }

    private void dataProcessing(List<Commit> commits){
        for (Commit commit : commits) {
            CommitDetails details = commit.getCommit();
            CommitData commitData  = new CommitData();
            commitData.setAuthor(processor.nameFormat(details.getAuthor().getName()));
            commitData.setCommitter(processor.nameFormat(details.getCommitter().getName()));
            commitData.setDate(processor.dateFormat(details.getAuthor().getDate()));
            commitData.setMessage(details.getMessage());
            commitData.setSha(commit.getSha());
            commitData.setUrl(commit.getHtmlUrl());
            commitList.add(commitData);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
