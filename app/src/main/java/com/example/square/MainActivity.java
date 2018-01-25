package com.example.square;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.square.data.models.repomodel.Repo;
import com.example.square.di.components.GithubComponent;
import com.example.square.di.components.DaggerGithubComponent;
import com.example.square.di.modules.ContextModule;
import com.example.square.utils.GithubApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private List<Repo> list = new ArrayList<>();
    private TextView tv;
    private Picasso picasso;
    private GithubApi githubApi;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GithubComponent component = DaggerGithubComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        picasso = component.getPicasso();
        githubApi = component.getGithubService();

        mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(githubApi.getSquareRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .map(data -> ())
                .subscribe(data -> {
                    String t = data.get(0).getArchiveUrl();
                    Log.d(TAG, t.toString());
                })
        );
    }
}
