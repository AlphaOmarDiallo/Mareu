package com.alphaomardiallo.mareu.service;

import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Meeting A",MeetingRooms.PARIS.getCity(), MeetingRooms.PARIS.getDrawable(), MeetingRooms.PARIS.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting B",MeetingRooms.LONDON.getCity(), MeetingRooms.LONDON.getDrawable(), MeetingRooms.LONDON.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting C",MeetingRooms.BERLIN.getCity(), MeetingRooms.BERLIN.getDrawable(),MeetingRooms.BERLIN.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting D",MeetingRooms.BRUSSELS.getCity(), MeetingRooms.BRUSSELS.getDrawable(), MeetingRooms.BRUSSELS.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting E",MeetingRooms.BUCHAREST.getCity(), MeetingRooms.BUCHAREST.getDrawable(), MeetingRooms.BUCHAREST.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting F",MeetingRooms.VIENNA.getCity(), MeetingRooms.VIENNA.getDrawable(), MeetingRooms.VIENNA.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting G",MeetingRooms.AMSTERDAM.getCity(), MeetingRooms.AMSTERDAM.getDrawable(), MeetingRooms.AMSTERDAM.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting H",MeetingRooms.MILAN.getCity(), MeetingRooms.MILAN.getDrawable(), MeetingRooms.MILAN.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting I",MeetingRooms.PRAGUE.getCity(), MeetingRooms.PRAGUE.getDrawable(), MeetingRooms.PRAGUE.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"),
            new Meeting("Meeting J",MeetingRooms.MADRID.getCity(), MeetingRooms.MADRID.getDrawable(), MeetingRooms.MADRID.getUrl(),1350000, 45000, 47500, "Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
