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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CommitsActivity extends AppCompatActivity {
    public static final String REPO_NAME = "repo_name";
    private StringProcessor processor = new StringProcessor();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LinearLayoutManager linearLayoutManager;
    private CommitAdapter commitAdapter;
    private RecyclerView recyclerView;
    private ArrayList<CommitData> commitList = new ArrayList<>();
    ;
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
        setContentView(R.layout.activity_commits);

        String repoName = getIntent().getStringExtra(REPO_NAME);

        getSupportActionBar().setSubtitle(repoName);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) findViewById(R.id.commitsRecycler);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new EndlessScrollImplementation(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                subscribeForData(repoName);
            }
        });
        setupAdapter();
        SquareComponent component = DaggerSquareComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        githubApi = component.getGithubService();
        subscribeForData(repoName);
    }

    private void setupAdapter() {
        commitAdapter = new CommitAdapter(this, commitList);
        recyclerView.setAdapter(commitAdapter);
    }

    private void subscribeForData(String repoName) {
        compositeDisposable.add(githubApi.getCommits(repoName, pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                dataProcessing(data);
                            commitAdapter.notifyItemRangeInserted(30 * pageNumber++, commitList.size());
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
}
