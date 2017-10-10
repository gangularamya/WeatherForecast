package com.demo.weatherforecast.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ramya on 10/9/17.
 */

public class Coord implements Parcelable{

    private float lon;
    private float lat;

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Coord(){
    }

    protected Coord(Parcel in) {
        lon = in.readFloat();
        lat = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(lon);
        dest.writeFloat(lat);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel in) {
            return new Coord(in);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[size];
        }
    };

}
