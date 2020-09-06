package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.DataModel.NewsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvHome, tvSettings, tvAboutUs;
    private List<NewsData.Article> articles;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress();
        ids();
        loader();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setFragment(new HomeFragment(MainActivity.this, articles, progressDialog));
                tvHome.setTextColor(Color.RED);
            }
        },2000);

    }

    private void progress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void loader() {
        ApiGoogle api=ApiClient.getRetrofit().create(ApiGoogle.class);
        api.getNewsData().enqueue(new Callback<NewsData>() {
            @Override
            public void onResponse(Call<NewsData> call, Response<NewsData> response) {
                assert response.body() != null;
                if (response.body().getStatus().equals("ok"))
                    articles=response.body().getArticles();
                else
                    Toast.makeText(MainActivity.this, "Issue", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<NewsData> call, Throwable t) {
            }
        });
    }

    private void ids() {
        tvHome = findViewById(R.id.tvHome);
        tvSettings = findViewById(R.id.tvSettings);
        tvAboutUs = findViewById(R.id.tvAboutUs);

        tvHome.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        tvHome.setTextColor(Color.WHITE);
        tvSettings.setTextColor(Color.WHITE);
        tvAboutUs.setTextColor(Color.WHITE);

        switch (view.getId())
        {
            case R.id.tvHome:
                progress();
                tvHome.setTextColor(Color.RED);
                setFragment(new HomeFragment(MainActivity.this, articles, progressDialog));
                break;
            case R.id.tvSettings:
                tvSettings.setTextColor(Color.RED);
                setFragment(new SettingsFragment(MainActivity.this));
                break;
            case R.id.tvAboutUs:
                tvAboutUs.setTextColor(Color.RED);
                setFragment(new AboutUsFragment(MainActivity.this));
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMain, fragment);
        ft.commit();
    }
}
