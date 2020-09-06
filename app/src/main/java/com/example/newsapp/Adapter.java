package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsapp.DataModel.NewsData;
import com.squareup.picasso.Picasso;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<NewsData.Article> articles;
    private Context context;
    Adapter(Context context, List<NewsData.Article> articles)
    {
        this.context=context;
        this.articles=articles;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final NewsData.Article article=articles.get(i);

            viewHolder.textViewTitle.setText(articles.get(i).getTitle());
            viewHolder.textViewTitle.setTextColor(Color.parseColor("#00574B"));
            viewHolder.textViewDetail.setText(article.getDescription());
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            Picasso.with(context).load(articles.get(i).getUrlToImage())
                    .placeholder(R.drawable.ic_refresh).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle,textViewDetail;
        private ImageView imageView;
        private CardView cardView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardView);
            textViewDetail=itemView.findViewById(R.id.textViewDetail);
            textViewTitle=itemView.findViewById(R.id.textViewHeading);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
