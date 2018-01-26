package com.example.square;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class MainActivity extends SingleFragmentActivity implements MainFragment.Callbacks{

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onRepoSelected(String repoId) {
        Log.d(TAG, repoId);

        if (findViewById(R.id.detailFragmentContainer) == null) {
            Intent i = new Intent(this, RepoActivity.class);
            i.putExtra(RepoFragment.REPO_ID, repoId);
            startActivity(i);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = RepoFragment.newInstance(repoId);

            if (oldDetail != null) {
                ft.remove(oldDetail);
            }

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }
}
