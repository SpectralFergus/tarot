package com.spectralfergus.practice.tarotapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository repository;
    private LiveData<List<Card>> cardList;
    private MutableLiveData<Integer> iSelected;

    public CardViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
        cardList = repository.getCardList();
        iSelected = new MutableLiveData<>();
        iSelected.setValue(0);
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

    public LiveData<Integer> getISelected() {
        return iSelected;
    }
    public void setiSelected(int position) {
        iSelected.setValue(position);
    }
}
