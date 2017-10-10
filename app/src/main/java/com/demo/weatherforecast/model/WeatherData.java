package com.demo.weatherforecast.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ramya on 10/9/17.
 */

public class WeatherData implements Parcelable{

    private Coord coord;
    private ArrayList<Weather> weather = new ArrayList<>();
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private int dt;
    private Sys sys;
    private int id;
    private String name;
    private int cod;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public WeatherData() {
    }

    private WeatherData(Parcel in) {
        coord = in.readParcelable(Coord.class.getClassLoader());
        in.readTypedList(weather,Weather.CREATOR);
        base = in.readString();
        main = in.readParcelable(Main.class.getClassLoader());
        visibility = in.readInt();
        wind = in.readParcelable(Wind.class.getClassLoader());
        clouds = in.readParcelable(Clouds.class.getClassLoader());
        dt = in.readInt();
        sys = in.readParcelable(Sys.class.getClassLoader());
        id = in.readInt();
        name = in.readString();
        cod = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(coord,flags);
        dest.writeTypedList(weather);
        dest.writeString(base);
        dest.writeParcelable(main,flags);
        dest.writeInt(visibility);
        dest.writeParcelable(wind,flags);
        dest.writeParcelable(clouds,flags);
        dest.writeInt(dt);
        dest.writeParcelable(sys,flags);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(cod);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WeatherData> CREATOR = new Parcelable.Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

}


