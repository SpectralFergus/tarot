package com.spectralfergus.practice.tarotapp.utils;

import com.spectralfergus.practice.tarotapp.Card;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    // Parses a JSONObject of Cards into a List
    /* SCHEMA
        cards[]             []
            name            str
            name_short      str
            value           str
            value_int       int
            suit            str
            arcana          str
            meaning_up      str
            meaning_rev     str
            desc            str
     */
    public static List<Card> parseCardsFromJson(String jsonString) throws JSONException, IOException {
        JSONObject cardsJSON = new JSONObject(jsonString);
        int hits = cardsJSON.getInt("nhits");
        JSONArray jsonArray = cardsJSON.getJSONArray("cards");
        List<Card> parsedCards = new ArrayList<>(hits);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonCard = jsonArray.getJSONObject(i);
            parsedCards.add(pluckJsonCard(jsonCard));
        }

        return parsedCards;
    }

    // Converts JSONObject -> Card
    private static Card pluckJsonCard(JSONObject card) throws JSONException {
        return new Card(
                card.getString("name_short"),
                card.getString("name"),
                card.getString("value"),
                card.getInt("value_int"),
                card.optString("suit"),
                toTitleCase(card.getString("type")),
                card.getString("meaning_up"),
                card.getString("meaning_rev"),
                card.getString("desc"),
                false);
    }

    private static String toTitleCase(String s) {
        if (s == null || s.length() <= 0) return s;
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(s.charAt(0)));
        for (int i = 1; i < s.length(); i++) {
            sb.append(Character.toLowerCase(s.charAt(i)));
        }
        return sb.toString();
    }
}
