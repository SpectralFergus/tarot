package com.spectralfergus.practice.tarotapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.spectralfergus.practice.tarotapp.utils.JsonUtils;
import com.spectralfergus.practice.tarotapp.utils.NetworkUtils;
import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CardRepository {
    private CardDao cardDao;
    private LiveData<List<Card>> cardList;
    private static final String TAG = CardRepository.class.getSimpleName();

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
    public void fetchRandomTarot(int n) { new FetchNCardsAsyncTask(cardDao).execute(n); }

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

    // === NETWORK LOGIC TO RETRIEVE CARD DATA ===
    private static class FetchNCardsAsyncTask extends AsyncTask<Integer, Void, Void> {
        private static final String URI_BASE = "https://rws-cards-api.herokuapp.com/api/v1/cards/";
        CardDao cardDao;

        private FetchNCardsAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            // BUILD URL
            String numCards = String.valueOf(integers[0]);
            Uri uri = Uri.parse(URI_BASE).buildUpon()
//                    .appendPath("swkn")
                    .appendPath("random")
                    .appendQueryParameter("n",numCards)
                    .build();
            URL url = null;
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: MalformedURL");
            }

            // QUERY OVER NETWORK
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                List<Card> cards = JsonUtils.parseCardsFromJson(jsonResponse);
                for(Card c: cards) {
                    Log.d(TAG, "myTrace inserting card: " + c.getName());
                    cardDao.insert(c);
                }
//                return cards;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: I/O err");
//                return null;
            } catch (JSONException e) {
                Log.e(TAG, "doInBackground: JSON err");
                e.printStackTrace();
//                return null;
            }
            return null;
        }
    }
}
