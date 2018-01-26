package com.example.square;

import android.support.v4.app.Fragment;
import static com.example.square.RepoFragment.REPO_ID;

public class RepoActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        String id = getIntent().getStringExtra(REPO_ID);
        return RepoFragment.newInstance(id);
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
