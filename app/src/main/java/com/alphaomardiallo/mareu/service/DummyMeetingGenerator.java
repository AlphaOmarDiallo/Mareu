package com.alphaomardiallo.mareu.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Meeting A", MeetingRooms.AMSTERDAM.getCity(), MeetingRooms.AMSTERDAM.getUrl(), "30/01/2022", "14:00", "14:45", "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting B", MeetingRooms.BERLIN.getCity(), MeetingRooms.BERLIN.getUrl(), "30/01/2022", "14:00", "14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting C", MeetingRooms.BRUSSELS.getCity(), MeetingRooms.BRUSSELS.getUrl(), "30/01/2022", "14:00", "14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting D", MeetingRooms.BUCHAREST.getCity(), MeetingRooms.BUCHAREST.getUrl(), "30/01/2022", "14:00", "14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting E", MeetingRooms.LONDON.getCity(), MeetingRooms.PARIS.getUrl(), "30/01/2022", "14:00", "14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting F", MeetingRooms.MADRID.getCity(), MeetingRooms.MADRID.getUrl(), "30/01/2022", "14:00", "14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting G", MeetingRooms.MILAN.getCity(), MeetingRooms.MILAN.getUrl(), "30/01/2022", "14:00","14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting H", MeetingRooms.PARIS.getCity(), MeetingRooms.PARIS.getUrl(), "30/01/2022", "14:00", "14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting I", MeetingRooms.PRAGUE.getCity(), MeetingRooms.PRAGUE.getUrl(), "30/01/2022", "14:00", "14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting J", MeetingRooms.VIENNA.getCity(), MeetingRooms.VIENNA.getUrl(), "30/01/2022", "14:00", "14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}