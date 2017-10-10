package com.demo.weatherforecast.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ramya on 10/9/17.
 */

public class Weather implements Parcelable{

    private int id;
    private String main;
    private String description;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Weather() {
    }

    private Weather(Parcel in) {
        id = in.readInt();
        main = in.readString();
        description = in.readString();
        icon = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(main);
        dest.writeString(description);
        dest.writeString(icon);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

}
