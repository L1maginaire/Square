package com.example.square.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.square.R;

public class MainActivity extends SingleFragmentActivity implements MainFragment.Callbacks{

    private final static String TAG = MainActivity.class.getSimpleName();
    private static final String SQUARE_URL = "https://github.com/square";

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onRepoSelected(String title, String description) {
//        Log.d(TAG, title);
//        Log.d(TAG, description);

        if (findViewById(R.id.detailFragmentContainer) == null) {
            Intent i = new Intent(this, RepoActivity.class);
            i.putExtra(RepoFragment.REPO_ID, title);
            i.putExtra(RepoFragment.REPO_DESCRIPTION, description);
            startActivity(i);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = RepoFragment.newInstance(title, description);

            if (oldDetail != null) {
                ft.remove(oldDetail);
            }

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    @Override
    public void onLogoClicked() {
        Intent i = BrowserActivity.newIntent(this, Uri.parse(SQUARE_URL));
        startActivity(i);
    }
}
