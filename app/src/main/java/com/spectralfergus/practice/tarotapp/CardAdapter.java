package com.spectralfergus.practice.tarotapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Card> mCardList = new ArrayList<>(); // Don't want to deal with NullPointerExceptions in Java

    final private ListItemOnClickListener mListener;

    public interface ListItemOnClickListener {
        void onClick(int position);
    }

    public CardAdapter(ListItemOnClickListener activity) {
        mListener = activity;
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
        holder.bind(curCard);
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public void setCardList(List<Card> cards) {
        mCardList = cards;
        notifyDataSetChanged(); //TODO: Replace NotifyDataSetChanged() with more granular notify methods
    }

    //==== INNER CLASS, CARD VIEW HOLDER
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivCard;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCard = itemView.findViewById(R.id.iv_card_item_image);
            itemView.setOnClickListener(this);
        }

        private void bind(Card curCard) {
//            ivCard.setImageDrawable(curCard.getImgDrawable());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onClick(position);
        }
    }
}
