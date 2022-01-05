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
            new Meeting("Meeting A", MeetingRooms.AMSTERDAM.getCity(), MeetingRooms.AMSTERDAM.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45), "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting B", MeetingRooms.BERLIN.getCity(), MeetingRooms.BERLIN.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting C", MeetingRooms.BRUSSELS.getCity(), MeetingRooms.BRUSSELS.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting D", MeetingRooms.BUCHAREST.getCity(), MeetingRooms.BUCHAREST.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting E", MeetingRooms.LONDON.getCity(), MeetingRooms.PARIS.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting F", MeetingRooms.MADRID.getCity(), MeetingRooms.MADRID.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting G", MeetingRooms.MILAN.getCity(), MeetingRooms.MILAN.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting H", MeetingRooms.PARIS.getCity(), MeetingRooms.PARIS.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting I", MeetingRooms.PRAGUE.getCity(), MeetingRooms.PRAGUE.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting I", MeetingRooms.VIENNA.getCity(), MeetingRooms.VIENNA.getUrl(), LocalDate.of(2022, 01, 30), LocalTime.of(14, 00), LocalTime.of(14, 45),"Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}