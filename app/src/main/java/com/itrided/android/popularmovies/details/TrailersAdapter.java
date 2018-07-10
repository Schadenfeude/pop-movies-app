package com.itrided.android.popularmovies.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itrided.android.popularmovies.R;
import com.itrided.android.popularmovies.model.Trailer;
import com.itrided.android.popularmovies.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private List<Trailer> trailers;
    private TrailerOnClickListener clickListener;

    public TrailersAdapter(@NonNull TrailerOnClickListener clickListener) {
        this(null, clickListener);
    }

    public TrailersAdapter(@Nullable List<Trailer> trailers,
                           @NonNull TrailerOnClickListener clickListener) {
        this.trailers = trailers;
        this.clickListener = clickListener;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.trailers_list_item, null);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer trailer = trailers.get(position);
        holder.bind(trailer, clickListener);
    }

    @Override
    public int getItemCount() {
        return trailers != null ? trailers.size() : 0;
    }

    static class TrailerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.trailer_background_iv)
        ImageView backgroundIv;
        @BindView(R.id.trailer_title_tv)
        TextView titleTv;

        TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Trailer trailer, @NonNull TrailerOnClickListener clickListener) {
            titleTv.setText(trailer.getName());
            loadTrailerImage(trailer.getYouTubeKey());
            itemView.setOnClickListener(v -> clickListener.onItemClicked(trailer));
        }

        private void loadTrailerImage(@NonNull String youTubeKey) {
            final Context context = itemView.getContext();

            ImageLoader.getInstance(context)
                    .loadTrailerImageIntoTarget(youTubeKey, backgroundIv);
        }
    }
}
