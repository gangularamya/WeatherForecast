package com.demo.weatherforecast.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ramya on 10/9/17.
 */

public class Main implements Parcelable{

    private float temp;
    private int pressure;
    private int humidity;
    private float temp_min;
    private float temp_max;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public Main() {
    }

    private Main(Parcel in) {
        temp = in.readFloat();
        pressure = in.readInt();
        humidity = in.readInt();
        temp_min = in.readFloat();
        temp_max = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(temp);
        dest.writeInt(pressure);
        dest.writeInt(humidity);
        dest.writeFloat(temp_min);
        dest.writeFloat(temp_max);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Main> CREATOR = new Parcelable.Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };


}
