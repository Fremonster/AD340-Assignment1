package com.shaffer.ad340assignments;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shaffer.ad340assignments.models.MatchItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchItemRecyclerViewAdapter extends RecyclerView.Adapter<MatchItemRecyclerViewAdapter.ViewHolder> {

    private final List<MatchItem> mValues;
    private final MatchesContentFragment.OnListFragmentInteractionListener mListener;

    public MatchItemRecyclerViewAdapter(List<MatchItem> items, MatchesContentFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_matches, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.matchItem = mValues.get(position);
        holder.matchName.setText(mValues.get(position).name);
        holder.matchImgUrl = mValues.get(position).imageUrl;
        Picasso.get().load(holder.matchImgUrl).into(holder.matchImage);
        holder.liked = mValues.get(position).liked;
        // change color of like button depending on liked value
        if (!holder.liked) {
            holder.favoriteButton.setColorFilter(Color.DKGRAY);
        } else {
            holder.favoriteButton.setColorFilter(Color.MAGENTA);
        }

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    if (!holder.liked) {
                        Toast.makeText(holder.mView.getContext(), "You liked " + mValues.get(position).name, Toast.LENGTH_LONG).show();
                        holder.favoriteButton.setColorFilter(Color.DKGRAY);
                    } else {
                        holder.favoriteButton.setColorFilter(Color.MAGENTA);
                    }
                    mListener.onListFragmentInteraction(holder.matchItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView matchName;
        public ImageView matchImage;
        public ImageButton favoriteButton;
        public String matchImgUrl;
        public MatchItem matchItem;
        public Boolean liked;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            matchName = view.findViewById(R.id.card_title);
            matchImage = view.findViewById(R.id.card_image);
            favoriteButton = view.findViewById(R.id.favorite_button);
        }
    }
}
