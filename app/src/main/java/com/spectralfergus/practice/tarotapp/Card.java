package com.spectralfergus.practice.tarotapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Drawable;

@Entity(tableName = "card_table")
public class Card {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name_short")
    private String nameShort;           // swkn
    private String name;                // Knight of Swords
    private String value;               // Knight
    @ColumnInfo(name = "value_int")
    private int valueInt;               // 12
    private String suit;                // Swords
    private String arcana;              // minor (ekelen name: type)
    @ColumnInfo(name = "meaning_up")
    private String meaningUp;           // Skill, bravery...
    @ColumnInfo(name = "meaning_rev")
    private String meaningRev;          // Imprudence, incapacity...
    private String desc;                // He is riding in full course, as if scattering his enemies...
    private boolean hasImage;

    // Constructor allows room to re-create Card objects from database
    public Card(String nameShort, String name, String value, int valueInt, String suit, String arcana, String meaningUp, String meaningRev, String desc, boolean hasImage) {
        this.nameShort = nameShort;
        this.name = name;
        this.value = value;
        this.valueInt = valueInt;
        this.suit = suit;
        this.arcana = arcana;
        this.meaningUp = meaningUp;
        this.meaningRev = meaningRev;
        this.desc = desc;
        this.hasImage = hasImage;
    }

    // == getters ==
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameShort() {
        return nameShort;
    }

    public String getValue() {
        return value;
    }

    public int getValueInt() {
        return valueInt;
    }

    public String getSuit() {
        return suit;
    }

    public String getArcana() {
        return arcana;
    }

    public String getMeaningUp() {
        return meaningUp;
    }

    public String getMeaningRev() {
        return meaningRev;
    }

    public String getDesc() {
        return desc;
    }

    public boolean getHasImage() {
        return hasImage;
    }

    @Override
    public String toString() {
        return name;
    }

    // == setters ==
    // Only value that does not appear in constructor, required for Room to interface with Card
    public void setId(int id) {
        this.id = id;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
}


