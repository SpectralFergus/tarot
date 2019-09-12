package com.spectralfergus.practice.tarotapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import com.spectralfergus.practice.tarotapp.utils.JsonUtils;
import com.spectralfergus.practice.tarotapp.utils.NetworkUtils;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class CardRepository {
    private static final String TAG = CardRepository.class.getSimpleName();

    private CardDao cardDao;
    private LiveData<List<Card>> cardList;
    //todo: convert to table of drawables for persistent internal storage of card images
    private static HashMap<String, Drawable> cardImages = new HashMap<>();

    public CardRepository(Application application) {
        CardDatabase db = CardDatabase.getInstance(application);
        cardDao = db.cardDao();
        cardList = cardDao.getAllCards();
    }

    // == API Functions ==
    public void insert(Card c) {
        new InsertCardAsyncTask(cardDao).execute(c);
    }

    public void update(Card c) {
        new UpdateCardAsyncTask(cardDao).execute(c);
    }

    public void delete(Card c) {
        new DeleteCardAsyncTask(cardDao).execute(c);
    }

    public void deleteAllCards() {
        new DeleteAllCardsAsyncTask(cardDao).execute();
    }

    public void fetchRandomTarot(int n) {
        new FetchNCardsAsyncTask(cardDao).execute(n);
    }

    public void fetchCardImage(int position) {
        Card c = cardList.getValue().get(position);
        new FetchImageAsyncTask(cardDao).execute(c);
    }

    public LiveData<List<Card>> getCardList() {
        return cardList;
    }

    public Drawable getCardImage(Card curCard) {
        return cardImages.get(curCard.getNameShort());
    }

    // === Background Threads ===
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
                    .appendPath("random")
                    .appendQueryParameter("n", numCards)
                    .build();
            URL url = null;
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: MalformedURL");
            }

            // QUERY FOR CARDS OVER NETWORK
            if (url == null) return null;
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                List<Card> cards = JsonUtils.parseCardsFromJson(jsonResponse);
                for (Card c : cards) {
                    cardDao.insert(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: I/O err");
            } catch (JSONException e) {
                Log.e(TAG, "doInBackground: JSON err");
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class FetchImageAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao cardDao;
        public FetchImageAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            if (!cardImages.containsKey(cards[0].getNameShort())) {
                Drawable cardImage;
                String nameShort = cards[0].getNameShort();
                String strImage = String.format("https://www.sacred-texts.com/tarot/pkt/img/%s.jpg", nameShort);
                try {
                    cardImage = Drawable.createFromStream((InputStream) new URL(strImage).getContent(), "src");
                    if (cardImage != null) {
                        cardImages.put(nameShort, cardImage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cards[0].setHasImage(true);
            cardDao.update(cards[0]);
            return null;
        }
    }
}
