package com.alphaomardiallo.mareu.models;

import com.alphaomardiallo.mareu.R;

public enum MeetingRooms {
    PARIS("Paris", 1, R.drawable.paris),
    LONDON("London", 2, R.drawable.london),
    MADRID("Madrid", 3, R.drawable.madrid),
    MILAN("Milano", 4, R.drawable.milan),
    BUCHAREST("Bucharest", 5, R.drawable.bucharest),
    VIENNA("Vienna", 6, R.drawable.vienna),
    BERLIN("Berlin", 7, R.drawable.berlin),
    PRAGUE("Prague", 8, R.drawable.prague),
    AMSTERDAM("Amsterdam", 9, R.drawable.amsterdam),
    BRUSSELS("Brussels", 10, R.drawable.brussels);

    private String city;
    private int position;



    private int drawable;

    MeetingRooms(String cityName, int positionCity, int picture){
        city = cityName;
        position = positionCity;
        drawable = picture;
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


}
