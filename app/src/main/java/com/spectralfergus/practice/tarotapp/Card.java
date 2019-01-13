package com.spectralfergus.practice.tarotapp;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "card_table")
public class Card {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name_short")
    private String nameShort;           // swkn
    private String name;                // Knight of Swords
    private String value;               // Knight
    @ColumnInfo(name = "value_int")
    private int valueInt;              // 12 (ekelen name: value_int)
    private String suit;                // Swords
    private String arcana;              // minor (ekelen name: type)
    @ColumnInfo(name = "meaning_up")
    private String meaningUp;           // Skill, bravery...
    @ColumnInfo(name = "meaning_down")
    private String meaningDown;         // Imprudence, incapacity... (ekelen name: meaning_rev)
    private String desc;                // He is riding in full course, as if scattering his enemies...
//    @ColumnInfo(name = "img_drawable")
//    @Ignore might be better to just fetch from web every time?
//    private Drawable imgDrawable;

    // Constructor allows room to re-create Card objects from database
    public Card(String nameShort, String name, String value, int valueInt, String suit, String arcana, String meaningUp, String meaningDown, String desc/*, Drawable imgDrawable*/) {
        this.nameShort = nameShort;
        this.name = name;
        this.value = value;
        this.valueInt = valueInt;
        this.suit = suit;
        this.arcana = arcana;
        this.meaningUp = meaningUp;
        this.meaningDown = meaningDown;
        this.desc = desc;
//        this.imgDrawable = imgDrawable;
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

    public String getMeaningDown() {
        return meaningDown;
    }

    public String getDesc() {
        return desc;
    }

//    public Drawable getImgDrawable() {
//        //todo: migrate async task that fetches drawable to Repository, then look into storing internally.
//
//        return imgDrawable;
//    }

    // == setters ==
    // only value that does not appear in constructor
    //
    public void setId(int id) {
        this.id = id;
    }
}


