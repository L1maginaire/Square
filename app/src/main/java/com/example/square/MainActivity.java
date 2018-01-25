package com.example.square;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.square.data.models.repomodel.Repo;
import com.example.square.utils.App;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<Repo> list = new ArrayList<>();
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getApi().getSquareRepos().enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                    String s = response.body().get(0).getArchiveUrl();
                    Log.d("TAG", s);
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.d("TAG", t.toString());
            }
        });
    }
}
