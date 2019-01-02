package com.spectralfergus.practice.tarotapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.spectralfergus.practice.tarotapp.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JsonUtils {
    public static Card[] parseCardsFromJson(Context context, String jsonString) throws JSONException, IOException {
        /* SCHEMA
            cards[]             []
                name            str
                name_short      str
                value           str
                value_int       int
                suit            str
                arcana            str
                meaning_up      str
                meaning_rev     str
                desc            str
         */
        JSONObject cardsJSON = new JSONObject(jsonString);
        int hits = cardsJSON.getInt("nhits");
//        if(hits < 2) return new Card[]{pluckJsonCard(cardsJSON.getJSONObject("card"))};
        JSONArray jsonArray = cardsJSON.getJSONArray("cards");
        Card[] parsedCards = new Card[hits];
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonCard = jsonArray.getJSONObject(i);
            parsedCards[i] = pluckJsonCard(jsonCard);
        }

        return parsedCards;
    }

    private static Card pluckJsonCard(JSONObject card) throws IOException, JSONException {
        String strImage = String.format("http://www.sacred-texts.com/tarot/pkt/img/%s.jpg", card.getString("name_short"));
        Drawable d = Drawable.createFromStream((InputStream) new URL(strImage).getContent(), "src");

        return new Card( card.getString("name"),
                card.getString("name_short"),
                card.getString("value"),
                card.getInt("value_int"),
                card.optString("suit"),
                card.getString("type"),
                card.getString("meaning_up"),
                card.getString("meaning_rev"),
                card.getString("desc"),
                d);
    }
}