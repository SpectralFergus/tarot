package com.spectralfergus.practice.tarotapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository repository;
    private LiveData<List<Card>> cardList;

    public CardViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
        cardList = repository.getCardList();
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

    public LiveData<List<Card>> getCardList() {
        return cardList;
    }
}
