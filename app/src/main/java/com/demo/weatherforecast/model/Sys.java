package com.demo.weatherforecast.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ramya on 10/9/17.
 */

public class Sys implements Parcelable{

    private int type;
    private int id;
    private float message;
    private String country;
    private int sunrise;
    private int sunset;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Sys(){
    }

    protected Sys(Parcel in) {
        type = in.readInt();
        id = in.readInt();
        message = in.readFloat();
        country = in.readString();
        sunrise = in.readInt();
        sunset = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeFloat(message);
        dest.writeString(country);
        dest.writeInt(sunrise);
        dest.writeInt(sunset);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sys> CREATOR = new Parcelable.Creator<Sys>() {
        @Override
        public Sys createFromParcel(Parcel in) {
            return new Sys(in);
        }

        @Override
        public Sys[] newArray(int size) {
            return new Sys[size];
        }
    };

}
