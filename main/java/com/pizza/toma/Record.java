package com.pizza.toma;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "record_table")
public class Record {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mTime")
    private String mTime;
    @ColumnInfo(name = "duration")
    private int mDuration;
    @ColumnInfo(name = "type")
    private int mType;

    /** Constructor. */
    public Record(String time,int duration, int type) {
        this.mTime = time;
        this.mDuration = duration;
        this.mType = type;
    }

    public String getTime() {
        return mTime;
    }

    public int getDuration() {
        return mDuration;
    }

    public int getType() {
        return mType;
    }

    public void setTime(@NonNull String time) {
        this.mTime = time;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setType(int type) {
        this.mType = type;
    }
}
