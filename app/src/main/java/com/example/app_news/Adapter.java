package com.example.app_news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Model> news;

    public Adapter(Context context, List<Model> news) {
        this.news = news;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Model news = this.news.get(position);
        holder.header.setText(news.header);
        holder.text.setText(news.text);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView header, text;
        CardView newsView;

        public ViewHolder(View view) {
            super(view);

            header = view.findViewById(R.id.txtHeader);
            text = view.findViewById(R.id.txtText);

            newsView = view.findViewById(R.id.NewsView);
        }
        public TextView getHeader() {
            return header;
        }
        public void setnewsView(CardView newsView) {
            this.newsView = newsView;
        }
    }
}
