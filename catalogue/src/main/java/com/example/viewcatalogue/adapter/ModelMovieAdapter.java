package com.example.viewcatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.viewcatalogue.CatalogueDetailActivity;
import com.example.viewcatalogue.R;
import com.example.viewcatalogue.helper.ModelMovie;

import java.util.ArrayList;

import static com.example.viewcatalogue.database.CatalogueDBContract.MOVIE_URI;

public class ModelMovieAdapter extends RecyclerView.Adapter<ModelMovieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ModelMovie> listModelMovie = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback {
        void onItemClicked(ModelMovie modelMovie);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public ModelMovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<ModelMovie> getListModelMovie() {
        return listModelMovie;
    }

    public void setListModelMovie(ArrayList<ModelMovie> modelMovie) {
        listModelMovie.clear();
        listModelMovie.addAll(modelMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ModelMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_catalogue, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ModelMovieAdapter.ViewHolder viewHolder, int i) {
        ModelMovie modelMovie = listModelMovie.get(i);
        Glide.with(context)
                .load(modelMovie.getMovie_poster())
                .into(viewHolder.imgPoster);
        Glide.with(context)
                .load(modelMovie.getMovie_backdrop())
                .into(viewHolder.imgBackdrop);
        viewHolder.txtName.setText(modelMovie.getMovie_name());
        viewHolder.txtDate.setText(modelMovie.getMovie_date());
        viewHolder.txtScore.setText(modelMovie.getMovie_score());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listModelMovie.get(viewHolder.getAdapterPosition()));
                Uri uriMovieAdapter = Uri.parse(MOVIE_URI + "/" + listModelMovie.get(viewHolder.getAdapterPosition()).getMovie_id());
                Intent movieMoveIntent = new Intent(viewHolder.itemView.getContext(), CatalogueDetailActivity.class);
                movieMoveIntent.setData(uriMovieAdapter);
                movieMoveIntent.putExtra(CatalogueDetailActivity.EXTRA_KEY_Movie, listModelMovie.get(viewHolder.getAdapterPosition()));
                viewHolder.itemView.getContext().startActivity(movieMoveIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModelMovie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster, imgBackdrop;
        TextView txtName, txtDate, txtScore;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.item_poster);
            imgBackdrop =itemView.findViewById(R.id.item_backdrop);
            txtName = itemView.findViewById(R.id.item_name);
            txtDate = itemView.findViewById(R.id.item_date);
            txtScore = itemView.findViewById(R.id.item_score);
        }
    }
}
