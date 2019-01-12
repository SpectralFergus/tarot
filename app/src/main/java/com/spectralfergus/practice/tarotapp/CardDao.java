package com.spectralfergus.practice.tarotapp;

import androidx.lifecycle.MutableLiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface CardDao {

    @Insert
    void insert(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);

    @Query("DELETE FROM card_table")
    void deleteAllCards();

    @Query("SELECT * FROM card_table ORDER BY valueInt, name_short")
    MutableLiveData<List<Card>> getAllCards();
}
