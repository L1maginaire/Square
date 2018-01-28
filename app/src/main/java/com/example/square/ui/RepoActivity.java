package com.example.square.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;

import static com.example.square.ui.RepoFragment.REPO_DESCRIPTION;
import static com.example.square.ui.RepoFragment.REPO_ID;

public class RepoActivity extends SingleFragmentActivity implements RepoFragment.Callbacks{

    @Override
    protected Fragment createFragment() {
        String id = getIntent().getStringExtra(REPO_ID);
        String description = getIntent().getStringExtra(REPO_DESCRIPTION);
        return RepoFragment.newInstance(id, description);
    }

    @Override
    public void onClickCommitButton(String name) {
        Intent intent = new Intent(this, CommitsActivity.class);
        intent.putExtra(CommitsActivity.REPO_NAME, name);
        startActivity(intent);
    }

    @Override
    public void onClickContributorsButton(String name) {
        Intent intent = new Intent(this, ContributorsActivity.class);
        intent.putExtra(CommitsActivity.REPO_NAME, name);
        startActivity(intent);
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }*/
}
