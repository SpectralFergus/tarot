package com.spectralfergus.practice.tarotapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CardAdapter.ListItemOnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView tvName;
//    TextView tvNameShort;
//    TextView tvValue;
    TextView tvValue_int;
//    TextView tvSuit;
    TextView tvType;
    TextView tvMeaningUp;
    TextView tvMeaningRev;
    TextView tvDesc;
//    ImageView ivCard;
//    Drawable d;

    private CardAdapter cardAdapter;
    private List<Card> cardsList;

    private ProgressBar progressIndicator;
    private View mainContent;

    private CardViewModel cardModel;

    private CardDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();

        RecyclerView rvCardList = findViewById(R.id.recyclerview_card_images);
        rvCardList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCardList.setHasFixedSize(true); // "size" refers to screen size, not item count.

        cardAdapter = new CardAdapter(this);
        rvCardList.setAdapter(cardAdapter);

        tvName = findViewById(R.id.name);
//        tvNameShort = findViewById(R.id.name_short);
//        tvValue = findViewById(R.id.value);
        tvValue_int = findViewById(R.id.valueInt);
//        tvSuit = findViewById(R.id.suit);
        tvType = findViewById(R.id.arcana);
        tvMeaningUp = findViewById(R.id.meaning_up);
        tvMeaningRev = findViewById(R.id.meaning_rev);
        tvDesc = findViewById(R.id.desc);
//        ivCard = findViewById(R.id.iv_card);

        cardModel = ViewModelProviders.of(this).get(CardViewModel.class);
        final Observer<List<Card>> cardObserver = new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                cardAdapter.setCardList(cards);
            }
        };
        cardModel.getCardList().observe(this, cardObserver);

        progressIndicator = (ProgressBar) findViewById(R.id.progress_circular);
        mainContent = (View) findViewById(R.id.main_content);

        db = CardDatabase.getInstance(this);
//        loadCardData();
    }

    void loadCardData() {
//        new FetchTarotAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_reset) {
            loadCardData();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Card c = cardsList.get(position);
        tvName.setText(c.getName());
        tvValue_int.setText("Rank: " + c.getValueInt());
        tvType.setText(String.format("%s Arcana", c.getArcana()));
        tvMeaningUp.setText(c.getMeaningUp());
        tvMeaningRev.setText(c.getMeaningDown());
        tvDesc.setText(c.getDesc());
    }


//    // === NETWORK LOGIC TO RETRIEVE CARD DATA ===
//     public class FetchTarotAsyncTask extends AsyncTask<String, Void, MutableLiveData<List<Card>>> {
//        private static final String URI_BASE = "https://rws-cards-api.herokuapp.com/api/v1/cards/";
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressIndicator.setVisibility(View.VISIBLE);
//            mainContent.setVisibility(View.GONE);
//        }
//
//        @Override
//        protected MutableLiveData<List<Card>> doInBackground(String... strings) {
//            // BUILD URL
//            Uri uri = Uri.parse(URI_BASE).buildUpon()
////                    .appendPath("swkn")
//                    .appendPath("random")
//                    .appendQueryParameter("n","3")
//                    .build();
//            URL url = null;
//            try {
//                url = new URL(uri.toString());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                Log.e(TAG, "doInBackground: MalformedURL");
//            }
//
//            // QUERY OVER NETWORK
//            try {
//                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
//                List<Card> cards = JsonUtils.parseCardsFromJson(getApplicationContext(),jsonResponse);
//
//                return cards;
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e(TAG, "doInBackground: I/O err");
//                return null;
//            } catch (JSONException e) {
//                Log.e(TAG, "doInBackground: JSON err");
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(List<Card> cards) {
//            super.onPostExecute(cards);
//            if(cards != null) {
//                cardsList = cards;
//                cardAdapter.setCardList(cards);
//                onClick(0);
//
//                progressIndicator.setVisibility(View.GONE);
//                mainContent.setVisibility(View.VISIBLE);
//            } else {
//                //TODO: error message display
//            }
//        }
//    }
}
