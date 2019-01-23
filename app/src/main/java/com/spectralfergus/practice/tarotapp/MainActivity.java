package com.spectralfergus.practice.tarotapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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

        final RecyclerView rvCardList = findViewById(R.id.recyclerview_card_images);
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

        progressIndicator = (ProgressBar) findViewById(R.id.progress_circular);
        mainContent = (View) findViewById(R.id.main_content);
        showLoadingScreen();

        cardModel = ViewModelProviders.of(this).get(CardViewModel.class);

        cardModel.getCardList().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
//                Toast.makeText(MainActivity.this, "onChanged observer", Toast.LENGTH_SHORT).show();
//                if (cards.size() == 0) {
//                    showLoadingScreen();
//                } else {
                    cardAdapter.setCardList(cards);
                    hideLoadingScreen();
                    LiveData<Integer> iSelected = cardModel.getISelected();
                    if (iSelected != null) {
                        int i = iSelected.getValue() == null ? 0 : iSelected.getValue();
                        onClick(i);
                    }
//                }
            }
        });

        cardModel.getISelected().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if (cardAdapter.getItemCount()>0) {
                    Card c = cardModel.getCardList().getValue().get(i);
                    tvName.setText(c.getName());
                    tvValue_int.setText("Rank: " + c.getValueInt());
                    tvType.setText(String.format("%s Arcana", c.getArcana()));
                    tvMeaningUp.setText(c.getMeaningUp());
                    tvMeaningRev.setText(c.getMeaningDown());
                    tvDesc.setText(c.getDesc());
                }
            }
        });

        //for debugging purposes
//        cardModel.deleteAllCards();
//        loadCardData();
//        Card c = new Card(
//                "swkn",
//                "Knight of Swords",
//                "knight",
//                12,
//                "swords",
//                "minor",
//                "Iunno, just knights n' stuff",
//                "Iunno, just sthgink n' stuff",
//                "Test test test hope this works"
////                    null
//        );
//        cardModel.insert(c);
    }

    void loadCardData() {
//        new FetchNCardsAsyncTask().execute();
        cardModel.deleteAllCards();
        cardModel.fetchRandomTarot(3);
    }

    void showLoadingScreen() {
        progressIndicator.setVisibility(View.VISIBLE);
        mainContent.setVisibility(View.GONE);
    }

    void hideLoadingScreen() {
        progressIndicator.setVisibility(View.GONE);
        mainContent.setVisibility(View.VISIBLE);
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
        cardModel.setiSelected(position);
    }
}
