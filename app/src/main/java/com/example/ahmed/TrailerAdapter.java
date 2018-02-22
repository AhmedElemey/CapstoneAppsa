package com.example.ahmed;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.retrofit.MovieReview;
import com.example.ahmed.retrofit.MovieTrailer;
import com.example.ahmed.retrofit.TrailerResult;

import java.util.List;



public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private List<TrailerResult> data;
    private Context mContext;

    public TrailerAdapter(List<TrailerResult> data, Context context) {
        this.data=data;
        mContext = context;

    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_trailer, parent, false);
        return new TrailerAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final TrailerAdapter.ViewHolder holder, final int position) {
        final TrailerResult trailer = data.get(position);
        holder.trailerName.setText(trailer.getName());
        holder.trailerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchYoutubeVideo(trailer.getKey());
            }
        });
    }
    public void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            mContext.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            mContext.startActivity(webIntent);
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView trailerName;

        public ViewHolder(View itemView) {
            super(itemView);
            trailerName = (TextView) itemView.findViewById(R.id.trailer_name);
        }
    }


}
