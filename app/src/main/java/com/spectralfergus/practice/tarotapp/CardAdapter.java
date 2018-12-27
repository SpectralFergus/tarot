package com.spectralfergus.practice.tarotapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private Card[] cardList;
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
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.length : 0;
    }

    public void setCardList(Card[] cards) {
        cardList = cards;
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

        private void bind(int position) {
            ivCard.setImageDrawable(cardList[position].getImgDrawable());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onClick(position);
        }
    }
}
