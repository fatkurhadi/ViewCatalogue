package com.example.favoritecatalogue;

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

import java.util.ArrayList;

import static com.example.favoritecatalogue.FavoriteDBContract.TV_SHOW_URI;

public class ModelTVShowAdapter extends RecyclerView.Adapter<ModelTVShowAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ModelTVShow> listModelTVShow = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback {
        void onItemClicked(ModelTVShow modelTVShow);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public ModelTVShowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<ModelTVShow> getListModelTVShow() {
        return listModelTVShow;
    }

    public void setListModelTVShow(ArrayList<ModelTVShow> modelTVShow) {
        listModelTVShow.clear();
        listModelTVShow.addAll(modelTVShow);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ModelTVShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_favorite, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ModelTVShowAdapter.ViewHolder viewHolder, int i) {
        ModelTVShow modelTVShow = listModelTVShow.get(i);
        Glide.with(context)
                .load(modelTVShow.getTv_show_poster())
                .into(viewHolder.imgPoster);
        Glide.with(context)
                .load(modelTVShow.getTv_show_backdrop())
                .into(viewHolder.imgBackdrop);
        viewHolder.txtName.setText(modelTVShow.getTv_show_name());
        viewHolder.txtDate.setText(modelTVShow.getTv_show_date());
        viewHolder.txtScore.setText(modelTVShow.getTv_show_score());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listModelTVShow.get(viewHolder.getAdapterPosition()));
                Uri uriTVShowAdapter = Uri.parse(TV_SHOW_URI + "/" + listModelTVShow.get(viewHolder.getAdapterPosition()).getTv_show_id());
                Intent tvShowMoveIntent = new Intent(viewHolder.itemView.getContext(), FavoriteDetailActivity.class);
                tvShowMoveIntent.setData(uriTVShowAdapter);
                tvShowMoveIntent.putExtra(FavoriteDetailActivity.EXTRA_KEY_TVShow, listModelTVShow.get(viewHolder.getAdapterPosition()));
                viewHolder.itemView.getContext().startActivity(tvShowMoveIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModelTVShow.size();
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
