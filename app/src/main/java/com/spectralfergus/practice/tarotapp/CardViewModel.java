package com.spectralfergus.practice.tarotapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CardViewModel extends ViewModel {
    private MutableLiveData<List<Card>> mCardList;

    public MutableLiveData<List<Card>> getCardList() {
        if (mCardList == null) {
            mCardList = new MutableLiveData<>();
        }
        return mCardList;
    }
}
