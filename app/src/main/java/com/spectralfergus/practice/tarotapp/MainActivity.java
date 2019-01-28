package com.spectralfergus.practice.tarotapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CardAdapter.ListItemOnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView tvName;
    TextView tvValue_int;
    TextView tvType;
    TextView tvMeaningUp;
    TextView tvMeaningRev;
    TextView tvDesc;

    private CardAdapter cardAdapter;
    private ProgressBar progressIndicator;
    private View mainContent;

    private CardViewModel cardModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView rvCardList = findViewById(R.id.recyclerview_card_images);
        rvCardList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCardList.setHasFixedSize(true); // "size" refers to screen size, not item count.

        cardAdapter = new CardAdapter(this);
        rvCardList.setAdapter(cardAdapter);

        tvName = findViewById(R.id.name);
        tvValue_int = findViewById(R.id.valueInt);
        tvType = findViewById(R.id.arcana);
        tvMeaningUp = findViewById(R.id.meaning_up);
        tvMeaningRev = findViewById(R.id.meaning_rev);
        tvDesc = findViewById(R.id.desc);

        progressIndicator = findViewById(R.id.progress_circular);
        mainContent = findViewById(R.id.main_content);
        showLoadingScreen();

        cardModel = ViewModelProviders.of(this).get(CardViewModel.class);
        cardModel.getCardList().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                Log.d(TAG, "myTrace onChanged: " + Arrays.toString(cards.toArray()));
                cardAdapter.setCardList(cards);
                if (cardAdapter.getItemCount() > 0) hideLoadingScreen();
                LiveData<Integer> iSelected = cardModel.getISelected();
                if (iSelected != null) {
                    int i = iSelected.getValue() == null ? 0 : iSelected.getValue();
                    onClick(i);
                }
            }
        });

        cardModel.getISelected().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if (cardAdapter.getItemCount() > 0 && i < cardAdapter.getItemCount()) {
                    Card c = cardModel.getCardList().getValue().get(i);
                    tvName.setText(c.getName());
                    tvValue_int.setText("Rank: " + c.getValueInt());
                    tvType.setText(String.format("%s Arcana", c.getArcana()));
                    tvMeaningUp.setText(c.getMeaningUp());
                    tvMeaningRev.setText(c.getMeaningRev());
                    tvDesc.setText(c.getDesc());
                }
            }
        });
    }

    void loadCardData() {
        showLoadingScreen();
        cardModel.deleteAllCards();
        cardModel.fetchRandomTarot(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_new_cards) {
            showLoadingScreen();
            loadCardData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        cardModel.setiSelected(position);
    }

    void showLoadingScreen() {
        progressIndicator.setVisibility(View.VISIBLE);
        mainContent.setVisibility(View.GONE);
    }

    void hideLoadingScreen() {
        progressIndicator.setVisibility(View.GONE);
        mainContent.setVisibility(View.VISIBLE);
    }
}
