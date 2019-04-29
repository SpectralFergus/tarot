package com.spectralfergus.practice.tarotapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository repository;
    private LiveData<List<Card>> cardList;
    private MutableLiveData<Integer> iSelected;
    private MutableLiveData<HashMap<String,Drawable>> cardImages;

    public CardViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
        cardList = repository.getCardList();
        iSelected = new MutableLiveData<>();
        if (cardList.getValue() == null || cardList.getValue().size() <= 0) {
            deleteAllCards();
            fetchRandomTarot(3);
        }
    }

    public void insert(Card c) {
        repository.insert(c);
    }

    public void update(Card c) {
        repository.update(c);
    }

    public void delete(Card c) {
        repository.delete(c);
    }

    public void deleteAllCards() {
        repository.deleteAllCards();
    }

    public void fetchRandomTarot(int n) {
        resetSelector();
        repository.fetchRandomTarot(n);
    }

    public void resetSelector() {
        iSelected.setValue(0);
    }

    public LiveData<List<Card>> getCardList() {
        return cardList;
    }

    public void fetchCardImage(int position) {
        repository.fetchCardImage(position);
    }

    public LiveData<Integer> getISelected() {
        return iSelected;
    }

    public void setiSelected(int position) {
        iSelected.setValue(position);
    }

    public Drawable getCardImage(Card curCard) {
        return repository.getCardImage(curCard);
    }
}
