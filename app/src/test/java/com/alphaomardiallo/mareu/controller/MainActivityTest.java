package com.alphaomardiallo.mareu.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;
import com.alphaomardiallo.mareu.service.DummyMeetingGenerator;
import com.alphaomardiallo.mareu.service.MeetingApiService;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Alphanumeric;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;

@OrderWith(Alphanumeric.class)
@RunWith(JUnit4.class)
public class MainActivityTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingsApiService();
    }

    @Test
    public void testA_getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        long meetingsSize = meetings.size();
        long expectedMeetingSize = expectedMeetings.size();
        MatcherAssert.assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
        assertEquals(meetingsSize, expectedMeetingSize);
    }

    @Test
    public void testB_deleteMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        int meetingToDelete = 0;
        long meetingsSizeBefore = meetings.size();
        meetings.remove(meetingToDelete);
        long meetingSizeAfter = meetings.size();
        assertNotEquals(meetingsSizeBefore, meetingSizeAfter);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void testC_filterMeetingByMeetingRoomWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        String filterRoom = MeetingRooms.AMSTERDAM.getCity();
        List<Meeting> expectedMeetings = meetings.stream()
                .filter(meeting -> meeting.getMeetingRoomName().equalsIgnoreCase(filterRoom))
                .collect(Collectors.toList());
        boolean containsAll = true;
        for (Meeting meeting : expectedMeetings) {

            if (!meeting.getMeetingRoomName().equalsIgnoreCase(filterRoom)) {
                containsAll = false;
            }
        }
        assertTrue(containsAll);
    }
}