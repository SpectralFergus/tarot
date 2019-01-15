package com.spectralfergus.practice.tarotapp;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.spectralfergus.practice.tarotapp.utils.JsonUtils;
import com.spectralfergus.practice.tarotapp.utils.NetworkUtils;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CardRepository {
    private CardDao cardDao;
    private LiveData<List<Card>> cardList;
    public CardRepository(Application application) {
        CardDatabase db = CardDatabase.getInstance(application);
        cardDao = db.cardDao();
        cardList = cardDao.getAllCards();
    }

    // == API Functions ==
    public void insert(Card c) {
        new InsertCardAsyncTask(cardDao).execute(c);
    }
    public void update(Card c) { new UpdateCardAsyncTask(cardDao).execute(c);}
    public void delete(Card c) { new DeleteCardAsyncTask(cardDao).execute(c);}
    public void deleteAllCards() { new DeleteAllCardsAsyncTask(cardDao).execute();}

    public LiveData<List<Card>> getCardList() {
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
            cardDao.insert(cards[0]);
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
            cardDao.update(cards[0]);
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
            cardDao.delete(cards[0]);
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
            cardDao.deleteAllCards();
            return null;
        }
    }
}
