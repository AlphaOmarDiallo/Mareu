package com.alphaomardiallo.mareu.controller;

import static org.junit.Assert.*;

import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.service.DummyMeetingGenerator;
import com.alphaomardiallo.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class MainActivityTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingsApiService();
    }

    @Test
    public void getMeetingsWithSuccess(){
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        int meetingToDelete = 0;
        long meetingsSizeBefore = meetings.size();
        meetings.remove(meetingToDelete);
        long meetingSizeAfter = meetings.size();
        assertNotEquals(meetingsSizeBefore, meetingSizeAfter);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }
}