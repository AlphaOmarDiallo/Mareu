package com.alphaomardiallo.mareu.service;

import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Meeting A", MeetingRooms.PARIS.getCity(), MeetingRooms.PARIS.getDrawable(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting B",MeetingRooms.LONDON.getCity(), MeetingRooms.LONDON.getDrawable(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting C",MeetingRooms.BERLIN.getCity(), MeetingRooms.BERLIN.getDrawable(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting D",MeetingRooms.BRUSSELS.getCity(), MeetingRooms.BRUSSELS.getDrawable(), 1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting E",MeetingRooms.BUCHAREST.getCity(), MeetingRooms.BUCHAREST.getDrawable(), 1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting F",MeetingRooms.VIENNA.getCity(), MeetingRooms.VIENNA.getDrawable(), 1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting G",MeetingRooms.AMSTERDAM.getCity(), MeetingRooms.AMSTERDAM.getDrawable(), 1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting H",MeetingRooms.MILAN.getCity(), MeetingRooms.MILAN.getDrawable(), 1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting I",MeetingRooms.PRAGUE.getCity(), MeetingRooms.PRAGUE.getDrawable(), 1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting J",MeetingRooms.MADRID.getCity(), MeetingRooms.MADRID.getDrawable(), 1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
