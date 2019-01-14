package com.spectralfergus.practice.tarotapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

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

    @Query("SELECT * FROM card_table ORDER BY value_int, name_short")
    LiveData<List<Card>> getAllCards();
}
