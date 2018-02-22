package com.example.ahmed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.example.ahmed.activities.DetailsActivity;
import com.example.ahmed.activities.MainActivity;
import com.example.ahmed.retrofit.Result;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Result> data;
    private Context context;
    private MainAdapter.onMovieClick movieClick;

    public MainAdapter(List<Result> data, Context context) {
        this.context = context;
        this.data=data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainAdapter.ViewHolder holder, final int position) {
        final Result movie = data.get(position);
        String imageURL = "http://image.tmdb.org/t/p/w185/" +
                movie.getPosterPath();
        Picasso.with(context).load(imageURL).into(holder.ivimage);
        holder.ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("result", (Serializable) movie);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivimage;


        public ViewHolder(View itemView) {
            super(itemView);
            ivimage = (ImageView) itemView.findViewById(R.id.iv_poster);

        }
    }

    public void setListener(MainAdapter.onMovieClick l){
        movieClick = l;
    }
    public interface onMovieClick{
        void onMovie(int position);
    }
}
