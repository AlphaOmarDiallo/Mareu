package com.alphaomardiallo.mareu.models;

import androidx.annotation.NonNull;

import com.alphaomardiallo.mareu.R;

public enum MeetingRooms {
    PARIS("Paris", 1, R.drawable.paris, "https://images.pexels.com/photos/1850619/pexels-photo-1850619.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"),
    LONDON("London", 2, R.drawable.london2, "https://images.pexels.com/photos/427679/pexels-photo-427679.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"),
    MADRID("Madrid", 3, R.drawable.madrid, "https://images.pexels.com/photos/947177/pexels-photo-947177.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"),
    MILAN("Milano", 4, R.drawable.milan, "https://images.pexels.com/photos/3937645/pexels-photo-3937645.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"),
    BUCHAREST("Bucharest", 5, R.drawable.bucharest, "https://images.pexels.com/photos/6273450/pexels-photo-6273450.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"),
    VIENNA("Vienna", 6, R.drawable.vienna, "https://images.pexels.com/photos/161074/vienna-st-charles-s-church-downtown-church-161074.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"),
    BERLIN("Berlin", 7, R.drawable.berlin, "https://images.pexels.com/photos/2570063/pexels-photo-2570063.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"),
    PRAGUE("Prague", 8, R.drawable.prague, "https://images.pexels.com/photos/126292/pexels-photo-126292.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"),
    AMSTERDAM("Amsterdam", 9, R.drawable.amsterdam, "https://images.pexels.com/photos/2031706/pexels-photo-2031706.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"),
    BRUSSELS("Brussels", 10, R.drawable.brussels, "https://images.pexels.com/photos/7901477/pexels-photo-7901477.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");

    private String city;
    private int position;
    private int drawable;
    private String url;

    MeetingRooms(String cityName, int positionCity, int picture, String pictureURL){
        city = cityName;
        position = positionCity;
        drawable = picture;
        url = pictureURL;
    }

    public String getCity() {
        return city;
    }

    public int getPosition() {
        return position;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getUrl() {
        return url;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
