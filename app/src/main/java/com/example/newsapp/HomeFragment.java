package com.example.newsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.DataModel.NewsData;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<NewsData.Article> articles;
    private ProgressDialog progressDialog;

    HomeFragment(Context context, List<NewsData.Article> articles, ProgressDialog progressDialog) {
        this.context = context;
        this.articles = articles;
        this.progressDialog = progressDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_home,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        progressDialog.dismiss();
        setLayout(articles);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh1, R.color.refresh2, R.color.refresh3, R.color.refresh4, R.color.refresh5);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Collections.shuffle(articles);
                setLayout(articles);
            }
        });
    }

    private void setLayout(List<NewsData.Article> articlesInner)
    {
        swipeRefreshLayout.setRefreshing(false);
        Adapter adapter=new Adapter(context,articlesInner);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1, LinearLayoutManager.VERTICAL,false));
    }

}
