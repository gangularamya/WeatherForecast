package com.demo.weatherforecast.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ramya on 10/9/17.
 */

public class Wind implements Parcelable {

    private float speed;
    private int deg;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Wind(){
    }

    protected Wind(Parcel in) {
        speed = in.readFloat();
        deg = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(speed);
        dest.writeInt(deg);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };

}
