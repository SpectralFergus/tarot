package com.spectralfergus.practice.tarotapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Card.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase {

    private static CardDatabase instance;

    public abstract CardDao cardDao();

    public static synchronized CardDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CardDatabase.class, "card_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static  RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CardDao cardDao;

        public PopulateDbAsyncTask(CardDatabase db) {
            this.cardDao = db.cardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cardDao.insert(new Card(
                    "swkn",
                    "Knight of Swords",
                    "knight",
                    12,
                    "swords",
                    "minor",
                    "Iunno, just knights n' stuff",
                    "Iunno, just sthgink n' stuff",
                    "Test test test hope this works"
//                    null
            ));
            return null;
        }
    }
}
