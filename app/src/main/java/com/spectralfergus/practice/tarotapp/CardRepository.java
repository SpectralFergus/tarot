package com.spectralfergus.practice.tarotapp;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class CardRepository {
//    private CardDao cardDao;
    private MutableLiveData<List<Card>> cardList;
    public CardRepository(Application application) {
//        CardDatabase db = CardDatabase.getInstance(application);
//        cardDao = db.cardDao();
//        cardList = cardDao.getCardList();
    }

    // == API Functions ==
    public void insert(Card c) {
        new InsertCardAsyncTask(cardDao).execute(c);
    }
    public void update(Card c) {

    }
    public void delete(Card c) {

    }
    public void deleteAllCards() {

    }
    public MutableLiveData<List<Card>> getCardList() {
        return cardList;
    }

    // == Background Threads ==
    public static class InsertCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao cardDao;
        private InsertCardAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }
        @Override
        protected Void doInBackground(Card... cards) {
            nodeDao.insert(cards[0]);
            return null;
        }
    }

    public static class UpdateCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao cardDao;
        private UpdateCardAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }
        @Override
        protected Void doInBackground(Card... cards) {
            nodeDao.update(cards[0]);
            return null;
        }
    }

    public static class DeleteCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao cardDao;
        private DeleteCardAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }
        @Override
        protected Void doInBackground(Card... cards) {
            nodeDao.delete(cards[0]);
            return null;
        }
    }

    public static class DeleteAllCardsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CardDao cardDao;
        private DeleteAllCardsAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            nodeDao.deleteAllCards();
            return null;
        }
    }
}
