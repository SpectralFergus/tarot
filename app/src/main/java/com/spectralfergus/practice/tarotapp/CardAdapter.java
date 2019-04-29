package com.spectralfergus.practice.tarotapp;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.animation.DrawableAlphaProperty;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Card> mCardList = new ArrayList<>(); // Don't want to deal with NullPointerExceptions in Java
    private CardViewModel viewModel;
    final private ListItemOnClickListener mListener;

    public interface ListItemOnClickListener {
        void onClick(int position);
    }

    CardAdapter(ListItemOnClickListener activity) {
        mListener = activity;
        viewModel = ViewModelProviders.of((FragmentActivity) activity).get(CardViewModel.class);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card curCard = mCardList.get(position);
        holder.bind(curCard, position);
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public void setCardList(List<Card> cards) {
        mCardList = cards;
        notifyDataSetChanged(); //TODO: Replace NotifyDataSetChanged() with more granular notify methods
    }

    public void setImages(HashMap<String, Drawable> cardImageMap) {
        notifyDataSetChanged();
    }

    //==== INNER CLASS, CARD VIEW HOLDER
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivCard;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCard = itemView.findViewById(R.id.iv_card_item_image);
            itemView.setOnClickListener(this);
        }

        private void bind(Card curCard, int position) {
            ivCard.setImageDrawable(null);
            if (!curCard.getHasImage()) {
                viewModel.fetchCardImage(position);
                return;
            }
            ivCard.setImageDrawable(viewModel.getCardImage(curCard));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onClick(position);
        }
    }
}