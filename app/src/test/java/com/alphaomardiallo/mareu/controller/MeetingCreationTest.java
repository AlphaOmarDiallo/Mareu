package com.alphaomardiallo.mareu.controller;

import static org.junit.Assert.assertTrue;

import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Collaborators;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;
import com.alphaomardiallo.mareu.service.MeetingApiService;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Alphanumeric;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@OrderWith(Alphanumeric.class)
@RunWith(JUnit4.class)
public class MeetingCreationTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingsApiService();
    }

    String meetingName = "FakeMeeting";
    String meetingRoomName = MeetingRooms.LONDON.getCity();
    String meetingRoomUrl = MeetingRooms.LONDON.getUrl();
    LocalDate meetingDate = LocalDate.of(2030, 01, 10);
    LocalTime meetingStart = LocalTime.of(12, 00);
    LocalTime meetingEnd = LocalTime.of(14, 00);
    String topic = "test meeting";
    String participatingCollaborators = Collaborators.ALPHADIALLO.getEmail();


    @Test
    public void testA_createNewMeeting() {
        Meeting meetingA = new Meeting(meetingName, meetingRoomName, meetingRoomUrl, meetingDate, meetingStart, meetingEnd, topic, participatingCollaborators);
        List<Meeting> meetings = service.getMeetings();
        meetings.add(meetingA);
        boolean isIn = false;
        for (Meeting meeting: meetings) {
            if (meeting == meetingA) {
                isIn = true;
                break;
            }
        }
        assertTrue(isIn);
    }

}