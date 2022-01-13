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

    private static int MEETING_TO_TEST;

    private List<Meeting> meetingList;

    @Before
    public void setup() {
        MeetingApiService service = DI.getMeetingsApiService();
        meetingList = service.getMeetings();
        MEETING_TO_TEST = 0;
    }

    @Test
    public void testA_getMeetingsWithSuccess() {
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        long meetingsSize = meetingList.size();
        long expectedMeetingSize = expectedMeetings.size();
        MatcherAssert.assertThat(meetingList, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
        assertEquals(meetingsSize, expectedMeetingSize);
    }

    @Test
    public void testB_deleteMeetingWithSuccess() {
        long meetingsSizeBefore = meetingList.size();
        meetingList.remove(MEETING_TO_TEST);
        long meetingSizeAfter = meetingList.size();
        assertNotEquals(meetingsSizeBefore, meetingSizeAfter);
        assertFalse(meetingList.contains(MEETING_TO_TEST));
    }

    @Test
    public void testC_filterMeetingByMeetingRoomWithSuccess() {
        String filterRoom = MeetingRooms.AMSTERDAM.getCity();
        List<Meeting> expectedMeetings = meetingList.stream()
                .filter(meeting -> meeting.getMeetingRoomName().equalsIgnoreCase(filterRoom))
                .collect(Collectors.toList());
        boolean containsAll = true;
        for (Meeting meeting : expectedMeetings) {
            if (!meeting.getMeetingRoomName().equalsIgnoreCase(filterRoom)) {
                containsAll = false;
                break;
            }
        }
        assertTrue(containsAll);
    }

    @Test
    public void testD_filterMeetingsByDateWithSuccess() {
        String filterDate = "01/01/2022";
        meetingList.add(new Meeting("Meeting I", MeetingRooms.VIENNA.getCity(), MeetingRooms.VIENNA.getUrl(), filterDate, "14:00","14:45","Marketing plan 2022", "john.doe@lamzone.com, jane.doe@lamzone.com, alpha.diallo@lamzone.com"));
        List<Meeting> expectedMeetings = meetingList.stream()
                .filter(meeting -> meeting.getMeetingDate().equalsIgnoreCase(filterDate))
                .collect(Collectors.toList());
        boolean containsAll = true;
        for (Meeting meeting : expectedMeetings) {
            if (!meeting.getMeetingDate().equalsIgnoreCase(filterDate)) {
                containsAll = false;
                break;
            }
        }
        assertTrue(containsAll);
    }
}